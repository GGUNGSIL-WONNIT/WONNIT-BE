package com.woonit.wonnit.domain.space

import com.woonit.wonnit.domain.share.AddressInfo
import com.woonit.wonnit.domain.share.AmountInfo
import com.woonit.wonnit.domain.share.OperationalInfo
import com.woonit.wonnit.domain.share.PhoneNumber
import com.woonit.wonnit.domain.user.User
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

    val tags: MutableList<String> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var spaceStatus: SpaceStatus = SpaceStatus.AVAILABLE,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renter_id")
    var renter: User? = null,

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

    fun rent(renter: User) {
        require(this.spaceStatus == SpaceStatus.AVAILABLE) { "공간이 대여 가능한 상태가 아닙니다" }
        this.renter = renter
        this.spaceStatus = SpaceStatus.OCCUPIED
    }

    fun returnRequest(renter: User) {
        require(this.spaceStatus == SpaceStatus.OCCUPIED) { "공간이 대여 중이 아닙니다" }
        require(this.renter == renter) { "반납 요청 유저가 대여 중인 유저와 일치하지 않습니다" }
        this.spaceStatus = SpaceStatus.RETURN_REQUEST
    }

    fun returnReject(renter: User) {
        require(this.spaceStatus == SpaceStatus.RETURN_REQUEST) { "반납 요청 상태의 공간이 아닙니다" }
        require(this.renter == renter) { "반납 요청 유저가 대여 중인 유저와 일치하지 않습니다" }
        this.spaceStatus == SpaceStatus.RETURN_REJECTED
    }

    fun returnApprove(renter: User) {
        require(this.spaceStatus == SpaceStatus.RETURN_REQUEST || this.spaceStatus == SpaceStatus.RETURN_REJECTED) { "반납 요청 또는 반납 반려 상태의 공간이 아닙니다" }
        require(this.renter == renter) { "반납 요청 유저가 대여 중인 유저와 일치하지 않습니다" }
        this.spaceStatus == SpaceStatus.AVAILABLE
        this.renter == null
    }
}