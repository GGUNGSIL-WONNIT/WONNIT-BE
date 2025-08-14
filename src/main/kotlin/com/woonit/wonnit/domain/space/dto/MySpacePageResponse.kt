package com.woonit.wonnit.domain.space.dto

import com.woonit.wonnit.domain.share.AddressInfo
import com.woonit.wonnit.domain.share.AmountInfo
import com.woonit.wonnit.domain.space.Space
import com.woonit.wonnit.domain.space.SpaceCategory
import com.woonit.wonnit.domain.space.SpaceStatus
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "내가 등록한 공간 페이징 응답")
data class MySpacePageResponse(
    @Schema(description = "내가 등록한 공간 목록")
    val spaces: List<MySpaceResponse>,
    @Schema(description = "총 개수")
    val totalCount: Long
) {
    companion object {
        fun of(spaces: List<MySpaceResponse>, totalCount: Long): MySpacePageResponse {
            return MySpacePageResponse(spaces, totalCount)
        }
    }
}

@Schema(description = "내가 등록한 공간 응답")
data class MySpaceResponse(
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
        fun from(space: Space): MySpaceResponse {
            return MySpaceResponse(
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