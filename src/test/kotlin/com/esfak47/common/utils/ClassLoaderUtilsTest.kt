package com.esfak47.common.utils

import org.junit.Test

import org.junit.Assert.*

/**
 * @author tonywang
 * created on 2018/1/28.
 */
class ClassLoaderUtilsTest {
    @Test
    @Throws(Exception::class)
    fun getDefaultClassLoader() {
        var defaultClassLoader = ClassLoaderUtils.getDefaultClassLoader()
        assertNotNull(defaultClassLoader)
    }

}