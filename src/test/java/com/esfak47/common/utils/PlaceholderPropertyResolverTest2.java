package com.esfak47.common.utils;

import com.esfak47.common.utils.properties.PlaceholderPropertyResolver;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author tonywang
 *         created on 2018/1/28.
 */
public class PlaceholderPropertyResolverTest2 {

    private PlaceholderPropertyResolver propertyResolver;

    @Before
    public void before() {
        propertyResolver = PlaceholderPropertyResolver.builder()
                .path("classpath:test.properties").build();
    }

    @Test
    public void getProperty3() {
        assertThat(propertyResolver.getProperty("a")
                , is("abc"));
    }

    @Test
    public void getProperty() {
        assertThat(propertyResolver.getProperty("a", String.class)
                , is("abc"));
    }

    @Test
    public void getProperty1() {
        assertThat(propertyResolver.getProperty("c", String.class, "abc")
                , is("abc"));
    }

}