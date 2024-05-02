package com.djk.core.dao;

import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

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

    /**
     * 执行查询sql
     *
     * @param sql
     * @return
     */
    List<LinkedHashMap<String, Object>> queryBySql(@Param("sql") String sql);

}
