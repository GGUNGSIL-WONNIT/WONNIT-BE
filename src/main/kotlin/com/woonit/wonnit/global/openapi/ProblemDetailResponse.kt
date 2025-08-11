package com.woonit.wonnit.global.openapi

import io.swagger.v3.oas.annotations.media.Schema

data class ProblemDetailResponse(

    @Schema(description = "문제 유형 URI", example = "urn:problem:user:USER_404_01")
    val type: String? = null,

    @Schema(description = "짧은 에러 요약(또는 에러 코드)", example = "USER_404_01")
    val title: String? = null,

    @Schema(description = "HTTP 상태 코드", example = "404")
    val status: Int? = null,

    @Schema(description = "상세 메시지", example = "사용자를 찾을 수 없습니다.")
    val detail: String? = null,

    @Schema(description = "에러가 발생한 요청 경로", example = "/auth/me")
    val instance: String? = null,

    @Schema(
        description = "추가 속성(필드 에러, 지원 메서드, 최대 업로드 크기 등)",
        type = "object",
        example = """{"fieldErrors":{"name":"must not be blank"},"validationFailed":true}"""
    )
    val properties: Map<String, @JvmSuppressWildcards Any?>? = null
)