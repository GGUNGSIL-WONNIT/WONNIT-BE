package com.woonit.wonnit.domain.column.service

import com.woonit.wonnit.domain.column.dto.ColumnResponse
import com.woonit.wonnit.domain.column.repository.ColumnRepository
import org.springframework.stereotype.Service

@Service
class ColumnQueryService(
    val columRepository: ColumnRepository
) {
    fun getMainColumns(): List<ColumnResponse> {
        val columns = columRepository.findTop3()
        return columns.map { column -> ColumnResponse.from(column) }
    }
}