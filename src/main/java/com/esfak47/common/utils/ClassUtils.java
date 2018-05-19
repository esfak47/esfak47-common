/*
 *    Copyright 2018 esfak47(esfak47@qq.com)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.esfak47.common.utils;

import com.esfak47.common.lang.Assert;
import com.esfak47.common.lang.Inject;
import com.esfak47.common.lang.Named;

import java.lang.reflect.*;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * CLass工具类}
 *
 * @author tony on 2016/5/23.
 */
public final class ClassUtils {
    /**
     * Map with primitive wrapper type as key and corresponding primitive type as value, for example: Integer.class ->
     * int.class.
     */
    private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_TYPE_MAP = new IdentityHashMap<>(8);
    /**
     * Map with primitive type as key and corresponding wrapper type as value, for example: int.class -> Integer.class.
     */
    private static final Map<Class<?>, Class<?>> PRIMITIVE_TYPE_TO_WRAPPER_MAP = new IdentityHashMap<>(8);

    static {
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Boolean.class, boolean.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Byte.class, byte.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Character.class, char.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Double.class, double.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Float.class, float.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Integer.class, int.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Long.class, long.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Short.class, short.class);

        for (Map.Entry<Class<?>, Class<?>> entry : PRIMITIVE_WRAPPER_TYPE_MAP.entrySet()) {
            PRIMITIVE_TYPE_TO_WRAPPER_MAP.put(entry.getValue(), entry.getKey());
        }
    }

    private ClassUtils() {}

    /**
     * 判断sourceType是否是targetType父类或接口，其类的本身
     *
     * @param sourceType 被检查的类型
     * @param targetType 被检查的类型
     * @return 如果是则返回{@code true}，否则返回{@code false}
     * @see Class#isAssignableFrom(Class)
     */
    public static boolean isAssignable(Class<?> sourceType, Class<?> targetType) {
        Assert.notNull(sourceType, "Source type must not be null");
        Assert.notNull(targetType, "Target side type must not be null");
        if (sourceType.isAssignableFrom(targetType)) {
            return true;
        }
        if (sourceType.isPrimitive()) {
            Class<?> resolvedPrimitive = PRIMITIVE_WRAPPER_TYPE_MAP.get(targetType);
            return sourceType == resolvedPrimitive;
        } else {
            Class<?> resolvedWrapper = PRIMITIVE_TYPE_TO_WRAPPER_MAP.get(targetType);
            return resolvedWrapper != null && sourceType.isAssignableFrom(resolvedWrapper);
        }
    }



    /**
     * 返回类的原始类型
     *
     * @param type 所有类的超类型
     * @return Class
     */
    public static Class getRawType(Type type) {
        // type is a normal class.
        if (type instanceof Class<?>) {
            return (Class<?>)type;
        } else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType)type;
            Type rawType = parameterizedType.getRawType();
            Assert.isTrue(rawType instanceof Class, "The rawType is not Class.");
            return (Class)rawType;
        } else if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType)type).getGenericComponentType();
            return Array.newInstance(getRawType(componentType), 0).getClass();
        } else if (type instanceof TypeVariable) {
            // we could use the variable's bounds, but that won't work if there are multiple.
            // having a raw type that's more general than necessary is okay
            return Object.class;
        } else if (type instanceof WildcardType) {
            return getRawType(((WildcardType)type).getUpperBounds()[0]);
        } else {
            String className = type == null ? "null" : type.getClass().getName();
            throw new IllegalArgumentException(
                "Expected a Class, ParameterizedType, or " + "GenericArrayType, but <" + type + "> is of type "
                    + className);
        }
    }

}
