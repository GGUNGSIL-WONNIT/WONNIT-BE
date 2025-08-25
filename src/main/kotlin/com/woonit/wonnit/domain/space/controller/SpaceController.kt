package com.woonit.wonnit.domain.space.controller

import com.woonit.wonnit.domain.space.dto.SpaceDetailResponse
import com.woonit.wonnit.domain.space.dto.SpaceSaveRequest
import com.woonit.wonnit.domain.space.service.SpaceCommandService
import com.woonit.wonnit.domain.space.service.SpaceQueryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@Tag(name = "Space", description = "공간 API")
@RestController
@RequestMapping("/api/v1/spaces")
class SpaceController(
    val spaceCommandService: SpaceCommandService,
    val spaceQueryService: SpaceQueryService
) {

    @Operation(summary = "새로운 공간 등록", description = "새로운 공간을 등록합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Created"
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid Category",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ProblemDetail::class)
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "User Not Found",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ProblemDetail::class)
                )]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal Server Error",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ProblemDetail::class)
                )]
            ),
        ]
    )

    @PostMapping
    fun createSpace(
        @Parameter(description = "등록할 공간의 정보", required = true) @Valid @RequestBody spaceSaveRequest: SpaceSaveRequest,
        @Parameter(description = "유저 ID", required = true) @RequestParam userId: String
    ): ResponseEntity<Void> {
        spaceCommandService.createSpace(spaceSaveRequest, userId)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @Operation(summary = "공간 수정", description = "기존에 등록된 공간을 수정합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "OK"
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid Category",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ProblemDetail::class)
                )]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Access Denied",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ProblemDetail::class)
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Space Not Found",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ProblemDetail::class)
                )]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal Server Error",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ProblemDetail::class)
                )]
            ),
        ]
    )
    @PutMapping("/{spaceId}")
    fun updateSpace(
        @Parameter(description = "수정할 공간의 ID", required = true) @PathVariable spaceId: String,
        @Parameter(description = "유저 ID", required = true) @RequestParam userId: String,
        @Parameter(description = "수정할 공간의 정보", required = true) @Valid @RequestBody spaceSaveRequest: SpaceSaveRequest
    ): ResponseEntity<Void> {
        spaceCommandService.updateSpace(spaceId, spaceSaveRequest, userId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @Operation(summary = "공간 일괄 삭제", description = "여러 공간을 한 번에 삭제합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "No Content"
            ),
            ApiResponse(
                responseCode = "403",
                description = "Access Denied",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ProblemDetail::class)
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Space Not Found",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ProblemDetail::class)
                )]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal Server Error",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ProblemDetail::class)
                )]
            ),
        ]
    )
    @DeleteMapping
    fun deleteSpaces(
        @Parameter(description = "삭제할 공간들의 ID 목록", required = true) @RequestParam spaceIds: List<String>,
        @Parameter(description = "유저 ID", required = true) @RequestParam userId: String
    ): ResponseEntity<Void> {
        spaceCommandService.deleteSpaces(spaceIds, userId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @Operation(summary = "공간 상세 조회", description = "공간의 상세 정보를 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "OK",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = SpaceDetailResponse::class)
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Space Not Found",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ProblemDetail::class)
                )]
            )
        ]
    )
    @GetMapping("/{spaceId}")
    fun getSpaceDetail(
        @Parameter(
            description = "조회할 공간의 ID",
            required = true
        ) @PathVariable spaceId: String
    ): ResponseEntity<SpaceDetailResponse> {
        val response = spaceQueryService.getSpaceDetail(spaceId)
        return ResponseEntity.ok(response)
    }
}