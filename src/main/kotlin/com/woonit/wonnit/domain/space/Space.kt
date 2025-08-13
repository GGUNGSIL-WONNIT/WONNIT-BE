package com.woonit.wonnit.domain.space

import com.woonit.wonnit.domain.share.AddressInfo
import com.woonit.wonnit.domain.share.AmountInfo
import com.woonit.wonnit.domain.share.OperationalInfo
import com.woonit.wonnit.domain.share.PhoneNumber
import com.woonit.wonnit.domain.user.User
import com.woonit.wonnit.global.entity.BaseEntity
import jakarta.persistence.*
import lombok.NoArgsConstructor
import kotlin.reflect.KProperty

@Entity
@Table(name = "spaces")
@NoArgsConstructor
class Space (
    name: String,
    addressInfo: AddressInfo,
    mainImgUrl: String,
    subImgUrls: MutableList<String>,
    spaceCategory: SpaceCategory,
    size: Double,
    operationalInfo: OperationalInfo,
    amountInfo: AmountInfo,
    spaceModelUrl: String?,
    modelThumbnailUrl: String?,
    phoneNumber: PhoneNumber,
    precautions: String?,
    tags: MutableList<String>,
    user: User,
): BaseEntity () {

    @Column(length = 255, nullable = false)
    var name: String = name
        protected set

    @Embedded
    var addressInfo: AddressInfo = addressInfo
        protected set

    @Column(nullable = false)
    var mainImgUrl: String = mainImgUrl
        protected set

    @ElementCollection
    @CollectionTable(
        name = "space_sub_images",
        joinColumns = [JoinColumn(name = "space_id")]
    )
    @Column(name = "url", nullable = false)
    var subImgUrls: MutableList<String> = subImgUrls
        protected set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var spaceCategory: SpaceCategory = spaceCategory
        protected set

    @Column(nullable = false)
    var size: Double = size
        protected set

    @Embedded
    var operationalInfo: OperationalInfo = operationalInfo
        protected set

    @Embedded
    var amountInfo: AmountInfo = amountInfo
        protected set

    var spaceModelUrl: String? = spaceModelUrl
        protected set

    var modelThumbnailUrl: String? = modelThumbnailUrl
        protected set

    @Embedded
    @AttributeOverride(
        name = "value",
        column = Column(name = "phone_number", nullable = false)
    )
    var phoneNumber: PhoneNumber = phoneNumber
        protected set

    @Column(columnDefinition = "TEXT")
    var precautions: String? = precautions
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User = user

    @ElementCollection
    @CollectionTable(
        name = "space_tags",
        joinColumns = [JoinColumn(name = "space_id")]
    )
    @Column(name = "tag", nullable = false)
    var tags: MutableList<String> = tags
        protected set

    init {
        require(subImgUrls.isNotEmpty()) { "추가 사진은 비어있을 수 없습니다" }
    }

    companion object {
        fun register(
            category: SpaceCategory,
            name: String,
            mainImgUrl: String,
            subImgUrls: MutableList<String>,
            addressInfo: AddressInfo,
            amountInfo: AmountInfo,
            size: Double,
            operationalInfo: OperationalInfo,
            spaceModelUrl: String?,
            modelThumbnailUrl: String?,
            user: User,
            phoneNumber: PhoneNumber,
            precautions: String?,
            tags: MutableList<String> = mutableListOf(),
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
            modelThumbnailUrl = modelThumbnailUrl,
            user = user,
            phoneNumber = phoneNumber,
            precautions = precautions,
            tags = tags
        )
    }

    public fun update(
        category: SpaceCategory,
        name: String,
        mainImgUrl: String,
        subImgUrls: MutableList<String>,
        addressInfo: AddressInfo,
        amountInfo: AmountInfo,
        size: Double,
        operationalInfo: OperationalInfo,
        spaceModelUrl: String?,
        modelThumbnailUrl: String?,
        phoneNumber: PhoneNumber,
        precautions: String?,
        tags: MutableList<String> = mutableListOf(),
    ) {
        this.spaceCategory   = category
        this.name            = name
        this.mainImgUrl      = mainImgUrl
        this.subImgUrls      = subImgUrls
        this.addressInfo     = addressInfo
        this.amountInfo      = amountInfo
        this.size            = size
        this.operationalInfo = operationalInfo
        this.spaceModelUrl   = spaceModelUrl
        this.modelThumbnailUrl = modelThumbnailUrl
        this.phoneNumber     = phoneNumber
        this.precautions     = precautions
        this.tags = tags
    }
}