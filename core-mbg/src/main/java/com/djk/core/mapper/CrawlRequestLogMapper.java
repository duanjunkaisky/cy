package com.djk.core.mapper;

import com.djk.core.model.CrawlRequestLog;
import com.djk.core.model.CrawlRequestLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CrawlRequestLogMapper {
    long countByExample(CrawlRequestLogExample example);

    int deleteByExample(CrawlRequestLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CrawlRequestLog record);

    int insertSelective(CrawlRequestLog record);

    List<CrawlRequestLog> selectByExampleWithBLOBs(CrawlRequestLogExample example);

    List<CrawlRequestLog> selectByExample(CrawlRequestLogExample example);

    CrawlRequestLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CrawlRequestLog record, @Param("example") CrawlRequestLogExample example);

    int updateByExampleWithBLOBs(@Param("record") CrawlRequestLog record, @Param("example") CrawlRequestLogExample example);

    int updateByExample(@Param("record") CrawlRequestLog record, @Param("example") CrawlRequestLogExample example);

    int updateByPrimaryKeySelective(CrawlRequestLog record);

    int updateByPrimaryKeyWithBLOBs(CrawlRequestLog record);

    int updateByPrimaryKey(CrawlRequestLog record);

    void batchInsert(@Param("items") List<CrawlRequestLog> items);
}