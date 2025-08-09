package com.woonit.wonnit.domain.space.controller

import com.fasterxml.jackson.module.kotlin.readValue
import com.woonit.wonnit.domain.share.AddressInfo
import com.woonit.wonnit.domain.share.PhoneNumber
import com.woonit.wonnit.domain.space.SpaceFixture
import com.woonit.wonnit.domain.space.dto.RecentSpaceResponse
import com.woonit.wonnit.domain.user.User
import com.woonit.wonnit.support.BaseControllerTest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test

class SearchControllerTest : BaseControllerTest() {

    @Test
    fun getNearBySpaces() {
        val user = User("user", PhoneNumber("010-0000-0000"))
        userRepository.save(user)

        for (i: Int in 1..5) {
            val space = SpaceFixture.createSpace(
                "space${i}",
                user = user,
                addressInfo = AddressInfo("address1", "address2", 37.6301, 127.0764),
            )
            spaceRepository.save(space)
        }

        for (i: Int in 1..5) {
            val space = SpaceFixture.createSpace(
                "space${i}",
                user = user,
                addressInfo = AddressInfo("address1", "address2", -37.6301, -127.0764),
            )
            spaceRepository.save(space)
        }

        // when & then
        val result = mvcTester.get().uri("/api/v1/search")
            .param("lat", "37.62")
            .param("lon", "127.06")
            .exchange()

        assertThat(result).hasStatusOk()

        val response: List<RecentSpaceResponse> = objectMapper.readValue(result.response.contentAsString)

        assertThat(response).hasSize(5)
    }

}