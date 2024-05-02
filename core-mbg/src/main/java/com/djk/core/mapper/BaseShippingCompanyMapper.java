package com.djk.core.mapper;

import com.djk.core.model.BaseShippingCompany;
import com.djk.core.model.BaseShippingCompanyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BaseShippingCompanyMapper {
    long countByExample(BaseShippingCompanyExample example);

    int deleteByExample(BaseShippingCompanyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BaseShippingCompany record);

    int insertSelective(BaseShippingCompany record);

    List<BaseShippingCompany> selectByExample(BaseShippingCompanyExample example);

    BaseShippingCompany selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BaseShippingCompany record, @Param("example") BaseShippingCompanyExample example);

    int updateByExample(@Param("record") BaseShippingCompany record, @Param("example") BaseShippingCompanyExample example);

    int updateByPrimaryKeySelective(BaseShippingCompany record);

    int updateByPrimaryKey(BaseShippingCompany record);

    void batchInsert(@Param("items") List<BaseShippingCompany> items);
}