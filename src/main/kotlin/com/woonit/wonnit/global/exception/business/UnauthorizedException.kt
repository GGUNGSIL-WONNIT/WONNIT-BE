package com.woonit.wonnit.global.exception.business

import com.woonit.wonnit.global.exception.code.ErrorCode
import com.woonit.wonnit.global.exception.log.LogLevel

class UnauthorizedException : BusinessException {
    constructor(errorCode: ErrorCode) : super(errorCode)
    constructor(errorCode: ErrorCode, cause: Throwable) : super(errorCode, cause)
    constructor(errorCode: ErrorCode, customMessage: String, cause: Throwable) : super(errorCode, customMessage, cause)

    // 인증 실패 이유와 함께 생성
    constructor(errorCode: ErrorCode, reason: String) : super(errorCode) {
        withProperty("reason", reason)
    }

    // 토큰 정보와 함께 생성
    constructor(errorCode: ErrorCode, tokenType: String, expiresAt: String? = null) : super(errorCode) {
        withProperty("tokenType", tokenType)
        expiresAt?.let { withProperty("expiresAt", it) }
    }

    // 보안 관련이므로 WARN 레벨
    override fun getLogLevel(): LogLevel = LogLevel.WARN

    fun getAuthenticationReason(): String? = properties["reason"] as? String

    fun getTokenType(): String? = properties["tokenType"] as? String
}
