package com.esfak47.common.digest

import org.junit.Test
import java.io.UnsupportedEncodingException

/**
 * @author tonywang
 * created on 2018/1/22.
 */
class DigestUtilsTest {
    @Test
    @Throws(Exception::class)
    fun sha1() {
        DigestUtils.sha1("hello".toByteArray())
    }

    @Test
    @Throws(Exception::class)
    fun sha1Hex() {
        DigestUtils.sha1Hex("hello")
    }

    @Test
    @Throws(Exception::class)
    fun sha256() {
        DigestUtils.sha256("hello")
    }

    @Test
    @Throws(Exception::class)
    fun sha256Hex() {
        DigestUtils.sha1Hex("hello")
    }

    @Test
    @Throws(Exception::class)
    fun sha384() {
        DigestUtils.sha384("hello")
    }

    @Test
    @Throws(Exception::class)
    fun sha384Hex() {
        DigestUtils.sha384Hex("hello")
    }

    @Test
    @Throws(Exception::class)
    fun sha512() {
        DigestUtils.sha512("hello")
    }

    @Test
    @Throws(UnsupportedEncodingException::class)
    fun sha256HexTest() {
        val bytes = DigestUtils.sha1Hex("CUTE amateur jap girl (She have Boyfriend)")
        println(bytes)
    }
}