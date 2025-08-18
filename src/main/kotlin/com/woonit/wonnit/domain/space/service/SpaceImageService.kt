package com.woonit.wonnit.domain.space.service

import com.woonit.wonnit.domain.space.dto.PresignUploadRequest
import com.woonit.wonnit.domain.space.dto.PresignUploadResponse
import com.woonit.wonnit.infra.s3.S3PresignService
import java.util.UUID

class SpaceImageService(
    private val s3PresignService: S3PresignService
) {
    fun uploadImage(userId: UUID, request: PresignUploadRequest): PresignUploadResponse {
        return s3PresignService.createUploadUrl(userId, request.fileName)
    }

}