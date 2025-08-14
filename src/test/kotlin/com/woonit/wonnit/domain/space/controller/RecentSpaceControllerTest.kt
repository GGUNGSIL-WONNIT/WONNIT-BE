package com.woonit.wonnit.domain.space.controller

import com.fasterxml.jackson.module.kotlin.readValue
import com.woonit.wonnit.domain.share.PhoneNumber
import com.woonit.wonnit.domain.space.SpaceFixture
import com.woonit.wonnit.domain.space.dto.RecentSpaceResponse
import com.woonit.wonnit.domain.user.User
import com.woonit.wonnit.support.BaseControllerTest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test

class RecentSpaceControllerTest : BaseControllerTest() {

    @Test
    fun `최근 추가된 공간 목록을 조회한다`() {
        val user = User("user", PhoneNumber("010-0000-0000"))
        userRepository.save(user)

        // given
        for (i: Int in 1..10) {
            val space = SpaceFixture.createSpace("space${i}", owner = user)
            spaceRepository.save(space)
        }

        // when & then
        val result = mvcTester.get().uri("/api/v1/recent-spaces")
            .exchange()

        assertThat(result).hasStatusOk()

        val response: List<RecentSpaceResponse> = objectMapper.readValue(result.response.contentAsString)

        assertThat(response).hasSize(5)
            .extracting("name")
            .containsExactly("space10", "space9", "space8", "space7", "space6")
    }
}
