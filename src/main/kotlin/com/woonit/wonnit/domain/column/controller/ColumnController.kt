package com.woonit.wonnit.domain.column.controller

import com.woonit.wonnit.domain.column.dto.ColumnResponse
import com.woonit.wonnit.domain.column.service.ColumnQueryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Column", description = "메인 페이지에 노출되는 칼럼 API")
@RestController
@RequestMapping("/api/v1/columns")
class ColumnController(
    val columnQueryService: ColumnQueryService
) {

    @Operation(summary = "메인 칼럼 조회", description = "메인 페이지에 노출되는 칼럼 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "OK", content = [
        Content(mediaType = "application/json", schema = Schema(implementation = ColumnResponse::class))
    ])
    @GetMapping
    fun getColumns(): List<ColumnResponse> = columnQueryService.getMainColumns()
}