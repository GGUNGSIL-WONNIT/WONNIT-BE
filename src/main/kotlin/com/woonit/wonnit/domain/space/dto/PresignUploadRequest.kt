package com.woonit.wonnit.domain.space.dto

import io.swagger.v3.oas.annotations.media.Schema

data class PresignUploadRequest(
    @Schema(description = "이미지명")
    val fileName: String
)