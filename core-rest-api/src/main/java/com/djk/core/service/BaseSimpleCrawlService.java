package com.djk.core.service;

import com.alibaba.fastjson.JSONObject;
import com.djk.core.dao.CustomDao;
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
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Data
@Slf4j
abstract class BaseSimpleCrawlService implements CrawlService {
    public static final String FROM_FLAG = "from";
    public static final String TO_FLAG = "to";

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

    @Autowired
    CrawlRequestLogMapper logMapper;

    private String hostCode;

    @Autowired
    CustomDao customDao;

    @Value("${redis.database}")
    public String REDIS_DATABASE;

    @Override
    public BasePort getFromPort(QueryRouteVo queryRouteVo) {
        List<BasePort> basePorts = getBasePorts(queryRouteVo, FROM_FLAG);
        return basePorts.get(0);
    }

    @Override
    public BasePort getToPort(QueryRouteVo queryRouteVo) {
        List<BasePort> basePorts = getBasePorts(queryRouteVo, TO_FLAG);
        return basePorts.get(0);
    }

    @Override
    public void addLog(Boolean addDataId, String businessName, String stepName, String msg, QueryRouteVo queryRouteVo) {
        CrawlRequestLog requestLog = new CrawlRequestLog();
        requestLog.setLogId(queryRouteVo.getLogId());
        requestLog.setHostCode(queryRouteVo.getHostCode());
        requestLog.setMsg(msg);
        requestLog.setSpotId(queryRouteVo.getSpotId());
        requestLog.setBusinessName(businessName);
        requestLog.setTimePoint(System.currentTimeMillis());
        if (null != addDataId && addDataId) {
            Long dataId = redisService.generateId(REDIS_DATABASE + ":tmp:log-dataId:" + queryRouteVo.getLogId(), 360L);
            requestLog.setDataId(dataId);
        }
        requestLog.setFromPort(queryRouteVo.getDeparturePortEn());
        requestLog.setToPort(queryRouteVo.getDestinationPortEn());
        requestLog.setStepName(stepName);
        Long aLong = redisService.generateId(REDIS_DATABASE + ":tmp:log-step-num:" + queryRouteVo.getLogId(), 360L);
        requestLog.setStepNum(aLong);
        logMapper.insertSelective(requestLog);
    }

    private List<BasePort> getBasePorts(QueryRouteVo queryRouteVo, String flag) {
        String portCode = queryRouteVo.getDestinationPortEn();
        String countryCode = queryRouteVo.getDestinationCountryCode();
        if (FROM_FLAG.equalsIgnoreCase(flag)) {
            portCode = queryRouteVo.getDeparturePortEn();
            countryCode = queryRouteVo.getDepartureCountryCode();
        }
        BasePortExample basePortExample = new BasePortExample();
        //0正常,1停用
        basePortExample.createCriteria().andPortCodeEqualTo(portCode).andCountryCodeEqualTo(countryCode).andStatusEqualTo((byte) 0);
        List<BasePort> basePorts = basePortMapper.selectByExample(basePortExample);
        if (null == basePorts || basePorts.isEmpty()) {
            throw new RuntimeException("base_port未找到, portCode=" + portCode + ", countryCode=" + countryCode + " 的配置信息");
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

    public String getLogId() {
        final int numLength = 18;
        Long logId = redisService.generateIdCommon("crawl_log_id");
        return "log_uniqueId_" + String.format("%0" + numLength + "d", logId);
    }

    public JSONObject getToken(QueryRouteVo queryRouteVo) {
        CrawlMetadataWebsiteConfigExample crawlMetadataWebsiteConfigExample = new CrawlMetadataWebsiteConfigExample();
        crawlMetadataWebsiteConfigExample.createCriteria().andHostCodeEqualTo(queryRouteVo.getHostCode().toLowerCase()).andDeployIpEqualTo(queryRouteVo.getTokenIp());
        List<CrawlMetadataWebsiteConfig> crawlMetadataWebsiteConfigs = crawlMetadataWebsiteConfigMapper.selectByExampleWithBLOBs(crawlMetadataWebsiteConfigExample);

        CrawlMetadataWebsiteConfig crawlMetadataWebsiteConfig = crawlMetadataWebsiteConfigs.get(0);
        String token = crawlMetadataWebsiteConfig.getToken();
        JSONObject tokenBean = JSONObject.parseObject(token);
        return tokenBean;
    }

    @Override
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

    @Override
    public void flagDelData(QueryRouteVo queryRouteVo, Long shipId) {
        redisService.del(REDIS_DATABASE + ":tmp:wait_del:" + queryRouteVo.getLogId() + ":" + queryRouteVo.getSpotId() + "-" + queryRouteVo.getHostCode() + "-productInfo");
        redisService.del(REDIS_DATABASE + ":tmp:wait_del:" + queryRouteVo.getLogId() + ":" + queryRouteVo.getSpotId() + "-" + queryRouteVo.getHostCode() + "-productContainer");
        redisService.del(REDIS_DATABASE + ":tmp:wait_del:" + queryRouteVo.getLogId() + ":" + queryRouteVo.getSpotId() + "-" + queryRouteVo.getHostCode() + "-productFeeItem");
        List<LinkedHashMap<String, Object>> productInfoIds = new ArrayList<>();
        List<LinkedHashMap<String, Object>> productContainerIds = new ArrayList<>();
        List<LinkedHashMap<String, Object>> productFeeItemIds = new ArrayList<>();
        //一次返回所有箱型的
        if ("cosco".equalsIgnoreCase(queryRouteVo.getHostCode())) {
            List<String> spotList = new ArrayList<>();
            spotList.add(createSpotId(queryRouteVo, "1"));
            spotList.add(createSpotId(queryRouteVo, "2"));
            spotList.add(createSpotId(queryRouteVo, "3"));
            String collect = spotList.stream().map(item -> "'" + item + "'").collect(Collectors.joining(","));

            productInfoIds = customDao.queryBySql("select id from product_info where spot_id in (" + collect + ") and shipping_company_id=" + shipId);
            productContainerIds = customDao.queryBySql("select id from product_container where spot_id in (" + collect + ") and shipping_company_id=" + shipId);
            productFeeItemIds = customDao.queryBySql("select id from product_fee_item where spot_id in (" + collect + ") and shipping_company_id=" + shipId);
        } else {
            productInfoIds = customDao.queryBySql("select id from product_info where spot_id='" + queryRouteVo.getSpotId() + "' and shipping_company_id=" + shipId);
            productContainerIds = customDao.queryBySql("select id from product_container where spot_id='" + queryRouteVo.getSpotId() + "' and shipping_company_id=" + shipId);
            productFeeItemIds = customDao.queryBySql("select id from product_fee_item where spot_id='" + queryRouteVo.getSpotId() + "' and shipping_company_id=" + shipId);
        }
        redisService.set(REDIS_DATABASE + ":tmp:wait_del:" + queryRouteVo.getLogId() + ":" + queryRouteVo.getSpotId() + "-" + queryRouteVo.getHostCode() + "-productInfo", productInfoIds.stream().map(item -> Long.parseLong(String.valueOf(item.get("id")))).collect(Collectors.toList()), 600L);
        redisService.set(REDIS_DATABASE + ":tmp:wait_del:" + queryRouteVo.getLogId() + ":" + queryRouteVo.getSpotId() + "-" + queryRouteVo.getHostCode() + "-productContainer", productContainerIds.stream().map(item -> Long.parseLong(String.valueOf(item.get("id")))).collect(Collectors.toList()), 600L);
        redisService.set(REDIS_DATABASE + ":tmp:wait_del:" + queryRouteVo.getLogId() + ":" + queryRouteVo.getSpotId() + "-" + queryRouteVo.getHostCode() + "-productFeeItem", productFeeItemIds.stream().map(item -> Long.parseLong(String.valueOf(item.get("id")))).collect(Collectors.toList()), 600L);
    }

    public void delData(QueryRouteVo queryRouteVo) {
        boolean hasRole = redisTemplate.opsForValue().setIfAbsent(REDIS_DATABASE + ":tmp:wait_del:" + queryRouteVo.getLogId() + ":" + queryRouteVo.getSpotId() + "-" + queryRouteVo.getHostCode(), "1", 600L, TimeUnit.SECONDS);
        if (hasRole) {
            List<Long> productInfoIds = (List<Long>) redisService.get(REDIS_DATABASE + ":tmp:wait_del:" + queryRouteVo.getLogId() + ":" + queryRouteVo.getSpotId() + "-" + queryRouteVo.getHostCode() + "-productInfo");
            List<Long> productContainerIds = (List<Long>) redisService.get(REDIS_DATABASE + ":tmp:wait_del:" + queryRouteVo.getLogId() + ":" + queryRouteVo.getSpotId() + "-" + queryRouteVo.getHostCode() + "-productContainer");
            List<Long> productFeeItemIds = (List<Long>) redisService.get(REDIS_DATABASE + ":tmp:wait_del:" + queryRouteVo.getLogId() + ":" + queryRouteVo.getSpotId() + "-" + queryRouteVo.getHostCode() + "-productFeeItem");
            if (null != productInfoIds && !productInfoIds.isEmpty()) {
                customDao.executeSql("delete from product_info where id in (" + productInfoIds.stream().map(item -> String.valueOf(item)).collect(Collectors.joining(",")) + ")");
            }
            if (null != productContainerIds && !productContainerIds.isEmpty()) {
                customDao.executeSql("delete from product_container where id in (" + productContainerIds.stream().map(item -> String.valueOf(item)).collect(Collectors.joining(",")) + ")");
            }
            if (null != productFeeItemIds && !productFeeItemIds.isEmpty()) {
                customDao.executeSql("delete from product_fee_item where id in (" + productFeeItemIds.stream().map(item -> String.valueOf(item)).collect(Collectors.joining(",")) + ")");
            }
        }
    }

    public String createSpotId(QueryRouteVo queryRouteVo, String containerType) {
        String spotIdStr = queryRouteVo.getDeparturePortEn().toUpperCase() + queryRouteVo.getDestinationPortEn().toUpperCase()
                + queryRouteVo.getDepartureCountryCode().toUpperCase() + queryRouteVo.getDestinationCountryCode().toUpperCase() + containerType.toUpperCase();
        return DigestUtils.md5DigestAsHex(spotIdStr.getBytes());
    }

    @Override
    public void setTokenIp(QueryRouteVo queryRouteVo) {
        long start = System.currentTimeMillis();
        Loop:
        //30秒内循环获取有效token
        while (StringUtils.isEmpty(queryRouteVo.getTokenIp()) && System.currentTimeMillis() - start <= 30 * 1000L) {
            CrawlMetadataWebsiteConfigExample crawlMetadataWebsiteConfigExample = new CrawlMetadataWebsiteConfigExample();
            crawlMetadataWebsiteConfigExample.createCriteria().andHostCodeEqualTo(queryRouteVo.getHostCode().toLowerCase());
            List<CrawlMetadataWebsiteConfig> crawlMetadataWebsiteConfigs = crawlMetadataWebsiteConfigMapper.selectByExampleWithBLOBs(crawlMetadataWebsiteConfigExample);
            if (null == crawlMetadataWebsiteConfigs || crawlMetadataWebsiteConfigs.isEmpty()) {
                throw new RuntimeException("数据库中未找到token数据");
            }

            for (int i = 0; i < crawlMetadataWebsiteConfigs.size(); i++) {
                CrawlMetadataWebsiteConfig crawlMetadataWebsiteConfig = crawlMetadataWebsiteConfigs.get(i);
                //token有效时间为距离当前时间30秒
                if (System.currentTimeMillis() - crawlMetadataWebsiteConfig.getTimePoint() < 30 * 1000L) {
                    Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(REDIS_DATABASE + ":tmp:token-busy:" + crawlMetadataWebsiteConfig.getDeployIp(), 1, 300, TimeUnit.SECONDS);
                    if (aBoolean) {
                        redisService.set(REDIS_DATABASE + ":tmp:token-busy:" + queryRouteVo.getLogId(), crawlMetadataWebsiteConfig.getDeployIp(), 300L);
                        queryRouteVo.setTokenIp(crawlMetadataWebsiteConfig.getDeployIp());
                        break Loop;
                    }
                }
            }
            try {
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (StringUtils.isEmpty(queryRouteVo.getTokenIp())) {
            throw new RuntimeException("30秒内未得到token");
        }
    }

    public int parseCurrentCy(String currency) {
        if ("USD".equalsIgnoreCase(currency)) {
            return 2;
        } else if ("CNY".equalsIgnoreCase(currency)) {
            return 1;
        } else if ("EUR".equalsIgnoreCase(currency)) {
            return 3;
        } else if ("THB".equalsIgnoreCase(currency)) {
            return 4;
        }
        throw new RuntimeException(currency + "解析币种出错,无法适配当前的币种信息");
    }

    public String checkPortName(String portName) {
        if (StringUtils.isEmpty(portName)) {
            return "";
        }
        String now = "";
        portName = portName.replaceAll("市", "").replaceAll("Shi", "").replaceAll("shi", "");
        String[] arr = portName.toLowerCase().split(" ");
        for (int i = 0; i < arr.length; i++) {
            String substring = arr[i].substring(0, 1);
            now += substring.toUpperCase() + arr[i].substring(1) + " ";
        }
        return now.trim();
    }

    public String getLogPrefix(String spotId, String hostCode) {
        return spotId + " - " + hostCode + " - ";
    }

    public static void main(String[] args) {
        System.out.println(new Date(1715061600000L));
    }
}
