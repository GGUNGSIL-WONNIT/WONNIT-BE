package com.woonit.wonnit.domain.space.controller

import com.woonit.wonnit.domain.space.SpaceCategory
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Category", description = "공간 카테고리 API")
@RestController
@RequestMapping("/api/v1/categories")
class CategoryController {

    @Operation(summary = "공간 카테고리 목록 조회", description = "공간을 등록할 때 사용되는 카테고리 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "OK", content = [
        Content(mediaType = "application/json", schema = Schema(implementation = SpaceCategory::class, example = "[\"STUDY_ROOM\", \"OFFICE\"]"))
    ])
    @GetMapping
    fun getSpaceCategories(): List<SpaceCategory> {
        return SpaceCategory.entries
    }

}