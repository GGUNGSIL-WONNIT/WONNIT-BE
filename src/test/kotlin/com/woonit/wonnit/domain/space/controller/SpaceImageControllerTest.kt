package com.woonit.wonnit.domain.space.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.woonit.wonnit.domain.space.dto.PresignUploadResponse
import com.woonit.wonnit.domain.space.service.SpaceImageService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@WebMvcTest(SpaceImageController::class)
class SpaceImageControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

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
            val presignedUrl = "https://s3.ap-northeast-2.amazonaws.com/wonnit-bucket/images/some-uuid-test-image.jpg?presigned-signature"
            val key = "images/some-uuid-test-image.jpg"
            val mockResponse = PresignUploadResponse(presignedUrl, key)

            given(spaceImageService.getPresignedUrlForImage(imageName)).willReturn(mockResponse)

            // when & then
            mockMvc.perform(
                post("/api/v1/images")
                    .param("imageName", imageName)
                    .accept(MediaType.APPLICATION_JSON)
            )
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.uploadUrl").value(presignedUrl))
                .andExpect(jsonPath("$.url").value(key))

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
            mockMvc.perform(
                delete("/api/v1/images")
                    .param("imageKey", imageKey)
            )
                .andExpect(status().isNoContent)

            verify(spaceImageService).deleteImage(imageKey)
        }
    }
}
