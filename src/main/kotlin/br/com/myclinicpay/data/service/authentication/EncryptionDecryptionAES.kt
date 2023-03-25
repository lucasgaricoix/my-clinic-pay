package br.com.myclinicpay.data.service.authentication

import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

object EncryptionDecryptionAES {
    private var cipher: Cipher? = null
    fun main(args: Array<String>) {
        /*
         create key
         If we need to generate a new key use a KeyGenerator
         If we have existing plaintext key use a SecretKeyFactory
        */
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(128) // block size is 128bits
        val secretKey = keyGenerator.generateKey()

        /*
          Cipher Info
          Algorithm : for the encryption of electronic data
          mode of operation : to avoid repeated blocks encrypt to the same values.
          padding: ensuring messages are the proper length necessary for certain ciphers
          mode/padding are not used with stream cyphers.
         */

        val cipher = Cipher.getInstance("AES") //SunJCE provider AES algorithm, mode(optional) and padding schema(optional)
        val plainText = "AES Symmetric Encryption Decryption"
        println("Plain Text Before Encryption: $plainText")
        val encryptedText = encrypt(plainText, secretKey)
        println("Encrypted Text After Encryption: $encryptedText")
        val decryptedText = decrypt(encryptedText, secretKey)
        println("Decrypted Text After Decryption: $decryptedText")
    }

    @Throws(Exception::class)
    fun encrypt(plainText: String, secretKey: SecretKey?): String {
        val plainTextByte = plainText.toByteArray()
        cipher!!.init(Cipher.ENCRYPT_MODE, secretKey)
        val encryptedByte = cipher!!.doFinal(plainTextByte)
        val encoder = Base64.getEncoder()
        return encoder.encodeToString(encryptedByte)
    }

    fun decrypt(encryptedText: String?, secretKey: SecretKey?): String {
        val decoder = Base64.getDecoder()
        val encryptedTextByte = decoder.decode(encryptedText)
        cipher?.init(Cipher.DECRYPT_MODE, secretKey)
//        cipher!!.init(Cipher.DECRYPT_MODE, secretKey)
        val decryptedByte = cipher!!.doFinal(encryptedTextByte)
        return decryptedByte.toString()
    }
}
