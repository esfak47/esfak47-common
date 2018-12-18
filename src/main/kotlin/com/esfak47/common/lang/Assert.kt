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

package com.esfak47.common.lang

import com.esfak47.common.utils.CollectionUtils
import com.esfak47.common.utils.StringUtils

/**
 * Assert Utils
 */
object Assert {


    /**
     * Assert a boolean expression,throwing `IllegalArgumentException` if the test result is `false`.
     * <pre class="code">Assert.isTrue(i &gt; 0, "The value must be greater than zero");</pre>
     *
     * @param expression a boolean expression
     * @param message    the detail message.
     * @throws IllegalArgumentException An invalid parameter exception.
     */
    @JvmStatic
    fun isTrue(expression: Boolean, message: String) {
        if (!expression) {
            throw IllegalArgumentException(message)
        }
    }

    /**
     * Assert an object value is not `null`,throwing `IllegalArgumentException` if the test result is `false`.
     * <pre class="code">Assert.notNull(clazz, "The class must not be null");</pre>
     *
     * @param data    the object to check
     * @param message the detail message.
     * @throws IllegalArgumentException An invalid parameter exception.
     */
    @JvmStatic
    fun notNull(data: Any?, message: String) {
        if (data == null) {
            throw IllegalArgumentException(message)
        }
    }

    /**
     * assert the given string is not empty.
     * <pre class="code">Assert.hasLength(name, "Name must not be empty");</pre>
     *
     * @param text    the string to check
     * @param message the detail message.
     * @throws IllegalArgumentException An invalid parameter exception.
     */
    @JvmStatic
    fun hasLength(text: String, message: String) {
        if (StringUtils.isEmpty(text)) {
            throw IllegalArgumentException(message)
        }
    }

    /**
     * Assert that the collection has one element at least.
     * <pre class="code">Assert.notEmpty(collection, "Collection must have elements");</pre>
     *
     * @param collection the collection to check
     * @param message    the detail message.
     * @throws IllegalArgumentException An invalid parameter exception.
     */
    @JvmStatic
    fun notEmpty(collection: Collection<*>, message: String) {
        if (CollectionUtils.isEmpty(collection)) {
            throw IllegalArgumentException(message)
        }
    }

    /**
     * Assert that the map has one entry at least.
     * <pre class="code">Assert.notEmpty(map, "Map must have entries");</pre>
     *
     * @param map     the map to check
     * @param message the detail message.
     * @throws IllegalArgumentException An invalid parameter exception.
     */
    @JvmStatic
    fun notEmpty(map: Map<*, *>, message: String) {
        if (CollectionUtils.isEmpty(map)) {
            throw IllegalArgumentException(message)
        }
    }

    /**
     * Assert that the array has one element at least.
     * <pre class="code">Assert.notEmpty(array, "The array must have elements");</pre>
     *
     * @param array   the array to check
     * @param message the detail message.
     * @param <T>     generic type
     * @throws IllegalArgumentException An invalid parameter exception.
    </T> */
    @JvmStatic
    fun <T> notEmpty(array: Array<T>?, message: String) {
        if (null == array || array.isEmpty()) {
            throw IllegalArgumentException(message)
        }
    }

    /**
     * Assert that the array has one char element at least.
     * <pre class="code">Assert.notEmpty(new char[]{}, "The array must have elements");</pre>
     *
     * @param array   the array to check
     * @param message the detail message.
     * @throws IllegalArgumentException An invalid parameter exception.
     */
    @JvmStatic
    fun notEmpty(array: CharArray?, message: String) {
        if (null == array || array.isEmpty()) {
            throw IllegalArgumentException(message)
        }
    }

    /**
     * Assert that the array has one boolean element at least.
     * <pre class="code">Assert.notEmpty(new boolean[]{}, "The array must have elements");</pre>
     *
     * @param array   the array to check
     * @param message the detail message.
     * @throws IllegalArgumentException An invalid parameter exception.
     */
    @JvmStatic
    fun notEmpty(array: BooleanArray?, message: String) {
        if (null == array || array.isEmpty()) {
            throw IllegalArgumentException(message)
        }
    }

    /**
     * Assert that the array has one byte element at least.
     * <pre class="code">Assert.notEmpty(new byte[]{}, "The array must have elements");</pre>
     *
     * @param array   the array to check
     * @param message the detail message.
     * @throws IllegalArgumentException An invalid parameter exception.
     */
    @JvmStatic
    fun notEmpty(array: ByteArray?, message: String) {
        if (null == array || array.isEmpty()) {
            throw IllegalArgumentException(message)
        }
    }

    /**
     * Assert that the array has one short element at least.
     * <pre class="code">Assert.notEmpty(new short[]{}, "The array must have elements");</pre>
     *
     * @param array   the array to check
     * @param message the detail message.
     * @throws IllegalArgumentException An invalid parameter exception.
     */
    @JvmStatic
    fun notEmpty(array: ShortArray?, message: String) {
        if (null == array || array.isEmpty()) {
            throw IllegalArgumentException(message)
        }
    }

    /**
     * Assert that the array has one int element at least.
     * <pre class="code">Assert.notEmpty(new int[]{}, "The array must have elements");</pre>
     *
     * @param array   the array to check
     * @param message the detail message.
     * @throws IllegalArgumentException An invalid parameter exception.
     */
    @JvmStatic
    fun notEmpty(array: IntArray?, message: String) {
        if (null == array || array.isEmpty()) {
            throw IllegalArgumentException(message)
        }
    }

    /**
     * Assert that the array has one long element at least.
     * <pre class="code">Assert.notEmpty(new long[]{}, "The array must have elements");</pre>
     *
     * @param array   the array to check
     * @param message the detail message.
     * @throws IllegalArgumentException An invalid parameter exception.
     */
    @JvmStatic
    fun notEmpty(array: LongArray?, message: String) {
        if (null == array || array.isEmpty()) {
            throw IllegalArgumentException(message)
        }
    }

    /**
     * Assert that the array has one float element at least.
     * <pre class="code">Assert.notEmpty(new float[]{}, "The array must have elements");</pre>
     *
     * @param array   the array to check
     * @param message the detail message.
     * @throws IllegalArgumentException An invalid parameter exception.
     */
    @JvmStatic
    fun notEmpty(array: FloatArray?, message: String) {
        if (null == array || array.isEmpty()) {
            throw IllegalArgumentException(message)
        }
    }

    /**
     * Assert that the array has one doube element at least.
     * <pre class="code">Assert.notEmpty(new double[]{}, "The array must have elements");</pre>
     *
     * @param array   the array to check
     * @param message the detail message.
     * @throws IllegalArgumentException An invalid parameter exception.
     */
    @JvmStatic
    fun notEmpty(array: DoubleArray?, message: String) {
        if (null == array || array.isEmpty()) {
            throw IllegalArgumentException(message)
        }
    }


}