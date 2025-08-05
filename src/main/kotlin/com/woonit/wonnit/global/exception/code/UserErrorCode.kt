package com.woonit.wonnit.global.exception.code

import org.springframework.http.HttpStatus

enum class UserErrorCode(
    override val code: String,
    override val message: String,
    override val httpStatus: HttpStatus,
    override val type: String
) : ErrorCode
