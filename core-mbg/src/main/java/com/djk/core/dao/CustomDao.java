package com.djk.core.dao;

import org.apache.ibatis.annotations.Param;

/**
 * @author duanjunkai
 * @date 2024/05/02
 */
public interface CustomDao
{
    /**
     * 执行sql语句
     *
     * @param sql
     */
    void executeSql(@Param("sql") String sql);
}
