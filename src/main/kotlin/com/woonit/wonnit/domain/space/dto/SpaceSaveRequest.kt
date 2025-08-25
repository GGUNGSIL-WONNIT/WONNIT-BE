package com.woonit.wonnit.domain.space.dto

import com.woonit.wonnit.domain.share.AddressInfo
import com.woonit.wonnit.domain.share.AmountInfo
import com.woonit.wonnit.domain.share.OperationalInfo
import com.woonit.wonnit.domain.share.PhoneNumber
import com.woonit.wonnit.domain.space.SpaceCategory
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class SpaceSaveRequest(

    @NotNull
    @Schema(description = "공간 카테고리", example = "DANCE_STUDIO", required = true)
    val category: SpaceCategory,

    @NotBlank
    @Schema(description = "공간 이름", example = "홍대 스튜디오", required = true)
    val name: String,

    @NotBlank
    @Schema(description = "전화번호", example = "010-1234-5678", required = true)
    val phoneNumber: String,

    @NotBlank
    @Schema(description = "대표 이미지 URL", example = "https://wonnit.s3.ap-northeast-2.amazonaws.com/main.jpg", required = true)
    val mainImgUrl: String,

    @Schema(description = "서브 이미지 URL 목록", example = "[\"https://wonnit.s3.ap-northeast-2.amazonaws.com/sub1.jpg\", \"https://wonnit.s3.ap-northeast-2.amazonaws.com/sub2.jpg\"]")
    val subImgUrls: MutableList<String>,

    @NotNull
    @Schema(description = "주소 정보", required = true)
    val address: AddressInfo,

    @NotNull
    @Schema(description = "금액 정보", required = true)
    val amountInfo: AmountInfo,

    @NotNull
    @Schema(description = "공간 크기(m²)", example = "45.0", required = true)
    val size: Double,

    @NotNull
    @Schema(description = "운영 정보", required = true)
    val operationInfo: OperationalInfo,

    @Schema(description = "3D 공간 모델 URL", example = "https://wonnit.com/model.glb")
    val spaceModelUrl: String? = null,

    @Schema(description = "유의사항", example = "애완동물 출입 금지")
    val precautions: String? = null,

    @Schema(description = "모델 스캔 대표 이미지 URL", example = "https://wonnit.com/model-thumbnail.jpg")
    val modelThumbnailUrl: String? = null,

    @Schema(description = "ai 공간 태그 리스트", example = "[화이트보드]")
    val tags: MutableList<String> = mutableListOf(),
)
