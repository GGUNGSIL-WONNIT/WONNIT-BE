package com.woonit.wonnit.global.exception.handler

import com.woonit.wonnit.global.exception.base.BaseException
import com.woonit.wonnit.global.exception.business.*
import com.woonit.wonnit.global.exception.code.ErrorCode
import com.woonit.wonnit.global.exception.server.BadGatewayException
import com.woonit.wonnit.global.exception.server.GatewayTimeoutException
import com.woonit.wonnit.global.exception.server.InternalServerException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ProblemDetail
import java.net.URI
import java.time.Instant

class ProblemDetailBuilder {
    companion object {
        fun from(exception: BaseException, request: HttpServletRequest): ProblemDetail {
            return exception.toProblemDetail().apply {
                instance = URI.create(request.requestURI)
                setProperty("timestamp", Instant.now())
                setProperty("path", request.requestURI)

                addTypeSpecificProperties(exception, this)
            }
        }

        fun from(errorCode: ErrorCode, request: HttpServletRequest, properties: Map<String, Any> = emptyMap()): ProblemDetail {
            return errorCode.toProblemDetail(properties).apply {
                instance = URI.create(request.requestURI)
                setProperty("timestamp", Instant.now())
                setProperty("path", request.requestURI)
            }
        }

        private fun addTypeSpecificProperties(exception: BaseException, problemDetail: ProblemDetail) {
            when (exception) {
                is BadRequestException -> {
                    if (exception.hasFieldErrors()) {
                        problemDetail.setProperty("fieldErrors", exception.getFieldErrors())
                    }
                }
                is UnauthorizedException -> {
                    exception.getAuthenticationReason()?.let {
                        problemDetail.setProperty("authReason", it)
                    }
                    exception.getTokenType()?.let {
                        problemDetail.setProperty("tokenType", it)
                    }
                }
                is ForbiddenException -> {
                    exception.getResource()?.let {
                        problemDetail.setProperty("resource", it)
                    }
                    exception.getAction()?.let {
                        problemDetail.setProperty("action", it)
                    }
                }
                is NotFoundException -> {
                    exception.getResourceType()?.let {
                        problemDetail.setProperty("resourceType", it)
                    }
                    exception.getResourceId()?.let {
                        problemDetail.setProperty("resourceId", it)
                    }
                }
                is ConflictException -> {
                    exception.getConflictResource()?.let {
                        problemDetail.setProperty("conflictResource", it)
                    }
                    exception.getCurrentState()?.let {
                        problemDetail.setProperty("currentState", it)
                    }
                    exception.getRequestedState()?.let {
                        problemDetail.setProperty("requestedState", it)
                    }
                }
                is InternalServerException -> {
                    exception.getTraceId()?.let {
                        problemDetail.setProperty("traceId", it)
                    }
                    exception.getComponent()?.let {
                        problemDetail.setProperty("component", it)
                    }
                }
                is BadGatewayException -> {
                    exception.getServiceName()?.let {
                        problemDetail.setProperty("serviceName", it)
                    }
                    exception.getEndpoint()?.let {
                        problemDetail.setProperty("endpoint", it)
                    }
                }
                is GatewayTimeoutException -> {
                    exception.getServiceName()?.let {
                        problemDetail.setProperty("serviceName", it)
                    }
                    exception.getTimeoutMs()?.let {
                        problemDetail.setProperty("timeoutMs", it)
                    }
                    exception.getRetryCount()?.let {
                        problemDetail.setProperty("retryCount", it)
                    }
                }
            }
        }
    }
}