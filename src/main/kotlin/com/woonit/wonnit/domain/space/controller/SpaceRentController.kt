package com.woonit.wonnit.domain.space.controller

import com.woonit.wonnit.domain.space.service.SpaceRentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "Space Rent", description = "공간 대여 API")
@RestController
@RequestMapping("/api/v1/space/{spaceId}/rentals")
class SpaceRentController(
    val spaceRentService: SpaceRentService
) {
    @Operation(summary = "공간 대여", description = "공간을 대여합니다.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun rentSpace(
        @RequestParam userId: String,
        @PathVariable spaceId: String,
    ) {
        spaceRentService.rent(userId, spaceId)
    }

    @Operation(summary = "공간 반납 요청", description = "공간 반납을 요청합니다.")
    @PatchMapping("/return-request")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun returnRequest(
        @RequestParam userId: String,
        @PathVariable spaceId: String,
    ) {
        spaceRentService.returnRequest(userId, spaceId)
    }

    @Operation(summary = "공간 반납 거절", description = "공간 반납을 거절합니다.")
    @PatchMapping("/return-reject")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun returnReject(
        @RequestParam userId: String,
        @PathVariable spaceId: String,
    ) {
        spaceRentService.returnReject(userId, spaceId)
    }

    @Operation(summary = "공간 반납 승인", description = "공간 반납을 승인합니다.")
    @PatchMapping("/return-approve")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun returnApprove(
        @RequestParam userId: String,
        @PathVariable spaceId: String,
    ) {
        spaceRentService.returnApprove(userId, spaceId)
    }
}