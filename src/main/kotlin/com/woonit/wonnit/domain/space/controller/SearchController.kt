package com.woonit.wonnit.domain.space.controller

import com.woonit.wonnit.domain.space.dto.SpaceSearchResponse
import com.woonit.wonnit.domain.space.service.SpaceQueryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/search")
class SearchController(
    val spaceQueryService: SpaceQueryService
) {

    @GetMapping
    fun getNearBySpaces(
        @RequestParam("lat", required = true) lat: Double,
        @RequestParam("lon", required = true) lon: Double
    ): List<SpaceSearchResponse> {
        return spaceQueryService.getNearBySpaces(lat, lon)
    }
}