package com.woonit.wonnit.infra.s3

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest

@Service
class S3Uploader(
    private val s3Client: S3Client,
    @Value("\${spring.cloud.aws.s3.bucket}") private val bucket: String
) {
    fun deleteFile(key: String) {
        val request = DeleteObjectRequest.builder().bucket(bucket).key(key).build()
        s3Client.deleteObject(request)
    }
}