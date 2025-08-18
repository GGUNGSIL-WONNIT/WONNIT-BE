package com.woonit.wonnit.domain.space.controller

import com.woonit.wonnit.domain.space.dto.PresignUploadRequest
import com.woonit.wonnit.domain.space.dto.PresignUploadResponse
import com.woonit.wonnit.domain.space.service.SpaceImageService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

@RestController
@RequestMapping("/api/v1/images")
class SpaceImageController(
    private val spaceImageService: SpaceImageService
) {
    @Value("\${test-user.id}")
    private lateinit var userId: UUID

    @PostMapping
    fun uploadImage(@RequestParam("image") request: PresignUploadRequest): ResponseEntity<PresignUploadResponse> {
        val response = spaceImageService.uploadImage(userId, request)
        return ResponseEntity.ok(response)
    }
}