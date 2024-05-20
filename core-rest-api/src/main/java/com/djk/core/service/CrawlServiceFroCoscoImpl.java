package com.djk.core.service;

import com.djk.core.vo.QueryRouteVo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author duanjunkai
 * @date 2024/05/01
 */
@Service
@Slf4j
@Data
public class CrawlServiceFroCoscoImpl extends BaseSimpleCrawlService implements CrawlService
{

    @Override
    public String queryData(QueryRouteVo queryRouteVo, String hostCode) throws Exception
    {

        return "0";
    }

}
