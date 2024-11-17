package com.modsen.software.authservice.client

import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Component
class AuthFeignInterceptor : RequestInterceptor {
    override fun apply(template: RequestTemplate) {
        val requestAttributes = RequestContextHolder.getRequestAttributes()
        if (requestAttributes != null) {
            val httpServletRequest = (requestAttributes as ServletRequestAttributes).request
            template.header(HttpHeaders.AUTHORIZATION, httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION))
        }
    }
}