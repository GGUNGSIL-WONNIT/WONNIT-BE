package com.woonit.wonnit.domain.space.controller

import com.woonit.wonnit.domain.space.dto.PresignUploadResponse
import com.woonit.wonnit.domain.space.service.SpaceImageService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Space Image", description = "공간 이미지 API")
@RestController
@RequestMapping("/api/v1/images")
class SpaceImageController(
    private val spaceImageService: SpaceImageService
) {

    @Operation(
        summary = "이미지 업로드 Presigned URL 발급",
        description = """
        클라이언트가 S3에 직접 업로드할 수 있도록 Presigned URL을 발급
        - 요청: 업로드할 파일명(필수)
        - 응답: Presigned URL, key
        """,
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Presigned URL 발급 성공",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = PresignUploadResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "잘못된 요청"
            )
        ]
    )
    @PostMapping
    fun getPresignedUrlForModel(
        @Parameter(
            description = "업로드할 이미지 파일 이름",
            required = true
        ) @RequestParam("imageName") imageName: String
    ): ResponseEntity<PresignUploadResponse> {
        val response = spaceImageService.getPresignedUrlForImage(imageName)
        return ResponseEntity.ok(response)
    }

    @Operation(
        summary = "이미지 삭제",
        description = "업로드 시 발급받은 key를 전달하면 해당 이미지를 S3에서 삭제",
        responses = [
            ApiResponse(
                responseCode = "204",
                description = "No Content"
            ),
            ApiResponse(responseCode = "404", description = "파일을 찾을 수 없음")
        ]
    )
    @DeleteMapping
    fun deleteImage(
        @Parameter(
            description = "S3에 업로드된 이미지의 key",
            required = true
        ) @RequestParam("imageKey") imageKey: String
    ): ResponseEntity<Void> {
        spaceImageService.deleteImage(imageKey)
        return ResponseEntity.noContent().build()
    }
}