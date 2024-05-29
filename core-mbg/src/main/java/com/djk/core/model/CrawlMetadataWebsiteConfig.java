package com.djk.core.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class CrawlMetadataWebsiteConfig implements Serializable {
    private Long id;

    @ApiModelProperty(value = "需要爬取数据的网站域名")
    private String hostName;

    @ApiModelProperty(value = "网站简码")
    private String hostCode;

    private Long timePoint;

    @ApiModelProperty(value = "token过期时间，单位秒")
    private Long expireTime;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "最后更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "爬虫程序部署的机器ip，该字段用来应对多机器同时爬数")
    private String deployIp;

    @ApiModelProperty(value = "网站登录后的授权码")
    private String token;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostCode() {
        return hostCode;
    }

    public void setHostCode(String hostCode) {
        this.hostCode = hostCode;
    }

    public Long getTimePoint() {
        return timePoint;
    }

    public void setTimePoint(Long timePoint) {
        this.timePoint = timePoint;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
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

    public String getDeployIp() {
        return deployIp;
    }

    public void setDeployIp(String deployIp) {
        this.deployIp = deployIp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", hostName=").append(hostName);
        sb.append(", hostCode=").append(hostCode);
        sb.append(", timePoint=").append(timePoint);
        sb.append(", expireTime=").append(expireTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", deployIp=").append(deployIp);
        sb.append(", token=").append(token);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}