package br.com.myclinicpay.data.service.authentication

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

@Component
class AESUtil : PasswordEncoder  {

    @Value("\${encrypt.secret}")
    private lateinit var secret: String

    @Value("\${encrypt.salt}")
    private lateinit var salt: String
    fun generateKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(128)
        return keyGenerator.generateKey()
    }

    fun getKeyFromPassword(): SecretKey {
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val keySpec = PBEKeySpec(secret.toCharArray(), salt.toByteArray(Charsets.UTF_8), 65536, 256)
        return SecretKeySpec(factory.generateSecret(keySpec).encoded, "AES")
    }

    fun encrypt(input: String, key: SecretKey, ivParameterSpec: IvParameterSpec): String {
        val cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec)
        val cipherText = cipher.doFinal(input.toByteArray(Charsets.UTF_8))
        return Base64.getEncoder().encodeToString(cipherText)
    }

    fun decrypt(input: String, key: SecretKey, ivParameterSpec: IvParameterSpec): String {
        val cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec)
        val decodedText = cipher.doFinal(Base64.getDecoder().decode(input))
        return String(decodedText)

    }

    fun getIvParameterSpec(): IvParameterSpec {
        return IvParameterSpec(ByteArray(16))
    }

    override fun encode(rawPassword: CharSequence?): String {
        val input = rawPassword.toString()
        val cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, this.getKeyFromPassword(), this.getIvParameterSpec())
        val cipherText = cipher.doFinal(input.toByteArray(Charsets.UTF_8))
        return Base64.getEncoder().encodeToString(cipherText)
    }

    override fun matches(rawPassword: CharSequence, encodedPassword: String): Boolean {
        val cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, this.getKeyFromPassword(), this.getIvParameterSpec())
        val decodedText = cipher.doFinal(Base64.getDecoder().decode(encodedPassword))
        return rawPassword == String(decodedText)
    }
}
