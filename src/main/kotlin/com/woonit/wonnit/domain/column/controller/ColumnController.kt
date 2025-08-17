package com.woonit.wonnit.domain.column.controller

import com.woonit.wonnit.domain.column.dto.ColumnResponse
import com.woonit.wonnit.domain.column.service.ColumnQueryService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/columns")
class ColumnController(
    val columnQueryService: ColumnQueryService
) {

    @Operation(summary = "칼럼 조회", description = "칼럼을 조회합니다.")
    @GetMapping
    fun getColumns(): List<ColumnResponse> = columnQueryService.getMainColumns()
}