package com.woonit.wonnit.domain.space.service

import com.woonit.wonnit.domain.space.Space
import com.woonit.wonnit.domain.space.SpaceCategory
import com.woonit.wonnit.domain.space.dto.SpaceSaveRequest
import com.woonit.wonnit.domain.space.repository.SpaceRepository
import com.woonit.wonnit.domain.user.repository.UserRepository
import com.woonit.wonnit.global.exception.business.BadRequestException
import com.woonit.wonnit.global.exception.business.ForbiddenException
import com.woonit.wonnit.global.exception.business.NotFoundException
import com.woonit.wonnit.global.exception.code.CommonErrorCode
import com.woonit.wonnit.global.exception.code.SpaceErrorCode
import com.woonit.wonnit.global.exception.code.UserErrorCode
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class SpaceUpdateService(
    val spaceRepository: SpaceRepository,
    val userRepository: UserRepository
) {

    @Transactional
    fun createSpace(request: SpaceSaveRequest, userId: UUID) {
        val user = userRepository.findByIdOrNull(userId)
            ?: throw NotFoundException(UserErrorCode.NOT_FOUND)

        val category = SpaceCategory.from(request.category)
            ?: throw BadRequestException(SpaceErrorCode.INVALID_CATEGORY, "카테고리=${request.category}")

        val space = Space.register(
                name = request.name,
                category = category,
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
                phoneNumber = request.phoneNumber,
                tags = request.tags
            )

        spaceRepository.save(space)
    }

    @Transactional
    fun updateSpace(spaceId: UUID, request: SpaceSaveRequest, userId: UUID) {
        val space = spaceRepository.findByIdOrNull(spaceId)
            ?: throw NotFoundException(SpaceErrorCode.NOT_FOUND)

        if (space.user.id != userId) {
            throw ForbiddenException(CommonErrorCode.ACCESS_DENIED, "spaceId=$spaceId, userId=$userId")
        }

        val category = SpaceCategory.from(request.category)
            ?: throw BadRequestException(SpaceErrorCode.INVALID_CATEGORY, "category=${request.category}")

        space.update(
            category        = category,
            name            = request.name,
            mainImgUrl      = request.mainImgUrl,
            subImgUrls      = request.subImgUrls,
            addressInfo     = request.address,
            amountInfo      = request.amountInfo,
            size            = request.size,
            operationalInfo = request.operationInfo,
            spaceModelUrl   = request.spaceModelUrl,
            modelThumbnailUrl = request.modelThumbnailUrl,
            phoneNumber     = request.phoneNumber,
            precautions     = request.precautions,
            tags            = request.tags
        )
    }

    @Transactional
    fun deleteSpaces(spaceIds: List<UUID>, userId: UUID) {
        spaceIds.forEach { spaceId ->
            val space = spaceRepository.findByIdOrNull(spaceId)
                ?: throw NotFoundException(SpaceErrorCode.NOT_FOUND)

            if (space.user.id != userId) {
                throw ForbiddenException(CommonErrorCode.ACCESS_DENIED, "spaceId=$spaceId, userId=$userId")
            }

            spaceRepository.delete(space)
        }
    }
}