package com.djk.core.utils;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author duanjunkai
 * @date 2024/05/01
 */
public class HttpUtil {

    /**
     * 不带参数的GET请求
     * @param url
     * @return {@link String}
     */
    public static String doGet(String url) {
        return doGet(url, null, null);
    }

    /**
     * @param url
     * @param map
     * @return {@link String}
     */
    public static String doGet(String url, Map<String, Object> map) {
        return doGet(url, map, null);
    }

    /**
     * 带参数的GET请求
     * @param url
     * @param map
     * @param headers
     * @return {@link String} 要返回http状态码以及响应的内容
     */
    public static String doGet(String url, Map<String, Object> map, Map<String, String> headers) {
        try {
            // 1.创建httpclient对象
            CloseableHttpClient httpClient = HttpClients.createDefault();
            // 2.创建httget请求
            // 创建uribuilder 构建Url 及设置参数
            URIBuilder uriBuilder = new URIBuilder(url);
            // 循环遍历参数集合 设置参数
            // 判断如果map不为空
            if (map != null) {
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
                }
            }
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpGet.setHeader(entry.getKey(), entry.getValue());
                }
            }

            // 3.执行请求（发送请求）
            CloseableHttpResponse response = httpClient.execute(httpGet);
            // 4.接收响应内容，进行解析 封装到httpResult
            // 内容有的时候
            Integer code = response.getStatusLine().getStatusCode();
            return EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 不带参数的POST请求
     * @param url
     * @return {@link String}
     */
    public static String doPost(String url) {
        return doPost(url, null);
    }

    /**
     * 不带header的POST请求
     * @param url
     * @param map
     * @return {@link String}
     */
    public static String doPost(String url, Map<String, Object> map) {
        return doPost(url, map, null);
    }

    /**
     * @param url
     * @param map
     * @param headers
     * @return {@link String}
     */
    public static String doPost(String url, Map<String, Object> map, Map<String, String> headers) {

        // 1.创建httpclient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 2.创建httppost请求
        HttpPost httpPost = new HttpPost(url);
        // 3.构建参数的列表
        //判断参数不为空的情况
        try {
            if (map != null) {
                List<NameValuePair> params = new ArrayList<>();
                // 遍历集合 构建参数列表
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                }
                // 4.创建form表单传递参数的实体对象
                //new UrlEncodedFormEntity(params, "utf-8") 解决服务端中文乱码问题
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "utf-8");

                // 设置httpost请求的实体对象
                httpPost.setEntity(entity);
            }
            fillHeader(headers, httpPost);

            // 5.执行请求
            CloseableHttpResponse response = httpClient.execute(httpPost);
            // 6.接收响应 封装到httpResult中
            // 内容有的时候
            Integer code = response.getStatusLine().getStatusCode();
            return EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static void fillHeader(Map<String, String> headers, HttpPost httpPost)
    {
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * @param url
     * @param jsonStr
     * @return {@link String}
     */
    public static String postBody(String url, String jsonStr) {
        return postBody(url, jsonStr, null);
    }

    /**
     * @param url
     * @param jsonStr
     * @param headers
     * @return {@link String}
     */
    public static String postBody(String url, String jsonStr, Map<String, String> headers) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(url);

            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(200000)
                    .setSocketTimeout(200000)
                    .build();
            httppost.setConfig(requestConfig);
            httppost.setEntity(new StringEntity(jsonStr, ContentType.create("application/json", "utf-8")));

            httppost.setHeader("Content-Type", "application/json");
            fillHeader(headers, httppost);

            CloseableHttpResponse response = httpclient.execute(httppost);
            String responseEntityStr = EntityUtils.toString(response.getEntity());
            return responseEntityStr;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
