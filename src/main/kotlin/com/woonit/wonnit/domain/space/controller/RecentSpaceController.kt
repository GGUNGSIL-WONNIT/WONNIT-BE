package com.woonit.wonnit.domain.space.controller

import com.woonit.wonnit.domain.space.dto.RecentSpaceResponse
import com.woonit.wonnit.domain.space.service.SpaceQueryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Recent Space", description = "최근 공간 API")
@RestController
@RequestMapping("/api/v1/recent-spaces")
class RecentSpaceController(
    val spaceQueryService: SpaceQueryService
) {
    @Operation(summary = "최근 등록된 공간 목록 조회", description = "최근 등록된 공간 목록을 조회합니다.")
    @GetMapping
    fun getRecentSpaces(): List<RecentSpaceResponse> {
        return spaceQueryService.getRecentSpaces()
    }
}