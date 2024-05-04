package com.djk.core.mapper;

import com.djk.core.model.ProductFeeItem;
import com.djk.core.model.ProductFeeItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductFeeItemMapper {
    long countByExample(ProductFeeItemExample example);

    int deleteByExample(ProductFeeItemExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProductFeeItem record);

    int insertSelective(ProductFeeItem record);

    List<ProductFeeItem> selectByExample(ProductFeeItemExample example);

    ProductFeeItem selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProductFeeItem record, @Param("example") ProductFeeItemExample example);

    int updateByExample(@Param("record") ProductFeeItem record, @Param("example") ProductFeeItemExample example);

    int updateByPrimaryKeySelective(ProductFeeItem record);

    int updateByPrimaryKey(ProductFeeItem record);

    void batchInsert(@Param("items") List<ProductFeeItem> items);
}