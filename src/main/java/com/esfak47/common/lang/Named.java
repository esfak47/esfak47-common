package com.esfak47.common.lang;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author tony
 * @date 2018/5/15
 */

@Documented
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Named {
    String value();
}
