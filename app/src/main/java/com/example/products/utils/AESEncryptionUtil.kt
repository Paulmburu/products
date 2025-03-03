package com.example.products.utils

import android.util.Base64
import java.nio.ByteBuffer
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

object AESEncryptionUtil {
    private const val AES_ALGORITHM = "AES/GCM/NoPadding"
    private const val IV_SIZE = 12
    private const val TAG_LENGTH = 128
    fun generateSecretKey(): SecretKey {
        val keyGen = KeyGenerator.getInstance("AES")
        keyGen.init(256)
        return keyGen.generateKey()
    }


//    @Headers("Content-Type: application/json")
//    @POST("endpoint")
//    suspend fun sendEncryptedData(@Body request: EncryptedRequest)

//    suspend fun sendUserData(userData: UserData) {
//    val json = kotlinx.serialization.json.Json.encodeToString(UserData.serializer(), userData)
//    val encryptedJson = AESEncryptionUtil.encrypt(json, secretKey)
//
//     val request = EncryptedRequest(encryptedJson)
//     apiService.sendEncryptedData(request)
//    }

//    fun encrypt(data: String, secretKey: SecretKey): String {
//        val cipher = Cipher.getInstance(AES_ALGORITHM)
//        val iv = ByteArray(IV_SIZE).apply { SecureRandom().nextBytes(this) }
//        cipher.init(Cipher.ENCRYPT_MODE, secretKey, GCMParameterSpec(TAG_LENGTH, iv))
//
//        val encryptedData = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
//
//        val combined = ByteBuffer.allocate(IV_SIZE + encryptedData.size)
//            .put(iv)
//            .put(encryptedData)
//            .array()
//
//        return Base64.encodeToString(combined, Base64.NO_WRAP)
//    }
//
//
//    fun decrypt(encryptedData: String, secretKey: SecretKey): String {
//        val decodedData = Base64.decode(encryptedData, Base64.NO_WRAP)
//        val iv = decodedData.copyOfRange(0, IV_SIZE)
//        val cipherText = decodedData.copyOfRange(IV_SIZE, decodedData.size)
//
//        val cipher = Cipher.getInstance(AES_ALGORITHM)
//        cipher.init(Cipher.DECRYPT_MODE, secretKey, GCMParameterSpec(TAG_LENGTH, iv))
//
//        return String(cipher.doFinal(cipherText), Charsets.UTF_8)
//    }
}
