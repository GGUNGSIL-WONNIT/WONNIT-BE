package com.woonit.wonnit.domain.share

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.time.DayOfWeek
import java.time.LocalDateTime

@Embeddable
data class OperationalInfo(

    @Column(nullable = false)
    val dayOfWeeks: List<DayOfWeek>,

    @Column(nullable = false)
    val startAt: LocalDateTime,

    @Column(nullable = false)
    val endAt: LocalDateTime,
) {
    init {
        require(dayOfWeeks.isNotEmpty()) { "영업 요일은 비어있을 수 없습니다" }
        require(startAt.isBefore(endAt)) { "시작 시간은 종료 시간 이전이어야 합니다" }
        require(endAt.isAfter(startAt)) { "종료 시간은 시작 시간 이후이어야 합니다" }
    }
}
