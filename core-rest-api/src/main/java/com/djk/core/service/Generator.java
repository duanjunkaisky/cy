package com.djk.core.service;

import com.djk.core.utils.SnowflakeIdWorker;

/**
 * @author duanjunkai
 * @date 2024/05/01
 */
public class Generator
{
    /**
     * 数据中心ID(0~31)
     */
    private final static long decenterId = 1L;

    /**
     * 机器ID(0~31)
     */
    private final static long machineId = 2L;

    public static final SnowflakeIdWorker worker = new SnowflakeIdWorker(machineId, decenterId);

    /**
     * 获取id
     *
     * @return long
     */
    public static long nextId()
    {
        return worker.nextId();
    }

}
