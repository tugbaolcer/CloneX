package com.tugbaolcer.clonex.di.datastore

import android.util.Base64
import com.google.crypto.tink.Aead

class AesCipher(private val aead: Aead) {

    fun encrypt(text: String): String {
        val encrypted = aead.encrypt(text.toByteArray(), null)
        return Base64.encodeToString(encrypted, Base64.NO_WRAP)
    }

    fun decrypt(text: String): String {
        val decrypted = aead.decrypt(Base64.decode(text, Base64.NO_WRAP), null)
        return String(decrypted)
    }
}
