package com.woonit.wonnit.domain.space.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class ReturnSpaceRequest(

    @NotBlank
    @Schema(
        description = "사용 전 이미지",
        example = "https://wonnit.s3.ap-northeast-2.amazonaws.com/before.jpg",
        required = true
    )
    val beforeImgUrl: String,

    @NotBlank
    @Schema(
        description = "사용 후 이미지",
        example = "https://wonnit.s3.ap-northeast-2.amazonaws.com/after.jpg",
        required = true
    )
    val afterImgUrl: String,

    @NotBlank
    @Schema(
        description = "비교 분석 결과 이미지",
        example = "https://wonnit.s3.ap-northeast-2.amazonaws.com/result.jpg",
        required = true
    )
    val resultImgUrl: String,

    @NotNull
    @Schema(
        description = "비교 분석 유사도 퍼센티지",
        example = "80",
        required = true
    )
    val similarity: Double
)