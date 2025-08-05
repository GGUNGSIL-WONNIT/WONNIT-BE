package com.woonit.wonnit.global.exception.business

import com.woonit.wonnit.global.exception.code.ErrorCode
import com.woonit.wonnit.global.exception.log.LogLevel

class BadRequestException : BusinessException {

    constructor(errorCode: ErrorCode) : super(errorCode)
    constructor(errorCode: ErrorCode, customMessage: String) : super(errorCode, customMessage)
    constructor(errorCode: ErrorCode, cause: Throwable) : super(errorCode, cause)
    constructor(errorCode: ErrorCode, customMessage: String, cause: Throwable) : super(errorCode, customMessage, cause)

    //  필드별 검증 실패 정보와 함께 생성
    constructor(errorCode: ErrorCode, fieldErrors: Map<String, String>) : super(errorCode) {
        withProperty("fieldErrors", fieldErrors)
    }

    // 단일 필드 검증 실패
    constructor(errorCode: ErrorCode, fieldName: String, fieldError: String) : super(errorCode) {
        withProperty("fieldErrors", mapOf(fieldName to fieldError))
    }

    // 400 에러는 INFO 레벨
    override fun getLogLevel(): LogLevel = LogLevel.INFO

    fun getFieldErrors(): Map<*, *>? {
        return properties["fieldErrors"] as? Map<*, *>
    }

    fun hasFieldErrors(): Boolean = getFieldErrors()?.isNotEmpty() == true
}