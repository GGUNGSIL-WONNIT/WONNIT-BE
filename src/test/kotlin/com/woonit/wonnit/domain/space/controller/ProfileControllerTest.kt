package com.woonit.wonnit.domain.space.controller

import com.fasterxml.jackson.module.kotlin.readValue
import com.woonit.wonnit.domain.share.PhoneNumber
import com.woonit.wonnit.domain.space.SpaceFixture
import com.woonit.wonnit.domain.space.dto.MySpaceResponse
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
            val space = SpaceFixture.createSpace("space${i}", user = user)
            spaceRepository.save(space)
        }

        val result = mvcTester.get().uri("/api/v1/my/spaces")
            .param("page", "1")
            .exchange()

        assertThat(result).hasStatusOk()

        val response: List<MySpaceResponse> = objectMapper.readValue(result.response.contentAsString)

        assertThat(response).hasSize(5)
    }

}