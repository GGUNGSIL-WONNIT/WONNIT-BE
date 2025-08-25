package com.woonit.wonnit.domain.space.service

import com.woonit.wonnit.domain.space.dto.*
import com.woonit.wonnit.domain.space.repository.SpaceQueryRepository
import com.woonit.wonnit.global.exception.business.NotFoundException
import com.woonit.wonnit.global.exception.code.SpaceErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class SpaceQueryService(
    private val spaceQueryRepository: SpaceQueryRepository,
) {
    /**
     * Retrieves the detailed information for a single space.
     *
     * @param spaceId The UUID of the space to retrieve.
     * @return A [SpaceDetailResponse] containing the full details of the space.
     * @throws NotFoundException if the space with the given ID is not found.
     */
    fun getSpaceDetail(spaceId: UUID): SpaceDetailResponse {
        val space = spaceQueryRepository.findSpace(spaceId)
            ?: throw NotFoundException(SpaceErrorCode.NOT_FOUND)
        val subImageUrls = spaceQueryRepository.findSubImageUrls(space.id)
        val tags = spaceQueryRepository.findTags(space.id)

        return SpaceDetailResponse.from(space, subImageUrls, tags)
    }

    /**
     * Retrieves a paginated list of spaces registered by a specific user.
     *
     * @param userId The ID of the user.
     * @param page The page number for pagination.
     * @return A [MySpacePageResponse] containing the list of spaces and pagination information.
     */
    fun getMySpaces(userId: String, page: Int): MySpacePageResponse {
        val spaces = spaceQueryRepository.findMySpaces(userId, page)
        val totalCount = spaceQueryRepository.countMySpaces(userId)
        return MySpacePageResponse.of(
            spaces.map { MySpaceResponse.from(it) },
            totalCount,
        )
    }

    /**
     * Retrieves a paginated list of spaces currently rented by a specific user.
     *
     * @param userId The ID of the user.
     * @param page The page number for pagination.
     * @return A [MyRentalSpacePageResponse] containing the list of rented spaces and pagination information.
     */
    fun getMyRentalSpaces(userId: String, page: Int): MyRentalSpacePageResponse {
        val rentalSpaces = spaceQueryRepository.findMyRentalSpaces(userId, page)
        val totalCount = spaceQueryRepository.countMyRentalSpaces(userId)
        return MyRentalSpacePageResponse.of(
            rentalSpaces.map { MyRentalSpaceResponse.from(it) },
            totalCount,
        )
    }

    /**
     * Retrieves a list of the most recently registered spaces.
     *
     * @return A list of [RecentSpaceResponse] objects.
     */
    fun getRecentSpaces(): List<RecentSpaceResponse> {
        return spaceQueryRepository.findRecentSpaces()
            .map { RecentSpaceResponse.from(it) }
    }

    /**
     * Retrieves a list of spaces near a given geographical coordinate.
     *
     * @param lat The latitude of the center point.
     * @param lon The longitude of the center point.
     * @return A list of [SpaceSearchResponse] objects.
     */
    fun getNearBySpaces(lat: Double, lon: Double): List<SpaceSearchResponse> {
        return spaceQueryRepository.findNearBySpaces(lat, lon)
            .map { SpaceSearchResponse.from(it) }
    }
}
