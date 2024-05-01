package com.djk.core.exception;

import com.djk.core.api.IErrorCode;

/**
 * @author duanjunkai
 * @date 2024/05/01
 */
public class Asserts {
    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }
}
