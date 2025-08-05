package com.woonit.wonnit.global.exception.base

import com.woonit.wonnit.global.exception.code.ErrorCode
import com.woonit.wonnit.global.exception.log.LogLevel
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail

abstract class BaseException : RuntimeException {

    val errorCode: ErrorCode
    protected val properties: MutableMap<String, Any>
    val httpStatus: HttpStatus
        get() = errorCode.httpStatus

    constructor(errorCode: ErrorCode) : super(errorCode.message) {
        this.errorCode = errorCode
        this.properties = mutableMapOf()
    }

    constructor(errorCode: ErrorCode, customMessage: String) : super(customMessage) {
        this.errorCode = errorCode
        this.properties = mutableMapOf()
    }

    constructor(errorCode: ErrorCode, cause: Throwable) : super(errorCode.message, cause) {
        this.errorCode = errorCode
        this.properties = mutableMapOf()
    }

    constructor(errorCode: ErrorCode, customMessage: String, cause: Throwable) : super(customMessage, cause) {
        this.errorCode = errorCode
        this.properties = mutableMapOf()
    }

    // ========== 공통 ==========

    fun withProperty(key: String, value: Any): BaseException {
        properties[key] = value
        return this
    }

    fun withProperties(props: Map<String, Any>): BaseException {
        properties.putAll(props)
        return this
    }

    open fun toProblemDetail(): ProblemDetail {
        return if (message == errorCode.message) {
            errorCode.toProblemDetail(properties)
        } else {
            errorCode.toProblemDetail(message!!).apply {
                properties?.forEach { (key, value) -> setProperty(key, value) }
            }
        }
    }

    abstract fun getLogLevel(): LogLevel

    override fun toString(): String {
        return "${this::class.simpleName}(errorCode=${errorCode.code}, message='$message', properties=$properties)"
    }
}