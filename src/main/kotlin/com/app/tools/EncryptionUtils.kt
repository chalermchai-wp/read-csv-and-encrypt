package com.app.tools

import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.nio.charset.StandardCharsets
import java.security.Security
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

class EncryptionUtils {

    fun encrypt(value: String, password: String, iv: String): String {
        val cipher = initCipher(Cipher.ENCRYPT_MODE, password, iv)
        val encryptedBytes = cipher.doFinal(value.toByteArray(StandardCharsets.UTF_8))
        return Base64.getEncoder().encodeToString(encryptedBytes)
    }

    fun decrypt(encValue: String?, password: String, iv: String): String {
        val cipher = initCipher(Cipher.DECRYPT_MODE, password, iv)
        val plainBytes = cipher.doFinal(Base64.getDecoder().decode(encValue))
        return String(plainBytes)
    }

    private fun initCipher(cipherMode: Int, password: String, iv: String): Cipher {
        Security.addProvider(BouncyCastleProvider())
        val keySpecification = SecretKeySpec(password!!.toByteArray(StandardCharsets.UTF_8), "AES")
        val cipher = Cipher.getInstance("AES/GCM/NoPadding", "BC")
        val spec = GCMParameterSpec(16 * 8, iv!!.toByteArray(StandardCharsets.UTF_8))
        cipher.init(cipherMode, keySpecification, spec)
        return cipher
    }
}