package com.woonit.wonnit.domain.share

import jakarta.persistence.Embeddable

@Embeddable
data class PhoneNumber(
    val value: String,
)