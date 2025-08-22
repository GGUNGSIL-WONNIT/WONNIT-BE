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

class SpaceRentControllerTest : BaseControllerTest() {

    private lateinit var user: User
    private lateinit var space: Space

    @BeforeEach
    fun setUp() {
        user = User("user", PhoneNumber("010-0000-0000"))
        userRepository.save(user)

        space = SpaceFixture.createSpace("testSpace", owner = user)
        spaceRepository.save(space)
    }

    @Test
    fun `공간을 대여한다`() {
        val renter = User("renter", PhoneNumber("010-0000-0001"))
        userRepository.save(renter)

        // when
        val result = mvcTester.post().uri("/api/v1/space/${space.id}/rentals")
            .param("userId", renter.id.toString())
            .exchange()

        // then
        assertThat(result).hasStatus(HttpStatus.CREATED)
    }

    @Test
    fun `공간 반납 요청을 한다`() {
        val renter = User("renter", PhoneNumber("010-0000-0001"))
        userRepository.save(renter)

        // when
        space.rent(renter)
        val result = mvcTester.patch().uri("/api/v1/space/${space.id}/rentals/return-request")
            .param("userId", renter.id.toString())
            .exchange()

        // then
        assertThat(result).hasStatus(HttpStatus.NO_CONTENT)
    }

    @Test
    fun `공간 반납 요청을 거절한다`() {
        val renter = User("renter", PhoneNumber("010-0000-0001"))
        userRepository.save(renter)

        // when
        space.rent(renter)
        space.returnRequest(renter)
        val result = mvcTester.patch().uri("/api/v1/space/${space.id}/rentals/return-reject")
            .param("userId", renter.id.toString())
            .exchange()

        // then
        assertThat(result).hasStatus(HttpStatus.NO_CONTENT)
    }

    @Test
    fun `공간 반납 요청을 승인한다`() {
        val renter = User("renter", PhoneNumber("010-0000-0001"))
        userRepository.save(renter)

        // when
        space.rent(renter)
        space.returnRequest(renter)
        val result = mvcTester.patch().uri("/api/v1/space/${space.id}/rentals/return-approve")
            .param("userId", renter.id.toString())
            .exchange()

        // then
        assertThat(result).hasStatus(HttpStatus.NO_CONTENT)
    }

    @Test
    fun `반려된 공간에 대해 반납을 승인한다`() {
        val renter = User("renter", PhoneNumber("010-0000-0001"))
        userRepository.save(renter)

        // when
        space.rent(renter)
        space.returnRequest(renter)
        space.returnReject(renter)
        val result = mvcTester.patch().uri("/api/v1/space/${space.id}/rentals/return-approve")
            .param("userId", renter.id.toString())
            .exchange()

        // then
        assertThat(result).hasStatus(HttpStatus.NO_CONTENT)
    }
}
