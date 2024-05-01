package com.djk.core.mapper;

import com.djk.core.model.CrawlProductContainer;
import com.djk.core.model.CrawlProductContainerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CrawlProductContainerMapper {
    long countByExample(CrawlProductContainerExample example);

    int deleteByExample(CrawlProductContainerExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CrawlProductContainer record);

    int insertSelective(CrawlProductContainer record);

    List<CrawlProductContainer> selectByExample(CrawlProductContainerExample example);

    CrawlProductContainer selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CrawlProductContainer record, @Param("example") CrawlProductContainerExample example);

    int updateByExample(@Param("record") CrawlProductContainer record, @Param("example") CrawlProductContainerExample example);

    int updateByPrimaryKeySelective(CrawlProductContainer record);

    int updateByPrimaryKey(CrawlProductContainer record);

    void batchInsert(@Param("items") List<CrawlProductContainer> items);
}