package com.djk.core.controller;

import com.alibaba.fastjson.JSON;
import com.djk.core.api.CommonResult;
import com.djk.core.mapper.CrawlMetadataWebsiteConfigMapper;
import com.djk.core.model.CrawlMetadataWebsiteConfig;
import com.djk.core.model.CrawlMetadataWebsiteConfigExample;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author duanjunkai
 * @date 2024/04/24
 */
@Controller
@Slf4j
@Api(tags = "TokenController", description = "token接口")
@RequestMapping("/token")
public class TokenController {
    @Autowired
    CrawlMetadataWebsiteConfigMapper metadataWebsiteConfigMapper;

    /**
     * @param metadataWebsiteConfig
     * @return {@link CommonResult}
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@RequestBody CrawlMetadataWebsiteConfig metadataWebsiteConfig) {
        String deployIp = metadataWebsiteConfig.getDeployIp();
        if (StringUtils.isEmpty(deployIp)) {
            return CommonResult.failed("插件参数[ip]未设置");
        }
        CrawlMetadataWebsiteConfigExample metadataWebsiteConfigExample = new CrawlMetadataWebsiteConfigExample();
        metadataWebsiteConfigExample
                .createCriteria()
                .andHostCodeEqualTo(metadataWebsiteConfig.getHostCode())
                .andDeployIpEqualTo(metadataWebsiteConfig.getDeployIp());
        List<CrawlMetadataWebsiteConfig> crawlMetadataWebsiteConfigs = metadataWebsiteConfigMapper.selectByExample(metadataWebsiteConfigExample);
        if (null == crawlMetadataWebsiteConfigs || crawlMetadataWebsiteConfigs.isEmpty()) {
            return CommonResult.failed("插件参数[ip]未授权,请联系管理员添加ip配置");
        }
        if (crawlMetadataWebsiteConfigs.size() > 1) {
            for (int i = 1; i < crawlMetadataWebsiteConfigs.size(); i++) {
                CrawlMetadataWebsiteConfig crawlMetadataWebsiteConfig = crawlMetadataWebsiteConfigs.get(i);
                Long id = crawlMetadataWebsiteConfig.getId();
                metadataWebsiteConfigMapper.deleteByPrimaryKey(id);
            }
        }

        CrawlMetadataWebsiteConfig updateBean = new CrawlMetadataWebsiteConfig();
        updateBean.setToken(metadataWebsiteConfig.getToken());
        updateBean.setTimePoint(System.currentTimeMillis());
        metadataWebsiteConfigMapper.updateByExampleSelective(updateBean, metadataWebsiteConfigExample);
        return CommonResult.success(JSON.toJSONString(metadataWebsiteConfig));
    }

}
