package com.woonit.wonnit.global.exception.business

import com.woonit.wonnit.global.exception.code.ErrorCode
import com.woonit.wonnit.global.exception.log.LogLevel

class MethodNotAllowedException : BusinessException {

    constructor(errorCode: ErrorCode) : super(errorCode)
    constructor(errorCode: ErrorCode, customMessage: String) : super(errorCode, customMessage)
    constructor(errorCode: ErrorCode, cause: Throwable) : super(errorCode, cause)
    constructor(errorCode: ErrorCode, customMessage: String, cause: Throwable) : super(errorCode, customMessage, cause)

    /**
     * HTTP 메서드 정보와 함께 생성
     */
    constructor(errorCode: ErrorCode, method: String, supportedMethods: Array<String>) : super(errorCode) {
        withProperty("method", method)
        withProperty("supportedMethods", supportedMethods)
    }

    /**
     * 클라이언트 실수이므로 INFO 레벨
     */
    override fun getLogLevel(): LogLevel = LogLevel.INFO

    override fun isRetryable(): Boolean = false

    fun getMethod(): String? = properties["method"] as? String

    fun getSupportedMethods(): Array<String>? = properties["supportedMethods"] as? Array<String>
}
