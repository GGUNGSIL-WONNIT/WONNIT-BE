package com.woonit.wonnit.domain.space

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.assertj.MockMvcTester

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest(
    @Autowired private val mvcTester: MockMvcTester,
    @Autowired val objectMapper: ObjectMapper
) {

    @Test
    fun getSpaceCategories() {
        val result = mvcTester.get().uri("/api/v1/categories")
            .exchange()

        assertThat(result).hasStatusOk()

        val response: MutableList<SpaceCategory> = objectMapper.readValue(result.response.contentAsString)

        assertThat(response.size).isEqualTo(SpaceCategory.entries.size)
    }

}