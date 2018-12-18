package com.esfak47.common.extension;

import java.lang.annotation.*;

/**
 * @author tony 2018/7/26
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JdkSpi {
    /**
     * SPI name
     *
     * @return name
     */
    String value() default "";

    /**
     * 排序顺序
     *
     * @return sortNo
     */
    int order() default 0;
}
