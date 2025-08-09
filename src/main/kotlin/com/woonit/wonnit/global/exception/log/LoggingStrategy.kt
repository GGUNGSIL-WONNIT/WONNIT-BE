package com.woonit.wonnit.global.exception.log

import com.woonit.wonnit.global.exception.base.BaseException
import com.woonit.wonnit.global.exception.code.ErrorCode
import jakarta.servlet.http.HttpServletRequest

interface LoggingStrategy {
    fun log(exception: BaseException, request: HttpServletRequest)
    fun log(exception: Exception, request: HttpServletRequest, errorCode: ErrorCode)
}