package com.woonit.wonnit.domain.space.controller

import com.woonit.wonnit.domain.space.dto.MySpaceResponse
import com.woonit.wonnit.domain.space.service.SpaceQueryService
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/my")
class ProfileController(
    val spaceQueryService: SpaceQueryService,
) {
    @Value("\${test-user.id}")
    private lateinit var userId: String

    @GetMapping("/spaces")
    fun getMySpaces(
        @RequestParam("page", defaultValue = "0") page: Int,
    ): List<MySpaceResponse> {
        return spaceQueryService.getMySpaces(userId, page)
    }
}