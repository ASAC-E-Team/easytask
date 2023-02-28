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
    NOT_FOUND_USER(false, HttpStatus.NOT_FOUND.value(), "유저가 존재하지 않습니다."),
    NOT_FOUND_TASK(false, HttpStatus.NOT_FOUND.value(), "업무가 존재하지 않습니다."),
    NOT_FOUND_ABILITY(false, HttpStatus.NOT_FOUND.value(), "해당 역량을 업무 요청서에 등록한 적이 없습니다."),

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