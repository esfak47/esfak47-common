package com.esfak47.common.utils

import org.junit.Assert
import org.junit.Test


/**
 * @author tonywang
 * created on 2018/1/27.
 */
class ClassUtilsTest {
    @Test
    @Throws(Exception::class)
    fun isAssignable() {
        Assert.assertFalse(ClassUtils.isAssignable(Int::class.java, Number::class.java))
        Assert.assertTrue(ClassUtils.isAssignable(Number::class.java, Int::class.java))

    }

    @Test
    @Throws(Exception::class)
    fun getRawType() {
        var clazz = ClassUtils.getRawType(Int.javaClass)
        Assert.assertEquals(clazz,Int.javaClass)

    }

}