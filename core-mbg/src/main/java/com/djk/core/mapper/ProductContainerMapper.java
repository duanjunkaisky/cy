package com.djk.core.mapper;

import com.djk.core.model.ProductContainer;
import com.djk.core.model.ProductContainerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductContainerMapper {
    long countByExample(ProductContainerExample example);

    int deleteByExample(ProductContainerExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProductContainer record);

    int insertSelective(ProductContainer record);

    List<ProductContainer> selectByExample(ProductContainerExample example);

    ProductContainer selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProductContainer record, @Param("example") ProductContainerExample example);

    int updateByExample(@Param("record") ProductContainer record, @Param("example") ProductContainerExample example);

    int updateByPrimaryKeySelective(ProductContainer record);

    int updateByPrimaryKey(ProductContainer record);

    void batchInsert(@Param("items") List<ProductContainer> items);
}