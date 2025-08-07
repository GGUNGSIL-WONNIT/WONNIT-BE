package com.woonit.wonnit.domain.share

import jakarta.persistence.Embeddable

@Embeddable
data class AddressInfo(
    val address1: String,
    val address2: String?,
    val lat: Double,
    val lon: Double,
) {
    init {
        require(address1.isNotEmpty()) { "주소는 비어있을 수 없습니다" }
    }
}
