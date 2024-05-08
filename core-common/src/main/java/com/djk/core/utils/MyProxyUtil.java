package com.djk.core.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

@Slf4j
public class MyProxyUtil {
    public static final String PROXY_USERNAME = "d1335559413";
    public static final String PROXY_PASSWORD = "gl4pk39d";

    public static final String SECRET_ID = "ojta9ox848z7rl48ef0q";
    public static final String SECRET_KEY = "2a6w6614cablpzz5ev6ptu70l8j8oqnp";
    public static final int PER_PROXY_COUNT = 30;
    public static ConcurrentLinkedDeque<String> proxyLIst = new ConcurrentLinkedDeque<>();

    public static void newProxyList() {
        log.info("获取代理ip");
        Map<String, Object> params = new HashMap<>();
        params.put("secret_id", SECRET_ID);
        params.put("secret_key", SECRET_KEY);
        String secretToken = null;
        int count = 1;
        while (count <= 3 && StringUtils.isEmpty(secretToken)) {
            HttpResp resp = HttpUtil.postForm("https://auth.kdlapi.com/api/get_secret_token", null, params, false);
            JSONObject jsonObject = JSONObject.parseObject(resp.getBodyJson());
            JSONObject data = jsonObject.getJSONObject("data");
            if (null != data) {
                secretToken = data.getString("secret_token");
            }
            count++;
        }

        HttpResp httpResp = HttpUtil.get("https://dps.kdlapi.com/api/getdps?format=json&secret_id=" + SECRET_ID + "&num=" + PER_PROXY_COUNT + "&signature=" + secretToken, null, false);
        JSONObject jsonObject = JSONObject.parseObject(httpResp.getBodyJson());
        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("proxy_list");
        jsonArray.stream().forEach(item -> proxyLIst.add(String.valueOf(item)));
    }

    public static String getProxy() {
        String proxy = proxyLIst.poll();
        int count = 1;
        while (StringUtils.isEmpty(proxy) && count <= 3) {
            newProxyList();
            proxy = proxyLIst.poll();
            count++;
        }
        return proxy;
    }

}
