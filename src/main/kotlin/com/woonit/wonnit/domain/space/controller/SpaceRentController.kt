package com.woonit.wonnit.domain.space.controller

import com.woonit.wonnit.domain.space.dto.ReturnSpaceRequest
import com.woonit.wonnit.domain.space.service.SpaceRentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "Space Rent", description = "공간 대여/반납 관련 API")
@RestController
@RequestMapping("/api/v1/space/{spaceId}/rentals")
class SpaceRentController(
    val spaceRentService: SpaceRentService
) {
    @Operation(summary = "공간 대여", description = "ID에 해당하는 공간을 대여합니다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Created"),
            ApiResponse(responseCode = "404", description = "User or Space Not Found")
        ]
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun rentSpace(
        @Parameter(description = "대여하는 유저 ID", required = true) @RequestParam userId: String,
        @Parameter(description = "대여할 공간 ID", required = true) @PathVariable spaceId: String,
    ) {
        spaceRentService.rent(userId, spaceId)
    }

    @Operation(summary = "공간 반납 요청", description = "대여했던 공간에 대한 반납을 요청합니다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "No Content"),
            ApiResponse(responseCode = "404", description = "User or Space Not Found")
        ]
    )
    @PatchMapping("/return-request")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun returnRequest(
        @Parameter(description = "반납하는 유저 ID", required = true) @RequestParam userId: String,
        @Parameter(description = "반납할 공간 ID", required = true) @PathVariable spaceId: String,
        @Parameter(description = "반납 시 필요한 정보", required = true) @RequestBody request: ReturnSpaceRequest
    ) {
        spaceRentService.returnRequest(userId, spaceId, request)
    }

    @Operation(summary = "공간 반납 거절", description = "요청된 공간 반납을 거절합니다. 공간의 소유주만 가능합니다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "No Content"),
            ApiResponse(responseCode = "403", description = "Access Denied"),
            ApiResponse(responseCode = "404", description = "User or Space Not Found")
        ]
    )
    @PatchMapping("/return-reject")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun returnReject(
        @Parameter(description = "반납을 거절하는 유저(소유주) ID", required = true) @RequestParam userId: String,
        @Parameter(description = "반납 요청된 공간 ID", required = true) @PathVariable spaceId: String,
    ) {
        spaceRentService.returnReject(userId, spaceId)
    }

    @Operation(summary = "공간 반납 승인", description = "요청된 공간 반납을 승인합니다. 공간의 소유주만 가능합니다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "No Content"),
            ApiResponse(responseCode = "403", description = "Access Denied"),
            ApiResponse(responseCode = "404", description = "User or Space Not Found")
        ]
    )
    @PatchMapping("/return-approve")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun returnApprove(
        @Parameter(description = "반납을 승인하는 유저(소유주) ID", required = true) @RequestParam userId: String,
        @Parameter(description = "반납 요청된 공간 ID", required = true) @PathVariable spaceId: String,
    ) {
        spaceRentService.returnApprove(userId, spaceId)
    }

    @Operation(summary = "공간 재등록하기", description = "반납 반려되었던 공간을 재등록합니다. 공간의 소유주만 가능합니다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "No Content"),
            ApiResponse(responseCode = "403", description = "Access Denied"),
            ApiResponse(responseCode = "404", description = "User or Space Not Found")
        ]
    )
    @PatchMapping("/re-registration")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun reRegistration(
        @Parameter(description = "소유주 ID", required = true) @RequestParam userId: String,
        @Parameter(description = "재등록할(반납 반려되었던) 공간 ID", required = true) @PathVariable spaceId: String,
    ) {
        spaceRentService.reRegistration(userId, spaceId)
    }
}