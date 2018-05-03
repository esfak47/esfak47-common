package com.esfak47.common.utils;

import com.esfak47.common.extension.Adaptive;
import com.esfak47.common.extension.SPI;
import com.esfak47.common.extension.SimpleAdaptive;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author tony
 * @date 2018/5/3
 */
public class AnnotationUtilsTest {

    @Test
    public void getAnnotation() {
        SPI annotation = AnnotationUtils.getAnnotation(Test1.class, SPI.class);
        Adaptive annotation2 = AnnotationUtils.getAnnotation(Test2.class, Adaptive.class);
        assertNotNull(annotation);
        assertNotNull(annotation2);
    }

    @SPI
    interface Test1 {}

    @SimpleAdaptive
    interface Test2 {}
}