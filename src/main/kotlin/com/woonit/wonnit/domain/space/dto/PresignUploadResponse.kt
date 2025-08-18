package com.woonit.wonnit.domain.space.dto

data class PresignUploadResponse(
    val uploadUrl: String,
    val key: String
)