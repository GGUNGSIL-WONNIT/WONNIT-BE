package com.woonit.wonnit.domain.space.service

import com.woonit.wonnit.domain.space.dto.PresignUploadResponse
import com.woonit.wonnit.infra.s3.S3PresignService
import com.woonit.wonnit.infra.s3.S3Uploader
import org.springframework.stereotype.Service

@Service
class SpaceImageService(
    private val s3PresignService: S3PresignService,
    private val s3Uploader: S3Uploader
) {

    /**
     * Generates a pre-signed URL for uploading a space image.
     *
     * @param fileName The name of the file to be uploaded.
     * @return A [PresignUploadResponse] containing the pre-signed URL and the file key.
     */
    fun getPresignedUrlForImage(fileName: String): PresignUploadResponse {
        return s3PresignService.createUploadUrl(fileName)
    }

    fun deleteImage(key: String) { s3Uploader.deleteFile(key) }
}