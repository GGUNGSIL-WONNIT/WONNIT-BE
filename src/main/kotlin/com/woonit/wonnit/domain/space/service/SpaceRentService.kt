package com.woonit.wonnit.domain.space.service

import com.woonit.wonnit.domain.space.Space
import com.woonit.wonnit.domain.space.dto.ReturnSpaceRequest
import com.woonit.wonnit.domain.space.repository.SpaceRepository
import com.woonit.wonnit.domain.user.User
import com.woonit.wonnit.domain.user.repository.UserRepository
import com.woonit.wonnit.global.exception.business.ForbiddenException
import com.woonit.wonnit.global.exception.code.SpaceErrorCode
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
@Transactional
class SpaceRentService(
    val spaceRepository: SpaceRepository,
    val userRepository: UserRepository,
) {

    fun rent(userId: String, spaceId: String) {
        val space = getSpace(spaceId)
        val renter = getUser(userId)
        space.rent(renter)
    }

    fun returnRequest(userId: String, spaceId: String, request: ReturnSpaceRequest) {
        val space = getSpace(spaceId)
        val renter = getUser(userId)
        space.returnRequest(renter, request)
    }

    fun returnReject(ownerId: String, spaceId: String) {
        val space = getSpace(spaceId)
        if (space.user.id.toString() != ownerId) {
            throw ForbiddenException(SpaceErrorCode.NO_OWNER)
        }
        space.returnReject()
    }

    fun returnApprove(ownerId: String, spaceId: String) {
        val space = getSpace(spaceId)
        if (space.user.id.toString() != ownerId) {
            throw ForbiddenException(SpaceErrorCode.NO_OWNER)
        }
        space.returnApprove()
    }

    fun reRegistration(ownerId: String, spaceId: String) {
        val space = getSpace(spaceId)
        if (space.user.id.toString() != ownerId) {
            throw ForbiddenException(SpaceErrorCode.NO_OWNER)
        }
        space.reRegistration()
    }

    private fun getUser(userId: String): User = (userRepository.findByIdOrNull(UUID.fromString(userId))
        ?: throw NoSuchElementException("유저 정보를 찾을 수 없습니다"))

    private fun getSpace(spaceId: String): Space = spaceRepository.findByIdOrNull(UUID.fromString(spaceId))
        ?: throw NoSuchElementException("공간 정보를 찾을 수 없습니다")
}