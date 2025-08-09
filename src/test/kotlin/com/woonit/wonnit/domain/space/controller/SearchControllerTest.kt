package com.woonit.wonnit.domain.space.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.woonit.wonnit.domain.share.AddressInfo
import com.woonit.wonnit.domain.share.PhoneNumber
import com.woonit.wonnit.domain.space.SpaceFixture
import com.woonit.wonnit.domain.space.dto.RecentSpaceResponse
import com.woonit.wonnit.domain.space.repository.SpaceRepository
import com.woonit.wonnit.domain.user.User
import com.woonit.wonnit.domain.user.repository.UserRepository
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.assertj.MockMvcTester

@SpringBootTest
@AutoConfigureMockMvc
class SearchControllerTest(
    @Autowired private val mvcTester: MockMvcTester,
    @Autowired val objectMapper: ObjectMapper,
    @Autowired val userRepository: UserRepository,
    @Autowired val spaceRepository: SpaceRepository
) {

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