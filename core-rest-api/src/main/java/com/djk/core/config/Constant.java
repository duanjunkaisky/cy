package com.djk.core.config;

/**
 * @author duanjunkai
 * @date 2024/05/02
 */
public class Constant {
    public static final String BUSINESS_NAME_CRAWL = "爬虫业务";
    public static final int MAX_REQ_COUNT = 5;

    /**
     * 爬取状态
     */
    public enum CRAWL_STATUS {
        WAITING,
        RUNNING,
        SUCCESS,
        ERROR
    }
}
