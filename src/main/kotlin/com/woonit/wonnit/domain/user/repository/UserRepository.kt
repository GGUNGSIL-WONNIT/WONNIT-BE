package com.woonit.wonnit.domain.user.repository

import com.woonit.wonnit.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, UUID> {
}