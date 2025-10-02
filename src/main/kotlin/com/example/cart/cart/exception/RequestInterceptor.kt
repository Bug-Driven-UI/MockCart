package com.example.cart.cart.exception

import jakarta.servlet.FilterChain
import jakarta.servlet.ReadListener
import jakarta.servlet.ServletInputStream
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingResponseWrapper
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
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
        val reqWrapper = CachedBodyRequestWrapper(request)
        val resWrapper = ContentCachingResponseWrapper(response)

        var thrown: Throwable? = null
        try {
            filterChain.doFilter(reqWrapper, resWrapper)
        } catch (t: Throwable) {
            thrown = t
            throw t
        } finally {
            val requestBody = truncate(reqWrapper.bodyAsString(), 10_000)
            val responseBody = runCatching {
                String(resWrapper.contentAsByteArray, charset(resWrapper.characterEncoding))
            }.getOrElse { "" }

            if (thrown != null) {
                log.error(
                    "✖ {} {} failed: status={} | reqBody={} | resBody={}",
                    reqWrapper.method,
                    reqWrapper.requestURI,
                    resWrapper.status,
                    requestBody,
                    truncate(responseBody, 10_000),
                    thrown
                )
            } else {
                log.info(
                    "✓ {} {}: status={} | reqBody={} | resBody={}",
                    reqWrapper.method,
                    reqWrapper.requestURI,
                    resWrapper.status,
                    requestBody,
                    truncate(responseBody, 10_000)
                )
            }

            resWrapper.copyBodyToResponse()
        }
    }

    private fun charset(name: String?): Charset =
        Charset.forName(name ?: "UTF-8")

    private fun truncate(s: String, max: Int): String =
        if (s.length <= max) s else s.take(max) + "...[truncated]"
}


class CachedBodyRequestWrapper(request: HttpServletRequest) : HttpServletRequestWrapper(request) {

    private val cachedBody: ByteArray = request.inputStream.readAllBytes()

    override fun getInputStream(): ServletInputStream {
        val bais = ByteArrayInputStream(cachedBody)
        return object : ServletInputStream() {
            override fun read(): Int = bais.read()
            override fun isFinished(): Boolean = bais.available() == 0
            override fun isReady(): Boolean = true
            override fun setReadListener(readListener: ReadListener?) {}
        }
    }

    override fun getReader(): BufferedReader {
        val charset = Charset.forName(characterEncoding ?: "UTF-8")
        return BufferedReader(InputStreamReader(inputStream, charset))
    }

    override fun getContentLength(): Int = cachedBody.size
    override fun getContentLengthLong(): Long = cachedBody.size.toLong()

    fun bodyAsString(): String {
        val charset = Charset.forName(characterEncoding ?: "UTF-8")
        return String(cachedBody, charset)
    }
}
