package com.woonit.wonnit.domain.column.service

import com.woonit.wonnit.domain.column.dto.ColumnResponse
import com.woonit.wonnit.domain.column.repository.ColumnRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ColumnQueryService(
    private val columnRepository: ColumnRepository,
) {
    /**
     * Retrieves the top 3 columns to be displayed on the main page.
     *
     * @return A list of [ColumnResponse] objects.
     */
    fun getMainColumns(): List<ColumnResponse> {
        val columns = columnRepository.findTop3()
        return columns.map { ColumnResponse.from(it) }
    }
}
