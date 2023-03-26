package br.com.myclinicpay.data.service.authentication

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AESUtilTest {
    private val inputText = "123456"

    @Autowired
    private lateinit var aesUtil: AESUtil

    @Test
    fun testEncryptAndDecrypt() {
        val key = aesUtil.generateKey()
        val ivParameterSpec = aesUtil.getIvParameterSpec()
        val encryptedText = aesUtil.encrypt(inputText, key, ivParameterSpec)
        val decryptedText = aesUtil.decrypt(encryptedText, key, ivParameterSpec)
        assertEquals(inputText, decryptedText)
    }

    @Test
    fun testEncryptAndDecryptFromPassword() {
        val key = aesUtil.getKeyFromPassword()
        val keyCopy = aesUtil.getKeyFromPassword()
        val ivParameterSpec = aesUtil.getIvParameterSpec()
        val encryptedText = aesUtil.encrypt(inputText, key, ivParameterSpec)
        val decryptedText = aesUtil.decrypt(encryptedText, keyCopy, ivParameterSpec)
        assertEquals(inputText, decryptedText)
    }
}
