package com.woonit.wonnit.domain.space.controller

import com.woonit.wonnit.domain.space.dto.SpaceSearchResponse
import com.woonit.wonnit.domain.space.service.SpaceQueryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Search", description = "검색 API")
@RestController
@RequestMapping("/api/v1/search")
class SearchController(
    val spaceQueryService: SpaceQueryService
) {

    @Operation(summary = "내 주변 공간 검색", description = "내 주변 공간을 검색합니다.")
    @GetMapping
    fun getNearBySpaces(
        @RequestParam("lat", required = true) lat: Double,
        @RequestParam("lon", required = true) lon: Double
    ): List<SpaceSearchResponse> {
        return spaceQueryService.getNearBySpaces(lat, lon)
    }
}