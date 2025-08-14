package com.woonit.wonnit.domain.space.controller

import com.woonit.wonnit.domain.space.SpaceCategory
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Category", description = "카테고리 API")
@RestController
@RequestMapping("/api/v1/categories")
class CategoryController {

    @Operation(summary = "카테고리 목록 조회", description = "카테고리 목록을 조회합니다.")
    @GetMapping
    fun getSpaceCategories(): MutableList<SpaceCategory> {
        return SpaceCategory.entries.toMutableList()
    }

}