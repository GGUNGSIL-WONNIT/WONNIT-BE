package com.woonit.wonnit.domain.space.service

import com.woonit.wonnit.domain.space.Space
import com.woonit.wonnit.domain.space.dto.ReturnSpaceRequest
import com.woonit.wonnit.domain.space.repository.SpaceRepository
import com.woonit.wonnit.domain.user.User
import com.woonit.wonnit.domain.user.repository.UserRepository
import com.woonit.wonnit.global.config.logger
import com.woonit.wonnit.global.exception.business.ForbiddenException
import com.woonit.wonnit.global.exception.code.SpaceErrorCode
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
@Transactional
class SpaceRentService(
    private val spaceRepository: SpaceRepository,
    private val userRepository: UserRepository,
) {

    /**
     * Rents a space to a user.
     *
     * @param userId The ID of the user who wants to rent the space.
     * @param spaceId The ID of the space to be rented.
     * @throws NoSuchElementException if the user or space is not found.
     */
    fun rent(userId: String, spaceId: String) {
        logger<SpaceRentService>().info("Rent for spaceId: $spaceId, userId: $userId")
        val space = getSpace(spaceId)
        val renter = getUser(userId)
        space.rent(renter)
    }

    /**
     * Submits a return request for a rented space.
     *
     * @param userId The ID of the user (renter) submitting the return request.
     * @param spaceId The ID of the space to be returned.
     * @param request The request object containing details for the return.
     * @throws NoSuchElementException if the user or space is not found.
     */
    fun returnRequest(userId: String, spaceId: String, request: ReturnSpaceRequest) {
        logger<SpaceRentService>().info("Return request spaceId: $spaceId, userId: $userId")
        val space = getSpace(spaceId)
        val renter = getUser(userId)
        space.returnRequest(renter, request)
    }

    /**
     * Rejects a return request for a space.
     *
     * @param ownerId The ID of the space owner.
     * @param spaceId The ID of the space.
     * @throws ForbiddenException if the user is not the owner of the space.
     * @throws NoSuchElementException if the space is not found.
     */
    fun returnReject(ownerId: String, spaceId: String) {
        logger<SpaceRentService>().info("Return Reject spaceId: $spaceId, ownerId: $ownerId")
        val space = getSpace(spaceId)
        checkIsOwner(space.user.id, ownerId)
        space.returnReject()
    }

    /**
     * Approves a return request for a space.
     *
     * @param ownerId The ID of the space owner.
     * @param spaceId The ID of the space.
     * @throws ForbiddenException if the user is not the owner of the space.
     * @throws NoSuchElementException if the space is not found.
     */
    fun returnApprove(ownerId: String, spaceId: String) {
        logger<SpaceRentService>().info("Return Approve spaceId: $spaceId, ownerId: $ownerId")
        val space = getSpace(spaceId)
        checkIsOwner(space.user.id, ownerId)
        space.returnApprove()
    }

    /**
     * Re-registers a returned space, making it available for rent again.
     *
     * @param ownerId The ID of the space owner.
     * @param spaceId The ID of the space.
     * @throws ForbiddenException if the user is not the owner of the space.
     * @throws NoSuchElementException if the space is not found.
     */
    fun reRegistration(ownerId: String, spaceId: String) {
        logger<SpaceRentService>().info("Re-Registration spaceId: $spaceId, ownerId: $ownerId")
        val space = getSpace(spaceId)
        checkIsOwner(space.user.id, ownerId)
        space.reRegistration()
    }

    private fun checkIsOwner(userId: UUID, ownerId: String) {
        if (userId.toString() != ownerId) {
            throw ForbiddenException(SpaceErrorCode.NO_OWNER)
        }
    }

    private fun getUser(userId: String): User = (userRepository.findByIdOrNull(UUID.fromString(userId))
        ?: throw NoSuchElementException("User not found with ID: $userId"))

    private fun getSpace(spaceId: String): Space = spaceRepository.findByIdOrNull(UUID.fromString(spaceId))
        ?: throw NoSuchElementException("Space not found with ID: $spaceId")
}
