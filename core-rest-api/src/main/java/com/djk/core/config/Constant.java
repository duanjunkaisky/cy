package com.djk.core.config;

/**
 * @author duanjunkai
 * @date 2024/05/02
 */
public class Constant
{
    public static final int MAX_REQ_COUNT = 3;

    /**
     * 爬取状态
     */
    public enum CRAWL_STATUS
    {
        WAITING,
        RUNNING,
        SUCCESS,
        ERROR
    }
}
