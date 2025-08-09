package com.woonit.wonnit.domain.column.controller

import com.woonit.wonnit.domain.column.dto.ColumnResponse
import com.woonit.wonnit.domain.column.service.ColumnQueryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/columns")
class ColumnController(
    val columnQueryService: ColumnQueryService
) {

    @GetMapping
    fun getColumns(): List<ColumnResponse> = columnQueryService.getMainColumns()
}