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


    REGISTERED_USER(false, HttpStatus.NOT_FOUND.value(), "이미 가입되어 있는 유저입니다."),
    NOT_VALID_EMAIL(false, HttpStatus.BAD_REQUEST.value(),"이메일 형식에 맞지 않습니다"),
    NOT_FIND_USER(false,HttpStatus.NOT_FOUND.value(),"이메일 또는 비밀번호를 확인해주세요"),
    INVALID_REQUEST(false,HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다."),
    INVALID_TOKEN(false,HttpStatus.BAD_REQUEST.value(), "유효하지 않은 토큰입니다."),






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