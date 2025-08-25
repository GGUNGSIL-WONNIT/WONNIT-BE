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
    val em: EntityManager,
) {
    /**
     * Retrieves a paginated list of spaces registered by a specific user.
     *
     * @param userId The ID of the user.
     * @param page The page number for pagination.
     * @return A list of [Space] objects.
     */
    fun findMySpaces(userId: String, page: Int): List<Space> {
        return em
            .createQuery(
                """
                    SELECT s FROM User u 
                    JOIN u.registeredSpaces s 
                    WHERE u.id = :userId 
                    ORDER BY s.id DESC
                """,
                Space::class.java,
            )
            .setParameter("userId", UUID.fromString(userId))
            .setFirstResult(page * 10)
            .setMaxResults(10)
            .resultList
    }

    /**
     * Counts the total number of spaces registered by a specific user.
     *
     * @param userId The ID of the user.
     * @return The total count of registered spaces.
     */
    fun countMySpaces(userId: String): Long {
        return em
            .createQuery(
                """
                    SELECT COUNT(*) FROM User u  
                    JOIN u.registeredSpaces s  
                    WHERE u.id = :userId
                """,
                Long::class.java,
            )
            .setParameter("userId", UUID.fromString(userId))
            .singleResult
    }

    /**
     * Retrieves the 5 most recently registered spaces.
     *
     * @return A list of the 5 most recent [Space] objects.
     */
    fun findRecentSpaces(): List<Space> {
        return em
            .createQuery(
                "SELECT s FROM Space s ORDER BY id DESC LIMIT 5",
                Space::class.java,
            )
            .resultList
    }

    /**
     * Finds nearby spaces within a 3km radius from the given latitude and longitude.
     *
     * This method performs a two-step search:
     * 1. A broad-phase search using a rectangular area for quick filtering.
     * 2. A narrow-phase search using the precise Haversine formula to filter the candidates.
     *
     * @param lat The latitude of the center point.
     * @param lon The longitude of the center point.
     * @return A list of [Space] objects within the 3km radius.
     */
    fun findNearBySpaces(lat: Double, lon: Double): List<Space> {
        // 1. A broad-phase search using a rectangular area for quick filtering.
        val latRange = 0.03 // A buffer of approx. 3.3km
        val lonRange = 0.03 / cos(Math.toRadians(lat))

        val candidates = em.createQuery(
            """
                SELECT s FROM Space s  
                WHERE s.addressInfo.lat BETWEEN (:lat - :latRange) AND (:lat + :latRange)  
                   AND s.addressInfo.lon BETWEEN (:lon - :lonRange) AND (:lon + :lonRange)
            """,
            Space::class.java,
        )
            .setParameter("lat", lat)
            .setParameter("lon", lon)
            .setParameter("latRange", latRange)
            .setParameter("lonRange", lonRange)
            .resultList

        // 2. A narrow-phase search using the precise Haversine formula to filter the candidates.
        return candidates.filter { space ->
            calculateDistance(lat, lon, space.addressInfo.lat, space.addressInfo.lon) <= 3.0
        }
    }

    /**
     * Calculates the distance between two geographical points using the Haversine formula.
     *
     * @param lat1 Latitude of the first point.
     * @param lon1 Longitude of the first point.
     * @param lat2 Latitude of the second point.
     * @param lon2 Longitude of the second point.
     * @return The distance in kilometers.
     */
    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val r = 6371.0 // Radius of the Earth in kilometers
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2) * sin(dLat / 2) +
            cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
            sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return r * c
    }

    /**
     * Counts the total number of spaces currently rented by a specific user.
     * This includes spaces with 'OCCUPIED' or 'RETURN_REQUEST' status.
     *
     * @param userId The ID of the user.
     * @return The total count of rented spaces.
     */
    fun countMyRentalSpaces(userId: String): Long {
        return em
            .createQuery(
                """
                    SELECT COUNT(*) FROM Space s 
                    JOIN s.renter r 
                    WHERE r.id = :userId AND (s.spaceStatus = :occupied OR s.spaceStatus = :return_request)
                """,
                Long::class.java,
            )
            .setParameter("userId", UUID.fromString(userId))
            .setParameter("occupied", SpaceStatus.OCCUPIED)
            .setParameter("return_request", SpaceStatus.RETURN_REQUEST)
            .singleResult
    }

    /**
     * Retrieves a paginated list of spaces currently rented by a specific user.
     * This includes spaces with 'OCCUPIED' or 'RETURN_REQUEST' status.
     *
     * @param userId The ID of the user.
     * @param page The page number for pagination.
     * @return A list of rented [Space] objects.
     */
    fun findMyRentalSpaces(userId: String, page: Int): List<Space> {
        return em
            .createQuery(
                """
                    SELECT s FROM Space s 
                    JOIN s.renter r 
                    WHERE r.id = :userId AND (s.spaceStatus = :occupied OR s.spaceStatus = :return_request) 
                    ORDER BY s.id DESC                    
                """,
                Space::class.java,
            )
            .setParameter("userId", UUID.fromString(userId))
            .setParameter("occupied", SpaceStatus.OCCUPIED)
            .setParameter("return_request", SpaceStatus.RETURN_REQUEST)
            .setFirstResult(page * 10)
            .setMaxResults(10)
            .resultList
    }

    /**
     * Retrieves a single space by its unique ID.
     *
     * @param spaceId The UUID of the space.
     * @return The [Space] object if found, otherwise null.
     */
    fun findSpace(spaceId: UUID): Space? =
        em.createQuery(
            """
            SELECT s
            FROM Space s
            WHERE s.id = :id
            """,
            Space::class.java,
        )
            .setParameter("id", spaceId)
            .resultList
            .firstOrNull()

    /**
     * Retrieves the URLs of additional (sub) images for a specific space.
     *
     * @param spaceId The UUID of the space.
     * @return A mutable list of image URL strings.
     */
    fun findSubImageUrls(spaceId: UUID): MutableList<String> =
        em.createQuery(
            """
            SELECT i
            FROM Space s
            JOIN s.subImgUrls i
            WHERE s.id = :id
            """,
            String::class.java,
        )
            .setParameter("id", spaceId)
            .resultList
            .toMutableList()

    /**
     * Retrieves the tags associated with a specific space.
     *
     * @param spaceId The UUID of the space.
     * @return A mutable list of tag strings.
     */
    fun findTags(spaceId: UUID): MutableList<String> =
        em.createQuery(
            """
            SELECT t
            FROM Space s
            JOIN s.tags t
            WHERE s.id = :id
            """,
            String::class.java,
        )
            .setParameter("id", spaceId)
            .resultList
            .toMutableList()
}
