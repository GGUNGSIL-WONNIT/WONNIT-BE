package com.woonit.wonnit.domain.space.dto

import com.woonit.wonnit.domain.share.AddressInfo
import com.woonit.wonnit.domain.share.AmountInfo
import com.woonit.wonnit.domain.space.Space
import com.woonit.wonnit.domain.space.SpaceCategory
import com.woonit.wonnit.domain.space.SpaceStatus

data class MyRentalSpacePageResponse(
    val spaces: List<MyRentalSpaceResponse>,
    val totalCount: Long
) {
    companion object {
        fun of(spaces: List<MyRentalSpaceResponse>, totalCount: Long): MyRentalSpacePageResponse {
            return MyRentalSpacePageResponse(spaces, totalCount)
        }
    }
}

data class MyRentalSpaceResponse(
    val spaceId: String,
    val category: SpaceCategory,
    val name: String,
    val addressInfo: AddressInfo,
    val mainImgUrl: String,
    val amountInfo: AmountInfo,
    val status: SpaceStatus
) {
    companion object {
        fun from(space: Space): MyRentalSpaceResponse {
            return MyRentalSpaceResponse(
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