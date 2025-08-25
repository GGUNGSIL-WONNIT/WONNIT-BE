package com.woonit.wonnit.global.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
class BaseEntity : PrimaryKeyEntity() {
    @CreatedDate
    @Column
    val createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    @Column
    var updatedAt: LocalDateTime ?= null
}