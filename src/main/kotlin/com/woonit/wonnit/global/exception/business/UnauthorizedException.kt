package com.woonit.wonnit.global.exception.business

import com.woonit.wonnit.global.exception.code.ErrorCode
import com.woonit.wonnit.global.exception.log.LogLevel

class UnauthorizedException : BusinessException {
    constructor(errorCode: ErrorCode) : super(errorCode)
    constructor(errorCode: ErrorCode, customMessage: String) : super(errorCode, customMessage)
    constructor(errorCode: ErrorCode, cause: Throwable) : super(errorCode, cause)
    constructor(errorCode: ErrorCode, customMessage: String, cause: Throwable) : super(errorCode, customMessage, cause)

    // 토큰 정보랑 같이 생성
    constructor(errorCode: ErrorCode, tokenType: String, expiresAt: String? = null) : super(errorCode) {
        withProperty("tokenType", tokenType)
        expiresAt?.let { withProperty("expiresAt", it) }
    }

    fun getAuthenticationReason(): String? = properties["reason"] as? String

    fun getTokenType(): String? = properties["tokenType"] as? String

    override fun getLogLevel(): LogLevel = LogLevel.WARN
}
