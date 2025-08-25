package com.woonit.wonnit.global.exception.server

import com.woonit.wonnit.global.exception.code.ErrorCode

class BadGatewayException : ServerException {

    constructor(errorCode: ErrorCode) : super(errorCode)
    constructor(errorCode: ErrorCode, customMessage: String) : super(errorCode, customMessage)
    constructor(errorCode: ErrorCode, cause: Throwable) : super(errorCode, cause)
    constructor(errorCode: ErrorCode, customMessage: String, cause: Throwable) : super(errorCode, customMessage, cause)

    /**
     * 외부 서비스 정보와 함께 생성
     */
    constructor(errorCode: ErrorCode, serviceName: String, endpoint: String) : super(errorCode) {
        withProperty("serviceName", serviceName)
        withProperty("endpoint", endpoint)
    }

    /**
     * 외부 서비스 응답 정보와 함께 생성
     */
    constructor(errorCode: ErrorCode, serviceName: String, endpoint: String, responseStatus: Int) : super(errorCode) {
        withProperty("serviceName", serviceName)
        withProperty("endpoint", endpoint)
        withProperty("responseStatus", responseStatus)
    }

    /**
     * 상세 요청/응답 정보와 함께 생성
     */
    constructor(
        errorCode: ErrorCode,
        serviceName: String,
        endpoint: String,
        requestPayload: Any?,
        responseBody: String?
    ) : super(errorCode) {
        withProperty("serviceName", serviceName)
        withProperty("endpoint", endpoint)
        requestPayload?.let { withProperty("requestPayload", it) }
        responseBody?.let { withProperty("responseBody", it) }
    }

    /**
     * 외부 서비스 문제이므로 알림 필요
     */
    override fun requiresAlert(): Boolean = true

    /**
     * 외부 서비스 복구 후 재시도 가능
     */
    override fun isRetryable(): Boolean = true

    fun getServiceName(): String? = properties["serviceName"] as? String

    fun getEndpoint(): String? = properties["endpoint"] as? String

    fun getResponseStatus(): Int? = properties["responseStatus"] as? Int
}
