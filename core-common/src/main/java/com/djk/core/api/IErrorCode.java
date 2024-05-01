package com.djk.core.api;

/**
 * 封装API的错误码
 *
 * @author duanjunkai
 * @date 2024/05/01
 */
public interface IErrorCode {
    /**
     * 返回码
     * @return long
     */
    long getCode();

    /**
     * 返回信息
     * @return {@link String}
     */
    String getMessage();
}
