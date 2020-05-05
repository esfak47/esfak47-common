package com.esfak47.common.utils

import com.esfak47.common.utils.properties.PlaceholderPropertyResolver
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test

/**
 * @author tonywang
 * created on 2018/1/28.
 */
class PlaceholderPropertyResolverTest {
    private lateinit var propertyResolver: PlaceholderPropertyResolver

    @Before
    fun before() {
        propertyResolver = PlaceholderPropertyResolver.builder()
                .path("classpath:test.properties").build()


    }


    @Test
    @Throws(Exception::class)
    fun containsProperty() {
        assertThat(propertyResolver.containsProperty("a"), `is`(java.lang.Boolean.TRUE))
        assertThat(propertyResolver.containsProperty("b"), `is`(true));
        assertThat(propertyResolver.containsProperty("d"), `is`(true))
        assertThat(propertyResolver.containsProperty("c"), `is`(false))
    }

    @Test
    @Throws(Exception::class)
    fun getAllProperties() {
        assertThat(propertyResolver.allProperties, notNullValue())
    }

    @Test
    @Throws(Exception::class)
    fun getProperty() {
        assertThat(propertyResolver.getProperty("a"), `is`("abc"))
        assertThat(propertyResolver.getProperty("b"), `is`("abc"))
        assertThat(propertyResolver.getProperty("d"), nullValue())
        assertThat(propertyResolver.getProperty("e"), `is`("abcabc"))
    }

    @Test
    @Throws(Exception::class)
    fun getProperty1() {
        assertThat(propertyResolver.getProperty("c",
                "ecb"),
                `is`("ecb"))
    }


    @Test
    @Throws(Exception::class)
    fun getProperty3() {
    }

    @Test
    @Throws(Exception::class)
    fun resolvePlaceholders() {
    }

}