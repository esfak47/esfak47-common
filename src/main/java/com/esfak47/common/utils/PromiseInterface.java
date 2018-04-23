package com.esfak47.common.utils;

import java.util.function.Consumer;

/**
 * @author tony
 * @date 2018/4/24
 */
@FunctionalInterface
public interface PromiseInterface<T,U> {
    void go(Consumer<T> resolve, Consumer<U> reject);
}
