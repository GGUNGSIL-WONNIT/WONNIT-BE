package com.woonit.wonnit.global.exception.business

import com.woonit.wonnit.global.exception.code.ErrorCode
import com.woonit.wonnit.global.exception.log.LogLevel

class NotFoundException : BusinessException {

    constructor(errorCode: ErrorCode) : super(errorCode)
    constructor(errorCode: ErrorCode, cause: Throwable) : super(errorCode, cause)
    constructor(errorCode: ErrorCode, customMessage: String, cause: Throwable) : super(errorCode, customMessage, cause)

    /**
     * 리소스 타입과 ID와 함께 생성
     */
    constructor(errorCode: ErrorCode, resourceType: String, resourceId: Any) : super(errorCode) {
        withProperty("resourceType", resourceType)
        withProperty("resourceId", resourceId)
    }

    /**
     * 복합 검색 조건과 함께 생성
     */
    constructor(errorCode: ErrorCode, resourceType: String, searchParams: Map<String, Any>) : super(errorCode) {
        withProperty("resourceType", resourceType)
        withProperty("searchParams", searchParams)
    }

    /**
     * 경로 정보와 함께 생성
     */
    constructor(errorCode: ErrorCode, path: String) : super(errorCode) {
        withProperty("path", path)
    }

    /**
     * 일반적인 상황이므로 INFO 레벨
     */
    override fun getLogLevel(): LogLevel = LogLevel.INFO

    /**
     * 리소스가 생성되면 재시도 가능
     */
    override fun isRetryable(): Boolean = true

    fun getResourceType(): String? = properties["resourceType"] as? String

    fun getResourceId(): Any? = properties["resourceId"]

    fun getSearchParams(): Map<String, Any>? = properties["searchParams"] as? Map<String, Any>
}
