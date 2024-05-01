package com.djk.core.mapper;

import com.djk.core.model.CrawlProductFeeItem;
import com.djk.core.model.CrawlProductFeeItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CrawlProductFeeItemMapper {
    long countByExample(CrawlProductFeeItemExample example);

    int deleteByExample(CrawlProductFeeItemExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CrawlProductFeeItem record);

    int insertSelective(CrawlProductFeeItem record);

    List<CrawlProductFeeItem> selectByExample(CrawlProductFeeItemExample example);

    CrawlProductFeeItem selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CrawlProductFeeItem record, @Param("example") CrawlProductFeeItemExample example);

    int updateByExample(@Param("record") CrawlProductFeeItem record, @Param("example") CrawlProductFeeItemExample example);

    int updateByPrimaryKeySelective(CrawlProductFeeItem record);

    int updateByPrimaryKey(CrawlProductFeeItem record);

    void batchInsert(@Param("items") List<CrawlProductFeeItem> items);
}