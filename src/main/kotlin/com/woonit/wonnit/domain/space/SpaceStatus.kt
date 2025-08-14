package com.woonit.wonnit.domain.space

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "공간 상태")
enum class SpaceStatus {
    AVAILABLE,       // 대여 가능
    OCCUPIED,        // 대여 불가
    RETURN_REQUEST,  // 반납 요청(확인 중)
    RETURN_REJECTED, // 반납 반려
}