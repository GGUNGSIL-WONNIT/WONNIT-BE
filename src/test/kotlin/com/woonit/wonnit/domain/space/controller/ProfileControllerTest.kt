package com.woonit.wonnit.domain.space.controller

import com.fasterxml.jackson.module.kotlin.readValue
import com.woonit.wonnit.domain.share.PhoneNumber
import com.woonit.wonnit.domain.space.SpaceFixture
import com.woonit.wonnit.domain.space.dto.MyRentalSpacePageResponse
import com.woonit.wonnit.domain.space.dto.MySpacePageResponse
import com.woonit.wonnit.domain.user.User
import com.woonit.wonnit.support.BaseControllerTest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import java.util.UUID

class ProfileControllerTest : BaseControllerTest() {

    @Test
    fun `내가 등록한 공간을 조회한다`() {
        val user = SpaceFixture.createUser()
        userRepository.save(user)

        for (i: Int in 1..15) {
            val space = SpaceFixture.createSpace("space${i}", owner = user)
            spaceRepository.save(space)
        }

        val result = mvcTester.get().uri("/api/v1/my/spaces")
            .param("page", "1")
            .param("userId", user.id.toString())
            .exchange()

        assertThat(result).hasStatusOk()

        val response: MySpacePageResponse = objectMapper.readValue(result.response.contentAsString)

        assertThat(response.spaces).hasSize(5)
        assertThat(response.totalCount).isEqualTo(15)
    }

    @Test
    fun `내가 대여 중인 공간을 조회한다`() {
        val renter = SpaceFixture.createUser()
        userRepository.save(renter)

        val owner = User("owner", PhoneNumber("010-1111-1111"))
        userRepository.save(owner)

        val spaces = (1..15).map {
            val space = SpaceFixture.createSpace("space$it", owner = owner)
            spaceRepository.save(space)
        }

        // renter -> 7개
        for (i in 0..6) {
            val spaceToRent = spaces[i]
            spaceToRent.rent(renter)
            spaceRepository.save(spaceToRent)
        }
        val result = mvcTester.get().uri("/api/v1/my/rental-spaces")
            .param("page", "0")
            .param("userId", renter.id.toString())
            .exchange()

        assertThat(result).hasStatusOk()

        val response: MyRentalSpacePageResponse = objectMapper.readValue(result.response.contentAsString)

        assertThat(response.spaces).hasSize(7)
        assertThat(response.totalCount).isEqualTo(7)
    }
}