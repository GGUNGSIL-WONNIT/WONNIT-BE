package com.woonit.wonnit.domain.space.service

import com.woonit.wonnit.domain.space.dto.*
import com.woonit.wonnit.domain.space.repository.SpaceQueryRepository
import org.springframework.stereotype.Service

@Service
class SpaceQueryService(
    val spaceQueryRepository: SpaceQueryRepository,
) {
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