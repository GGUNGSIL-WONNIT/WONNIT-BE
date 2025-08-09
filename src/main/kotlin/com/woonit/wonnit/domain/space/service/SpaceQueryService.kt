package com.woonit.wonnit.domain.space.service

import com.woonit.wonnit.domain.space.dto.MySpaceResponse
import com.woonit.wonnit.domain.space.dto.RecentSpaceResponse
import com.woonit.wonnit.domain.space.dto.SpaceSearchResponse
import com.woonit.wonnit.domain.space.repository.SpaceQueryRepository
import org.springframework.stereotype.Service

@Service
class SpaceQueryService(
    val spaceQueryRepository: SpaceQueryRepository,
) {
    fun getMySpaces(userId: String, page: Int): List<MySpaceResponse> {
        return spaceQueryRepository.findMySpaces(userId, page)
            .map { space -> MySpaceResponse.from(space) }
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