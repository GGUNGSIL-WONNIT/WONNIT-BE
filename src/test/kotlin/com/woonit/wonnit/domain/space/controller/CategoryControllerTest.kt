package com.woonit.wonnit.domain.space.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.woonit.wonnit.domain.space.SpaceCategory
import org.assertj.core.api.AssertionsForClassTypes
import org.assertj.core.api.AssertionsForInterfaceTypes
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

        AssertionsForInterfaceTypes.assertThat(result).hasStatusOk()

        val response: MutableList<SpaceCategory> = objectMapper.readValue(result.response.contentAsString)

        AssertionsForClassTypes.assertThat(response.size).isEqualTo(SpaceCategory.entries.size)
    }

}