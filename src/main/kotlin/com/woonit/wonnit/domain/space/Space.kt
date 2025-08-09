package com.woonit.wonnit.domain.space

import com.woonit.wonnit.domain.share.AddressInfo
import com.woonit.wonnit.domain.share.AmountInfo
import com.woonit.wonnit.domain.share.OperationalInfo
import com.woonit.wonnit.domain.share.PhoneNumber
import com.woonit.wonnit.global.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "spaces")
class Space(

    @Column(length = 255, nullable = false)
    val name: String,

    @Column(nullable = false)
    val addressInfo: AddressInfo,

    @Column(nullable = false)
    val mainImgUrl: String,

    val subImgUrls: List<String>,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val spaceCategory: SpaceCategory,

    @Column(nullable = false)
    val size: Double,

    val operationalInfo: OperationalInfo,

    val amountInfo: AmountInfo,

    val spaceModelUrl: String?,

    @Embedded
    @AttributeOverride(
        name = "value",
        column = Column(name = "phone_number", nullable = false)
    )
    val phoneNumber: PhoneNumber,

    @Column(columnDefinition = "TEXT")
    val precautions: String?,

    val tags: MutableList<String> = mutableListOf()
) : BaseEntity() {

    init {
        require(subImgUrls.isNotEmpty()) { "추가 사진은 비어있을 수 없습니다" }
    }

    companion object {
        fun register() {

        }
    }

    fun update() {

    }

}