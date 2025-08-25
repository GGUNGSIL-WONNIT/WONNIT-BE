package com.woonit.wonnit.domain.space

import com.woonit.wonnit.domain.share.AddressInfo
import com.woonit.wonnit.domain.share.AmountInfo
import com.woonit.wonnit.domain.share.OperationalInfo
import com.woonit.wonnit.domain.share.PhoneNumber
import com.woonit.wonnit.domain.space.dto.ReturnSpaceRequest
import com.woonit.wonnit.domain.space.dto.SpaceSaveRequest
import com.woonit.wonnit.domain.user.User
import com.woonit.wonnit.global.entity.BaseEntity
import jakarta.persistence.*
import lombok.NoArgsConstructor

@Entity
@Table(name = "spaces")
@NoArgsConstructor
class Space(
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
    beforeImgUrl: String?,
    afterImgUrl: String?,
    resultImgUrl: String?,
    similarity: Double?,
) : BaseEntity() {

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

    @ElementCollection
    @CollectionTable(
        name = "space_tags",
        joinColumns = [JoinColumn(name = "space_id")]
    )
    @Column(name = "tag", nullable = false)
    var tags: MutableList<String> = tags
        protected set

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User = user

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var spaceStatus: SpaceStatus = SpaceStatus.AVAILABLE

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renter_id")
    var renter: User? = null

    var beforeImgUrl: String? = beforeImgUrl
        protected set

    var afterImgUrl: String? = afterImgUrl
        protected set

    var resultImgUrl: String? = resultImgUrl
        protected set

    var similarity: Double? = similarity
        protected set

    init {
        require(subImgUrls.isNotEmpty()) { "추가 사진은 비어있을 수 없습니다" }
    }

    companion object {
        fun register(request: SpaceSaveRequest, user: User) = Space(
            name = request.name,
            spaceCategory = request.category,
            mainImgUrl = request.mainImgUrl,
            subImgUrls = request.subImgUrls,
            addressInfo = request.address,
            amountInfo = request.amountInfo,
            operationalInfo = request.operationInfo,
            size = request.size,
            spaceModelUrl = request.spaceModelUrl,
            modelThumbnailUrl = request.modelThumbnailUrl,
            precautions = request.precautions,
            user = user,
            phoneNumber = PhoneNumber(request.phoneNumber),
            tags = request.tags,
            beforeImgUrl = null,
            afterImgUrl = null,
            resultImgUrl = null,
            similarity = null
        )
    }

    fun update(request: SpaceSaveRequest) {
        this.spaceCategory = request.category
        this.name = request.name
        this.mainImgUrl = request.mainImgUrl
        this.subImgUrls = request.subImgUrls
        this.addressInfo = request.address
        this.amountInfo = request.amountInfo
        this.size = request.size
        this.operationalInfo = request.operationInfo
        this.spaceModelUrl = request.spaceModelUrl
        this.modelThumbnailUrl = request.modelThumbnailUrl
        this.phoneNumber = PhoneNumber(request.phoneNumber)
        this.precautions = request.precautions
        this.tags = request.tags
    }

    fun rent(renter: User) {
        require(this.spaceStatus == SpaceStatus.AVAILABLE) { "공간이 대여 가능한 상태가 아닙니다" }
        this.renter = renter
        this.spaceStatus = SpaceStatus.OCCUPIED
    }

    fun returnRequest(renter: User, request: ReturnSpaceRequest) {
        require(this.spaceStatus == SpaceStatus.OCCUPIED) { "공간이 대여 중이 아닙니다" }
        require(this.renter == renter) { "반납 요청 유저가 대여 중인 유저와 일치하지 않습니다" }
//        require(request.similarity >= 80) { "유사도가 80% 이상이어야 합니다" }
        this.spaceStatus = SpaceStatus.RETURN_REQUEST

        this.beforeImgUrl = request.beforeImgUrl
        this.afterImgUrl = request.afterImgUrl
        this.resultImgUrl = request.resultImgUrl
        this.similarity = request.similarity
    }

    fun returnReject() {
        require(this.spaceStatus == SpaceStatus.RETURN_REQUEST) { "반납 요청 상태의 공간이 아닙니다" }
        this.spaceStatus = SpaceStatus.RETURN_REJECTED
    }

    fun returnApprove() {
        require(this.spaceStatus == SpaceStatus.RETURN_REQUEST || this.spaceStatus == SpaceStatus.RETURN_REJECTED) { "반납 요청 또는 반납 반려 상태의 공간이 아닙니다" }
        this.spaceStatus = SpaceStatus.AVAILABLE
        this.renter = null
    }

    fun reRegistration() {
        require(this.spaceStatus == SpaceStatus.RETURN_REJECTED) { "반납 반려 상태의 공간이 아닙니다" }
        this.spaceStatus = SpaceStatus.AVAILABLE
        this.renter = null
    }
}