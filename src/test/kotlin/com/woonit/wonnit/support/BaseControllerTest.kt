package com.woonit.wonnit.support

import com.fasterxml.jackson.databind.ObjectMapper
import com.woonit.wonnit.domain.column.repository.ColumnRepository
import com.woonit.wonnit.domain.space.repository.SpaceRepository
import com.woonit.wonnit.domain.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.assertj.MockMvcTester

@SpringBootTest
@AutoConfigureMockMvc
abstract class BaseControllerTest {

    @Autowired
    protected lateinit var mvcTester: MockMvcTester

    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    @Autowired
    protected lateinit var userRepository: UserRepository

    @Autowired
    protected lateinit var spaceRepository: SpaceRepository

    @Autowired
    protected lateinit var columnRepository: ColumnRepository

}