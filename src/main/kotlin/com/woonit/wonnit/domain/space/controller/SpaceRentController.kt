package com.woonit.wonnit.domain.space.controller

import com.woonit.wonnit.domain.space.service.SpaceRentService
import com.woonit.wonnit.global.annotation.UserId
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
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun rentSpace(
        @UserId userId: String,
        @PathVariable spaceId: String,
    ) {
        spaceRentService.rent(userId, spaceId)
    }

    @PatchMapping("/return-request")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun returnRequest(
        @UserId userId: String,
        @PathVariable spaceId: String,
    ) {
        spaceRentService.returnRequest(userId, spaceId)
    }

    @PatchMapping("/return-reject")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun returnReject(
        @UserId userId: String,
        @PathVariable spaceId: String,
    ) {
        spaceRentService.returnReject(userId, spaceId)
    }

    @PatchMapping("/return-approve")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun returnApprove(
        @UserId userId: String,
        @PathVariable spaceId: String,
    ) {
        spaceRentService.returnApprove(userId, spaceId)
    }
}