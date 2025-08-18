package com.woonit.wonnit.domain.space.dto

import io.swagger.v3.oas.annotations.media.Schema

data class PresignUploadResponse(
    @Schema(description = "presigned URL", example = "https://example.amazon.aws.com")
    val uploadUrl: String,

    @Schema(description = "이미지 키 값", example = "uploads/0289s3e0-9231-297d-a001-0a02ba2dd12e-ex")
    val key: String
)