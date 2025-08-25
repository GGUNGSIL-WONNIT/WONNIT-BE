package com.woonit.wonnit.domain.space.dto

import io.swagger.v3.oas.annotations.media.Schema

data class PresignUploadResponse(
    @Schema(description = "presigned URL", example = "https://example.amazon.aws.com")
    val uploadUrl: String,

    @Schema(description = "이미지 url", example = "https://wonnit.s3.ap-northeast-2.amazonaws.com/d2e1ec9b-6191-4769-9f22-ca673f24c1a3-%25EB%25A7%2588%25EB%25A6%25B0.jpg")
    val url: String
)