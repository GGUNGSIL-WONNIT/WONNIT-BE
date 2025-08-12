package com.woonit.wonnit.domain.space

enum class SpaceStatus {
    AVAILABLE,       // 대여 가능
    OCCUPIED,        // 대여 불가
    RETURN_REQUEST,  // 반납 요청(확인 중)
    RETURN_REJECTED, // 반납 반려
}