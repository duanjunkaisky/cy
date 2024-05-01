package com.djk.core.mapper;

import com.djk.core.model.CrawlRequestStatus;
import com.djk.core.model.CrawlRequestStatusExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CrawlRequestStatusMapper {
    long countByExample(CrawlRequestStatusExample example);

    int deleteByExample(CrawlRequestStatusExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CrawlRequestStatus record);

    int insertSelective(CrawlRequestStatus record);

    List<CrawlRequestStatus> selectByExample(CrawlRequestStatusExample example);

    CrawlRequestStatus selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CrawlRequestStatus record, @Param("example") CrawlRequestStatusExample example);

    int updateByExample(@Param("record") CrawlRequestStatus record, @Param("example") CrawlRequestStatusExample example);

    int updateByPrimaryKeySelective(CrawlRequestStatus record);

    int updateByPrimaryKey(CrawlRequestStatus record);

    void batchInsert(@Param("items") List<CrawlRequestStatus> items);
}