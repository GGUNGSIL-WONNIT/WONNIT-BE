package com.woonit.wonnit.domain.space.controller

import com.woonit.wonnit.domain.space.dto.MyRentalSpacePageResponse
import com.woonit.wonnit.domain.space.dto.MySpacePageResponse
import com.woonit.wonnit.domain.space.service.SpaceQueryService
import com.woonit.wonnit.global.annotation.UserId
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Profile", description = "프로필 API")
@RestController
@RequestMapping("/api/v1/my")
class ProfileController(
    val spaceQueryService: SpaceQueryService,
) {
    @Operation(summary = "내가 등록한 공간 목록 조회", description = "내가 등록한 공간 목록을 조회합니다.")
    @GetMapping("/spaces")
    fun getMySpaces(
        @UserId userId: String,
        @RequestParam("page", defaultValue = "0") page: Int,
    ): MySpacePageResponse {
        return spaceQueryService.getMySpaces(userId, page)
    }

    @Operation(summary = "내가 대여한 공간 목록 조회", description = "내가 대여한 공간 목록을 조회합니다.")
    @GetMapping("/rental-spaces")
    fun getRentalSpaces(
        @UserId userId: String,
        @RequestParam("page", defaultValue = "0") page: Int,
    ): MyRentalSpacePageResponse {
        return spaceQueryService.getMyRentalSpaces(userId, page)
    }
}