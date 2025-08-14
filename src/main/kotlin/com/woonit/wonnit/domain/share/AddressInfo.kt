package com.woonit.wonnit.domain.share

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Embeddable

@Schema(description = "주소 정보")
@Embeddable
data class AddressInfo(
    @Schema(description = "주소")
    val address1: String,
    @Schema(description = "상세 주소")
    val address2: String?,
    @Schema(description = "위도")
    val lat: Double,
    @Schema(description = "경도")
    val lon: Double,
) {
    init {
        require(address1.isNotEmpty()) { "주소는 비어있을 수 없습니다" }
    }
}
