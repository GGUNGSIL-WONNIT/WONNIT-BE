package com.woonit.wonnit.domain.space.service

import com.woonit.wonnit.domain.space.dto.PresignUploadResponse
import com.woonit.wonnit.infra.s3.S3PresignService
import com.woonit.wonnit.infra.s3.S3Uploader
import org.springframework.stereotype.Service

@Service
class Space3DModelService(
    private val s3PresignService: S3PresignService,
) {

    fun uploadModel(modelName: String): PresignUploadResponse {
        return s3PresignService.createUploadUrl(modelName)
    }
}