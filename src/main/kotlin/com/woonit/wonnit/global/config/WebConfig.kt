package com.woonit.wonnit.global.config

import com.woonit.wonnit.global.resolver.UserIdArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
//    private val userIdArgumentResolver: UserIdArgumentResolver
) : WebMvcConfigurer {
//    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
//        resolvers.add(userIdArgumentResolver)
//    }
}