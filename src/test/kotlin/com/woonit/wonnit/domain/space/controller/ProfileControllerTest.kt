package com.woonit.wonnit.domain.space.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.woonit.wonnit.domain.share.PhoneNumber
import com.woonit.wonnit.domain.space.SpaceFixture
import com.woonit.wonnit.domain.space.dto.MySpaceResponse
import com.woonit.wonnit.domain.space.repository.SpaceRepository
import com.woonit.wonnit.domain.user.User
import com.woonit.wonnit.domain.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.util.ReflectionTestUtils
import org.springframework.test.web.servlet.assertj.MockMvcTester
import java.util.*

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class ProfileControllerTest(
    @Autowired private val mvcTester: MockMvcTester,
    @Autowired val objectMapper: ObjectMapper,
    @Autowired val userRepository: UserRepository,
    @Autowired val spaceRepository: SpaceRepository
) {

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