package com.woonit.wonnit.global.exception.code

import org.springframework.http.HttpStatus

enum class ColumnErrorCode(
    override val code: String,
    override val message: String,
    override val httpStatus: HttpStatus,
    override val type: String
) : ErrorCode
