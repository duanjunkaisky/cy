package com.djk.core.service;

import com.djk.core.model.BasePort;
import com.djk.core.model.BasePortExample;
import com.djk.core.vo.QueryRouteVo;

import java.util.List;
import java.util.concurrent.ExecutionException;

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
     * @return xxx
     * @throws InterruptedException
     */
    String queryData(QueryRouteVo queryRouteVo, String hostCode) throws Exception;
}
