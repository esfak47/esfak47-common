package com.esfak47.common.utils

import org.junit.Test

import org.junit.Assert.*
import org.hamcrest.CoreMatchers.*
import java.util.*

/**
 * @author tonywang
 * created on 2018/1/28.
 */
class CollectionUtilsTest {
    @Test
    fun isEmpty() {
        assertThat(CollectionUtils.isEmpty(Collections.EMPTY_MAP), `is`(true))
    }

    @Test
    fun isEmpty1() {
        assertThat(CollectionUtils.isEmpty(Collections.EMPTY_LIST), `is`(true))
    }

}