package com.esfak47.common.digest

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author tonywang
 * created on 2018/1/22.
 */
class HexTest {
    @Test
    @Throws(Exception::class)
    fun encode2String() {
        val encode2String = Hex.encode2String("hello".toByteArray())
        val decode2String = Hex.decode2String(encode2String)
        assertEquals("hello", decode2String)

    }

    @Test
    @Throws(Exception::class)
    fun encode() {
        val encode: CharArray = Hex.encode("hello".toByteArray())
        val string = String(Hex.decode(encode))
        assertEquals("hello", string)
    }

}