package com.tugbaolcer.clonex.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class Utils {

    companion object {
        fun getEncryptedTokenSharedPrefs(context: Context) : SharedPreferences {
            return getEncryptedSharedPrefs(context, TOKEN_PREFS)
        }

        private fun getEncryptedSharedPrefs(context: Context, prefsType: String) : SharedPreferences {
            val masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            return EncryptedSharedPreferences.create(
                context,
                prefsType,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }
    }
}