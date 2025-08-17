package com.woonit.wonnit.domain.share

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.time.DayOfWeek
import java.time.LocalDateTime

@Schema(description = "운영 정보")
@Embeddable
data class OperationalInfo(

    @Schema(description = "운영 요일")
    @Column(nullable = false)
    val dayOfWeeks: List<DayOfWeek>,

    @Schema(description = "시작 시간")
    @Column(nullable = false)
    val startAt: LocalDateTime,

    @Schema(description = "종료 시간")
    @Column(nullable = false)
    val endAt: LocalDateTime,
) {
    init {
        require(dayOfWeeks.isNotEmpty()) { "영업 요일은 비어있을 수 없습니다" }
        require(startAt.isBefore(endAt)) { "시작 시간은 종료 시간 이전이어야 합니다" }
        require(endAt.isAfter(startAt)) { "종료 시간은 시작 시간 이후이어야 합니다" }
    }
}
