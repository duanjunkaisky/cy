package com.djk.core.mapper;

import com.djk.core.model.ProductInfo;
import com.djk.core.model.ProductInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductInfoMapper {
    long countByExample(ProductInfoExample example);

    int deleteByExample(ProductInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProductInfo record);

    int insertSelective(ProductInfo record);

    List<ProductInfo> selectByExample(ProductInfoExample example);

    ProductInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProductInfo record, @Param("example") ProductInfoExample example);

    int updateByExample(@Param("record") ProductInfo record, @Param("example") ProductInfoExample example);

    int updateByPrimaryKeySelective(ProductInfo record);

    int updateByPrimaryKey(ProductInfo record);

    void batchInsert(@Param("items") List<ProductInfo> items);
}