package com.djk.core.exception;

import com.djk.core.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author duanjunkai
 * @date 2024/05/01
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler
{

    /**
     * @param e
     * @return {@link CommonResult}
     */
    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public CommonResult handle(ApiException e)
    {
        log.error("server exception \n", e);
        if (e.getErrorCode() != null) {
            return CommonResult.failed(e.getErrorCode());
        }
        return CommonResult.failed(e.getMessage());
    }

    /**
     * @param e
     * @return {@link CommonResult}
     */
    @ResponseBody
    @ExceptionHandler({RuntimeException.class, Exception.class})
    public CommonResult exceptionHandler(Throwable e)
    {
        log.error("server exception \n", e);
        return CommonResult.failed(e.getMessage());
    }

    /**
     * @param e
     * @return {@link CommonResult}
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CommonResult handleValidException(MethodArgumentNotValidException e)
    {
        return getCommonResult(e);
    }

    private CommonResult getCommonResult(Exception e)
    {
        log.error("server exception \n", e);
        BindingResult bindingResult = null;
        if (e instanceof MethodArgumentNotValidException) {
            bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
        }
        if (e instanceof BindException) {
            bindingResult = ((BindException) e).getBindingResult();
        }

        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField() + fieldError.getDefaultMessage();
            }
        }
        return CommonResult.validateFailed(message);
    }

    /**
     * @param e
     * @return {@link CommonResult}
     */
    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public CommonResult handleValidException(BindException e)
    {
        return getCommonResult(e);
    }
}
