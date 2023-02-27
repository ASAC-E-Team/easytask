package com.easytask.easytask.common.response;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 200 : 요청 성공
     */
    SUCCESS(true, HttpStatus.OK.value(), "요청에 성공하였습니다."),


    /**
     * 400 : Request, Response 오류
     */
    NOT_FOUND_USER(false, HttpStatus.NO_CONTENT.value(), "유저가 존재하지 않습니다."),

    /**
     * 500
     */
    DB_CONNECTION_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "데이터 베이스 오류 발생"),
    UNEXPECTED_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "예상치 못한 에러가 발생했습니다.");


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}