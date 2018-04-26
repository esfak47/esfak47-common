package com.esfak47.common.extension;

import java.lang.annotation.*;

/**
 * @author tony
 * @date 2018/4/25
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Adaptive("protocol")
public @interface SimpleAdaptive {
}
