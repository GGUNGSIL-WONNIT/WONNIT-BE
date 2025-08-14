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
import org.springframework.test.util.ReflectionTestUtils
import java.util.*

class ProfileControllerTest : BaseControllerTest() {

    @Test
    fun getMySpaces() {
        val user = User("user", PhoneNumber("010-0000-0000"))
        ReflectionTestUtils.setField(user, "id", UUID.fromString("028195e0-6999-137d-a747-0a02b343a12e"))
        userRepository.save(user)

        for (i: Int in 1..15) {
            val space = SpaceFixture.createSpace("space${i}", owner = user)
            spaceRepository.save(space)
        }

        val result = mvcTester.get().uri("/api/v1/my/spaces")
            .param("page", "1")
            .exchange()

        assertThat(result).hasStatusOk()

        val response: MySpacePageResponse = objectMapper.readValue(result.response.contentAsString)

        assertThat(response.spaces).hasSize(5)
        assertThat(response.totalCount).isEqualTo(15)
    }

    @Test
    fun getRentalSpaces() {
        val owner = User("owner", PhoneNumber("010-0000-0000"))
        userRepository.save(owner)


        val renter = User("renter", PhoneNumber("010-1111-1111"))
        ReflectionTestUtils.setField(renter, "id", UUID.fromString("028195e0-6999-137d-a747-0a02b343a12e"))
        userRepository.save(renter)

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
            .exchange()

        assertThat(result).hasStatusOk()

        val response: MyRentalSpacePageResponse = objectMapper.readValue(result.response.contentAsString)

        assertThat(response.spaces).hasSize(7)
        assertThat(response.totalCount).isEqualTo(7)
    }
}