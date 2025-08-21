package com.woonit.wonnit.domain.space.controller

import com.woonit.wonnit.domain.space.dto.SpaceDetailResponse
import com.woonit.wonnit.domain.space.dto.SpaceSaveRequest
import com.woonit.wonnit.domain.space.service.SpaceQueryService
import com.woonit.wonnit.domain.space.service.SpaceUpdateService
import com.woonit.wonnit.global.annotation.UserId
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/spaces")
class SpaceController(
    val spaceUpdateService: SpaceUpdateService,
    private val spaceQueryService: SpaceQueryService
) {

    @PostMapping
    @Operation(summary = "새로운 공간 등록")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Created",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ApiResponse::class)
                )]
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
    fun createSpace(
        @Valid @RequestBody spaceSaveRequest: SpaceSaveRequest,
        @UserId userId: String
    ): ResponseEntity<Void> {
        spaceUpdateService.createSpace(spaceSaveRequest, userId)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PutMapping("/{spaceId}")
    @Operation(summary = "공간 수정")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "OK",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ApiResponse::class)
                )]
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
    fun updateSpace(
        @PathVariable spaceId: String,
        @UserId userId: String,
        @Valid @RequestBody spaceSaveRequest: SpaceSaveRequest
    ): ResponseEntity<Void> {
        spaceUpdateService.updateSpace(spaceId, spaceSaveRequest, userId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @DeleteMapping
    @Operation(summary = "공간 일괄 삭제")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "No Content",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ApiResponse::class)
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
    fun deleteSpaces(
        @RequestParam spaceIds: List<String>,
        @UserId userId: String
    ): ResponseEntity<Void> {
        spaceUpdateService.deleteSpaces(spaceIds, userId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @GetMapping("/{spaceId}")
    fun getSpaceDetail(@PathVariable spaceId: UUID): ResponseEntity<SpaceDetailResponse> {
        val response = spaceQueryService.getSpaceDetail(spaceId)
        return ResponseEntity.ok(response)
    }
}