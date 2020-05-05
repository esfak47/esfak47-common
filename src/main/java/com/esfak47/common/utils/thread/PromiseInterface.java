package com.esfak47.common.utils.thread;

import java.util.function.Consumer;

/**
 * @author tony
 * @date 2018/4/24
 */
@FunctionalInterface
public interface PromiseInterface<T> {
    void go(Consumer<T> resolve, Consumer<Throwable> reject);
}
