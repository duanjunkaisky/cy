package com.djk.core.service;

import com.alibaba.fastjson.JSONObject;
import com.djk.core.mapper.*;
import com.djk.core.model.*;
import com.djk.core.vo.QueryRouteVo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Slf4j
abstract class BaseSimpleCrawlService implements CrawlService {
    @Autowired
    BasePortMapper basePortMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    BaseShippingCompanyMapper shippingCompanyMapper;

    @Autowired
    CrawlMetadataWebsiteConfigMapper crawlMetadataWebsiteConfigMapper;

    @Autowired
    RedisService redisService;

    @Autowired
    ProductInfoMapper productInfoMapper;

    @Autowired
    ProductContainerMapper productContainerMapper;

    @Autowired
    ProductFeeItemMapper productFeeItemMapper;

    private String hostCode;

    @Value("${redis.database}")
    public String REDIS_DATABASE;

    public BasePort getFromPort(QueryRouteVo queryRouteVo) {
        String formCode = queryRouteVo.getDeparturePortEn();
        List<BasePort> basePorts = getBasePorts(formCode);
        return basePorts.get(0);
    }

    public BasePort getToPort(QueryRouteVo queryRouteVo) {
        String toCode = queryRouteVo.getDestinationPortEn();
        List<BasePort> basePorts = getBasePorts(toCode);
        return basePorts.get(0);
    }

    private List<BasePort> getBasePorts(String toCode) {
        BasePortExample basePortExample = new BasePortExample();
        //0正常,1停用
        basePortExample.createCriteria().andPortCodeEqualTo(toCode).andStatusEqualTo((byte) 0);
        List<BasePort> basePorts = basePortMapper.selectByExample(basePortExample);
        if (null == basePorts || basePorts.isEmpty()) {
            throw new RuntimeException("base_port未找到 " + toCode + "的配置信息");
        }
        return basePorts;
    }

    public String getProductNumber() {
        final int numLength = 6;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMM");
        String start = sdf.format(date);
        String yyMM = (String) redisService.get(REDIS_DATABASE + ":yyMM");
        Long number = redisService.generateIdCommon("product_number");
        if (StringUtils.isEmpty(yyMM) || !yyMM.equalsIgnoreCase(start)) {
            Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(REDIS_DATABASE + ":yyMM", start);
            if (aBoolean) {
                number = 1L;
                redisService.set(REDIS_DATABASE + ":commons:ids:product_number", number);
            }
        }
        return "CGP" + start + String.format("%0" + numLength + "d", number);
    }

    public JSONObject getToken(String hostCode, int tokenIndex) {
        CrawlMetadataWebsiteConfigExample crawlMetadataWebsiteConfigExample = new CrawlMetadataWebsiteConfigExample();
        crawlMetadataWebsiteConfigExample.createCriteria().andHostCodeEqualTo(hostCode.toLowerCase());
        List<CrawlMetadataWebsiteConfig> crawlMetadataWebsiteConfigs = crawlMetadataWebsiteConfigMapper.selectByExampleWithBLOBs(crawlMetadataWebsiteConfigExample);

        if (tokenIndex > crawlMetadataWebsiteConfigs.size() - 1) {
            tokenIndex = 0;
        }
        CrawlMetadataWebsiteConfig crawlMetadataWebsiteConfig = crawlMetadataWebsiteConfigs.get(tokenIndex);
        String token = crawlMetadataWebsiteConfig.getToken();
        JSONObject tokenBean = JSONObject.parseObject(token);
        return tokenBean;
    }

    public BaseShippingCompany getShipCompany(String hostCode) {
        BaseShippingCompanyExample baseShippingCompanyExample = new BaseShippingCompanyExample();
        baseShippingCompanyExample.createCriteria().andEnAbbreviationEqualTo(hostCode.toUpperCase());
        List<BaseShippingCompany> baseShippingCompanies = shippingCompanyMapper.selectByExample(baseShippingCompanyExample);
        if (null == baseShippingCompanies || baseShippingCompanies.isEmpty()) {
            throw new RuntimeException("base_ship_company未配置" + hostCode.toUpperCase() + "信息");
        }
        BaseShippingCompany baseShippingCompany = baseShippingCompanies.get(0);
        return baseShippingCompany;
    }

    public int computeContainerType(String code) {
        if ("22G1".equals(code)) {
            return 1;
        } else if ("42G1".equals(code)) {
            return 2;
        } else if ("45G1".equals(code)) {
            return 3;
        }
        throw new RuntimeException("箱型解析出错");
    }

    public String createSpotId(String departurePortEn, String destinationPortEn) {
        String spotIdStr = departurePortEn.toUpperCase() + destinationPortEn.toUpperCase();
        return DigestUtils.md5DigestAsHex(spotIdStr.getBytes());
    }

    @Transactional(readOnly = true)
    public String insertData(QueryRouteVo queryRouteVo, String hostCode, List<ProductInfo> productInfoList, List<ProductContainer> productContainerList, List<ProductFeeItem> productFeeItemList) {
        log.info(getLogPrefix(queryRouteVo.getSpotId(), hostCode) + " -爬取有效数据数量: " + productInfoList.size());
        if (!productInfoList.isEmpty()) {
            ProductInfo productInfo = productInfoList.get(0);
            String spotId = productInfo.getSpotId();
            ProductInfoExample productInfoExample = new ProductInfoExample();
            productInfoExample.createCriteria().andSpotIdEqualTo(spotId).andShippingCompanyIdEqualTo(productInfo.getShippingCompanyId());
            Map<String, ProductInfo> productNumberPerInfoMap = new HashMap<>(10);
            List<ProductInfo> productInfos = productInfoMapper.selectByExample(productInfoExample);
            for (ProductInfo info : productInfos) {
                String key = info.getDeparturePortEn() + info.getDestinationPortEn() + info.getShippingCompanyId() + info.getEstimatedDepartureDate() + info.getVoyageNumber();
                productNumberPerInfoMap.put(key, info);
            }
            productInfoMapper.deleteByExample(productInfoExample);

            List<Long> productIds = null;
            if (null != productInfos && !productInfos.isEmpty()) {
                productIds = productInfos.stream().map(item -> item.getId()).collect(Collectors.toList());
            }

            if (null != productIds && !productIds.isEmpty()) {
                ProductContainerExample productContainerExample = new ProductContainerExample();
                ProductContainerExample.Criteria criteria = productContainerExample.createCriteria();
                criteria.andSpotIdEqualTo(spotId);
                criteria.andProductIdIn(productIds);
                productContainerMapper.deleteByExample(productContainerExample);

                ProductFeeItemExample productFeeItemExample = new ProductFeeItemExample();
                ProductFeeItemExample.Criteria criteria1 = productFeeItemExample.createCriteria();
                criteria1.andSpotIdEqualTo(spotId);
                criteria1.andProductIdIn(productIds);
                productFeeItemMapper.deleteByExample(productFeeItemExample);
            }

            //相同的航线，productNumber不变
            for (ProductInfo info : productInfoList) {
                String key = info.getDeparturePortEn() + info.getDestinationPortEn() + info.getShippingCompanyId() + info.getEstimatedDepartureDate() + info.getVoyageNumber();
                ProductInfo existInfo = productNumberPerInfoMap.get(key);
                if (null != existInfo) {
                    info.setProductNumber(existInfo.getProductNumber());
                } else {
                    info.setProductNumber(getProductNumber());
                }
            }
            productInfoMapper.batchInsert(productInfoList);
            productContainerMapper.batchInsert(productContainerList);
            productFeeItemMapper.batchInsert(productFeeItemList);

            log.info(getLogPrefix(queryRouteVo.getSpotId(), hostCode) + " - 入库完成");
        }

        return String.valueOf(productInfoList.size());
    }

    public int parseCurrentCy(String currency) {
        if ("USD".equalsIgnoreCase(currency)) {
            return 2;
        } else if ("CNY".equalsIgnoreCase(currency)) {
            return 1;
        } else if ("EUR".equalsIgnoreCase(currency)) {
            return 3;
        }
        throw new RuntimeException("解析币种出错, 不在 USD、CNY、EUR之内");
    }

    public String getLogPrefix(String spotId, String hostCode) {
        return spotId + " - " + hostCode + " - ";
    }

    public static void main(String[] args) {
        System.out.println(new Date(1715061600000L));
    }
}
