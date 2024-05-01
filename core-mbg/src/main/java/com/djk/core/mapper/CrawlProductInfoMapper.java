package com.djk.core.mapper;

import com.djk.core.model.CrawlProductInfo;
import com.djk.core.model.CrawlProductInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CrawlProductInfoMapper {
    long countByExample(CrawlProductInfoExample example);

    int deleteByExample(CrawlProductInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CrawlProductInfo record);

    int insertSelective(CrawlProductInfo record);

    List<CrawlProductInfo> selectByExample(CrawlProductInfoExample example);

    CrawlProductInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CrawlProductInfo record, @Param("example") CrawlProductInfoExample example);

    int updateByExample(@Param("record") CrawlProductInfo record, @Param("example") CrawlProductInfoExample example);

    int updateByPrimaryKeySelective(CrawlProductInfo record);

    int updateByPrimaryKey(CrawlProductInfo record);

    void batchInsert(@Param("items") List<CrawlProductInfo> items);
}