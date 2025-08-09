package com.woonit.wonnit.domain.space.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.woonit.wonnit.domain.space.SpaceFixture
import com.woonit.wonnit.domain.space.dto.RecentSpaceResponse
import com.woonit.wonnit.domain.space.repository.SpaceRepository
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.assertj.MockMvcTester


@SpringBootTest
@AutoConfigureMockMvc
class RecentSpaceControllerTest(
    @Autowired private val mvcTester: MockMvcTester,
    @Autowired val objectMapper: ObjectMapper,
    @Autowired val spaceRepository: SpaceRepository
) {

    @Test
    fun `최근 추가된 공간 목록을 조회한다`() {
        // given
        for (i: Int in 1..10) {
            val space = SpaceFixture.createSpace("space${i}")
            spaceRepository.save(space)
        }

        // when & then
        val result = mvcTester.get().uri("/api/recent-spaces")
            .exchange()

        assertThat(result).hasStatusOk()

        val response: List<RecentSpaceResponse> = objectMapper.readValue(result.response.contentAsString)

        assertThat(response).hasSize(5)
            .extracting("name")
            .containsExactly("space10", "space9", "space8", "space7", "space6")
    }
}
