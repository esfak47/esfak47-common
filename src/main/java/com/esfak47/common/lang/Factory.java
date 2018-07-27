package com.esfak47.common.lang;

import java.util.function.Supplier;

@FunctionalInterface
public interface Factory<T> extends Supplier<T> {
}