package com.djk.core.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProductFeeItem implements Serializable {
    @ApiModelProperty(value = "序号")
    private Long id;

    @ApiModelProperty(value = "产品ID")
    private Long productId;

    @ApiModelProperty(value = "费项id")
    private Long feeId;

    @ApiModelProperty(value = "费项中文名称")
    private String feeCnName;

    @ApiModelProperty(value = "费项英文名称 ，多个逗号分隔")
    private String feeEnName;

    @ApiModelProperty(value = "单位  字典配置（1.箱/2.柜）")
    private Integer feeUnit;

    @ApiModelProperty(value = "箱型 1.20GP 2.40GP 3.40HQ 4.单票")
    private Integer containerType;

    @ApiModelProperty(value = "来源，字典配置（1.船司/2.自建）")
    private Integer feeSource;

    @ApiModelProperty(value = "费用类型 1海运费，2附加费，3其他费用，4亏仓费，5目的港费用，6免用箱费")
    private Integer feeCostType;

    @ApiModelProperty(value = "币种 字典配置 1人民币/2美元/3欧元")
    private Integer feeCurrency;

    @ApiModelProperty(value = "单价")
    private BigDecimal price;

    @ApiModelProperty(value = "爬虫字段唯一标识")
    private String spotId;

    @ApiModelProperty(value = "船公司id")
    private Long shippingCompanyId;

    @ApiModelProperty(value = "船公司中文全称")
    private String cnFullName;

    @ApiModelProperty(value = "船公司中文简称")
    private String cnAbbreviation;

    @ApiModelProperty(value = "起运港 英文名称")
    private String departurePortEn;

    @ApiModelProperty(value = "起运港 中文名称")
    private String departurePortZh;

    @ApiModelProperty(value = "目的港 中文名称")
    private String destinationPortZh;

    @ApiModelProperty(value = "目的港 英文名称")
    private String destinationPortEn;

    @ApiModelProperty(value = "产品类型 C:自营产品 K:快捷报价 S:供应商产品 P:SPOT产品")
    private String productType;

    @ApiModelProperty(value = "创建者")
    private String creator;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    private String updater;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "是否删除")
    private Boolean deleted;

    @ApiModelProperty(value = "租户编号")
    private Long tenantId;

    @ApiModelProperty(value = "删除人")
    private String deletedBy;

    @ApiModelProperty(value = "删除时间")
    private Date deletedTime;

    @ApiModelProperty(value = "单价计算方式：0:有多个箱子时需要乘以个数,1:固定单价,与箱子个数无关")
    private Integer priceComputeType;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getFeeId() {
        return feeId;
    }

    public void setFeeId(Long feeId) {
        this.feeId = feeId;
    }

    public String getFeeCnName() {
        return feeCnName;
    }

    public void setFeeCnName(String feeCnName) {
        this.feeCnName = feeCnName;
    }

    public String getFeeEnName() {
        return feeEnName;
    }

    public void setFeeEnName(String feeEnName) {
        this.feeEnName = feeEnName;
    }

    public Integer getFeeUnit() {
        return feeUnit;
    }

    public void setFeeUnit(Integer feeUnit) {
        this.feeUnit = feeUnit;
    }

    public Integer getContainerType() {
        return containerType;
    }

    public void setContainerType(Integer containerType) {
        this.containerType = containerType;
    }

    public Integer getFeeSource() {
        return feeSource;
    }

    public void setFeeSource(Integer feeSource) {
        this.feeSource = feeSource;
    }

    public Integer getFeeCostType() {
        return feeCostType;
    }

    public void setFeeCostType(Integer feeCostType) {
        this.feeCostType = feeCostType;
    }

    public Integer getFeeCurrency() {
        return feeCurrency;
    }

    public void setFeeCurrency(Integer feeCurrency) {
        this.feeCurrency = feeCurrency;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSpotId() {
        return spotId;
    }

    public void setSpotId(String spotId) {
        this.spotId = spotId;
    }

    public Long getShippingCompanyId() {
        return shippingCompanyId;
    }

    public void setShippingCompanyId(Long shippingCompanyId) {
        this.shippingCompanyId = shippingCompanyId;
    }

    public String getCnFullName() {
        return cnFullName;
    }

    public void setCnFullName(String cnFullName) {
        this.cnFullName = cnFullName;
    }

    public String getCnAbbreviation() {
        return cnAbbreviation;
    }

    public void setCnAbbreviation(String cnAbbreviation) {
        this.cnAbbreviation = cnAbbreviation;
    }

    public String getDeparturePortEn() {
        return departurePortEn;
    }

    public void setDeparturePortEn(String departurePortEn) {
        this.departurePortEn = departurePortEn;
    }

    public String getDeparturePortZh() {
        return departurePortZh;
    }

    public void setDeparturePortZh(String departurePortZh) {
        this.departurePortZh = departurePortZh;
    }

    public String getDestinationPortZh() {
        return destinationPortZh;
    }

    public void setDestinationPortZh(String destinationPortZh) {
        this.destinationPortZh = destinationPortZh;
    }

    public String getDestinationPortEn() {
        return destinationPortEn;
    }

    public void setDestinationPortEn(String destinationPortEn) {
        this.destinationPortEn = destinationPortEn;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Date getDeletedTime() {
        return deletedTime;
    }

    public void setDeletedTime(Date deletedTime) {
        this.deletedTime = deletedTime;
    }

    public Integer getPriceComputeType() {
        return priceComputeType;
    }

    public void setPriceComputeType(Integer priceComputeType) {
        this.priceComputeType = priceComputeType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", productId=").append(productId);
        sb.append(", feeId=").append(feeId);
        sb.append(", feeCnName=").append(feeCnName);
        sb.append(", feeEnName=").append(feeEnName);
        sb.append(", feeUnit=").append(feeUnit);
        sb.append(", containerType=").append(containerType);
        sb.append(", feeSource=").append(feeSource);
        sb.append(", feeCostType=").append(feeCostType);
        sb.append(", feeCurrency=").append(feeCurrency);
        sb.append(", price=").append(price);
        sb.append(", spotId=").append(spotId);
        sb.append(", shippingCompanyId=").append(shippingCompanyId);
        sb.append(", cnFullName=").append(cnFullName);
        sb.append(", cnAbbreviation=").append(cnAbbreviation);
        sb.append(", departurePortEn=").append(departurePortEn);
        sb.append(", departurePortZh=").append(departurePortZh);
        sb.append(", destinationPortZh=").append(destinationPortZh);
        sb.append(", destinationPortEn=").append(destinationPortEn);
        sb.append(", productType=").append(productType);
        sb.append(", creator=").append(creator);
        sb.append(", createTime=").append(createTime);
        sb.append(", updater=").append(updater);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", deleted=").append(deleted);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", deletedBy=").append(deletedBy);
        sb.append(", deletedTime=").append(deletedTime);
        sb.append(", priceComputeType=").append(priceComputeType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}