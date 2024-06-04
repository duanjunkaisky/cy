package com.djk.core.vo;

import com.alibaba.fastjson.JSONObject;
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
    private String departureCountryCode;
    /**
     * 离港时间,2024-05-02
     */
    private String departureDate;
    /**
     * 到达港口英文名称
     */
    private String destinationPortEn;
    private String destinationCountryCode;

    /**
     * 20GP 40GP 40HC
     * 1,2,3
     */
    private String containerType;

    private String spotId;

    private long startTime;

    private String hostCode;

    private String beanName;

    private String logId;

    private String tokenIp;

    private JSONObject otherData;
}
