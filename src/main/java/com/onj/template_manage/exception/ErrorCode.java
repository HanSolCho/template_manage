package com.onj.template_manage.exception;

public enum ErrorCode {
    // 공통 오류 코드
    INTERNAL_SERVER_ERROR(500, "서버 오류"),

    // 회원 관련 오류 코드
    USER_NOT_FOUND(404, "존재하지 않는 회원입니다."),
    USER_FAIL_VALIDATE(403, "로그인 정보가 일치하지 않습니다."),
    USER_ALREADY_EXISTS(409, "이미 존재하는 회원 아이디입니다."),

    // 아이템 관련 오류 코드
    ITEM_NOT_FOUND(404, "등록할 아이템 목록이 존재 하지 않습니다."),
    ITEM_OPTION_IS_NULL(404, "아이템 옵션 값이 존재하지 않습니다."),

    // 콘텐츠 관련 오류
    CONTENT_NOT_FOUND(404, "등록된 컨텐츠가 존재 하지 않습니다."),

    // 로그인 관련 오류 코드
    INVALID_LOGIN_INFO(401, "잘못된 아이디 또는 비밀번호입니다."),

    // 범용 에러
    BAD_REQUEST(400, "잘못된 요청입니다.");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
