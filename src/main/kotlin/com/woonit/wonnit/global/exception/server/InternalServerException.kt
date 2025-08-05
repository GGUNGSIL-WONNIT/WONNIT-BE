package com.woonit.wonnit.global.exception.server

import com.woonit.wonnit.global.exception.code.ErrorCode

class InternalServerException : ServerException {

    constructor(errorCode: ErrorCode) : super(errorCode)
    constructor(errorCode: ErrorCode, cause: Throwable) : super(errorCode, cause)
    constructor(errorCode: ErrorCode, customMessage: String, cause: Throwable) : super(errorCode, customMessage, cause)

    /**
     * 추적 ID와 함께 생성
     */
    constructor(errorCode: ErrorCode, traceId: String) : super(errorCode) {
        withProperty("traceId", traceId)
    }

    /**
     * 컴포넌트와 작업 정보와 함께 생성
     */
    constructor(errorCode: ErrorCode, component: String, operation: String) : super(errorCode) {
        withProperty("component", component)
        withProperty("operation", operation)
    }

    /**
     * 상세 오류 정보와 함께 생성
     */
    constructor(errorCode: ErrorCode, component: String, operation: String, details: Map<String, Any>) : super(errorCode) {
        withProperty("component", component)
        withProperty("operation", operation)
        withProperties(details)
    }

    /**
     * 심각한 오류이므로 즉시 알림 필요
     */
    override fun requiresAlert(): Boolean = true

    /**
     * 원인 불명이므로 재시도 위험
     */
    override fun isRetryable(): Boolean = false

    fun getTraceId(): String? = properties["traceId"] as? String

    fun getComponent(): String? = properties["component"] as? String

    fun getOperation(): String? = properties["operation"] as? String
}
