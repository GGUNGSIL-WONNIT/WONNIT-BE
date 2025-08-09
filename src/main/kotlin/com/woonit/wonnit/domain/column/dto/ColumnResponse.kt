package com.woonit.wonnit.domain.column.dto

import com.woonit.wonnit.domain.column.Column

data class ColumnResponse(
    val columnId: String,
    val title: String,
    val subtitle: String,
    val url: String,
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
