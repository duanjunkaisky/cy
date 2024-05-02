package com.djk.core.mapper;

import com.djk.core.model.BasePort;
import com.djk.core.model.BasePortExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BasePortMapper {
    long countByExample(BasePortExample example);

    int deleteByExample(BasePortExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BasePort record);

    int insertSelective(BasePort record);

    List<BasePort> selectByExample(BasePortExample example);

    BasePort selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BasePort record, @Param("example") BasePortExample example);

    int updateByExample(@Param("record") BasePort record, @Param("example") BasePortExample example);

    int updateByPrimaryKeySelective(BasePort record);

    int updateByPrimaryKey(BasePort record);

    void batchInsert(@Param("items") List<BasePort> items);
}