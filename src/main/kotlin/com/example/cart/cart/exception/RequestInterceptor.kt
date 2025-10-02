package com.example.cart.cart.exception

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import java.nio.charset.Charset

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class LoggingFilter : OncePerRequestFilter() {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val reqWrapper = ContentCachingRequestWrapper(request)
        val resWrapper = ContentCachingResponseWrapper(response)

        filterChain.doFilter(reqWrapper, resWrapper)

        val charsetReq = Charset.forName(reqWrapper.characterEncoding ?: "UTF-8")
        val charsetRes = Charset.forName(resWrapper.characterEncoding ?: "UTF-8")

        val requestBody = reqWrapper.contentAsByteArray.let {
            if (it.isNotEmpty()) String(it, charsetReq) else ""
        }
        val responseBody = resWrapper.contentAsByteArray.let {
            if (it.isNotEmpty()) String(it, charsetRes) else ""
        }

        log.info(
            ">>> {} {} | body={}",
            reqWrapper.method,
            reqWrapper.requestURI,
            requestBody
        )

        log.info("<<< status={} | body={}", resWrapper.status, responseBody)

        // обязательно вернуть тело клиенту
        resWrapper.copyBodyToResponse()
    }
}