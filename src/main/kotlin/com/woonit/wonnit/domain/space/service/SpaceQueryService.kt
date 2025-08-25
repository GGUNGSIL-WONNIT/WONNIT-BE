package com.woonit.wonnit.domain.space.service

import com.woonit.wonnit.domain.space.dto.*
import com.woonit.wonnit.domain.space.dto.MySpaceResponse
import com.woonit.wonnit.domain.space.dto.RecentSpaceResponse
import com.woonit.wonnit.domain.space.dto.SpaceDetailResponse
import com.woonit.wonnit.domain.space.dto.SpaceSearchResponse
import com.woonit.wonnit.domain.space.repository.SpaceQueryRepository
import com.woonit.wonnit.global.exception.business.NotFoundException
import com.woonit.wonnit.global.exception.code.SpaceErrorCode
import org.springframework.stereotype.Service
import java.util.*

@Service
class SpaceQueryService(
    val spaceQueryRepository: SpaceQueryRepository,
) {
    fun getSpaceDetail(spaceId: UUID): SpaceDetailResponse {
        val space = spaceQueryRepository.findSpace(spaceId)
            ?: throw NotFoundException(SpaceErrorCode.NOT_FOUND)
        val subImageUrls = spaceQueryRepository.findSubImageUrls(space.id)
        val tags = spaceQueryRepository.findTags(space.id)

        return SpaceDetailResponse.from(space, subImageUrls, tags)
    }

    fun getMySpaces(userId: String, page: Int): MySpacePageResponse {
        return MySpacePageResponse.of(
            spaceQueryRepository.findMySpaces(userId, page)
                .map { space -> MySpaceResponse.from(space) },
            spaceQueryRepository.countMySpaces(userId)
        )
    }

    fun getMyRentalSpaces(userId: String, page: Int): MyRentalSpacePageResponse {
        return MyRentalSpacePageResponse.of(
            spaceQueryRepository.findMyRentalSpaces(userId, page)
                .map { space -> MyRentalSpaceResponse.from(space) },
            spaceQueryRepository.countMyRentalSpaces(userId)
        )
    }

    fun getRecentSpaces(): List<RecentSpaceResponse> {
        return spaceQueryRepository.findRecentSpaces()
            .map { space -> RecentSpaceResponse.from(space) }
    }

    fun getNearBySpaces(lat: Double, lon: Double): List<SpaceSearchResponse> {
        return spaceQueryRepository.findNearBySpaces(lat, lon)
            .map { space -> SpaceSearchResponse.from(space) }
    }
}