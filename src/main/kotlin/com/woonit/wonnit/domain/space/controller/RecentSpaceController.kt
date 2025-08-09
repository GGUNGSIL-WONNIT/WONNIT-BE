package com.woonit.wonnit.domain.space.controller

import com.woonit.wonnit.domain.space.dto.RecentSpaceResponse
import com.woonit.wonnit.domain.space.service.SpaceQueryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/recent-spaces")
class RecentSpaceController(
    val spaceQueryService: SpaceQueryService
) {
    @GetMapping
    fun getRecentSpaces(): List<RecentSpaceResponse> {
        return spaceQueryService.getRecentSpaces()
    }
}