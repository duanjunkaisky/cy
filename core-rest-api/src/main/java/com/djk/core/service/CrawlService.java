package com.djk.core.service;

import com.djk.core.vo.QueryRouteVo;

/**
 * @author duanjunkai
 * @date 2024/05/01
 */
public interface CrawlService
{
    /**
     * 查询航班信息
     * @param queryRouteVo
     * @return {@link String}
     */
    String queryData(QueryRouteVo queryRouteVo);
}
