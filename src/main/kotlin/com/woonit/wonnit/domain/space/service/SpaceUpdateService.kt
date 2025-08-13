package com.woonit.wonnit.domain.space.service

import com.woonit.wonnit.domain.space.Space
import com.woonit.wonnit.domain.space.SpaceCategory
import com.woonit.wonnit.domain.space.dto.SpaceCreateRequest
import com.woonit.wonnit.domain.space.repository.SpaceRepository
import com.woonit.wonnit.domain.user.repository.UserRepository
import com.woonit.wonnit.global.exception.business.BadRequestException
import com.woonit.wonnit.global.exception.business.NotFoundException
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
    fun createSpace(request: SpaceCreateRequest, userId: UUID) {
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
                precautions = request.precautions,
                user = user,
                phoneNumber = request.phoneNumber,
            )

        spaceRepository.save(space)
    }
}