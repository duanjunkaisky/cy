package com.djk.core.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class ProductInfo implements Serializable {
    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "产品编号")
    private String productNumber;

    @ApiModelProperty(value = "起运港 中文名称")
    private String departurePortZh;

    @ApiModelProperty(value = "起运港 英文名称")
    private String departurePortEn;

    @ApiModelProperty(value = "目的港 中文名称")
    private String destinationPortZh;

    @ApiModelProperty(value = "目的港 英文名称")
    private String destinationPortEn;

    @ApiModelProperty(value = "船公司")
    private Long shippingCompanyId;

    @ApiModelProperty(value = "船公司中文全称")
    private String cnFullName;

    @ApiModelProperty(value = "船公司中文简称")
    private String cnAbbreviation;

    @ApiModelProperty(value = "船司图片")
    private String image;

    @ApiModelProperty(value = "预计开船日期")
    private Date estimatedDepartureDate;

    @ApiModelProperty(value = "路径 字典 1直达 2中转")
    private Integer route;

    @ApiModelProperty(value = "航线")
    private String course;

    @ApiModelProperty(value = "到港时间")
    private Date arrivalTime;

    @ApiModelProperty(value = "产品有效期")
    private Date productExpiryDate;

    @ApiModelProperty(value = "船名")
    private String shipName;

    @ApiModelProperty(value = "船次")
    private String voyageNumber;

    @ApiModelProperty(value = "航程")
    private String distance;

    @ApiModelProperty(value = "货物类型")
    private String cargoType;

    @ApiModelProperty(value = "产品类型 C:自营产品 K:快捷报价 S:供应商产品 P:SPOT产品")
    private String productType;

    @ApiModelProperty(value = "冷冻箱-温度")
    private String reeferTemperature;

    @ApiModelProperty(value = "冷冻箱-湿度")
    private String reeferHumidity;

    @ApiModelProperty(value = "冷冻箱-通风要求")
    private String reeferVentilationRequirements;

    @ApiModelProperty(value = "特种柜-备注")
    private String specialContainerRemarks;

    @ApiModelProperty(value = "危险品-UN.no")
    private String dangerousGoodsUnNumber;

    @ApiModelProperty(value = "危险品-Class")
    private String dangerousGoodsClass;

    @ApiModelProperty(value = "危险品-备注")
    private String dangerousGoodsRemarks;

    @ApiModelProperty(value = "免用箱说明")
    private String waiverOfContainerInstructions;

    @ApiModelProperty(value = "亏舱费说明")
    private String deficitFreightInstructions;

    @ApiModelProperty(value = "客户备注")
    private String customerRemarks;

    @ApiModelProperty(value = "发布人")
    private Long publisher;

    @ApiModelProperty(value = "发布人手机")
    private String publisherPhone;

    @ApiModelProperty(value = "产品负责人")
    private Long productOwner;

    @ApiModelProperty(value = "代理公司")
    private String agentCompany;

    @ApiModelProperty(value = "产品备注")
    private String productRemarks;

    @ApiModelProperty(value = "提交时间")
    private Date submissionTime;

    @ApiModelProperty(value = "审核状态 0已保存，1待审核，2审核不通过，3已上架，4已下架、5已失效")
    private Integer reviewStatus;

    @ApiModelProperty(value = "上架时间")
    private Date shelvesTime;

    @ApiModelProperty(value = "浏览次数")
    private Integer views;

    @ApiModelProperty(value = "业务类型")
    private String businessType;

    @ApiModelProperty(value = "状态（0正常 1停用）")
    private Byte status;

    @ApiModelProperty(value = "审核人")
    private Long approver;

    @ApiModelProperty(value = "审核时间")
    private Date approvalTime;

    @ApiModelProperty(value = "审核意见")
    private String approvalComment;

    @ApiModelProperty(value = "产品标签 10.自营产品 11.秒杀 12.低价 ")
    private String productLabels;

    @ApiModelProperty(value = "爬虫批次")
    private String spotId;

    @ApiModelProperty(value = "爬虫字段唯一标识")
    private String spotPk;

    @ApiModelProperty(value = "数据权限部门字段")
    private Long dpDeptId;

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

    @ApiModelProperty(value = "创建人姓名")
    private String creatorName;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getDeparturePortZh() {
        return departurePortZh;
    }

    public void setDeparturePortZh(String departurePortZh) {
        this.departurePortZh = departurePortZh;
    }

    public String getDeparturePortEn() {
        return departurePortEn;
    }

    public void setDeparturePortEn(String departurePortEn) {
        this.departurePortEn = departurePortEn;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getEstimatedDepartureDate() {
        return estimatedDepartureDate;
    }

    public void setEstimatedDepartureDate(Date estimatedDepartureDate) {
        this.estimatedDepartureDate = estimatedDepartureDate;
    }

    public Integer getRoute() {
        return route;
    }

    public void setRoute(Integer route) {
        this.route = route;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Date getProductExpiryDate() {
        return productExpiryDate;
    }

    public void setProductExpiryDate(Date productExpiryDate) {
        this.productExpiryDate = productExpiryDate;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getVoyageNumber() {
        return voyageNumber;
    }

    public void setVoyageNumber(String voyageNumber) {
        this.voyageNumber = voyageNumber;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getCargoType() {
        return cargoType;
    }

    public void setCargoType(String cargoType) {
        this.cargoType = cargoType;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getReeferTemperature() {
        return reeferTemperature;
    }

    public void setReeferTemperature(String reeferTemperature) {
        this.reeferTemperature = reeferTemperature;
    }

    public String getReeferHumidity() {
        return reeferHumidity;
    }

    public void setReeferHumidity(String reeferHumidity) {
        this.reeferHumidity = reeferHumidity;
    }

    public String getReeferVentilationRequirements() {
        return reeferVentilationRequirements;
    }

    public void setReeferVentilationRequirements(String reeferVentilationRequirements) {
        this.reeferVentilationRequirements = reeferVentilationRequirements;
    }

    public String getSpecialContainerRemarks() {
        return specialContainerRemarks;
    }

    public void setSpecialContainerRemarks(String specialContainerRemarks) {
        this.specialContainerRemarks = specialContainerRemarks;
    }

    public String getDangerousGoodsUnNumber() {
        return dangerousGoodsUnNumber;
    }

    public void setDangerousGoodsUnNumber(String dangerousGoodsUnNumber) {
        this.dangerousGoodsUnNumber = dangerousGoodsUnNumber;
    }

    public String getDangerousGoodsClass() {
        return dangerousGoodsClass;
    }

    public void setDangerousGoodsClass(String dangerousGoodsClass) {
        this.dangerousGoodsClass = dangerousGoodsClass;
    }

    public String getDangerousGoodsRemarks() {
        return dangerousGoodsRemarks;
    }

    public void setDangerousGoodsRemarks(String dangerousGoodsRemarks) {
        this.dangerousGoodsRemarks = dangerousGoodsRemarks;
    }

    public String getWaiverOfContainerInstructions() {
        return waiverOfContainerInstructions;
    }

    public void setWaiverOfContainerInstructions(String waiverOfContainerInstructions) {
        this.waiverOfContainerInstructions = waiverOfContainerInstructions;
    }

    public String getDeficitFreightInstructions() {
        return deficitFreightInstructions;
    }

    public void setDeficitFreightInstructions(String deficitFreightInstructions) {
        this.deficitFreightInstructions = deficitFreightInstructions;
    }

    public String getCustomerRemarks() {
        return customerRemarks;
    }

    public void setCustomerRemarks(String customerRemarks) {
        this.customerRemarks = customerRemarks;
    }

    public Long getPublisher() {
        return publisher;
    }

    public void setPublisher(Long publisher) {
        this.publisher = publisher;
    }

    public String getPublisherPhone() {
        return publisherPhone;
    }

    public void setPublisherPhone(String publisherPhone) {
        this.publisherPhone = publisherPhone;
    }

    public Long getProductOwner() {
        return productOwner;
    }

    public void setProductOwner(Long productOwner) {
        this.productOwner = productOwner;
    }

    public String getAgentCompany() {
        return agentCompany;
    }

    public void setAgentCompany(String agentCompany) {
        this.agentCompany = agentCompany;
    }

    public String getProductRemarks() {
        return productRemarks;
    }

    public void setProductRemarks(String productRemarks) {
        this.productRemarks = productRemarks;
    }

    public Date getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(Date submissionTime) {
        this.submissionTime = submissionTime;
    }

    public Integer getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(Integer reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public Date getShelvesTime() {
        return shelvesTime;
    }

    public void setShelvesTime(Date shelvesTime) {
        this.shelvesTime = shelvesTime;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Long getApprover() {
        return approver;
    }

    public void setApprover(Long approver) {
        this.approver = approver;
    }

    public Date getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(Date approvalTime) {
        this.approvalTime = approvalTime;
    }

    public String getApprovalComment() {
        return approvalComment;
    }

    public void setApprovalComment(String approvalComment) {
        this.approvalComment = approvalComment;
    }

    public String getProductLabels() {
        return productLabels;
    }

    public void setProductLabels(String productLabels) {
        this.productLabels = productLabels;
    }

    public String getSpotId() {
        return spotId;
    }

    public void setSpotId(String spotId) {
        this.spotId = spotId;
    }

    public String getSpotPk() {
        return spotPk;
    }

    public void setSpotPk(String spotPk) {
        this.spotPk = spotPk;
    }

    public Long getDpDeptId() {
        return dpDeptId;
    }

    public void setDpDeptId(Long dpDeptId) {
        this.dpDeptId = dpDeptId;
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

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", productNumber=").append(productNumber);
        sb.append(", departurePortZh=").append(departurePortZh);
        sb.append(", departurePortEn=").append(departurePortEn);
        sb.append(", destinationPortZh=").append(destinationPortZh);
        sb.append(", destinationPortEn=").append(destinationPortEn);
        sb.append(", shippingCompanyId=").append(shippingCompanyId);
        sb.append(", cnFullName=").append(cnFullName);
        sb.append(", cnAbbreviation=").append(cnAbbreviation);
        sb.append(", image=").append(image);
        sb.append(", estimatedDepartureDate=").append(estimatedDepartureDate);
        sb.append(", route=").append(route);
        sb.append(", course=").append(course);
        sb.append(", arrivalTime=").append(arrivalTime);
        sb.append(", productExpiryDate=").append(productExpiryDate);
        sb.append(", shipName=").append(shipName);
        sb.append(", voyageNumber=").append(voyageNumber);
        sb.append(", distance=").append(distance);
        sb.append(", cargoType=").append(cargoType);
        sb.append(", productType=").append(productType);
        sb.append(", reeferTemperature=").append(reeferTemperature);
        sb.append(", reeferHumidity=").append(reeferHumidity);
        sb.append(", reeferVentilationRequirements=").append(reeferVentilationRequirements);
        sb.append(", specialContainerRemarks=").append(specialContainerRemarks);
        sb.append(", dangerousGoodsUnNumber=").append(dangerousGoodsUnNumber);
        sb.append(", dangerousGoodsClass=").append(dangerousGoodsClass);
        sb.append(", dangerousGoodsRemarks=").append(dangerousGoodsRemarks);
        sb.append(", waiverOfContainerInstructions=").append(waiverOfContainerInstructions);
        sb.append(", deficitFreightInstructions=").append(deficitFreightInstructions);
        sb.append(", customerRemarks=").append(customerRemarks);
        sb.append(", publisher=").append(publisher);
        sb.append(", publisherPhone=").append(publisherPhone);
        sb.append(", productOwner=").append(productOwner);
        sb.append(", agentCompany=").append(agentCompany);
        sb.append(", productRemarks=").append(productRemarks);
        sb.append(", submissionTime=").append(submissionTime);
        sb.append(", reviewStatus=").append(reviewStatus);
        sb.append(", shelvesTime=").append(shelvesTime);
        sb.append(", views=").append(views);
        sb.append(", businessType=").append(businessType);
        sb.append(", status=").append(status);
        sb.append(", approver=").append(approver);
        sb.append(", approvalTime=").append(approvalTime);
        sb.append(", approvalComment=").append(approvalComment);
        sb.append(", productLabels=").append(productLabels);
        sb.append(", spotId=").append(spotId);
        sb.append(", spotPk=").append(spotPk);
        sb.append(", dpDeptId=").append(dpDeptId);
        sb.append(", creator=").append(creator);
        sb.append(", createTime=").append(createTime);
        sb.append(", updater=").append(updater);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", deleted=").append(deleted);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", deletedBy=").append(deletedBy);
        sb.append(", deletedTime=").append(deletedTime);
        sb.append(", creatorName=").append(creatorName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}