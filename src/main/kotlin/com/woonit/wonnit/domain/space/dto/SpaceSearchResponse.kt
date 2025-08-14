package com.woonit.wonnit.domain.space.dto

import com.woonit.wonnit.domain.share.AddressInfo
import com.woonit.wonnit.domain.share.AmountInfo
import com.woonit.wonnit.domain.space.Space
import com.woonit.wonnit.domain.space.SpaceCategory
import com.woonit.wonnit.domain.space.SpaceStatus
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "탐색된 공간 응답")
data class SpaceSearchResponse(
    @Schema(description = "공간 ID")
    val spaceId: String,
    @Schema(description = "카테고리")
    val category: SpaceCategory,
    @Schema(description = "공간 이름")
    val name: String,
    @Schema(description = "주소 정보")
    val addressInfo: AddressInfo,
    @Schema(description = "메인 이미지 URL")
    val mainImgUrl: String,
    @Schema(description = "가격 정보")
    val amountInfo: AmountInfo,
    @Schema(description = "공간 상태")
    val status: SpaceStatus
) {
    companion object {
        fun from(space: Space): SpaceSearchResponse {
            return SpaceSearchResponse(
                spaceId = space.id.toString(),
                category = space.spaceCategory,
                name = space.name,
                addressInfo = space.addressInfo,
                mainImgUrl = space.mainImgUrl,
                amountInfo = space.amountInfo,
                status = space.spaceStatus
            )
        }
    }
}
