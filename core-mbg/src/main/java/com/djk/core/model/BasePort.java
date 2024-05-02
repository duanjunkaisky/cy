package com.djk.core.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class BasePort implements Serializable {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "港口英文名称")
    private String portNameEn;

    @ApiModelProperty(value = "港口中文名称")
    private String portNameZh;

    @ApiModelProperty(value = "港口代码")
    private String portCode;

    @ApiModelProperty(value = "国家英文名称")
    private String countryNameEn;

    @ApiModelProperty(value = "国家中文名称")
    private String countryNameZh;

    @ApiModelProperty(value = "国家代码")
    private String countryCode;

    @ApiModelProperty(value = "省份中文名称")
    private String provinceZh;

    @ApiModelProperty(value = "省份英文名称")
    private String provinceEn;

    @ApiModelProperty(value = "州/邦中文名称")
    private String stateZh;

    @ApiModelProperty(value = "州/邦英文名称")
    private String stateEn;

    @ApiModelProperty(value = "城市中文名称")
    private String cityZh;

    @ApiModelProperty(value = "城市英文名称")
    private String cityEn;

    @ApiModelProperty(value = "港口类型 字典配置")
    private Integer portType;

    @ApiModelProperty(value = "地区 route表id")
    private Long region;

    @ApiModelProperty(value = "区域 route表id")
    private Long area;

    @ApiModelProperty(value = "状态（0正常 1停用）")
    private Byte status;

    @ApiModelProperty(value = "备注")
    private String notes;

    @ApiModelProperty(value = "COSCO代码")
    private String coscoCode;

    @ApiModelProperty(value = "OOCL代码")
    private String ooclCode;

    @ApiModelProperty(value = "EMC代码")
    private String emcCode;

    @ApiModelProperty(value = "MSK代码")
    private String mskCode;

    @ApiModelProperty(value = "MCC代码")
    private String mccCode;

    @ApiModelProperty(value = "ONE代码")
    private String oneCode;

    @ApiModelProperty(value = "MSC代码")
    private String mscCode;

    @ApiModelProperty(value = "ZIM代码")
    private String zimCode;

    @ApiModelProperty(value = "HMM代码")
    private String hmmCode;

    @ApiModelProperty(value = "CMA代码")
    private String cmaCode;

    @ApiModelProperty(value = "CNC代码")
    private String cncCode;

    @ApiModelProperty(value = "HPL代码")
    private String hplCode;

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

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPortNameEn() {
        return portNameEn;
    }

    public void setPortNameEn(String portNameEn) {
        this.portNameEn = portNameEn;
    }

    public String getPortNameZh() {
        return portNameZh;
    }

    public void setPortNameZh(String portNameZh) {
        this.portNameZh = portNameZh;
    }

    public String getPortCode() {
        return portCode;
    }

    public void setPortCode(String portCode) {
        this.portCode = portCode;
    }

    public String getCountryNameEn() {
        return countryNameEn;
    }

    public void setCountryNameEn(String countryNameEn) {
        this.countryNameEn = countryNameEn;
    }

    public String getCountryNameZh() {
        return countryNameZh;
    }

    public void setCountryNameZh(String countryNameZh) {
        this.countryNameZh = countryNameZh;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getProvinceZh() {
        return provinceZh;
    }

    public void setProvinceZh(String provinceZh) {
        this.provinceZh = provinceZh;
    }

    public String getProvinceEn() {
        return provinceEn;
    }

    public void setProvinceEn(String provinceEn) {
        this.provinceEn = provinceEn;
    }

    public String getStateZh() {
        return stateZh;
    }

    public void setStateZh(String stateZh) {
        this.stateZh = stateZh;
    }

    public String getStateEn() {
        return stateEn;
    }

    public void setStateEn(String stateEn) {
        this.stateEn = stateEn;
    }

    public String getCityZh() {
        return cityZh;
    }

    public void setCityZh(String cityZh) {
        this.cityZh = cityZh;
    }

    public String getCityEn() {
        return cityEn;
    }

    public void setCityEn(String cityEn) {
        this.cityEn = cityEn;
    }

    public Integer getPortType() {
        return portType;
    }

    public void setPortType(Integer portType) {
        this.portType = portType;
    }

    public Long getRegion() {
        return region;
    }

    public void setRegion(Long region) {
        this.region = region;
    }

    public Long getArea() {
        return area;
    }

    public void setArea(Long area) {
        this.area = area;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCoscoCode() {
        return coscoCode;
    }

    public void setCoscoCode(String coscoCode) {
        this.coscoCode = coscoCode;
    }

    public String getOoclCode() {
        return ooclCode;
    }

    public void setOoclCode(String ooclCode) {
        this.ooclCode = ooclCode;
    }

    public String getEmcCode() {
        return emcCode;
    }

    public void setEmcCode(String emcCode) {
        this.emcCode = emcCode;
    }

    public String getMskCode() {
        return mskCode;
    }

    public void setMskCode(String mskCode) {
        this.mskCode = mskCode;
    }

    public String getMccCode() {
        return mccCode;
    }

    public void setMccCode(String mccCode) {
        this.mccCode = mccCode;
    }

    public String getOneCode() {
        return oneCode;
    }

    public void setOneCode(String oneCode) {
        this.oneCode = oneCode;
    }

    public String getMscCode() {
        return mscCode;
    }

    public void setMscCode(String mscCode) {
        this.mscCode = mscCode;
    }

    public String getZimCode() {
        return zimCode;
    }

    public void setZimCode(String zimCode) {
        this.zimCode = zimCode;
    }

    public String getHmmCode() {
        return hmmCode;
    }

    public void setHmmCode(String hmmCode) {
        this.hmmCode = hmmCode;
    }

    public String getCmaCode() {
        return cmaCode;
    }

    public void setCmaCode(String cmaCode) {
        this.cmaCode = cmaCode;
    }

    public String getCncCode() {
        return cncCode;
    }

    public void setCncCode(String cncCode) {
        this.cncCode = cncCode;
    }

    public String getHplCode() {
        return hplCode;
    }

    public void setHplCode(String hplCode) {
        this.hplCode = hplCode;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", portNameEn=").append(portNameEn);
        sb.append(", portNameZh=").append(portNameZh);
        sb.append(", portCode=").append(portCode);
        sb.append(", countryNameEn=").append(countryNameEn);
        sb.append(", countryNameZh=").append(countryNameZh);
        sb.append(", countryCode=").append(countryCode);
        sb.append(", provinceZh=").append(provinceZh);
        sb.append(", provinceEn=").append(provinceEn);
        sb.append(", stateZh=").append(stateZh);
        sb.append(", stateEn=").append(stateEn);
        sb.append(", cityZh=").append(cityZh);
        sb.append(", cityEn=").append(cityEn);
        sb.append(", portType=").append(portType);
        sb.append(", region=").append(region);
        sb.append(", area=").append(area);
        sb.append(", status=").append(status);
        sb.append(", notes=").append(notes);
        sb.append(", coscoCode=").append(coscoCode);
        sb.append(", ooclCode=").append(ooclCode);
        sb.append(", emcCode=").append(emcCode);
        sb.append(", mskCode=").append(mskCode);
        sb.append(", mccCode=").append(mccCode);
        sb.append(", oneCode=").append(oneCode);
        sb.append(", mscCode=").append(mscCode);
        sb.append(", zimCode=").append(zimCode);
        sb.append(", hmmCode=").append(hmmCode);
        sb.append(", cmaCode=").append(cmaCode);
        sb.append(", cncCode=").append(cncCode);
        sb.append(", hplCode=").append(hplCode);
        sb.append(", creator=").append(creator);
        sb.append(", createTime=").append(createTime);
        sb.append(", updater=").append(updater);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", deleted=").append(deleted);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", deletedBy=").append(deletedBy);
        sb.append(", deletedTime=").append(deletedTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}