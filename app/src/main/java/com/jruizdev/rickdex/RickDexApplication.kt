package com.jruizdev.rickdex

import android.app.Application
import android.content.Context
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.util.DebugLogger
import com.jruizdev.rickdex.data.interceptor.RateLimitRetryInterceptor
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import okio.Path.Companion.toOkioPath

@HiltAndroidApp
class RickDexApplication : Application(), SingletonImageLoader.Factory {

    override fun newImageLoader(context: Context): ImageLoader {
        return ImageLoader.Builder(context).logger(
                if (BuildConfig.DEBUG) {
                    DebugLogger()
                } else {
                    null
                }
            ).memoryCache {
                MemoryCache.Builder().maxSizePercent(context, 0.25).build()
            }.diskCache {
                DiskCache.Builder().directory(context.cacheDir.resolve("image_cache").toOkioPath())
                    .maxSizeBytes(512L * 1024 * 1024) // 512 MB
                    .build()
            }.components {
                add(
                    OkHttpNetworkFetcherFactory(
                        OkHttpClient.Builder()
                            .addInterceptor(RateLimitRetryInterceptor(delayMillis = 5000L)).build()
                    )
                )
            }.build()
    }
}