package com.jruizdev.rickdex

import android.app.Application
import android.content.Context
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.util.DebugLogger
import dagger.hilt.android.HiltAndroidApp
import okio.Path.Companion.toOkioPath

@HiltAndroidApp
class RickDexApplication : Application(), SingletonImageLoader.Factory {

    override fun newImageLoader(context: Context): ImageLoader {
        return ImageLoader.Builder(context)
            // 1. Activa los logs de Coil en Logcat (busca la etiqueta "Coil")

            .logger(
                if (BuildConfig.DEBUG) {
                    DebugLogger()
                } else {
                    null
                }
            )

            // 2. Personaliza la caché en memoria (ej. usa 25% de la memoria disponible)
            .memoryCache {
                MemoryCache.Builder()
                    .maxSizePercent(context, 0.25)
                    .build()
            }

            // 3. Personaliza la caché en disco (ej. 512 MB)
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("image_cache").toOkioPath())
                    .maxSizeBytes(512L * 1024 * 1024) // 512 MB
                    .build()
            }

            // .respectCacheHeaders(false) // No disponible directamente en Coil 3 en el Builder principal

            .build()
    }
}