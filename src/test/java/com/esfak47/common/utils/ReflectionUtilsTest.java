package com.esfak47.common.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author tony
 * @date 2018/5/15
 */
public class ReflectionUtilsTest {

    @Test
    public void defaultConstructorOnly() {
        Assert.assertTrue(ReflectionUtils.defaultConstructorOnly(ClassA.class));
    }

    public static class ClassA {
    }
}