package com.woonit.wonnit.domain.column.controller

import com.fasterxml.jackson.module.kotlin.readValue
import com.woonit.wonnit.domain.column.Column
import com.woonit.wonnit.domain.column.dto.ColumnResponse
import com.woonit.wonnit.support.BaseControllerTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ColumnControllerTest : BaseControllerTest() {

    @BeforeEach
    fun setUp() {
        columnRepository.saveAll(
            listOf(
                Column(
                    title = "column1",
                    subtitle = "subtitle",
                    url = "https://example.com/column/1",
                    thumbnailUrl = "https://example.com/images/thumbnail1.jpg"
                ),
                Column(
                    title = "column2",
                    subtitle = "subtitle",
                    url = "https://example.com/column/1",
                    thumbnailUrl = "https://example.com/images/thumbnail1.jpg"
                ),
                Column(
                    title = "column3",
                    subtitle = "subtitle",
                    url = "https://example.com/column/1",
                    thumbnailUrl = "https://example.com/images/thumbnail1.jpg"
                )
            )
        )
    }

    @Test
    fun `칼럼 목록을 정상적으로 조회한다`() {
        // when
        val result = mvcTester.get().uri("/api/v1/columns")
            .exchange()

        // then
        assertThat(result).hasStatusOk()

        val response: List<ColumnResponse> = objectMapper.readValue(result.response.contentAsString)

        assertThat(response).hasSize(3)
    }
}
