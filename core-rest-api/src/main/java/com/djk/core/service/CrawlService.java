package com.djk.core.service;

import com.djk.core.model.BasePort;
import com.djk.core.model.BaseShippingCompany;
import com.djk.core.vo.QueryRouteVo;

/**
 * @author duanjunkai
 * @date 2024/05/01
 */
public interface CrawlService
{

    String queryData(BaseShippingCompany shippingCompany, BasePort fromPort, BasePort toPort, QueryRouteVo queryRouteVo) throws Exception;

    BasePort getFromPort(QueryRouteVo queryRouteVo);

    BasePort getToPort(QueryRouteVo queryRouteVo);

    BaseShippingCompany getShipCompany(String hostCode);

    void addLog(Boolean addDataId, String businessName, String stepName, String msg, QueryRouteVo queryRouteVo);

    void setTokenIp(QueryRouteVo queryRouteVo);

    void flagDelData(QueryRouteVo queryRouteVo, Long shipId);
}
