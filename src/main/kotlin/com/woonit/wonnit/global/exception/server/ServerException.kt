package com.woonit.wonnit.global.exception.server

import com.woonit.wonnit.global.exception.code.ErrorCode
import com.woonit.wonnit.global.exception.base.BaseException
import com.woonit.wonnit.global.exception.log.LogLevel

abstract class ServerException : BaseException {

    constructor(errorCode: ErrorCode) : super(errorCode)
    constructor(errorCode: ErrorCode, customMessage: String) : super(errorCode, customMessage)
    constructor(errorCode: ErrorCode, cause: Throwable) : super(errorCode, cause)
    constructor(errorCode: ErrorCode, customMessage: String, cause: Throwable) : super(errorCode, customMessage, cause)

    override fun getLogLevel(): LogLevel = LogLevel.WARN

    open fun requiresAlert(): Boolean = true

    open fun isRetryable(): Boolean = false

    open fun getTechnicalDetails(): Map<String, Any> = properties.toMap()

    fun getUserMessage(): String = "일시적인 서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요."
}
