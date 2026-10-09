package com.jruizdev.rickdex.data.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class RateLimitRetryInterceptor(
    private val delayMillis: Long = 5000L, // 5 segundos
    private val maxRetries: Int = 3
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var response = chain.proceed(request)
        var attempt = 0

        while (response.code == 429 && attempt < maxRetries) {
            attempt++
            response.close()

            try {
                Thread.sleep(delayMillis)
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
                break
            }

            response = chain.proceed(request)
        }
        return response
    }
}
