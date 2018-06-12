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
package com.esfak47.common.json;

import com.esfak47.common.utils.ClassUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Comparator;

/**
 * <p> 该类主要用于传递泛型的类型，避免在运行时期找不到泛型的实际类型。 具体用法：由于本类是一个抽象类，所以需要子类去实现。比如下面的代码实现String的泛型传递。 </p>
 * <pre>
 *  TypeReference ref = new TypeReference&lt;List&lt;String&gt;&gt;() { };
 * </pre>
 *
 * @author tony on 2016-06-12
 */
public abstract class AbstractTypeRef<T> implements Comparator<T> {

    final Type type;
    final Class<? super T> rawType;

    @SuppressWarnings("unchecked")
    protected AbstractTypeRef() {
        Type superClass = getClass().getGenericSuperclass();
        if (superClass instanceof Class<?>) {
            throw new IllegalArgumentException(
                    "Internal error: TypeReference constructed without actual type information");
        }
        type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
        this.rawType = (Class<? super T>) ClassUtils.getRawType(type);
    }

    /**
     * Gets underlying {@code Type} instance.
     *
     * @return {@link Type}
     */
    public Type getType() {
        return type;
    }

    /**
     * Returns the raw (non-generic) type for this type.
     *
     * @return 原始类的类型
     */
    public Class<? super T> getRawType() {
        return rawType;
    }

    /**
     * Compares its two arguments for order.  Returns a negative integer, zero, or a positive integer as the first
     * argument is less than, equal to, or greater than the second.
     */
    @Override
    public int compare(T o1, T o2) {
        return 0;
    }

}
