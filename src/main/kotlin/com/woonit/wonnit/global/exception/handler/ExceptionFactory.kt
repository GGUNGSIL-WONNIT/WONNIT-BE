package com.woonit.wonnit.global.exception.handler

import com.woonit.wonnit.global.exception.base.BaseException
import com.woonit.wonnit.global.exception.business.*
import com.woonit.wonnit.global.exception.code.ErrorCode
import com.woonit.wonnit.global.exception.server.BadGatewayException
import com.woonit.wonnit.global.exception.server.GatewayTimeoutException
import com.woonit.wonnit.global.exception.server.InternalServerException
import com.woonit.wonnit.global.exception.server.ServerException
import org.springframework.http.HttpStatus

object ExceptionFactory {
    fun createException(errorCode: ErrorCode, message: String? = null, cause: Throwable? = null): BaseException {
        return when (errorCode.httpStatus.value()) {
            in 400..499 -> createBusinessException(errorCode, message, cause)
            in 500..599 -> createSystemException(errorCode, message, cause)
            else -> throw IllegalArgumentException("Unsupported HTTP status: ${errorCode.httpStatus}")
        }
    }

    private fun createBusinessException(errorCode: ErrorCode, message: String?, cause: Throwable?): BusinessException {
        return when (errorCode.httpStatus) {
            HttpStatus.BAD_REQUEST -> when {
                message != null && cause != null -> BadRequestException(errorCode, message, cause)
                message != null -> BadRequestException(errorCode, message)
                cause != null -> BadRequestException(errorCode, cause)
                else -> BadRequestException(errorCode)
            }
            HttpStatus.UNAUTHORIZED -> when {
                message != null && cause != null -> UnauthorizedException(errorCode, message, cause)
                message != null -> UnauthorizedException(errorCode, message)
                cause != null -> UnauthorizedException(errorCode, cause)
                else -> UnauthorizedException(errorCode)
            }
            HttpStatus.FORBIDDEN -> when {
                message != null && cause != null -> ForbiddenException(errorCode, message, cause)
                message != null -> ForbiddenException(errorCode, message)
                cause != null -> ForbiddenException(errorCode, cause)
                else -> ForbiddenException(errorCode)
            }
            HttpStatus.NOT_FOUND -> when {
                message != null && cause != null -> NotFoundException(errorCode, message, cause)
                message != null -> NotFoundException(errorCode, message)
                cause != null -> NotFoundException(errorCode, cause)
                else -> NotFoundException(errorCode)
            }
            HttpStatus.METHOD_NOT_ALLOWED -> when {
                message != null && cause != null -> MethodNotAllowedException(errorCode, message, cause)
                message != null -> MethodNotAllowedException(errorCode, message)
                cause != null -> MethodNotAllowedException(errorCode, cause)
                else -> MethodNotAllowedException(errorCode)
            }
            HttpStatus.CONFLICT -> when {
                message != null && cause != null -> ConflictException(errorCode, message, cause)
                message != null -> ConflictException(errorCode, message)
                cause != null -> ConflictException(errorCode, cause)
                else -> ConflictException(errorCode)
            }
            else -> object : BusinessException(errorCode, message ?: errorCode.message) {
                init { cause?.let { initCause(it) } }
            }
        }
    }

    private fun createSystemException(errorCode: ErrorCode, message: String?, cause: Throwable?): ServerException {
        return when (errorCode.httpStatus) {
            HttpStatus.INTERNAL_SERVER_ERROR -> when {
                message != null && cause != null -> InternalServerException(errorCode, message, cause)
                message != null -> InternalServerException(errorCode, message)
                cause != null -> InternalServerException(errorCode, cause)
                else -> InternalServerException(errorCode)
            }
            HttpStatus.BAD_GATEWAY -> when {
                message != null && cause != null -> BadGatewayException(errorCode, message, cause)
                message != null -> BadGatewayException(errorCode, message)
                cause != null -> BadGatewayException(errorCode, cause)
                else -> BadGatewayException(errorCode)
            }
            HttpStatus.GATEWAY_TIMEOUT -> when {
                message != null && cause != null -> GatewayTimeoutException(errorCode, message, cause)
                message != null -> GatewayTimeoutException(errorCode, message)
                cause != null -> GatewayTimeoutException(errorCode, cause)
                else -> GatewayTimeoutException(errorCode)
            }
            else -> object : ServerException(errorCode, message ?: errorCode.message) {
                init { cause?.let { initCause(it) } }
            }
        }
    }
}