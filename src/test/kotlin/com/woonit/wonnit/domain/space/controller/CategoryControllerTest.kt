package com.woonit.wonnit.domain.space.controller

import com.fasterxml.jackson.module.kotlin.readValue
import com.woonit.wonnit.domain.space.SpaceCategory
import com.woonit.wonnit.support.BaseControllerTest
import org.assertj.core.api.AssertionsForClassTypes
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test

class CategoryControllerTest : BaseControllerTest() {

    @Test
    fun `카테고리 리스트를 조회한다`() {
        val result = mvcTester.get().uri("/api/v1/categories")
            .exchange()

        assertThat(result).hasStatusOk()

        val response: MutableList<SpaceCategory> = objectMapper.readValue(result.response.contentAsString)

        AssertionsForClassTypes.assertThat(response.size).isEqualTo(SpaceCategory.entries.size)
    }

}