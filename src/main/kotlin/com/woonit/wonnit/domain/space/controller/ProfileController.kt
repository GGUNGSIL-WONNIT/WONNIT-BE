package com.woonit.wonnit.domain.space.controller

import com.woonit.wonnit.domain.space.dto.MyRentalSpacePageResponse
import com.woonit.wonnit.domain.space.dto.MySpacePageResponse
import com.woonit.wonnit.domain.space.service.SpaceQueryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Profile", description = "마이페이지 관련 API")
@RestController
@RequestMapping("/api/v1/my")
class ProfileController(
    val spaceQueryService: SpaceQueryService,
) {
    @Operation(summary = "내가 등록한 공간 목록 조회", description = "페이지 번호를 받아 내가 등록한 공간 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "OK", content = [
        Content(mediaType = "application/json", schema = Schema(implementation = MySpacePageResponse::class))
    ])
    @GetMapping("/spaces")
    fun getMySpaces(
        @Parameter(description = "유저 ID") @RequestParam userId: String,
        @Parameter(description = "페이지 번호 (0부터 시작)") @RequestParam("page", defaultValue = "0") page: Int,
    ): MySpacePageResponse {
        return spaceQueryService.getMySpaces(userId, page)
    }

    @Operation(summary = "내가 대여한 공간 목록 조회", description = "페이지 번호를 받아 내가 대여한 공간 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "OK", content = [
        Content(mediaType = "application/json", schema = Schema(implementation = MyRentalSpacePageResponse::class))
    ])
    @GetMapping("/rental-spaces")
    fun getRentalSpaces(
        @Parameter(description = "유저 ID") @RequestParam userId: String,
        @Parameter(description = "페이지 번호 (0부터 시작)") @RequestParam("page", defaultValue = "0") page: Int,
    ): MyRentalSpacePageResponse {
        return spaceQueryService.getMyRentalSpaces(userId, page)
    }
}