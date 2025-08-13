package com.woonit.wonnit.global.exception.code

import org.springframework.http.HttpStatus

enum class UserErrorCode(
    override val code: String,
    override val message: String,
    override val httpStatus: HttpStatus,
    override val type: String
) : ErrorCode {

    NOT_FOUND(
        "USER_001",
        "사용자를 찾을 수 없습니다.",
        HttpStatus.NOT_FOUND,
        "https://api.wonnit.com/errors/user/not-found"
    )
}
