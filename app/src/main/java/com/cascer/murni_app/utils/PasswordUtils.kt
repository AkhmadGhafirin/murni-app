package com.cascer.murni_app.utils

import android.util.Base64
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object PasswordUtils {
    fun encrypt(data: String): String {
        val key = generateKey()
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val encryptedData = cipher.doFinal(data.toByteArray())
        return Base64.encodeToString(encryptedData, Base64.DEFAULT).orEmpty()
    }

    fun decrypt(encryptedData: String): String {
        val key = generateKey()
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, key)
        val decryptedData = cipher.doFinal(Base64.decode(encryptedData, Base64.DEFAULT))
        return String(decryptedData)
    }

    private fun generateKey(): SecretKeySpec {
        val keyData = "SECRET_KEY".toByteArray()
        val hashedKeyData = keyData.sha256()
        val truncatedKeyData = hashedKeyData.copyOf(16)
        return SecretKeySpec(truncatedKeyData, "AES")
    }

    private fun ByteArray.sha256(): ByteArray {
        val md = MessageDigest.getInstance("SHA-256")
        return md.digest(this)
    }
}