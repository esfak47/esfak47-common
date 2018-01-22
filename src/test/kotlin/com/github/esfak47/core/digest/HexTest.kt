package com.github.esfak47.core.digest

import org.junit.Test

import org.junit.Assert.*

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
        assertEquals("hello",decode2String)

    }

    @Test
    @Throws(Exception::class)
    fun encode() {
    }

    @Test
    @Throws(Exception::class)
    fun encode1() {
    }

    @Test
    @Throws(Exception::class)
    fun decode2String() {
    }

    @Test
    @Throws(Exception::class)
    fun decode2String1() {
    }

    @Test
    @Throws(Exception::class)
    fun decode() {
    }

    @Test
    @Throws(Exception::class)
    fun decode1() {
    }

}