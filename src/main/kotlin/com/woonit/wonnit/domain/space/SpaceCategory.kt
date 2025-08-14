package com.woonit.wonnit.domain.space

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "공간 카테고리")
enum class SpaceCategory() {
    SMALL_THEATER,         // 소극장·전시 공간
    MAKER_SPACE,           // 창작공방·메이커스페이스
    MUSIC_PRACTICE_ROOM,   // 음악 연습실
    DANCE_STUDIO,          // 댄스 연습실
    STUDY_ROOM             // 회의실·스터디룸
}