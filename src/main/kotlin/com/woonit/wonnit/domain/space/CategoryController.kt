package com.woonit.wonnit.domain.space

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/categories")
class CategoryController {

    @GetMapping
    fun getSpaceCategories(): MutableList<SpaceCategory> {
        return SpaceCategory.entries.toMutableList()
    }

}