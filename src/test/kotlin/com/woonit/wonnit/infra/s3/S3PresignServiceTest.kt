package com.woonit.wonnit.infra.s3

import com.woonit.wonnit.domain.space.dto.PresignUploadResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class S3PresignServiceTest {

    @Autowired
    lateinit var s3PresignService: S3PresignService

    @Test
    fun `createUploadUrl은 유효한 PresignUploadResponse를 반환해야 한다`() {
        // given
        val fileName = "테스트.jpg"

        // when
        val response: PresignUploadResponse = s3PresignService.createUploadUrl(fileName)

        // then
        assertThat(response.uploadUrl).isNotBlank()
        assertThat(response.url).isNotBlank()
    }
}
