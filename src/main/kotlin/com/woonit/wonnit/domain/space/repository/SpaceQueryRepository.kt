package com.woonit.wonnit.domain.space.repository

import com.woonit.wonnit.domain.space.Space
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class SpaceQueryRepository(
    val em: EntityManager
) {
    fun findMySpaces(userId: String, page: Int): List<Space> {
        return em
            .createQuery(
                "SELECT s FROM User u " +
                        "JOIN u.registeredSpaces s " +
                        "WHERE u.id = :userId",
                Space::class.java
            )
            .setParameter("userId", UUID.fromString(userId))
            .setFirstResult(page * 10)
            .setMaxResults(10)
            .resultList
    }

    fun findRecentSpaces(): List<Space> {
        return em
            .createQuery(
                "SELECT s FROM Space s ORDER BY id DESC LIMIT 5",
                Space::class.java
            )
            .resultList
    }
}