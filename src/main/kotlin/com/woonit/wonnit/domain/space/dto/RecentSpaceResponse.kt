package com.woonit.wonnit.domain.space.dto

import com.woonit.wonnit.domain.share.AddressInfo
import com.woonit.wonnit.domain.space.Space
import com.woonit.wonnit.domain.space.SpaceCategory
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "최근 추가된 공간 응답")
data class RecentSpaceResponse(
    @Schema(description = "공간 ID")
    val spaceId: String,
    @Schema(description = "카테고리")
    val category: SpaceCategory,
    @Schema(description = "공간 이름")
    val name: String,
    @Schema(description = "주소 정보")
    val addressInfo: AddressInfo,
) {
    companion object {
        fun from(space: Space): RecentSpaceResponse {
            return RecentSpaceResponse(
                spaceId = space.id.toString(),
                category = space.spaceCategory,
                name = space.name,
                addressInfo = space.addressInfo,
            )
        }
    }
}
