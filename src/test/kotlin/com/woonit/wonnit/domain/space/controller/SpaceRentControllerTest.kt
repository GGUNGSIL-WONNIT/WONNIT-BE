package com.woonit.wonnit.domain.space.controller

import com.woonit.wonnit.domain.share.PhoneNumber
import com.woonit.wonnit.domain.space.Space
import com.woonit.wonnit.domain.space.SpaceFixture
import com.woonit.wonnit.domain.space.SpaceStatus
import com.woonit.wonnit.domain.user.User
import com.woonit.wonnit.support.BaseControllerTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

class SpaceRentControllerTest : BaseControllerTest() {

    private lateinit var owner: User
    private lateinit var renter: User
    private lateinit var space: Space
    private lateinit var otherUser: User


    @BeforeEach
    fun setUp() {
        owner = User("owner", PhoneNumber("010-0000-0000"))
        renter = User("renter", PhoneNumber("010-1111-1111"))
        otherUser = User("otherUser", PhoneNumber("010-2222-2222"))
        userRepository.saveAll(listOf(owner, renter, otherUser))

        space = SpaceFixture.createSpace("testSpace", owner = owner)
        spaceRepository.save(space)
    }

    @Test
    fun `공간을 대여한다`() {
        // when
        val result = mvcTester.post().uri("/api/v1/space/${space.id}/rentals")
            .param("userId", renter.id.toString())
            .exchange()

        // then
        assertThat(result).hasStatus(HttpStatus.CREATED)
    }

    @Test
    fun `공간 반납 요청을 한다`() {
        val request = SpaceFixture.createReturnSpaceRequest()

        // when
        space.rent(renter)
        entityManager.flush()
        entityManager.clear()

        val result = mvcTester.patch().uri("/api/v1/space/${space.id}/rentals/return-request")
            .param("userId", renter.id.toString())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .exchange()

        // then
        assertThat(result).hasStatus(HttpStatus.NO_CONTENT)
    }

    @Nested
    @DisplayName("공간 반납 요청 거절")
    inner class ReturnReject {
        @Test
        @DisplayName("소유주가 요청하면 성공한다")
        fun `공간 반납 요청을 거절한다`() {
            val request = SpaceFixture.createReturnSpaceRequest()
            // when
            space.rent(renter)
            space.returnRequest(renter, request)
            entityManager.flush()
            entityManager.clear()

            val result = mvcTester.patch().uri("/api/v1/space/${space.id}/rentals/return-reject")
                .param("userId", owner.id.toString())
                .exchange()

            // then
            assertThat(result).hasStatus(HttpStatus.NO_CONTENT)
        }

        @Test
        @DisplayName("소유주가 아니면 403 에러가 발생한다")
        fun `소유주가 아니면 403 에러가 발생한다`() {
            val request = SpaceFixture.createReturnSpaceRequest()
            // when
            space.rent(renter)
            space.returnRequest(renter, request)
            entityManager.flush()
            entityManager.clear()

            val result = mvcTester.patch().uri("/api/v1/space/${space.id}/rentals/return-reject")
                .param("userId", otherUser.id.toString())
                .exchange()

            // then
            assertThat(result).hasStatus(HttpStatus.FORBIDDEN)
        }
    }


    @Nested
    @DisplayName("공간 반납 요청 승인")
    inner class ReturnApprove {
        @Test
        @DisplayName("소유주가 요청하면 성공한다")
        fun `공간 반납 요청을 승인한다`() {
            val request = SpaceFixture.createReturnSpaceRequest()

            // when
            space.rent(renter)
            space.returnRequest(renter, request)
            entityManager.flush()
            entityManager.clear()

            val result = mvcTester.patch().uri("/api/v1/space/${space.id}/rentals/return-approve")
                .param("userId", owner.id.toString())
                .exchange()

            // then
            assertThat(result).hasStatus(HttpStatus.NO_CONTENT)
        }

        @Test
        @DisplayName("소유주가 아니면 403 에러가 발생한다")
        fun `소유주가 아니면 403 에러가 발생한다`() {
            val request = SpaceFixture.createReturnSpaceRequest()

            // when
            space.rent(renter)
            space.returnRequest(renter, request)
            entityManager.flush()
            entityManager.clear()

            val result = mvcTester.patch().uri("/api/v1/space/${space.id}/rentals/return-approve")
                .param("userId", otherUser.id.toString())
                .exchange()

            // then
            assertThat(result).hasStatus(HttpStatus.FORBIDDEN)
        }
    }

    @Nested
    @DisplayName("반납 반려된 공간 재등록")
    inner class ReRegistration {
        @BeforeEach
        fun setUp() {
            val request = SpaceFixture.createReturnSpaceRequest()
            space.rent(renter)
            space.returnRequest(renter, request)
            space.returnReject()
            entityManager.flush()
            entityManager.clear()
        }

        @Test
        @DisplayName("소유주가 요청하면 성공한다")
        fun `반납 반려된 공간을 재등록한다`() {
            // when
            val result = mvcTester.patch().uri("/api/v1/space/${space.id}/rentals/re-registration")
                .param("userId", owner.id.toString())
                .exchange()

            // then
            assertThat(result).hasStatus(HttpStatus.NO_CONTENT)

            val updatedSpace = spaceRepository.findById(space.id).get()
            assertThat(updatedSpace.spaceStatus).isEqualTo(SpaceStatus.AVAILABLE)
            assertThat(updatedSpace.renter).isNull()
        }

        @Test
        @DisplayName("소유주가 아니면 403 에러가 발생한다")
        fun `소유주가 아니면 403 에러가 발생한다`() {
            // when
            val result = mvcTester.patch().uri("/api/v1/space/${space.id}/rentals/re-registration")
                .param("userId", otherUser.id.toString())
                .exchange()

            // then
            assertThat(result).hasStatus(HttpStatus.FORBIDDEN)
        }
    }
}

