package com.tugbaolcer.clonex.di.datastore

import android.content.Context
import com.google.crypto.tink.BinaryKeysetReader
import com.google.crypto.tink.BinaryKeysetWriter
import com.google.crypto.tink.CleartextKeysetHandle
import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.aead.AeadKeyTemplates
import java.io.File


object EncryptionKeyManager {
    private const val KEYSET_FILE = "tink_keyset.bin"

    fun getOrCreateKeysetHandle(context: Context): KeysetHandle {
        val file = File(context.filesDir, KEYSET_FILE)

        return if (file.exists()) {
            CleartextKeysetHandle.read(
                BinaryKeysetReader.withFile(file)
            )
        } else {
            val handle = KeysetHandle.generateNew(AeadKeyTemplates.AES256_GCM)

            CleartextKeysetHandle.write(
                handle,
                BinaryKeysetWriter.withFile(file)
            )

            handle
        }
    }
}


