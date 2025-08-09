package com.woonit.wonnit.global.exception.handler

import com.woonit.wonnit.global.exception.base.BaseException
import com.woonit.wonnit.global.exception.code.ErrorCode

class ExceptionBuilder(private val errorCode: ErrorCode) {
    private var customMessage: String? = null
    private var cause: Throwable? = null
    private val properties = mutableMapOf<String, Any>()

    fun message(message: String) = apply { this.customMessage = message }

    fun cause(cause: Throwable) = apply { this.cause = cause }

    fun property(key: String, value: Any) = apply { this.properties[key] = value }

    fun properties(props: Map<String, Any>) = apply { this.properties.putAll(props) }

    fun build(): BaseException {
        val exception = if (customMessage != null && cause != null) {
            ExceptionFactory.createException(errorCode, customMessage, cause)
        } else if (customMessage != null) {
            ExceptionFactory.createException(errorCode, customMessage)
        } else if (cause != null) {
            ExceptionFactory.createException(errorCode, cause = cause)
        } else {
            ExceptionFactory.createException(errorCode)
        }

        return exception.withProperties(properties)
    }
}

fun ErrorCode.toException() = ExceptionBuilder(this)

fun ErrorCode.toException(message: String) = ExceptionBuilder(this).message(message).build()

fun ErrorCode.toException(cause: Throwable) = ExceptionBuilder(this).cause(cause).build()

