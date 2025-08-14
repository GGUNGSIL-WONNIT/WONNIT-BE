package com.woonit.wonnit.domain.space.controller

import com.woonit.wonnit.domain.share.PhoneNumber
import com.woonit.wonnit.domain.space.Space
import com.woonit.wonnit.domain.space.SpaceFixture
import com.woonit.wonnit.domain.user.User
import com.woonit.wonnit.support.BaseControllerTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.test.util.ReflectionTestUtils
import java.util.UUID

class SpaceRentControllerTest : BaseControllerTest() {

    private lateinit var user: User
    private lateinit var space: Space

    @BeforeEach
    fun setUp() {
        user = User("testUser", PhoneNumber("010-1234-5678"))
        ReflectionTestUtils.setField(user, "id", UUID.fromString("028195e0-6999-137d-a747-0a02b343a12e"))
        userRepository.save(user)

        space = SpaceFixture.createSpace("testSpace", owner = user)
        spaceRepository.save(space)
    }

    @Test
    fun `공간을 대여한다`() {
        // when
        val result = mvcTester.post().uri("/api/v1/space/${space.id}/rentals")
            .exchange()

        // then
        assertThat(result).hasStatus(HttpStatus.CREATED)
    }

    @Test
    fun `공간 반납 요청을 한다`() {
        // when
        space.rent(user)
        val result = mvcTester.patch().uri("/api/v1/space/${space.id}/rentals/return-request")
            .exchange()

        // then
        assertThat(result).hasStatus(HttpStatus.NO_CONTENT)
    }

    @Test
    fun `공간 반납 요청을 거절한다`() {
        // when
        space.rent(user)
        space.returnRequest(user)
        val result = mvcTester.patch().uri("/api/v1/space/${space.id}/rentals/return-reject")
            .exchange()

        // then
        assertThat(result).hasStatus(HttpStatus.NO_CONTENT)
    }

    @Test
    fun `공간 반납 요청을 승인한다`() {
        // when
        space.rent(user)
        space.returnRequest(user)
        val result = mvcTester.patch().uri("/api/v1/space/${space.id}/rentals/return-approve")
            .exchange()

        // then
        assertThat(result).hasStatus(HttpStatus.NO_CONTENT)
    }

    @Test
    fun `반려된 공간에 대해 반납을 승인한다`() {
        // when
        space.rent(user)
        space.returnRequest(user)
        space.returnReject(user)
        val result = mvcTester.patch().uri("/api/v1/space/${space.id}/rentals/return-approve")
            .exchange()

        // then
        assertThat(result).hasStatus(HttpStatus.NO_CONTENT)
    }
}
