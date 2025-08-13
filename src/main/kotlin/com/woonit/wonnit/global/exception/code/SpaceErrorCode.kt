package com.woonit.wonnit.global.exception.code

import org.springframework.http.HttpStatus

enum class SpaceErrorCode(
    override val code: String,
    override val message: String,
    override val httpStatus: HttpStatus,
    override val type: String
) : ErrorCode {

    INVALID_CATEGORY(
        "Space_001",
        "공간 카테고리가 유효하지 않습니다.",
        HttpStatus.BAD_REQUEST,
        "https://api.wonnit.com/errors/space/invalid-category"
    ),

    INVALID_SIZE(
        "Space_002",
        "공간 크기가 유효하지 않습니다.",
        HttpStatus.BAD_REQUEST,
        "https://api.wonnit.com/errors/space/invalid-size"
    ),
}

