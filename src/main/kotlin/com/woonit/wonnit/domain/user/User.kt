package com.woonit.wonnit.domain.user

import com.woonit.wonnit.domain.share.PhoneNumber
import com.woonit.wonnit.domain.space.Space
import com.woonit.wonnit.global.entity.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.NaturalId
import org.hibernate.annotations.NaturalIdCache

@Entity
@Table(name = "users")
@NaturalIdCache
class User(

    val name: String,

    @NaturalId
    @Embedded
    @AttributeOverride(
        name = "value",
        column = Column(name = "phone_number", unique = true, nullable = false)
    )
    val phoneNumber: PhoneNumber,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val registeredSpaces: MutableList<Space> = mutableListOf()
) : BaseEntity() {

    init {
        if (name.isBlank()) {
            throw IllegalArgumentException("이름은 비어있을 수 없습니다")
        }
    }
}