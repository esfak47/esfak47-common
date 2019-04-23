package com.esfak47.common.lang;

import java.util.function.Supplier;

/**
 * @author tonywang
 */
@FunctionalInterface
public interface Factory<T> extends Supplier<T> {
}