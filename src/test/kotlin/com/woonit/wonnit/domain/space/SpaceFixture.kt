package com.woonit.wonnit.domain.space

import com.woonit.wonnit.domain.share.*
import com.woonit.wonnit.domain.user.User
import org.springframework.test.util.ReflectionTestUtils
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.util.*

object SpaceFixture {

    fun createUser(): User {
        val user = User("user", PhoneNumber("010-0000-0000"))

        return user
    }

    fun createSpace(
        name: String = "테스트 공간",
        addressInfo: AddressInfo = AddressInfo("서울시 강남구", "테헤란로 123", 37.5, 127.0),
        mainImgUrl: String = "https://example.com/main.jpg",
        subImgUrls: MutableList<String> = mutableListOf("https://example.com/sub1.jpg", "https://example.com/sub2.jpg"),
        spaceCategory: SpaceCategory = SpaceCategory.STUDY_ROOM,
        size: Double = 50.0,
        operationalInfo: OperationalInfo = OperationalInfo(
            listOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY),
            LocalDateTime.now(),
            LocalDateTime.now().plusHours(8)
        ),
        amountInfo: AmountInfo = AmountInfo(TimeUnit.PER_DAY, 10000),
        spaceModelUrl: String? = "https://example.com/model.obj",
        phoneNumber: PhoneNumber = PhoneNumber("010-1234-5678"),
        precautions: String? = "주의사항",
        tags: MutableList<String> = mutableListOf("스터디", "강남"),
        modelThumbnailUrl: String? = null,
        owner: User
    ): Space {
        return Space(
            name = name,
            addressInfo = addressInfo,
            mainImgUrl = mainImgUrl,
            subImgUrls = subImgUrls,
            spaceCategory = spaceCategory,
            size = size,
            operationalInfo = operationalInfo,
            amountInfo = amountInfo,
            spaceModelUrl = spaceModelUrl,
            phoneNumber = phoneNumber,
            precautions = precautions,
            tags = tags,
            user = owner,
            modelThumbnailUrl = modelThumbnailUrl,
            beforeImgUrl = null,
            afterImgUrl = null,
            resultImgUrl = null,
            similarity = null
        )
    }
}
