package com.woonit.wonnit.domain.share

import jakarta.persistence.Embeddable

@Embeddable
data class PhoneNumber(
    val value: String,
) {
    init {
        require(value.matches(Regex("""^010-\d{4}-\d{4}$"""))) { "전화번호 형식이 올바르지 않습니다." }
    }
}