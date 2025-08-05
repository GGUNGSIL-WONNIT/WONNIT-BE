package com.woonit.wonnit.global.exception.business

import com.woonit.wonnit.global.exception.code.ErrorCode
import com.woonit.wonnit.global.exception.log.LogLevel

class ForbiddenException : BusinessException {

    constructor(errorCode: ErrorCode) : super(errorCode)
    constructor(errorCode: ErrorCode, customMessage: String) : super(errorCode, customMessage)
    constructor(errorCode: ErrorCode, cause: Throwable) : super(errorCode, cause)
    constructor(errorCode: ErrorCode, customMessage: String, cause: Throwable) : super(errorCode, customMessage, cause)

    constructor(errorCode: ErrorCode, userId: Long, resource: String, action: String) : super(errorCode) {
        withProperty("userId", userId)
        withProperty("resource", resource)
        withProperty("action", action)
    }

    constructor(errorCode: ErrorCode, requiredRole: String, userRole: String) : super(errorCode) {
        withProperty("requiredRole", requiredRole)
        withProperty("userRole", userRole)
    }

    override fun getLogLevel(): LogLevel = LogLevel.WARN

    override fun isRetryable(): Boolean = false

    fun getResource(): String? = properties["resource"] as? String

    fun getAction(): String? = properties["action"] as? String

    fun getUserId(): Long? = properties["userId"] as? Long

}