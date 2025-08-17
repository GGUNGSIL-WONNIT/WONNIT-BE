package com.woonit.wonnit.domain.column.dto

import com.woonit.wonnit.domain.column.Column
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description =  "칼럼 응답")
data class ColumnResponse(
    @Schema(description = "칼럼 ID")
    val columnId: String,
    @Schema(description = "제목")
    val title: String,
    @Schema(description = "소제목")
    val subtitle: String,
    @Schema(description = "칼럼 주소")
    val url: String,
    @Schema(description = "썸네일 경로")
    val thumbnailUrl: String
) {
    companion object {
        fun from(column: Column): ColumnResponse {
            return ColumnResponse(
                columnId = column.id.toString(),
                title = column.title,
                subtitle = column.subtitle,
                url = column.url,
                thumbnailUrl = column.thumbnailUrl
            )
        }
    }
}
