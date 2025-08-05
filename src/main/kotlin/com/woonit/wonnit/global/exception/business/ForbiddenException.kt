package com.woonit.wonnit.global.exception.business

import com.woonit.wonnit.global.exception.code.ErrorCode
import com.woonit.wonnit.global.exception.log.LogLevel

class ForbiddenException : BusinessException {

    constructor(errorCode: ErrorCode) : super(errorCode)
    constructor(errorCode: ErrorCode, customMessage: String) : super(errorCode, customMessage)
    constructor(errorCode: ErrorCode, cause: Throwable) : super(errorCode, cause)
    constructor(errorCode: ErrorCode, customMessage: String, cause: Throwable) : super(errorCode, customMessage, cause)

    // 권한 정보와 함께 생성
    constructor(errorCode: ErrorCode, resource: String, action: String) : super(errorCode) {
        withProperty("resource", resource)
        withProperty("action", action)
    }

    // 사용자와 권한 정보 함께 생성
    constructor(errorCode: ErrorCode, userId: Long, resource: String, action: String) : super(errorCode) {
        withProperty("userId", userId)
        withProperty("resource", resource)
        withProperty("action", action)
    }

    // 보안 관련이므로 WARN 레벨
    override fun getLogLevel(): LogLevel = LogLevel.WARN

    fun getResource(): String? = properties["resource"] as? String

    fun getAction(): String? = properties["action"] as? String
}