package com.jruizdev.rickdex.data.database

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterCacheValidator @Inject constructor(
    @ApplicationContext context: Context
) {

    private val prefs = context.getSharedPreferences("rickdex_cache_prefs", Context.MODE_PRIVATE)

    fun isCacheExpired(timeoutHours: Long = 24): Boolean {
        val lastUpdated = prefs.getLong(KEY_LAST_UPDATED, 0L)
        if (lastUpdated == 0L) return true
        val expirationTime = lastUpdated + TimeUnit.HOURS.toMillis(timeoutHours)
        return System.currentTimeMillis() > expirationTime
    }

    fun updateLastUpdatedTime(timestamp: Long = System.currentTimeMillis()) {
        prefs.edit().putLong(KEY_LAST_UPDATED, timestamp).apply()
    }

    fun clearCacheTime() {
        prefs.edit().remove(KEY_LAST_UPDATED).apply()
    }

    companion object {
        private const val KEY_LAST_UPDATED = "characters_last_updated"
    }
}
