package com.djk.core.api;

/**
 * 枚举了一些常用API操作码
 *
 * @author duanjunkai
 * @date 2024/05/01
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限");
    private long code;
    private String message;

    /**
     * @param code
     * @param message
     */
    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * @return long
     */
    @Override
    public long getCode() {
        return code;
    }

    /**
     * @return {@link String}
     */
    @Override
    public String getMessage() {
        return message;
    }
}
