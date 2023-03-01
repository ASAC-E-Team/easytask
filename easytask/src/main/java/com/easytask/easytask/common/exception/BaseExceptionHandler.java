package com.easytask.easytask.common.exception;

import com.easytask.easytask.common.response.BaseResponse;
import com.easytask.easytask.common.response.BaseResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler(BaseException.class)
    public BaseResponse<BaseResponseStatus> BaseExceptionHandle(BaseException exception) {
        log.warn("BaseException : {}", exception.getMessage());
        log.warn("BaseException : ", exception);
        return new BaseResponse<>(exception.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public BaseResponse<BaseResponseStatus> ExceptionHandle(Exception exception) {
        log.error("Exception : ", exception);
        return new BaseResponse<>(BaseResponseStatus.UNEXPECTED_ERROR);
    }
}
