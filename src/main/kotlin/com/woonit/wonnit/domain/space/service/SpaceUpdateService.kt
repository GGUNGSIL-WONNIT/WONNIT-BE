package com.woonit.wonnit.domain.space.service

import com.woonit.wonnit.domain.share.PhoneNumber
import com.woonit.wonnit.domain.space.Space
import com.woonit.wonnit.domain.space.dto.SpaceSaveRequest
import com.woonit.wonnit.domain.space.repository.SpaceRepository
import com.woonit.wonnit.domain.user.repository.UserRepository
import com.woonit.wonnit.global.exception.business.ForbiddenException
import com.woonit.wonnit.global.exception.business.NotFoundException
import com.woonit.wonnit.global.exception.code.CommonErrorCode
import com.woonit.wonnit.global.exception.code.SpaceErrorCode
import com.woonit.wonnit.global.exception.code.UserErrorCode
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class SpaceUpdateService(
    val spaceRepository: SpaceRepository,
    val userRepository: UserRepository
) {

    @Transactional
    fun createSpace(request: SpaceSaveRequest, userId: String) {
        val user = userRepository.findByIdOrNull(UUID.fromString(userId))
            ?: throw NotFoundException(UserErrorCode.NOT_FOUND)

        val space = Space.register(
            name = request.name,
            category = request.category,
            mainImgUrl = request.mainImgUrl,
            subImgUrls = request.subImgUrls,
            addressInfo = request.address,
            amountInfo = request.amountInfo,
            operationalInfo = request.operationInfo,
            size = request.size,
            spaceModelUrl = request.spaceModelUrl,
            modelThumbnailUrl = request.modelThumbnailUrl,
            precautions = request.precautions,
            user = user,
            phoneNumber = PhoneNumber(request.phoneNumber),
            tags = request.tags
        )

        spaceRepository.save(space)
    }

    @Transactional
    fun updateSpace(spaceId: String, request: SpaceSaveRequest, userId: String) {
        val space = spaceRepository.findByIdOrNull(UUID.fromString(spaceId))
            ?: throw NotFoundException(SpaceErrorCode.NOT_FOUND)

        if (space.user.id != UUID.fromString(userId)) {
            throw ForbiddenException(CommonErrorCode.ACCESS_DENIED, "spaceId=$spaceId, userId=$userId")
        }

        space.update(
            category = request.category,
            name = request.name,
            mainImgUrl = request.mainImgUrl,
            subImgUrls = request.subImgUrls,
            addressInfo = request.address,
            amountInfo = request.amountInfo,
            size = request.size,
            operationalInfo = request.operationInfo,
            spaceModelUrl = request.spaceModelUrl,
            modelThumbnailUrl = request.modelThumbnailUrl,
            phoneNumber = PhoneNumber(request.phoneNumber),
            precautions = request.precautions,
            tags = request.tags
        )
    }

    @Transactional
    fun deleteSpaces(spaceIds: List<String>, userId: String) {
        spaceIds.forEach { spaceId ->
            val space = spaceRepository.findByIdOrNull(UUID.fromString(spaceId))
                ?: throw NotFoundException(SpaceErrorCode.NOT_FOUND)

            if (space.user.id.toString() != userId) {
                throw ForbiddenException(CommonErrorCode.ACCESS_DENIED, "spaceId=$spaceId, userId=$userId")
            }

            spaceRepository.delete(space)
        }
    }
}