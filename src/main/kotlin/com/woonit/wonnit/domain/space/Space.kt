package com.woonit.wonnit.domain.space

import com.woonit.wonnit.domain.share.AddressInfo
import com.woonit.wonnit.domain.share.AmountInfo
import com.woonit.wonnit.domain.share.OperationalInfo
import com.woonit.wonnit.domain.share.PhoneNumber
import com.woonit.wonnit.domain.user.User
import com.woonit.wonnit.global.entity.BaseEntity
import com.woonit.wonnit.global.entity.PrimaryKeyEntity
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

    val tags: MutableList<String> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User

) : BaseEntity() {

    init {
        require(subImgUrls.isNotEmpty()) { "추가 사진은 비어있을 수 없습니다" }
    }

    companion object {
        fun register(
            category: SpaceCategory,
            name: String,
            mainImgUrl: String,
            subImgUrls: List<String>,
            addressInfo: AddressInfo,
            amountInfo: AmountInfo,
            size: Double,
            operationalInfo: OperationalInfo,
            spaceModelUrl: String?,
            user: User,
            phoneNumber: PhoneNumber,
            precautions: String?
        ) = Space(
            spaceCategory = category,
            name = name,
            mainImgUrl = mainImgUrl,
            subImgUrls = subImgUrls,
            addressInfo = addressInfo,
            amountInfo = amountInfo,
            size = size,
            operationalInfo = operationalInfo,
            spaceModelUrl = spaceModelUrl,
            user = user,
            phoneNumber = phoneNumber,
            precautions = precautions
        )
    }

    fun update() {

    }

}