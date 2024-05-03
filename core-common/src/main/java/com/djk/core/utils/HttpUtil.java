package com.djk.core.utils;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.*;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * HTTP实用程序
 *
 * @author user1
 * @date 2023/01/19
 */
@Slf4j
public class HttpUtil
{

    /**
     * HttpUtil发送请求输出日志级别
     */
    public static HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.NONE;

    /**
     * post请求-表单参数模式
     *
     * @param url     网址
     * @param headers 请求头
     * @param params  参数
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    public static HttpResp postForm(String url, Map<String, String> headers, Map<String, Object> params)
    {
        return post(url, "form", headers, params);
    }


    /**
     * post请求-body-json参数模式
     *
     * @param url     网址
     * @param headers 请求头
     * @param params  参数
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    public static HttpResp postBody(String url, Map<String, String> headers, Map<String, Object> params)
    {
        return post(url, "body", headers, params);
    }

    /**
     * post请求-body-json参数模式
     *
     * @param url        网址
     * @param headers    请求头
     * @param jsonString json参数
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    public static HttpResp postBody(String url, Map<String, String> headers, String jsonString)
    {
        Map<String, Object> params = JSON.parseObject(jsonString, new HashMap<String, Object>().getClass());
        return post(url, "body", headers, params);
    }

    /**
     * post请求-base-auth-ntlm认证
     *
     * @param url      网址
     * @param headers  请求头
     * @param params   参数
     * @param username 用户名
     * @param password 密码
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    public static HttpResp postBodyNTLM(String url, Map<String, String> headers, Map<String, Object> params, String username, String password)
    {
        return post(url, "body", "post", headers, params, null, username, password);
    }

    public static HttpResp postBodyNTLM(String url, Map<String, String> headers, String jsonParams, String username, String password)
    {
        return post(url, "body", "post", headers, null, jsonParams, username, password);
    }

    public static HttpResp patchBodyNTLM(String url, Map<String, String> headers, String jsonParams, String username, String password)
    {
        return post(url, "body", "patch", headers, null, jsonParams, username, password);
    }

    /**
     * get请求-base-auth-ntlm认证
     *
     * @param url      网址
     * @param headers  请求头
     * @param username 用户名
     * @param password 密码
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    public static HttpResp getNTLM(String url, Map<String, String> headers, String username, String password)
    {
        return get(url, headers, username, password);
    }

    public static HttpResp get(String url, Map<String, String> headers)
    {
        return get(url, headers, null, null);
    }

    public static HttpResp get(String url, Map<String, String> headers, String username, String password)
    {
        HttpResp resp = new HttpResp();
        OkHttpClient client = buildClient(url, username, password);

        if (sendRequest(url, "get", false, headers, null, resp, client, null)) {
            return resp;
        }
        return null;
    }

    private static OkHttpClient buildClient(String url, String username, String password, long timeOut)
    {

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
//        builder.connectionSpecs(Collections.singletonList(ConnectionSpec.MODERN_TLS));
        try {
            URL urlObj = new URL(url);
            if (SslTrustUtil.HTTPS_PROTOCOL.equalsIgnoreCase(urlObj.getProtocol())) {
                SSLContext sc = SSLContext.getInstance("TLS");
                sc.init(null, SslTrustUtil.trustAllCerts, new SecureRandom());
                SSLSocketFactory socketFactory = sc.getSocketFactory();
                builder.sslSocketFactory(socketFactory, (X509TrustManager) SslTrustUtil.trustAllCerts[0]);
                builder.hostnameVerifier(new SslTrustUtil.NullHostnameVerifier());
            }
        } catch (Exception e) {
            log.error("创建httpClient出错", e);
        }

        builder.retryOnConnectionFailure(false)
                .callTimeout(10, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .connectTimeout(timeOut, TimeUnit.SECONDS);
//                .writeTimeout(5, TimeUnit.SECONDS);

        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        if (log.isDebugEnabled()) {
            level = HttpLoggingInterceptor.Level.BASIC;
        } else {
            level = HttpLoggingInterceptor.Level.NONE;
        }
        logInterceptor.setLevel(level);
        builder.addInterceptor(logInterceptor);

        if (!StringUtils.isEmpty(username)) {
            builder.authenticator(new NTLMAuthenticator(username, password));
        }

        return builder.build();
    }

    private static OkHttpClient buildClient(String url, String username, String password)
    {
        return buildClient(url, username, password, 120);
    }

    private static HttpResp post(String url, String type, Map<String, String> headers, Map<String, Object> params)
    {
        return post(url, type, "post", headers, params, null, null, null);
    }

    public static HttpResp post(String url, String type, String method, Map<String, String> headers, String jsonString)
    {
        return post(url, type, method, headers, null, jsonString, null, null);
    }

    public static HttpResp post(String url, String type, String method, Map<String, String> headers, Map<String, Object> params)
    {
        return post(url, type, method, headers, params, null, null, null);
    }

    private static HttpResp post(String url, String type, String method, Map<String, String> headers, Map<String, Object> params, String jsonParams, String username, String password)
    {
        HttpResp resp = new HttpResp();
        OkHttpClient client = buildClient(url, username, password);

        String contentType = "application/json; charset=utf-8";
        if (null != headers) {
            String contentTypeTmp = headers.get("Content-Type");
            if (StringUtils.isEmpty(contentTypeTmp)) {
                contentTypeTmp = headers.get("content-type");
            }
            if (!StringUtils.isEmpty(contentTypeTmp)) {
                contentType = contentTypeTmp;
            }
        }
        RequestBody body;
        String jsonString = "";
        if ("body".equals(type)) {
            if (StringUtils.isEmpty(jsonParams)) {
                jsonString = JSONObject.toJSONString(params);
            } else {
                jsonString = jsonParams;
            }
            body = RequestBody.create(MediaType.parse(contentType), jsonString);
        } else {
            final MediaType ct = MediaType.parse(String.valueOf(params.get("fileType")));
            MultipartBody.Builder formBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            if (null != params && params.size() > 0) {
                for (Map.Entry<String, Object> item : params.entrySet()) {
                    String key = item.getKey();
                    if (!key.toUpperCase().contains("FILE")) {
                        formBuilder.addFormDataPart(item.getKey(), String.valueOf(item.getValue()));
                    } else if (key.toUpperCase().equals("FILE")) {
                        InputStream inputStream = (InputStream) item.getValue();
                        RequestBody rb = new RequestBody()
                        {
                            @Override
                            @Nullable
                            public MediaType contentType()
                            {
                                return ct;
                            }

                            @Override
                            public long contentLength()
                            {
                                try {
                                    return inputStream.available();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return 0;
                            }

                            @Override
                            public void writeTo(BufferedSink sink) throws IOException
                            {
                                Source source = Okio.source(inputStream);
                                Throwable var3 = null;

                                try {
                                    sink.writeAll(source);
                                } catch (Throwable var12) {
                                    var3 = var12;
                                    throw var12;
                                } finally {
                                    if (source != null) {
                                        if (var3 != null) {
                                            try {
                                                source.close();
                                            } catch (Throwable var11) {
                                                var3.addSuppressed(var11);
                                            }
                                        } else {
                                            source.close();
                                        }
                                    }
                                    inputStream.close();
                                }

                            }
                        };
                        formBuilder.addFormDataPart("file", String.valueOf(params.get("fileName")), rb);
                    }
                }
                jsonString = JSONObject.toJSONString(params);
            }
            body = formBuilder.build();
        }

        if (sendRequest(url, method, false, headers, jsonString, resp, client, body)) {
            return resp;
        }
        return null;
    }

    private static boolean sendRequest(String url, String method, boolean lazyBody, Map<String, String> headers, String jsonString, HttpResp resp, OkHttpClient client, RequestBody body)
    {
        Request request = getRequest(url, method, headers, body);

        long startTime = System.currentTimeMillis();
        long endTime;
        long useTime;
        try {
            Response response = client.newCall(request).execute();
            String content = response.body().string();
            if (!lazyBody) {
                resp.setBodyJson(content);
            }
            if (null != body) {
                resp.setResponse(response.newBuilder().body(ResponseBody.create(body.contentType(), content)).build());
            } else {
                resp.setResponse(response.newBuilder().body(ResponseBody.create(response.body().contentType(), content)).build());
            }
            resp.setHeaders(response.headers());
            if (null != response.priorResponse() && null != response.priorResponse().priorResponse()) {
                resp.setPrior2Headers(response.priorResponse().priorResponse().headers());
            }
            return true;
        } catch (Exception e) {
            log.error(ExceptionUtil.getMessage(e));
            log.error(ExceptionUtil.stacktraceToString(e));
            endTime = System.currentTimeMillis();
            useTime = endTime - startTime;
            log.error("\r" + method + "请求发送失败，用时:" + useTime + "(ms)\nurl：" + url + "\nheaders: " + JSONObject.toJSONString(headers) + "\nparams: " + jsonString + "\n" + e.getMessage());
        }
        return false;
    }

    private static Request getRequest(String url, String method, Map<String, String> headers, RequestBody body)
    {
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(url).method(method.toUpperCase(), body);
        fillWithHeader(url, headers, requestBuilder);
        return requestBuilder.build();
    }

    private static boolean sendRequestDb(String url, String method, boolean lazyBody, Map<String, String> headers, String jsonString, Map<String, Object> retMap, OkHttpClient client, RequestBody body)
    {
        Request request = getRequest(url, method, headers, body);

        long startTime = System.currentTimeMillis();
        long endTime;
        long useTime;
        try {
            Response response = client.newCall(request).execute();
            String content = response.body().string();
            if (!lazyBody) {
                retMap.put("responseBody", content);
            }
            if (null != body) {
                retMap.put("response", response.newBuilder().body(ResponseBody.create(body.contentType(), content)).build());
            } else {
                retMap.put("response", response.newBuilder().body(ResponseBody.create(response.body().contentType(), content)).build());
            }
//            response.close();
            return true;
        } catch (Exception e) {
            log.error(ExceptionUtil.getMessage(e));
            log.error(ExceptionUtil.stacktraceToString(e));
            endTime = System.currentTimeMillis();
            useTime = endTime - startTime;
            log.error("\r" + method + "请求发送失败，用时:" + useTime + "(ms)\n\rurl：" + url + "\n\rheaders: " + JSONObject.toJSONString(headers) + "\n\rparams: " + jsonString + "\n\r" + e.getMessage());
        }
        return false;
    }

    /**
     * 下载
     *
     * @param url     网址
     * @param headers 请求头
     * @param rootDir 下载至根目录
     * @return boolean
     */
    public static boolean download(String url, Map<String, String> headers, String rootDir, String fileName)
    {
        OkHttpClient client = buildClient(url, null, null, 10);
        Map<String, Object> retMap = new HashMap<>();
        if (sendRequestDb(url, "get", true, headers, null, retMap, client, null)) {
            Response response = (Response) retMap.get("response");
            int code = response.code();

            Map<String, List<String>> stringListMap = changeHeaderKey(response);
            String contentType = org.apache.commons.lang3.StringUtils.join(stringListMap.get("Content-Type"), "");

            if (200 == code && !StringUtils.isEmpty(contentType) && !contentType.toLowerCase().contains("text/html")) {
                String contentDisposition = org.apache.commons.lang3.StringUtils.join(stringListMap.get("Content-Disposition"), "");
//                String fileName = System.currentTimeMillis() + ".apk";
//                if (!StringUtils.isEmpty(contentDisposition)) {
//                    fileName = contentDisposition.split("=")[1].trim().replaceAll("\"", "");
//                    try {
//                        fileName = URLDecoder.decode(fileName, "utf-8");
//                        fileName = fileName.replaceAll("UTF-8", "").replaceAll("''", "");
//                        fileName = fileName.trim();
//                    } catch (Exception ignored) {
//                    }
//                }

                try {
                    InputStream inputStream = response.body().byteStream();
                    File target = new File(rootDir, fileName);
                    FileOutputStream fileOutputStream = new FileOutputStream(target);
                    byte[] buffer = new byte[2048];
                    int len;
                    while ((len = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, len);
                    }
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    inputStream.close();
                    closeResponse(retMap);
                    return true;
                } catch (IOException e) {
                    log.error("下载失败", e);
                }
            }
        }
        closeResponse(retMap);
        return false;
    }

    public static void httpDownload(String httpUrl, HttpServletRequest request, HttpServletResponse response, String fileName) throws IOException
    {
//        URL url = new URL(httpUrl);
//        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//        urlConnection.connect();
//        response.setContentType("multipart/form-data");
//        response.setHeader("Content-Type","application/vnd.android.package-archive");
//        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
//        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
//        OutputStream outputStream = response.getOutputStream();
//        InputStream inputStream = urlConnection.getInputStream();
//        IOUtils.copy(inputStream, outputStream);
//        inputStream.close();
//        response.flushBuffer();
//        outputStream.close();

        try {
            request.setCharacterEncoding("UTF-8");

            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            String downLoadPath = httpUrl;

            response.setContentType("application/octet-stream");
            response.reset();//清除response中的缓存
            //根据网络文件地址创建URL
            URL url = new URL(downLoadPath);
            //获取此路径的连接
            URLConnection conn = url.openConnection();

            String redirect = conn.getHeaderField("Location");
            if (null != redirect && !"".equals(redirect)) {
                conn = new URL(redirect).openConnection();
            }

            Long fileLength = conn.getContentLengthLong();//获取文件大小
            //设置reponse响应头，真实文件名重命名，就是在这里设置，设置编码
            response.setHeader("Content-disposition",
                    "attachment; filename=" + new String((fileName).getBytes("utf-8"), "ISO8859-1"));
            response.setHeader("Content-Length", String.valueOf(fileLength));

            bis = new BufferedInputStream(conn.getInputStream());//构造读取流
            bos = new BufferedOutputStream(response.getOutputStream());//构造输出流
            byte[] buff = new byte[1024];
            int bytesRead;
            //每次读取缓存大小的流，写到输出流
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            response.flushBuffer();//将所有的读取的流返回给客户端
            bis.close();
            bos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 判断链接是否可以下载
     * 存在小bug：如果链接返回的是 Content-Type=text/html，然后在js中通过js进行的制定地址等方式下载的话，无法判断准确性
     * 例如：https://111.222.333.asdasd 访问的是一个页面，然后页面在onload方法中，制定的另外的地址去或者其它方式去下载，就无法判断，
     * 例如这个url:
     * https://3gc5nhcjigozdec3qgr4dy.ourdvsss.com:20443/down-vest-hj.yijianhuayou.com/appDownload/14802/2a02b242676e1e9c3a96fe16b89b9dfd?app=%E8%8A%B1%E5%AD%A3%E4%BC%A0%E5%AA%92%E6%97%A7%E7%89%88_JMCPA1.6701_645926&wsiphost=ipdbme&wsrid_tag=63474859_PS-NKG-01gU644_60564-58662-s1t1665615961420
     *
     * @param url     网址
     * @param headers 请求头
     * @return boolean
     */
    public static boolean canDownload(String url, Map<String, String> headers)
    {
        OkHttpClient client = buildClient(url, null, null, 10);
        Map<String, Object> retMap = new HashMap<>();
        if (sendRequestDb(url, "get", true, headers, null, retMap, client, null)) {
            Response response = (Response) retMap.get("response");

            Map<String, List<String>> stringListMap = changeHeaderKey(response);
            String contentType = org.apache.commons.lang3.StringUtils.join(stringListMap.get("Content-Type"), "");
            int code = response.code();
            if (200 == code && !StringUtils.isEmpty(contentType) && !contentType.toLowerCase().contains("text/html")) {
                closeResponse(retMap);
                return true;
            }
        }
        closeResponse(retMap);
        return false;
    }

    public static Map<String, List<String>> changeHeaderKey(Response response)
    {
        Map<String, List<String>> stringListMap = response.headers().toMultimap();
        Map<String, List<String>> headerMap = response.headers().toMultimap();
        Set<Map.Entry<String, List<String>>> entries = stringListMap.entrySet();
        for (Map.Entry<String, List<String>> next : entries) {
            String key = next.getKey();
            List<String> value = next.getValue();
            headerMap.put(key.toUpperCase(), value);
        }
        return headerMap;
    }

    public static void closeResponse(Map<String, Object> retMap)
    {
        Response response = (Response) retMap.get("response");
        if (null != response) {
            try {
                response.close();
            } catch (Exception ignored) {
            }
        }
    }

    public static void fillWithHeader(String url, Map<String, String> headers, Request.Builder requestBuilder)
    {
        if (null == headers) {
            headers = new HashMap<>();
        }
        String host = headers.get("Host");
        if (StringUtils.isEmpty(host)) {
            String currentHost = url.replaceAll("http://", "").replaceAll("https://", "").split("/")[0];
            headers.put("Host", currentHost);
        } else if (host.equalsIgnoreCase("del")) {
            headers.remove("Host");
        }

        String useragent = headers.get("user-agent");
        if (StringUtils.isEmpty(useragent)) {
            useragent = headers.get("User-Agent");
        }

        if (StringUtils.isEmpty(useragent)) {
            headers.put("user-agent", UAS.get(random.nextInt(UAS.size())));
        } else if ("del".equalsIgnoreCase(useragent)) {
            headers.remove("user-agent");
            headers.remove("User-Agent");
        }

        for (Map.Entry<String, String> item : headers.entrySet()) {
            requestBuilder.addHeader(item.getKey(), item.getValue());
        }
    }

    public static Random random = new Random();
    public static List<String> UAS = Arrays.asList(
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.70 Safari/537.36",
            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; AcooBrowser; .NET CLR 1.1.4322; .NET CLR 2.0.50727)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0; Acoo Browser; SLCC1; .NET CLR 2.0.50727; Media Center PC 5.0; .NET CLR 3.0.04506)",
            "Mozilla/4.0 (compatible; MSIE 7.0; AOL 9.5; AOLBuild 4337.35; Windows NT 5.1; .NET CLR 1.1.4322; .NET CLR 2.0.50727)",
            "Mozilla/5.0 (Windows; U; MSIE 9.0; Windows NT 9.0; en-US)",
            "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET CLR 2.0.50727; Media Center PC 6.0)",
            "Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET CLR 1.0.3705; .NET CLR 1.1.4322)",
            "Mozilla/4.0 (compatible; MSIE 7.0b; Windows NT 5.2; .NET CLR 1.1.4322; .NET CLR 2.0.50727; InfoPath.2; .NET CLR 3.0.04506.30)",
            "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN) AppleWebKit/523.15 (KHTML, like Gecko, Safari/419.3) Arora/0.3 (Change: 287 c9dfb30)",
            "Mozilla/5.0 (X11; U; Linux; en-US) AppleWebKit/527+ (KHTML, like Gecko, Safari/419.3) Arora/0.6",
            "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.2pre) Gecko/20070215 K-Ninja/2.1.1",
            "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9) Gecko/20080705 Firefox/3.0 Kapiko/3.0",
            "Mozilla/5.0 (X11; Linux i686; U;) Gecko/20070322 Kazehakase/0.4.5",
            "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.8) Gecko Fedora/1.9.0.8-1.fc10 Kazehakase/0.5.6",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.20 (KHTML, like Gecko) Chrome/19.0.1036.7 Safari/535.20",
            "Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; fr) Presto/2.9.168 Version/11.52"
    );

    public static void main(String[] args) throws IOException
    {

        String api = "https://ecomm.one-line.com/api/v1/quotation/schedules/vessel-dates";
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer Ra4b2DGF6k5UiN1OZ5NE8GQ8_z0u2S7wZlw7NUvNSYyc3CzrIJEasS3i3zUzH5lp");
        header.put("user-Agent", "del");
        header.put("Host", "del");
        HttpResp resp = HttpUtil.postBody(api, header, "{\"isSoc\":false,\"destinationLocationType\":\"CY\",\"serviceScopeCode\":\"AEW\",\"originLoc\":\"CNSHA\",\"reeferType\":\"\",\"destinationLoc\":\"NLRTM\",\"commodityCode\":\"000000\",\"containers\":[{\"cargoType\":\"DR\",\"quantity\":1,\"isError\":false,\"isFocus\":false,\"equipmentONECntrTpSz\":\"D2\",\"equipmentIsoCode\":\"22G1\",\"equipmentName\":\"DRY 20\",\"equipmentSize\":\"20\",\"commodityGroups\":[{\"commodityGroup\":\"FAK\"}]}],\"commodityGroups\":[],\"originLocationType\":\"CY\"}");
        String bodyJson = resp.getBodyJson();
        System.out.println(bodyJson);


    }
}