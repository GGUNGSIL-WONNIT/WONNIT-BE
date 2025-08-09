package com.woonit.wonnit.domain.space.repository

import com.woonit.wonnit.domain.space.Space
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class SpaceQueryRepository(
    val em: EntityManager
) {
    fun findRecentSpaces(): List<Space> {
        return em
            .createQuery(
                "SELECT s FROM Space s ORDER BY id DESC LIMIT 5",
                Space::class.java
            )
            .resultList
    }
}