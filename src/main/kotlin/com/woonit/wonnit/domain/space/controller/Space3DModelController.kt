package com.woonit.wonnit.domain.space.controller

import com.woonit.wonnit.domain.space.dto.PresignUploadResponse
import com.woonit.wonnit.domain.space.service.Space3DModelService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Space 3D Model", description = "공간 3D 모델 API")
@RestController
@RequestMapping("/api/v1/models")
class Space3DModelController(
    private val modelService: Space3DModelService
) {

    @Operation(
        summary = "모델 업로드 Presigned URL 발급",
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
            description = "업로드할 모델 파일 이름",
            required = true
        ) @RequestParam("modelName") modelName: String
    ): ResponseEntity<PresignUploadResponse> {
        val response = modelService.getPresignedUrlForModel(modelName)
        return ResponseEntity.ok(response)
    }
}