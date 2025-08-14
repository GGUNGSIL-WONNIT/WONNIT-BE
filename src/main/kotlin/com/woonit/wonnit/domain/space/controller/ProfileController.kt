package com.woonit.wonnit.domain.space.controller

import com.woonit.wonnit.domain.space.dto.MyRentalSpacePageResponse
import com.woonit.wonnit.domain.space.dto.MySpacePageResponse
import com.woonit.wonnit.domain.space.service.SpaceQueryService
import com.woonit.wonnit.global.annotation.UserId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/my")
class ProfileController(
    val spaceQueryService: SpaceQueryService,
) {
    @GetMapping("/spaces")
    fun getMySpaces(
        @UserId userId: String,
        @RequestParam("page", defaultValue = "0") page: Int,
    ): MySpacePageResponse {
        return spaceQueryService.getMySpaces(userId, page)
    }

    @GetMapping("/rental-spaces")
    fun getRentalSpaces(
        @UserId userId: String,
        @RequestParam("page", defaultValue = "0") page: Int,
    ): MyRentalSpacePageResponse {
        return spaceQueryService.getMyRentalSpaces(userId, page)
    }
}