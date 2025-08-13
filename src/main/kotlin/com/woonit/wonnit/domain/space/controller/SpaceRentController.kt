package com.woonit.wonnit.domain.space.controller

import com.woonit.wonnit.domain.space.service.SpaceRentService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/space/{spaceId}/rentals")
class SpaceRentController(
    val spaceRentService: SpaceRentService
) {
    @Value("\${test-user.id}")
    private lateinit var userId: String

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun rentSpace(
        @PathVariable spaceId: String,
    ) {
        spaceRentService.rent(userId, spaceId)
    }

    @PatchMapping("/return-request")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun returnRequest(
        @PathVariable spaceId: String,
    ) {
        spaceRentService.returnRequest(userId, spaceId)
    }

    @PatchMapping("/return-reject")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun returnReject(
        @PathVariable spaceId: String,
    ) {
        spaceRentService.returnReject(userId, spaceId)
    }

    @PatchMapping("/return-approve")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun returnApprove(
        @PathVariable spaceId: String,
    ) {
        spaceRentService.returnApprove(userId, spaceId)
    }
}