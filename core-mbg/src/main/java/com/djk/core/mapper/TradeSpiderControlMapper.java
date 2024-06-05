package com.djk.core.mapper;

import com.djk.core.model.TradeSpiderControl;
import com.djk.core.model.TradeSpiderControlExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradeSpiderControlMapper {
    long countByExample(TradeSpiderControlExample example);

    int deleteByExample(TradeSpiderControlExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TradeSpiderControl record);

    int insertSelective(TradeSpiderControl record);

    List<TradeSpiderControl> selectByExample(TradeSpiderControlExample example);

    TradeSpiderControl selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TradeSpiderControl record, @Param("example") TradeSpiderControlExample example);

    int updateByExample(@Param("record") TradeSpiderControl record, @Param("example") TradeSpiderControlExample example);

    int updateByPrimaryKeySelective(TradeSpiderControl record);

    int updateByPrimaryKey(TradeSpiderControl record);

    void batchInsert(@Param("items") List<TradeSpiderControl> items);
}