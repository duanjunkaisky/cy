package com.djk.core.utils;

import lombok.Data;
import okhttp3.Headers;
import okhttp3.Response;

/**
 * @author duanjunkai
 * @date 2024/05/01
 */
@Data
public class HttpResp {

    String bodyJson;

    Headers headers;

    Headers prior2Headers;

    Response response;
}
