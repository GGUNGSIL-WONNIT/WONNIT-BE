package com.woonit.wonnit.domain.space.repository

import com.woonit.wonnit.domain.space.Space
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface SpaceRepository : JpaRepository<Space, UUID> {
}