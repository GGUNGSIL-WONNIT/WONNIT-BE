package com.woonit.wonnit.infra.s3

import com.woonit.wonnit.domain.space.SpaceImage
import com.woonit.wonnit.domain.space.dto.PresignUploadResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.util.UUID

@Component
class S3PresignService(
    private val presigner: S3Presigner,
    @Value("\${cloud.aws.s3.bucket}") private val bucket: String
) {
    fun createUploadUrl(userId: UUID, fileName: String): PresignUploadResponse {
        val safeName = fileName.substringAfterLast('/').substringAfterLast('\\')
        val encodedName = URLEncoder.encode(safeName, StandardCharsets.UTF_8)
        val prefix = "uploads/$userId"
        val key = "$prefix/${UUID.randomUUID()}-$encodedName"

        val put = PutObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .build()

        val presignRequest = PutObjectPresignRequest.builder()
            .signatureDuration(Duration.ofMinutes(10))
            .putObjectRequest(put)
            .build()

        val presigned = presigner.presignPutObject(presignRequest)

        return PresignUploadResponse(
            uploadUrl = presigned.url().toExternalForm(),
            key = key
        )
    }
}