package com.woonit.wonnit.global.exception.business

import com.woonit.wonnit.global.exception.code.ErrorCode
import com.woonit.wonnit.global.exception.log.LogLevel

class ConflictException : BusinessException {

    constructor(errorCode: ErrorCode) : super(errorCode)
    constructor(errorCode: ErrorCode, customMessage: String) : super(errorCode, customMessage)
    constructor(errorCode: ErrorCode, cause: Throwable) : super(errorCode, cause)
    constructor(errorCode: ErrorCode, customMessage: String, cause: Throwable) : super(errorCode, customMessage, cause)

    /**
     * 충돌하는 리소스 정보와 함께 생성
     */
    constructor(errorCode: ErrorCode, conflictResource: String, conflictValue: Any) : super(errorCode) {
        withProperty("conflictResource", conflictResource)
        withProperty("conflictValue", conflictValue)
    }

    /**
     * 상태 충돌 정보와 함께 생성
     */
    constructor(errorCode: ErrorCode, currentState: String, requestedState: String) : super(errorCode) {
        withProperty("currentState", currentState)
        withProperty("requestedState", requestedState)
    }

    /**
     * 중복 리소스 정보와 함께 생성
     */
    constructor(errorCode: ErrorCode, resourceType: String, duplicateField: String, duplicateValue: Any) : super(errorCode) {
        withProperty("resourceType", resourceType)
        withProperty("duplicateField", duplicateField)
        withProperty("duplicateValue", duplicateValue)
    }

    /**
     * 비즈니스 로직 문제이므로 WARN 레벨
     */
    override fun getLogLevel(): LogLevel = LogLevel.WARN

    /**
     * 상황에 따라 재시도 가능
     */
    override fun isRetryable(): Boolean = true

    fun getConflictResource(): String? = properties["conflictResource"] as? String

    fun getCurrentState(): String? = properties["currentState"] as? String

    fun getRequestedState(): String? = properties["requestedState"] as? String
}
