package com.djk.core.service;

import com.djk.core.model.BasePort;
import com.djk.core.model.BasePortExample;
import com.djk.core.vo.QueryRouteVo;

import java.util.List;

/**
 * @author duanjunkai
 * @date 2024/05/01
 */
public interface CrawlService
{

    /**
     * 爬取
     *
     * @param queryRouteVo
     * @param hostCode
     * @return
     * @throws InterruptedException
     */
    void queryData(QueryRouteVo queryRouteVo, String hostCode) throws InterruptedException;
}
