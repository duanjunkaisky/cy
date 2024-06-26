package com.djk.core.utils;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class MyProxyUtil
{
    public static final boolean proxyOn = true;
    public static final String PROXY_USERNAME = "d2408575423";
    public static final String PROXY_PASSWORD = "gl4pk39d";

    public static final String SECRET_ID = "oda2ogeyl4chqa237c5d";
    public static final String SECRET_KEY = "giok0a2fpr6srczuj7l594wlboe84um4";
    public static final int PER_PROXY_COUNT = 10;
    public static ConcurrentHashMap<Integer, String> proxyMap = new ConcurrentHashMap<>(10);

    public synchronized static void newProxyList()
    {
        Map<String, Object> params = new HashMap<>();
        params.put("secret_id", SECRET_ID);
        params.put("secret_key", SECRET_KEY);
        String secretToken = null;
        int count = 1;
        while (count <= 3 && StringUtils.isEmpty(secretToken)) {
            HttpResp resp = HttpUtil.postForm("https://auth.kdlapi.com/api/get_secret_token", null, params, null);
            JSONObject jsonObject = JSONObject.parseObject(resp.getBodyJson());
            JSONObject data = jsonObject.getJSONObject("data");
            if (null != data) {
                secretToken = data.getString("secret_token");
            }
            count++;
        }

        try {
            HttpResp httpResp = HttpUtil.get("https://dps.kdlapi.com/api/getdps?format=json&secret_id=" + SECRET_ID + "&num=" + PER_PROXY_COUNT + "&signature=" + secretToken, null, null);
            JSONObject jsonObject = JSONObject.parseObject(httpResp.getBodyJson());
            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("proxy_list");
            for (int i = 0; i < jsonArray.size(); i++) {
                String proxyString = (String) jsonArray.get(i);
                proxyMap.put(i, proxyString);
            }
            log.info("获取代理ip: " + JSONArray.toJSONString(jsonArray));
        } catch (Exception e) {
            log.info("获取代理ip出错", e);
        }

    }

    public static String getProxy()
    {
        String errorMsg = "";
        if (proxyOn) {
            try {
                int count = 1;
                while (proxyMap.isEmpty() && count <= 3) {
                    newProxyList();
                    count++;
                }
                int index = new Random().nextInt(proxyMap.size());
                return proxyMap.get(index);
            } catch (Exception e) {
                log.error(ExceptionUtil.getMessage(e));
                log.error(ExceptionUtil.stacktraceToString(e));
                errorMsg = ExceptionUtil.getMessage(e) + "\n" + ExceptionUtil.stacktraceToString(e);
            }
        }
        throw new RuntimeException("获取代理失败\n" + errorMsg);
    }

    public static String getProxyIp(String proxy)
    {
        if (!StringUtils.isEmpty(proxy) && proxy.split(":").length == 2) {
            String[] split = proxy.split(":");
            return split[0];
        }
        return null;
    }

    public static String getProxyPort(String proxy)
    {
        if (!StringUtils.isEmpty(proxy) && proxy.split(":").length == 2) {
            String[] split = proxy.split(":");
            return split[1];
        }
        return null;
    }

    public static void main(String[] args)
    {
        String proxy = getProxy();
        System.out.println(proxy);
    }

}
