package com.woonit.wonnit.domain.space.controller

import com.fasterxml.jackson.module.kotlin.readValue
import com.woonit.wonnit.domain.space.dto.PresignUploadResponse
import com.woonit.wonnit.domain.space.service.SpaceImageService
import com.woonit.wonnit.support.BaseControllerTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.verify
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean


class SpaceImageControllerTest : BaseControllerTest() {

    @MockitoBean
    private lateinit var spaceImageService: SpaceImageService

    @Nested
    @DisplayName("이미지 업로드 Presigned URL 발급 API")
    inner class GetPresignedUrl {
        @Test
        @DisplayName("요청이 성공하면, Presigned URL과 Key를 담은 200 OK 응답을 반환한다")
        fun getPresignedUrlForImage_returnsPresignedUrlResponse() {
            // given
            val imageName = "test-image.jpg"
            val presignedUrl =
                "https://s3.ap-northeast-2.amazonaws.com/wonnit-bucket/images/some-uuid-test-image.jpg?presigned-signature"
            val key = "images/some-uuid-test-image.jpg"
            val mockResponse = PresignUploadResponse(presignedUrl, key)

            given(spaceImageService.getPresignedUrlForImage(imageName)).willReturn(mockResponse)

            // when & then
            val result = mvcTester.post().uri("/api/v1/images")
                .param("imageName", imageName)
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()

            assertThat(result).hasStatusOk()

            val response: PresignUploadResponse = objectMapper.readValue(result.response.contentAsString)

            assertThat(response.uploadUrl).isEqualTo(presignedUrl)
            assertThat(response.url).isEqualTo(key)
            verify(spaceImageService).getPresignedUrlForImage(imageName)
        }
    }

    @Nested
    @DisplayName("이미지 삭제 API")
    inner class DeleteImage {

        @Test
        @DisplayName("요청이 성공하면 204 No Content 응답을 반환한다")
        fun deleteImage_returnsNoContent() {
            // given
            val imageKey = "images/some-uuid-test-image.jpg"
            given(spaceImageService.deleteImage(imageKey)).willAnswer { /* do nothing */ }

            // when & then
            val result = mvcTester.delete().uri("/api/v1/images")
                .param("imageKey", imageKey)
                .exchange()

            assertThat(result).hasStatus(HttpStatus.NO_CONTENT)
            verify(spaceImageService).deleteImage(imageKey)
        }
    }
}
