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
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Slf4j
abstract class BaseSimpleCrawlService implements CrawlService
{
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
    CrawlProductInfoMapper productInfoMapper;

    @Autowired
    CrawlProductContainerMapper productContainerMapper;

    @Autowired
    CrawlProductFeeItemMapper productFeeItemMapper;

    private String hostCode;

    @Value("${redis.database}")
    public String REDIS_DATABASE;

    public BasePort getFromPort(QueryRouteVo queryRouteVo)
    {
        String formCode = queryRouteVo.getDeparturePortEn();
        List<BasePort> basePorts = getBasePorts(formCode);
        return basePorts.get(0);
    }

    public BasePort getToPort(QueryRouteVo queryRouteVo)
    {
        String toCode = queryRouteVo.getDestinationPortEn();
        List<BasePort> basePorts = getBasePorts(toCode);
        return basePorts.get(0);
    }

    private List<BasePort> getBasePorts(String toCode)
    {
        BasePortExample basePortExample = new BasePortExample();
        //0正常,1停用
        basePortExample.createCriteria().andPortCodeEqualTo(toCode).andStatusEqualTo((byte) 0);
        List<BasePort> basePorts = basePortMapper.selectByExample(basePortExample);
        if (null == basePorts || basePorts.isEmpty()) {
            throw new RuntimeException("base_port未找到 " + toCode + "的配置信息");
        }
        return basePorts;
    }

    public String getProductNumber()
    {
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

    public JSONObject getToken(String hostCode, int tokenIndex)
    {
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

    public BaseShippingCompany getShipCompany(String hostCode)
    {
        BaseShippingCompanyExample baseShippingCompanyExample = new BaseShippingCompanyExample();
        baseShippingCompanyExample.createCriteria().andEnAbbreviationEqualTo(hostCode.toUpperCase());
        List<BaseShippingCompany> baseShippingCompanies = shippingCompanyMapper.selectByExample(baseShippingCompanyExample);
        if (null == baseShippingCompanies || baseShippingCompanies.isEmpty()) {
            throw new RuntimeException("base_ship_company未配置" + hostCode.toUpperCase() + "信息");
        }
        BaseShippingCompany baseShippingCompany = baseShippingCompanies.get(0);
        return baseShippingCompany;
    }

    public int computeContainerType(String code)
    {
        if ("22G1".equals(code)) {
            return 1;
        } else if ("42G1".equals(code)) {
            return 2;
        } else if ("45G1".equals(code)) {
            return 3;
        }
        throw new RuntimeException("箱型解析出错");
    }

    public String createSpotId(String departurePortEn, String destinationPortEn)
    {
        String spotIdStr = departurePortEn + destinationPortEn;
        return DigestUtils.md5DigestAsHex(spotIdStr.getBytes());
    }

    public void insertData(QueryRouteVo queryRouteVo, String hostCode, List<CrawlProductInfo> productInfoList, List<CrawlProductContainer> productContainerList, List<CrawlProductFeeItem> productFeeItemList)
    {
        log.info(queryRouteVo.getSpotId() + hostCode + " -爬取有效数据数量: " + productInfoList.size());
        if (!productInfoList.isEmpty()) {
            String spotId = productInfoList.get(0).getSpotId();
            CrawlProductInfoExample crawlProductInfoExample = new CrawlProductInfoExample();
            crawlProductInfoExample.createCriteria().andSpotIdEqualTo(spotId);
            Map<String, CrawlProductInfo> productNumberPerInfoMap = new HashMap<>(10);
            List<CrawlProductInfo> productInfos = productInfoMapper.selectByExample(crawlProductInfoExample);
            for (CrawlProductInfo info : productInfos) {
                String key = info.getDeparturePortEn() + info.getDestinationPortEn() + info.getShippingCompanyId() + info.getEstimatedDepartureDate() + info.getVoyageNumber();
                productNumberPerInfoMap.put(key, info);
            }
            productInfoMapper.deleteByExample(crawlProductInfoExample);

            CrawlProductContainerExample crawlProductContainerExample = new CrawlProductContainerExample();
            crawlProductContainerExample.createCriteria().andSpotIdEqualTo(spotId);
            productContainerMapper.deleteByExample(crawlProductContainerExample);

            CrawlProductFeeItemExample crawlProductFeeItemExample = new CrawlProductFeeItemExample();
            crawlProductFeeItemExample.createCriteria().andSpotIdEqualTo(spotId);
            productFeeItemMapper.deleteByExample(crawlProductFeeItemExample);

            //相同的航线，productNumber不变
            for (CrawlProductInfo info : productInfoList) {
                String key = info.getDeparturePortEn() + info.getDestinationPortEn() + info.getShippingCompanyId() + info.getEstimatedDepartureDate() + info.getVoyageNumber();
                CrawlProductInfo productInfo = productNumberPerInfoMap.get(key);
                if (null != productInfo) {
                    info.setProductNumber(productInfo.getProductNumber());
                } else {
                    info.setProductNumber(getProductNumber());
                }
            }
            productInfoMapper.batchInsert(productInfoList);
            productContainerMapper.batchInsert(productContainerList);
            productFeeItemMapper.batchInsert(productFeeItemList);

            log.info(queryRouteVo.getSpotId() + hostCode + " -入库完成");
        }
    }

    public int parseCurrentCy(String currency)
    {
        if ("USD".equalsIgnoreCase(currency)) {
            return 2;
        } else if ("CNY".equalsIgnoreCase(currency)) {
            return 1;
        } else if ("EUR".equalsIgnoreCase(currency)) {
            return 3;
        }
        throw new RuntimeException("解析币种出错, 不在 USD、CNY、EUR之内");
    }

    public String getLogPrefix(String spotId, String hostCode)
    {
        return spotId + " - " + hostCode + " - ";
    }

}
