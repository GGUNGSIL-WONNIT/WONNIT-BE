package com.woonit.wonnit.domain.space.controller

import com.woonit.wonnit.domain.space.dto.SpaceSearchResponse
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

@Tag(name = "Search", description = "공간 검색 API")
@RestController
@RequestMapping("/api/v1/search")
class SearchController(
    val spaceQueryService: SpaceQueryService
) {

    @Operation(summary = "내 주변 공간 검색", description = "현재 위도와 경도를 기준으로 내 주변에 있는 공간을 검색합니다.")
    @ApiResponse(responseCode = "200", description = "OK", content = [
        Content(mediaType = "application/json", schema = Schema(implementation = SpaceSearchResponse::class))
    ])
    @GetMapping
    fun getNearBySpaces(
        @Parameter(description = "위도", required = true, example = "37.50448") @RequestParam("lat") lat: Double,
        @Parameter(description = "경도", required = true, example = "127.0489") @RequestParam("lon") lon: Double
    ): List<SpaceSearchResponse> {
        return spaceQueryService.getNearBySpaces(lat, lon)
    }
}