package com.djk.core.service;

import com.djk.core.vo.QueryRouteVo;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author duanjunkai
 * @date 2024/05/01
 */
@Service
public class CrawlServiceFroMskImpl implements CrawlService
{
    @SneakyThrows
    @Override
    public String queryData(QueryRouteVo queryRouteVo)
    {
        System.out.println("cma");
        TimeUnit.SECONDS.sleep(20L);
        return null;
    }
}
