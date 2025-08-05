package com.woonit.wonnit.global.exception.code

import org.springframework.http.HttpStatus

enum class CommonErrorCode(
    override val code: String,
    override val message: String,
    override val httpStatus: HttpStatus,
    override val type: String
) : ErrorCode {

    // ========== 400 BAD REQUEST ==========
    INVALID_INPUT(
        "VALID_001",
        "입력값이 유효하지 않습니다.",
        HttpStatus.BAD_REQUEST,
        "https://api.wonnit.com/errors/validation/invalid-input"
    ),
    MALFORMED_REQUEST(
        "VALID_002",
        "잘못된 형식의 요청입니다.",
        HttpStatus.BAD_REQUEST,
        "https://api.wonnit.com/errors/validation/malformed-request"
    ),
    MISSING_REQUIRED_FIELD(
        "VALID_003",
        "필수 필드가 누락되었습니다.",
        HttpStatus.BAD_REQUEST,
        "https://api.wonnit.com/errors/validation/missing-field"
    ),
    BUSINESS_RULE_VIOLATION(
        "BIZ_001",
        "비즈니스 규칙에 위배됩니다.",
        HttpStatus.BAD_REQUEST,
        "https://api.wonnit.com/errors/business/rule-violation"
    ),

    // ========== 401 UNAUTHORIZED ==========
    AUTHENTICATION_FAILED(
        "AUTH_001",
        "인증에 실패했습니다.",
        HttpStatus.UNAUTHORIZED,
        "https://api.wonnit.com/errors/auth/authentication-failed"
    ),
    TOKEN_EXPIRED(
        "AUTH_003",
        "토큰이 만료되었습니다.",
        HttpStatus.UNAUTHORIZED,
        "https://api.wonnit.com/errors/auth/token-expired"
    ),
    INVALID_TOKEN(
        "AUTH_004",
        "유효하지 않은 토큰입니다.",
        HttpStatus.UNAUTHORIZED,
        "https://api.wonnit.com/errors/auth/invalid-token"
    ),

    // ========== 403 FORBIDDEN ==========
    ACCESS_DENIED(
        "AUTH_002",
        "접근이 거부되었습니다.",
        HttpStatus.FORBIDDEN,
        "https://api.wonnit.com/errors/auth/access-denied"
    ),
    OPERATION_NOT_ALLOWED(
        "BIZ_002",
        "허용되지 않은 작업입니다.",
        HttpStatus.FORBIDDEN,
        "https://api.wonnit.com/errors/business/operation-not-allowed"
    ),

    // ========== 404 NOT FOUND ==========
    RESOURCE_NOT_FOUND(
        "RESOURCE_001",
        "요청한 리소스를 찾을 수 없습니다.",
        HttpStatus.NOT_FOUND,
        "https://api.wonnit.com/errors/resource/not-found"
    ),
    USER_NOT_FOUND(
        "USER_001",
        "사용자를 찾을 수 없습니다.",
        HttpStatus.NOT_FOUND,
        "https://api.wonnit.com/errors/user/not-found"
    ),

    // ========== 405 METHOD NOT ALLOWED ==========
    METHOD_NOT_ALLOWED(
        "HTTP_001",
        "지원하지 않는 HTTP 메서드입니다.",
        HttpStatus.METHOD_NOT_ALLOWED,
        "https://api.wonnit.com/errors/http/method-not-allowed"
    ),

    // ========== 409 CONFLICT ==========
    RESOURCE_ALREADY_EXISTS(
        "RESOURCE_002",
        "이미 존재하는 리소스입니다.",
        HttpStatus.CONFLICT,
        "https://api.wonnit.com/errors/resource/already-exists"
    ),
    USER_ALREADY_EXISTS(
        "USER_002",
        "이미 존재하는 사용자입니다.",
        HttpStatus.CONFLICT,
        "https://api.wonnit.com/errors/user/already-exists"
    ),
    DATABASE_ERROR(
        "SYS_002",
        "데이터베이스 오류가 발생했습니다.",
        HttpStatus.CONFLICT,
        "https://api.wonnit.com/errors/system/database-error"
    ),

    // ========== 413 PAYLOAD TOO LARGE ==========
    FILE_TOO_LARGE(
        "FILE_001",
        "파일 크기가 너무 큽니다.",
        HttpStatus.PAYLOAD_TOO_LARGE,
        "https://api.wonnit.com/errors/file/too-large"
    ),

    // ========== 415 UNSUPPORTED MEDIA TYPE ==========
    UNSUPPORTED_MEDIA_TYPE(
        "HTTP_002",
        "지원하지 않는 미디어 타입입니다.",
        HttpStatus.UNSUPPORTED_MEDIA_TYPE,
        "https://api.wonnit.com/errors/http/unsupported-media-type"
    ),
    INVALID_FILE_FORMAT(
        "FILE_002",
        "지원하지 않는 파일 형식입니다.",
        HttpStatus.UNSUPPORTED_MEDIA_TYPE,
        "https://api.wonnit.com/errors/file/invalid-format"
    ),

    // ========== 423 LOCKED ==========
    RESOURCE_LOCKED(
        "RESOURCE_003",
        "리소스가 잠겨있습니다.",
        HttpStatus.LOCKED,
        "https://api.wonnit.com/errors/resource/locked"
    ),

    // ========== 429 TOO MANY REQUESTS ==========
    QUOTA_EXCEEDED(
        "BIZ_003",
        "할당량을 초과했습니다.",
        HttpStatus.TOO_MANY_REQUESTS,
        "https://api.wonnit.com/errors/business/quota-exceeded"
    ),
    RATE_LIMIT_EXCEEDED(
        "RATE_001",
        "요청 한도를 초과했습니다.",
        HttpStatus.TOO_MANY_REQUESTS,
        "https://api.wonnit.com/errors/rate-limit/exceeded"
    ),

    // ========== 500 INTERNAL SERVER ERROR ==========
    INTERNAL_SERVER_ERROR(
        "SYS_001",
        "내부 서버 오류가 발생했습니다.",
        HttpStatus.INTERNAL_SERVER_ERROR,
        "https://api.wonnit.com/errors/system/internal-error"
    ),

    // ========== 503 SERVICE UNAVAILABLE ==========
    EXTERNAL_SERVICE_ERROR(
        "SYS_003",
        "외부 서비스 오류가 발생했습니다.",
        HttpStatus.SERVICE_UNAVAILABLE,
        "https://api.wonnit.com/errors/system/external-service-error"
    ),
    SERVICE_UNAVAILABLE(
        "SYS_004",
        "서비스를 사용할 수 없습니다.",
        HttpStatus.SERVICE_UNAVAILABLE,
        "https://api.wonnit.com/errors/system/service-unavailable"
    )
}
