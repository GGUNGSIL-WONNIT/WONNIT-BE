package com.woonit.wonnit.domain.space.dto

import com.woonit.wonnit.domain.share.AddressInfo
import com.woonit.wonnit.domain.space.Space
import com.woonit.wonnit.domain.space.SpaceCategory

data class RecentSpaceResponse(
    val spaceId: String,
    val category: SpaceCategory,
    val name: String,
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
