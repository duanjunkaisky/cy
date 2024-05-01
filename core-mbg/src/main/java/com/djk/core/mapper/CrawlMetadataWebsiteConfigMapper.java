package com.djk.core.mapper;

import com.djk.core.model.CrawlMetadataWebsiteConfig;
import com.djk.core.model.CrawlMetadataWebsiteConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CrawlMetadataWebsiteConfigMapper {
    long countByExample(CrawlMetadataWebsiteConfigExample example);

    int deleteByExample(CrawlMetadataWebsiteConfigExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CrawlMetadataWebsiteConfig record);

    int insertSelective(CrawlMetadataWebsiteConfig record);

    List<CrawlMetadataWebsiteConfig> selectByExampleWithBLOBs(CrawlMetadataWebsiteConfigExample example);

    List<CrawlMetadataWebsiteConfig> selectByExample(CrawlMetadataWebsiteConfigExample example);

    CrawlMetadataWebsiteConfig selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CrawlMetadataWebsiteConfig record, @Param("example") CrawlMetadataWebsiteConfigExample example);

    int updateByExampleWithBLOBs(@Param("record") CrawlMetadataWebsiteConfig record, @Param("example") CrawlMetadataWebsiteConfigExample example);

    int updateByExample(@Param("record") CrawlMetadataWebsiteConfig record, @Param("example") CrawlMetadataWebsiteConfigExample example);

    int updateByPrimaryKeySelective(CrawlMetadataWebsiteConfig record);

    int updateByPrimaryKeyWithBLOBs(CrawlMetadataWebsiteConfig record);

    int updateByPrimaryKey(CrawlMetadataWebsiteConfig record);

    void batchInsert(@Param("items") List<CrawlMetadataWebsiteConfig> items);
}