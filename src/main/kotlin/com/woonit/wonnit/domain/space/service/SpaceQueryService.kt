package com.woonit.wonnit.domain.space.service

import com.woonit.wonnit.domain.space.dto.RecentSpaceResponse
import com.woonit.wonnit.domain.space.repository.SpaceQueryRepository
import org.springframework.stereotype.Service

@Service
class SpaceQueryService(
    val spaceQueryRepository: SpaceQueryRepository,
) {

    fun getRecentSpaces(): List<RecentSpaceResponse> {
        return spaceQueryRepository.findRecentSpaces()
            .map { space -> RecentSpaceResponse.from(space) }
    }

}