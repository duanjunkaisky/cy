package com.djk.core.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class CrawlRequestLog implements Serializable {
    private Long id;

    @ApiModelProperty(value = "请求唯一标识")
    private String logId;

    @ApiModelProperty(value = "关联crawl_request_status")
    private String spotId;

    @ApiModelProperty(value = "数据ID，第一条数据，第二条数据...")
    private Long dataId;

    @ApiModelProperty(value = "业务名称:爬虫、抢单")
    private String businessName;

    @ApiModelProperty(value = "步骤顺序编号")
    private Long stepNum;

    @ApiModelProperty(value = "步骤名称")
    private String stepName;

    @ApiModelProperty(value = "当前步骤执行的时间点")
    private Long currentTime;

    @ApiModelProperty(value = "出发港口代码")
    private String fromPort;

    @ApiModelProperty(value = "到达港口代码")
    private String toPort;

    @ApiModelProperty(value = "开航日期")
    private String departureDate;

    @ApiModelProperty(value = "网站简码")
    private String hostCode;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "最后更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "msg")
    private String msg;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getSpotId() {
        return spotId;
    }

    public void setSpotId(String spotId) {
        this.spotId = spotId;
    }

    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Long getStepNum() {
        return stepNum;
    }

    public void setStepNum(Long stepNum) {
        this.stepNum = stepNum;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public Long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Long currentTime) {
        this.currentTime = currentTime;
    }

    public String getFromPort() {
        return fromPort;
    }

    public void setFromPort(String fromPort) {
        this.fromPort = fromPort;
    }

    public String getToPort() {
        return toPort;
    }

    public void setToPort(String toPort) {
        this.toPort = toPort;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getHostCode() {
        return hostCode;
    }

    public void setHostCode(String hostCode) {
        this.hostCode = hostCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", logId=").append(logId);
        sb.append(", spotId=").append(spotId);
        sb.append(", dataId=").append(dataId);
        sb.append(", businessName=").append(businessName);
        sb.append(", stepNum=").append(stepNum);
        sb.append(", stepName=").append(stepName);
        sb.append(", currentTime=").append(currentTime);
        sb.append(", fromPort=").append(fromPort);
        sb.append(", toPort=").append(toPort);
        sb.append(", departureDate=").append(departureDate);
        sb.append(", hostCode=").append(hostCode);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", msg=").append(msg);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}