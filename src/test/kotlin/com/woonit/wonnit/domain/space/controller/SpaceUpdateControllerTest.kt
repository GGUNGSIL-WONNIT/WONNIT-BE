package com.woonit.wonnit.domain.space.controller

import com.woonit.wonnit.domain.share.*
import com.woonit.wonnit.domain.space.Space
import com.woonit.wonnit.domain.space.SpaceCategory
import com.woonit.wonnit.domain.space.dto.SpaceSaveRequest
import com.woonit.wonnit.domain.user.User
import com.woonit.wonnit.global.exception.business.ForbiddenException
import com.woonit.wonnit.global.exception.business.NotFoundException
import com.woonit.wonnit.global.exception.code.CommonErrorCode
import com.woonit.wonnit.global.exception.code.SpaceErrorCode
import com.woonit.wonnit.global.exception.code.UserErrorCode
import com.woonit.wonnit.support.BaseControllerTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.util.*

class SpaceUpdateControllerTest : BaseControllerTest() {

    private fun createRequest(
        name: String = "테스트 공간",
        category: SpaceCategory = SpaceCategory.DANCE_STUDIO,
        mainImg: String = "https://img/main.jpg"
    ) = SpaceSaveRequest(
        category = category,
        name = name,
        phoneNumber = "010-0000-0000",
        mainImgUrl = mainImg,
        subImgUrls = mutableListOf("https://img/1.jpg", "https://img/2.jpg"),
        address = AddressInfo("서울시 OO구", null, 37.5, 127.0),
        amountInfo = AmountInfo(TimeUnit.PER_DAY, amount = 100000),
        size = 45.0,
        operationInfo = OperationalInfo(
            listOf(DayOfWeek.MONDAY, DayOfWeek.SUNDAY),
            LocalDateTime.MIN,
            LocalDateTime.MAX
        ),
        spaceModelUrl = "https://model.glb",
        modelThumbnailUrl = "https://model-thumb.jpg",
        precautions = "조심히 사용",
        tags = mutableListOf("태그1", "태그2")
    )

    private fun createSpace(request: SpaceSaveRequest, userId: UUID): UUID {
        val user = userRepository.findByIdOrNull(userId)
            ?: throw NotFoundException(UserErrorCode.NOT_FOUND)

        val space = Space.register(
            category = request.category,
            name = request.name,
            mainImgUrl = request.mainImgUrl,
            subImgUrls = request.subImgUrls.toMutableList(),
            addressInfo = request.address,
            amountInfo = request.amountInfo,
            size = request.size,
            operationalInfo = request.operationInfo,
            spaceModelUrl = request.spaceModelUrl,
            modelThumbnailUrl = request.modelThumbnailUrl,
            user = user,
            phoneNumber = PhoneNumber(request.phoneNumber),
            precautions = request.precautions,
            tags = request.tags.toMutableList()
        )
        return spaceRepository.save(space).id
    }

    private fun updateSpace(spaceId: UUID, request: SpaceSaveRequest, userId: UUID) {
        val space = spaceRepository.findByIdOrNull(spaceId)
            ?: throw NotFoundException(SpaceErrorCode.NOT_FOUND)

        if (space.user.id != userId) {
            throw ForbiddenException(CommonErrorCode.ACCESS_DENIED, "spaceId=$spaceId, userId=$userId")
        }

        space.update(
            category = request.category,
            name = request.name,
            mainImgUrl = request.mainImgUrl,
            subImgUrls = request.subImgUrls.toMutableList(),
            addressInfo = request.address,
            amountInfo = request.amountInfo,
            size = request.size,
            operationalInfo = request.operationInfo,
            spaceModelUrl = request.spaceModelUrl,
            modelThumbnailUrl = request.modelThumbnailUrl,
            phoneNumber = PhoneNumber(request.phoneNumber),
            precautions = request.precautions,
            tags = request.tags.toMutableList()
        )
    }

    @Test
    fun `공간-등록`() {
        val user = User("user", PhoneNumber("010-0000-0000"))
        userRepository.save(user)

        val request = createRequest()
        val requestJson = objectMapper.writeValueAsString(request)

        val result = mvcTester.post().uri("/api/v1/spaces")
            .param("userId", user.id.toString())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
            .exchange()

        assertThat(result).hasStatus(HttpStatus.CREATED)

        val spaceId = createSpace(request, user.id)
        val saved = spaceRepository.findById(spaceId).orElseThrow()
        assertThat(saved.name).isEqualTo("테스트 공간")
    }

    @Test
    fun `공간-수정`() {
        val user = User("user", PhoneNumber("010-0000-0000"))
        userRepository.save(user)

        val spaceId = createSpace(createRequest(), user.id)
        entityManager.flush()
        entityManager.clear()

        val request = createRequest(name = "수정된 이름")
        val requestJson = objectMapper.writeValueAsString(request)

        val result = mvcTester.put().uri("/api/v1/spaces/{spaceId}", spaceId)
            .param("userId", user.id.toString())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
            .exchange()
        assertThat(result).hasStatus(HttpStatus.NO_CONTENT)

        entityManager.flush()
        entityManager.clear()

        val updated = spaceRepository.findById(spaceId).orElseThrow()
        assertThat(updated.name).isEqualTo("수정된 이름")
    }

    @Test
    fun `공간-삭제`() {
        val user = User("user", PhoneNumber("010-0000-0000"))
        userRepository.save(user)

        val spaceId1 = createSpace(createRequest(), user.id)
        val spaceId2 = createSpace(createRequest(), user.id)
        entityManager.flush()
        entityManager.clear()

        val result = mvcTester.delete().uri("/api/v1/spaces")
            .param("userId", user.id.toString())
            .queryParam("spaceIds", spaceId1.toString())
            .queryParam("spaceIds", spaceId2.toString())
            .exchange()

        assertThat(result).hasStatus(HttpStatus.NO_CONTENT)

        entityManager.flush()
        entityManager.clear()

        val exists1 = spaceRepository.findById(spaceId1)
        val exists2 = spaceRepository.findById(spaceId2)
        assertThat(exists2).isEmpty
        assertThat(exists1).isEmpty
    }
}