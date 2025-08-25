package com.woonit.wonnit.domain.column.repository

import com.woonit.wonnit.domain.column.Column
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface ColumnRepository : JpaRepository<Column, UUID> {

    @Query("SELECT c FROM Column c ORDER BY c.id DESC LIMIT 3")
    fun findTop3(): List<Column>
}