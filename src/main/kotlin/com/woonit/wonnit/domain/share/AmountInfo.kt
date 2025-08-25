package com.woonit.wonnit.domain.share

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Schema(description = "가격 정보")
@Embeddable
data class AmountInfo(

    @Schema(description = "시간 단위")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val timeUnit: TimeUnit,

    @Schema(description = "가격")
    @Column(nullable = false)
    val amount: Long
) {
    init {
        require(amount >= 0) { "가격은 0원 이상이어야 합니다" }
    }
}
