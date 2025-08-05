package com.woonit.wonnit.global.exception.log

import com.woonit.wonnit.global.exception.code.ErrorCode
import com.woonit.wonnit.global.exception.base.BaseException
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class DefaultLoggingStrategy : LoggingStrategy {

    private val logger: Logger = LoggerFactory.getLogger(DefaultLoggingStrategy::class.java)

    override fun log(exception: BaseException, request: HttpServletRequest) {
        val logMessage = createLogMessage(exception, request)

        when (exception.getLogLevel()) {
            LogLevel.DEBUG -> logger.debug(logMessage, exception)
            LogLevel.INFO -> logger.info(logMessage)
            LogLevel.WARN -> logger.warn(logMessage)
            LogLevel.ERROR -> logger.error(logMessage, exception)
        }
    }

    override fun log(exception: Exception, request: HttpServletRequest, errorCode: ErrorCode) {
        val logMessage = createLogMessage(exception, request, errorCode)
        logger.error(logMessage, exception)
    }

    private fun createLogMessage(exception: BaseException, request: HttpServletRequest): String {
        return "Exception [${exception.errorCode.code}]: ${exception.message} at ${request.requestURI}"
    }

    private fun createLogMessage(exception: Exception, request: HttpServletRequest, errorCode: ErrorCode): String {
        return "Exception [${errorCode.code}]: ${exception.message} at ${request.requestURI}"
    }
}