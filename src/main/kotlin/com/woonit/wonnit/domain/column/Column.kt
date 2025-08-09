package com.woonit.wonnit.domain.column

import com.woonit.wonnit.global.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "columns")
class Column(

    @Column(nullable = false, unique = true)
    val title: String,

    @Column(nullable = false)
    val subtitle: String,

    @Column(nullable = false)
    val url: String,

    @Column(nullable = false)
    val thumbnailUrl: String,
) : BaseEntity() {

    init {
        require(title.isNotEmpty()) { "제목은 비어있을 수 없습니다" }
        require(subtitle.isNotEmpty()) { "소제목은 비어있을 수 없습니다" }
        require(url.isNotEmpty()) { "URL은 비어있을 수 없습니다" }
        require(thumbnailUrl.isNotEmpty()) { "썸네일은 비어있을 수 없습니다" }
    }
}