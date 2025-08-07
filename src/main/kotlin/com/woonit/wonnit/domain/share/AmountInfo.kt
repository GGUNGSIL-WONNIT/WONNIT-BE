package com.woonit.wonnit.domain.share

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Embeddable
data class AmountInfo(

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val timeUnit: TimeUnit,

    @Column(nullable = false)
    val amount: Long
) {
    init {
        require(amount >= 0) { "가격은 0원 이상이어야 합니다" }
    }
}
