package com.woonit.wonnit.domain.space.dto

import com.woonit.wonnit.domain.share.AddressInfo
import com.woonit.wonnit.domain.share.AmountInfo
import com.woonit.wonnit.domain.space.Space
import com.woonit.wonnit.domain.space.SpaceCategory
import com.woonit.wonnit.domain.space.SpaceStatus

data class SpaceSearchResponse(
    val spaceId: String,
    val category: SpaceCategory,
    val name: String,
    val addressInfo: AddressInfo,
    val mainImgUrl: String,
    val amountInfo: AmountInfo,
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
