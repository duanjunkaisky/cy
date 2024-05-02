package com.djk.core.service;

import com.djk.core.mapper.BasePortMapper;
import com.djk.core.model.BasePort;
import com.djk.core.model.BasePortExample;
import com.djk.core.vo.QueryRouteVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

abstract class BaseSimpleCrawlService implements CrawlService
{
    @Autowired
    BasePortMapper basePortMapper;

    @Autowired
    RedisService redisService;

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
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMM");
        String start = sdf.format(date);
        String yyMM = (String) redisService.get(REDIS_DATABASE + ":yyMM");
        Long number = redisService.generateIdCommon("product_number");
        if (StringUtils.isEmpty(yyMM) || !yyMM.equalsIgnoreCase(start)) {
            redisService.set(REDIS_DATABASE + ":yyMM", start);
            number = 1L;
            redisService.set(REDIS_DATABASE + ":commons:ids:product_number", number);
        }
        return "CGP" + start + String.format("%0" + 6 + "d", number);
    }

}
