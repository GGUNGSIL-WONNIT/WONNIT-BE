package com.woonit.wonnit.domain.space.service

import com.woonit.wonnit.domain.space.dto.PresignUploadResponse
import com.woonit.wonnit.global.config.logger
import com.woonit.wonnit.infra.s3.S3PresignService
import org.springframework.stereotype.Service

@Service
class Space3DModelService(
    private val s3PresignService: S3PresignService,
) {

    /**
     * Generates a pre-signed URL for uploading a 3D model file.
     *
     * @param modelName The name of the 3D model file to be uploaded.
     * @return A [PresignUploadResponse] containing the pre-signed URL and the file key.
     */
    fun getPresignedUrlForModel(modelName: String): PresignUploadResponse {
        logger<Space3DModelService>().info("Get Presigned Url for model: $modelName")
        return s3PresignService.createUploadUrl(modelName)
    }
}
