package com.woonit.wonnit.domain.space.controller

import com.woonit.wonnit.domain.share.PhoneNumber
import com.woonit.wonnit.domain.space.SpaceFixture
import com.woonit.wonnit.domain.user.User
import com.woonit.wonnit.support.BaseControllerTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

class SpaceControllerTest : BaseControllerTest() {

    @Test
    fun `공간-등록`() {
        val user = User("user", PhoneNumber("010-0000-0000"))
        userRepository.save(user)

        val request = SpaceFixture.createSpaceRequest()
        val requestJson = objectMapper.writeValueAsString(request)

        val result = mvcTester.post().uri("/api/v1/spaces")
            .param("userId", user.id.toString())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
            .exchange()

        assertThat(result).hasStatus(HttpStatus.CREATED)

        val space = spaceRepository.findAll().first()
        assertThat(space.name).isEqualTo("테스트 공간")
    }

    @Test
    fun `공간-수정`() {
        val owner = User("user", PhoneNumber("010-0000-0000"))
        userRepository.save(owner)

        val space = SpaceFixture.createSpace(owner)
        spaceRepository.save(space)
        entityManager.flush()
        entityManager.clear()

        val request = SpaceFixture.createSpaceRequest(name = "수정된 이름")
        val requestJson = objectMapper.writeValueAsString(request)

        val result = mvcTester.put().uri("/api/v1/spaces/{spaceId}", space.id)
            .param("userId", owner.id.toString())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
            .exchange()
        assertThat(result).hasStatus(HttpStatus.NO_CONTENT)

        entityManager.flush()
        entityManager.clear()

        val updated = spaceRepository.findById(space.id).orElseThrow()
        assertThat(updated.name).isEqualTo("수정된 이름")
    }

    @Test
    fun `공간-삭제`() {
        val owner = User("user", PhoneNumber("010-0000-0000"))
        userRepository.save(owner)

        val space1 = SpaceFixture.createSpace(owner)
        val space2 = SpaceFixture.createSpace(owner)
        spaceRepository.saveAll(listOf(space1, space2))
        entityManager.flush()
        entityManager.clear()

        val result = mvcTester.delete().uri("/api/v1/spaces")
            .param("userId", owner.id.toString())
            .queryParam("spaceIds", space1.id.toString())
            .queryParam("spaceIds", space2.id.toString())
            .exchange()

        assertThat(result).hasStatus(HttpStatus.NO_CONTENT)

        entityManager.flush()
        entityManager.clear()

        val exists1 = spaceRepository.findById(space1.id)
        val exists2 = spaceRepository.findById(space2.id)
        assertThat(exists2).isEmpty
        assertThat(exists1).isEmpty
    }
}