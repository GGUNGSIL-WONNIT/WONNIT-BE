package com.woonit.wonnit.global.exception.server

import com.woonit.wonnit.global.exception.code.ErrorCode

class GatewayTimeoutException : ServerException {

    constructor(errorCode: ErrorCode) : super(errorCode)
    constructor(errorCode: ErrorCode, customMessage: String) : super(errorCode, customMessage)
    constructor(errorCode: ErrorCode, cause: Throwable) : super(errorCode, cause)
    constructor(errorCode: ErrorCode, customMessage: String, cause: Throwable) : super(errorCode, customMessage, cause)

    /**
     * 타임아웃 정보와 함께 생성
     */
    constructor(errorCode: ErrorCode, serviceName: String, endpoint: String, timeoutMs: Long) : super(errorCode) {
        withProperty("serviceName", serviceName)
        withProperty("endpoint", endpoint)
        withProperty("timeoutMs", timeoutMs)
    }

    /**
     * 재시도 정보와 함께 생성
     */
    constructor(
        errorCode: ErrorCode,
        serviceName: String,
        endpoint: String,
        timeoutMs: Long,
        retryCount: Int
    ) : super(errorCode) {
        withProperty("serviceName", serviceName)
        withProperty("endpoint", endpoint)
        withProperty("timeoutMs", timeoutMs)
        withProperty("retryCount", retryCount)
    }

    /**
     * 성능 이슈 관련 설정
     */
    constructor(
        errorCode: ErrorCode,
        serviceName: String,
        endpoint: String,
        timeoutMs: Long,
        retryCount: Int,
        circuitBreakerOpen: Boolean
    ) : super(errorCode) {
        withProperty("serviceName", serviceName)
        withProperty("endpoint", endpoint)
        withProperty("timeoutMs", timeoutMs)
        withProperty("retryCount", retryCount)
        withProperty("circuitBreakerOpen", circuitBreakerOpen)
    }

    /**
     * 성능 저하 알림 필요
     */
    override fun requiresAlert(): Boolean = true

    /**
     * 타임아웃이므로 재시도 가능 (단, 백오프 적용)
     */
    override fun isRetryable(): Boolean = true

    fun getServiceName(): String? = properties["serviceName"] as? String

    fun getEndpoint(): String? = properties["endpoint"] as? String

    fun getTimeoutMs(): Long? = properties["timeoutMs"] as? Long

    fun getRetryCount(): Int? = properties["retryCount"] as? Int

    fun isCircuitBreakerOpen(): Boolean = properties["circuitBreakerOpen"] as? Boolean ?: false
}