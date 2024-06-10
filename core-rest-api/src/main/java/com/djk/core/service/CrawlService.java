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

    /**
     * 爬取数据
     *
     * @param shippingCompany
     * @param fromPort
     * @param toPort
     * @param queryRouteVo
     * @return {@link String}
     * @throws Exception
     */
    String queryData(BaseShippingCompany shippingCompany, BasePort fromPort, BasePort toPort, QueryRouteVo queryRouteVo) throws Exception;

    /**
     * 获取起始港
     *
     * @param queryRouteVo
     * @return {@link BasePort}
     */
    BasePort getFromPort(QueryRouteVo queryRouteVo);

    /**
     * 获取到达港
     *
     * @param queryRouteVo
     * @return {@link BasePort}
     */
    BasePort getToPort(QueryRouteVo queryRouteVo);

    /**
     * 获取船司
     *
     * @param hostCode
     * @return {@link BaseShippingCompany}
     */
    BaseShippingCompany getShipCompany(String hostCode);

    /**
     * 增加爬取日志
     *
     * @param addDataId
     * @param businessName
     * @param stepName
     * @param msg
     * @param queryRouteVo
     */
    void addLog(Boolean addDataId, String businessName, String stepName, String msg, QueryRouteVo queryRouteVo);

    /**
     * 绑定ip
     *
     * @param queryRouteVo
     */
    void setTokenAccount(QueryRouteVo queryRouteVo);
    void returnAccount(QueryRouteVo queryRouteVo);

    /**
     * 标记需要删除的数据
     *
     * @param queryRouteVo
     * @param shipId
     */
    void flagDelData(QueryRouteVo queryRouteVo, Long shipId);

    void delData(QueryRouteVo queryRouteVo);
}
