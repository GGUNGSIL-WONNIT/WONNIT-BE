package com.woonit.wonnit.domain.share

import org.junit.jupiter.api.DisplayName

@DisplayName("PhoneNumber 테스트")
class PhoneNumberTest {

//    @Test
//    @DisplayName("유효한 전화번호로 PhoneNumber 객체를 생성할 수 있다.")
//    fun createPhoneNumberWithValidNumber() {
//        // Given
//        val validPhoneNumber = "010-1234-5678"
//
//        val phoneNumber = PhoneNumber(validPhoneNumber)
//
//        // When & Then
//        assertThat(phoneNumber.value).isEqualTo(validPhoneNumber)
//    }
//
//    @Test
//    @DisplayName("유효하지 않은 전화번호로 PhoneNumber 객체 생성 시 예외가 발생한다.")
//    fun createPhoneNumberWithInvalidNumber() {
//        // Given
//        val invalidPhoneNumber1 = "01012345678" // 하이픈 없음
//        val invalidPhoneNumber2 = "010-123-4567" // 중간 자리수 부족
//        val invalidPhoneNumber3 = "011-1234-5678" // 010 아님
//        val invalidPhoneNumber4 = "010-1234-567" // 마지막 자리수 부족
//
//        // When & Then
//        assertThrows<IllegalArgumentException> {
//            PhoneNumber(invalidPhoneNumber1)
//        }
//        assertThrows<IllegalArgumentException> {
//            PhoneNumber(invalidPhoneNumber2)
//        }
//        assertThrows<IllegalArgumentException> {
//            PhoneNumber(invalidPhoneNumber3)
//        }
//        assertThrows<IllegalArgumentException> {
//            PhoneNumber(invalidPhoneNumber4)
//        }
//    }
}
