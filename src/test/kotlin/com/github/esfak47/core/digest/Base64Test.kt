package com.github.esfak47.core.digest

import org.junit.Test

import org.junit.Assert.*

/**
 * @author tonywang
 * created on 2018/1/21.
 */
class Base64Test {
    @Test
    fun encode() {

        val encode:String = Base64.encode("abc")
        val decode:String = String(Base64.decode(encode))

        assertEquals(decode,"abc");
    }
    @Test
    fun encodeURL() {

        val encode:String = Base64.encode("file://c.txt",true)
        val decode:String = String(Base64.decode(encode,true))

        assertEquals(decode,"file://c.txt");
    }

}