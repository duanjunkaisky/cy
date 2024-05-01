package com.djk.core.service;

import com.djk.core.mapper.BasePortMapper;
import com.djk.core.model.BasePort;
import com.djk.core.model.BasePortExample;
import com.djk.core.vo.QueryRouteVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

abstract class BaseSimpleCrawlService implements CrawlService
{
    @Autowired
    BasePortMapper basePortMapper;

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
        basePortExample.createCriteria().andPortCodeEqualTo(toCode);
        List<BasePort> basePorts = basePortMapper.selectByExample(basePortExample);
        if (null == basePorts || basePorts.isEmpty()) {
            throw new RuntimeException("base_port未找到 " + toCode + "的配置信息");
        }
        return basePorts;
    }

}
