package com.woonit.wonnit.domain.space.repository

import com.woonit.wonnit.domain.space.Space
import com.woonit.wonnit.domain.space.SpaceStatus
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository
import java.util.*
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@Repository
class SpaceQueryRepository(
    val em: EntityManager
) {
    fun findMySpaces(userId: String, page: Int): List<Space> {
        return em
            .createQuery(
                "SELECT s FROM User u " +
                        "JOIN u.registeredSpaces s " +
                        "WHERE u.id = :userId " +
                        "ORDER BY s.id DESC",
                Space::class.java
            )
            .setParameter("userId", UUID.fromString(userId))
            .setFirstResult(page * 10)
            .setMaxResults(10)
            .resultList
    }

    fun countMySpaces(userId: String): Long {
        return em
            .createQuery(
                "SELECT COUNT(*) FROM User u " +
                        "JOIN u.registeredSpaces s " +
                        "WHERE u.id = :userId",
                Long::class.java
            )
            .setParameter("userId", UUID.fromString(userId))
            .singleResult
    }

    fun findRecentSpaces(): List<Space> {
        return em
            .createQuery(
                "SELECT s FROM Space s ORDER BY id DESC LIMIT 5",
                Space::class.java
            )
            .resultList
    }

    /**
     * 사각형 범위 검색 이후, Haversine 공식을 이용한 범위 검색
     */
    fun findNearBySpaces(lat: Double, lon: Double): List<Space> {
        // 1단계: 대략적인 사각형 범위로 빠르게 필터링
        val latRange = 0.03 // 여유있게 3.3km 정도
        val lonRange = 0.03 / cos(Math.toRadians(lat))

        val candidates = em.createQuery(
            "SELECT s FROM Space s " +
                    "WHERE s.addressInfo.lat BETWEEN (:lat - :latRange) AND (:lat + :latRange) " +
                    "   AND s.addressInfo.lon BETWEEN (:lon - :lonRange) AND (:lon + :lonRange)",
            Space::class.java
        )
            .setParameter("lat", lat)
            .setParameter("lon", lon)
            .setParameter("latRange", latRange)
            .setParameter("lonRange", lonRange)
            .resultList

        // 2단계: 애플리케이션에서 정확한 거리 계산으로 필터링
        return candidates.filter { space ->
            calculateDistance(lat, lon, space.addressInfo.lat, space.addressInfo.lon) <= 3.0
        }
    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6371.0 // 지구 반지름 (km)
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return R * c
    }


    fun countMyRentalSpaces(userId: String): Long {
        return em
            .createQuery(
                "SELECT COUNT(*) FROM Space s " +
                        "JOIN s.renter r " +
                        "WHERE r.id = :userId AND (s.spaceStatus = :occupied OR s.spaceStatus = :return_request)",
                Long::class.java
            )
            .setParameter("userId", UUID.fromString(userId))
            .setParameter("occupied", SpaceStatus.OCCUPIED)
            .setParameter("return_request", SpaceStatus.RETURN_REQUEST)
            .singleResult
    }

    fun findMyRentalSpaces(userId: String, page: Int): List<Space> {
        return em
            .createQuery(
                "SELECT s FROM Space s " +
                        "JOIN s.renter r " +
                        "WHERE r.id = :userId AND (s.spaceStatus = :occupied OR s.spaceStatus = :return_request) " +
                        "ORDER BY s.id DESC",
                Space::class.java
            )
            .setParameter("userId", UUID.fromString(userId))
            .setParameter("occupied", SpaceStatus.OCCUPIED)
            .setParameter("return_request", SpaceStatus.RETURN_REQUEST)
            .setFirstResult(page * 10)
            .setMaxResults(10)
            .resultList
    }

    fun findSpace(spaceId: UUID): Space? =
        em.createQuery(
            """
            select s
            from Space s
            where s.id = :id
            """.trimIndent(),
            Space::class.java
        )
            .setParameter("id", spaceId)
            .resultList
            .firstOrNull()

    fun findSubImageUrls(spaceId: UUID): MutableList<String> =
        em.createQuery(
            """
            select i
            from Space s
            join s.subImgUrls i
            where s.id = :id
            """.trimIndent(),
            String::class.java
        )
            .setParameter("id", spaceId)
            .resultList
            .toMutableList()

    fun findTags(spaceId: UUID): MutableList<String> =
        em.createQuery(
            """
            select t
            from Space s
            join s.tags t
            where s.id = :id
            """.trimIndent(),
            String::class.java
        )
            .setParameter("id", spaceId)
            .resultList
            .toMutableList()

}