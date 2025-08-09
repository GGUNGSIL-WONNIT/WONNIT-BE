package com.woonit.wonnit.global.exception.business

import com.woonit.wonnit.global.exception.base.BaseException
import com.woonit.wonnit.global.exception.code.ErrorCode
import com.woonit.wonnit.global.exception.log.LogLevel

abstract class BusinessException : BaseException {

    constructor(errorCode: ErrorCode) : super(errorCode)
    constructor(errorCode: ErrorCode, customMessage: String) : super(errorCode, customMessage)
    constructor(errorCode: ErrorCode, cause: Throwable) : super(errorCode, cause)
    constructor(errorCode: ErrorCode, customMessage: String, cause: Throwable) : super(errorCode, customMessage, cause)

    // 비즈니스 예외는 일반적으로 WARN 레벨
    override fun getLogLevel(): LogLevel = LogLevel.WARN
}
