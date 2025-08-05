package com.woonit.wonnit.global.exception.code

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import java.net.URI

// RFC 7807 Problem Details for HTTP APIs Standard
interface ErrorCode {
    val code: String
    val message: String
    val httpStatus: HttpStatus
    val type: String

    // 1. 기본 ProblemDetail 생성 - 기본 메시지 사용
    fun toProblemDetail(): ProblemDetail {
        val problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, message)

        problemDetail.type = URI.create(type)
        problemDetail.title = httpStatus.name
        problemDetail.setProperty("errorCode", code)

        return problemDetail
    }

    // 2. 커스텀 메시지로 ProblemDetail 생성
    fun toProblemDetail(customMessage: String?): ProblemDetail {
        val problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, customMessage)

        problemDetail.type = URI.create(type)
        problemDetail.title = httpStatus.name
        problemDetail.setProperty("errorCode", code)

        return problemDetail
    }

    // 3. 추가 속성과 함께 ProblemDetail 생성
    fun toProblemDetail(properties: Map<String, Any>): ProblemDetail {
        val problemDetail = toProblemDetail()
        properties.forEach { (name, value) -> problemDetail.setProperty(name, value) }

        return problemDetail
    }
}