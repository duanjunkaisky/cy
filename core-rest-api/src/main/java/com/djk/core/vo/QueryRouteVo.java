package com.djk.core.vo;

import lombok.Data;

/**
 * @author duanjunkai
 * @date 2024/05/01
 */
@Data
public class QueryRouteVo
{
    /**
     * 出发港口英文名称
     */
    private String departurePortEn;
    /**
     * 离港时间,2024-05-02
     */
    private String departureDate;
    /**
     * 到达港口英文名称
     */
    private String destinationPortEn;

    private String spotId;

    private long startTime;
}
