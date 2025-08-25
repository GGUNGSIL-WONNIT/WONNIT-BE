package com.woonit.wonnit.global.exception.handler

import com.woonit.wonnit.global.exception.base.BaseException
import com.woonit.wonnit.global.exception.business.BadRequestException
import com.woonit.wonnit.global.exception.business.BusinessException
import com.woonit.wonnit.global.exception.code.CommonErrorCode
import com.woonit.wonnit.global.exception.log.DefaultLoggingStrategy
import com.woonit.wonnit.global.exception.log.LoggingStrategy
import com.woonit.wonnit.global.exception.server.BadGatewayException
import com.woonit.wonnit.global.exception.server.InternalServerException
import com.woonit.wonnit.global.exception.server.ServerException
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.ConstraintViolationException
import org.springframework.dao.DataAccessException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.ResourceAccessException
import org.springframework.web.multipart.MaxUploadSizeExceededException
import java.util.*

@RestControllerAdvice
class GlobalExceptionHandler(
    private val loggingStrategy: LoggingStrategy = DefaultLoggingStrategy()
) {

    // ========== 1. 비즈니스 예외 처리 (4xx) ==========
    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(
        exception: BusinessException,
        request: HttpServletRequest
    ): ResponseEntity<ProblemDetail> {

        loggingStrategy.log(exception, request)

        val problemDetail = ProblemDetailBuilder.from(exception, request)

        return ResponseEntity
            .status(exception.httpStatus)
            .body(problemDetail)
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(
        exception: BadRequestException,
        request: HttpServletRequest
    ): ResponseEntity<ProblemDetail> {

        loggingStrategy.log(exception, request)

        val problemDetail = ProblemDetailBuilder.from(exception, request)

        if (exception.hasFieldErrors()) {
            problemDetail.setProperty("validationFailed", true)
        }

        return ResponseEntity.badRequest().body(problemDetail)
    }

    @ExceptionHandler(IllegalArgumentException::class, IllegalStateException::class)
    fun handleIllegalArgumentException(
        exception: Exception,
        request: HttpServletRequest
    ): ResponseEntity<ProblemDetail> {
        val badRequestException = BadRequestException(CommonErrorCode.INVALID_INPUT, exception.message!!)
        loggingStrategy.log(badRequestException, request)

        val problemDetail = ProblemDetailBuilder.from(badRequestException, request)

        if (badRequestException.hasFieldErrors()) {
            problemDetail.setProperty("validationFailed", true)
        }

        return ResponseEntity.badRequest().body(problemDetail)
    }

    // ========== 2. 시스템 예외 처리 (5xx) ==========
    @ExceptionHandler(ServerException::class)
    fun handleSystemException(
        exception: ServerException,
        request: HttpServletRequest
    ): ResponseEntity<ProblemDetail> {

        loggingStrategy.log(exception, request)

        val problemDetail = ProblemDetailBuilder.from(exception, request)

        // 시스템 예외는 사용자에게 기술 세부노출 x
        if (exception !is InternalServerException) {
            problemDetail.detail = exception.getUserMessage()
        }

        return ResponseEntity
            .status(exception.httpStatus)
            .body(problemDetail)
    }

    // 최상위 BaseException 처리 - 위에서 처리되지 않은 모든 커스텀 예외의 최종 처리
    @ExceptionHandler(BaseException::class)
    fun handleBaseException(
        exception: BaseException,
        request: HttpServletRequest
    ): ResponseEntity<ProblemDetail> {

        loggingStrategy.log(exception, request)

        val problemDetail = ProblemDetailBuilder.from(exception, request)

        return ResponseEntity
            .status(exception.httpStatus)
            .body(problemDetail)
    }

    // ========== 3. Spring 기본 예외 처리 ==========
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(
        exception: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): ResponseEntity<ProblemDetail> {

        val fieldErrors = exception.bindingResult.fieldErrors.associate { error ->
            error.field to (error.defaultMessage ?: "유효하지 않은 값입니다")
        }

        val properties = mapOf("fieldErrors" to fieldErrors)
        val problemDetail = ProblemDetailBuilder.from(CommonErrorCode.INVALID_INPUT, request, properties)

        loggingStrategy.log(exception, request, CommonErrorCode.INVALID_INPUT)

        return ResponseEntity.badRequest().body(problemDetail)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(
        exception: ConstraintViolationException,
        request: HttpServletRequest
    ): ResponseEntity<ProblemDetail> {

        val violations = exception.constraintViolations.map { violation ->
            "${violation.propertyPath}: ${violation.message}"
        }

        val properties = mapOf("violations" to violations)
        val problemDetail = ProblemDetailBuilder.from(CommonErrorCode.INVALID_INPUT, request, properties)

        loggingStrategy.log(exception, request, CommonErrorCode.INVALID_INPUT)

        return ResponseEntity.badRequest().body(problemDetail)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(
        exception: HttpMessageNotReadableException,
        request: HttpServletRequest
    ): ResponseEntity<ProblemDetail> {

        val problemDetail = ProblemDetailBuilder.from(CommonErrorCode.MALFORMED_REQUEST, request)

        loggingStrategy.log(exception, request, CommonErrorCode.MALFORMED_REQUEST)

        return ResponseEntity.badRequest().body(problemDetail)
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleMethodNotSupportedException(
        exception: HttpRequestMethodNotSupportedException,
        request: HttpServletRequest
    ): ResponseEntity<ProblemDetail> {

        val properties = mapOf(
            "method" to exception.method,
            "supportedMethods" to (exception.supportedMethods ?: emptyArray())
        )

        val problemDetail = ProblemDetailBuilder.from(CommonErrorCode.METHOD_NOT_ALLOWED, request, properties)
        problemDetail.detail = "HTTP ${exception.method} 메서드는 지원되지 않습니다."

        loggingStrategy.log(exception, request, CommonErrorCode.METHOD_NOT_ALLOWED)

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(problemDetail)
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException::class)
    fun handleMediaTypeNotSupportedException(
        exception: HttpMediaTypeNotSupportedException,
        request: HttpServletRequest
    ): ResponseEntity<ProblemDetail> {

        val properties = buildMap {
            exception.contentType?.let { put("contentType", it) }
            put("supportedMediaTypes", exception.supportedMediaTypes)
        }

        val problemDetail = ProblemDetailBuilder.from(CommonErrorCode.MALFORMED_REQUEST, request, properties)
        problemDetail.detail = "미디어 타입 '${exception.contentType}'는 지원되지 않습니다."

        loggingStrategy.log(exception, request, CommonErrorCode.MALFORMED_REQUEST)

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(problemDetail)
    }

    @ExceptionHandler(MaxUploadSizeExceededException::class)
    fun handleMaxUploadSizeExceededException(
        exception: MaxUploadSizeExceededException,
        request: HttpServletRequest
    ): ResponseEntity<ProblemDetail> {

        val properties = mapOf("maxSize" to exception.maxUploadSize)
        val problemDetail = ProblemDetailBuilder.from(CommonErrorCode.INVALID_INPUT, request, properties)
        problemDetail.detail = "파일 크기가 제한을 초과했습니다."

        loggingStrategy.log(exception, request, CommonErrorCode.INVALID_INPUT)

        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(problemDetail)
    }

    // ========== 4. 시스템 레벨 예외 처리 ==========
    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(
        exception: AccessDeniedException,
        request: HttpServletRequest
    ): ResponseEntity<ProblemDetail> {

        val problemDetail = ProblemDetailBuilder.from(CommonErrorCode.ACCESS_DENIED, request)

        loggingStrategy.log(exception, request, CommonErrorCode.ACCESS_DENIED)

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(problemDetail)
    }

    @ExceptionHandler(DataIntegrityViolationException::class, DataAccessException::class)
    fun handleDataAccessException(
        exception: Exception,
        request: HttpServletRequest
    ): ResponseEntity<ProblemDetail> {

        val systemException = InternalServerException(CommonErrorCode.INTERNAL_SERVER_ERROR, "Database", "query")
        val problemDetail = ProblemDetailBuilder.from(systemException, request)

        loggingStrategy.log(systemException, request)

        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail)
    }

    @ExceptionHandler(ResourceAccessException::class)
    fun handleExternalServiceException(
        exception: ResourceAccessException,
        request: HttpServletRequest
    ): ResponseEntity<ProblemDetail> {

        val systemException = BadGatewayException(CommonErrorCode.EXTERNAL_SERVICE_ERROR, "ExternalService", "unknown")
        val problemDetail = ProblemDetailBuilder.from(systemException, request)

        loggingStrategy.log(systemException, request)

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(problemDetail)
    }

    // ========== 5. 최종 예외 처리 ==========
    @ExceptionHandler(Exception::class)
    fun handleUnexpectedException(
        exception: Exception,
        request: HttpServletRequest
    ): ResponseEntity<ProblemDetail> {

        val traceId = UUID.randomUUID().toString().substring(0, 8)
        val systemException = InternalServerException(CommonErrorCode.INTERNAL_SERVER_ERROR, traceId)

        val problemDetail = ProblemDetailBuilder.from(systemException, request)

        exception.printStackTrace()

        loggingStrategy.log(systemException, request)

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail)
    }
}