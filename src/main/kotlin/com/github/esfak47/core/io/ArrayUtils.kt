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
package com.github.esfak47.core.io

import com.github.esfak47.core.utils.StringUtils
import java.util.*

/**
 * 数组工具类
 *
 * @author mzlion on 2016-05-05 22:15
 */

object ArrayUtils {


    /**
     *
     * 判断是否是数组类型
     * <pre class="code">
     * ObjectUtils.isArray(null); //--- false;
     * ObjectUtils.isArray(new String[]{"aa","bb"}); //--- true
    </pre> *
     *
     * @param obj 对象
     * @return 如果是数组类型则返回{@code true},否则返回`false`
     */
    @JvmStatic fun isArray(obj: Any?): Boolean {
        return obj != null && obj.javaClass.isArray
    }

    /**
     * 判断是否为空或者为`null`
     *
     * @param array 数组
     * @return 当数组为空或{@code null}时返回`true`
     */
    @JvmStatic fun isEmpty(array: CharArray?): Boolean {
        return array == null || array.isEmpty()
    }

    /**
     * 判断是否为空或者为`null`
     *
     * @param array 数组
     * @return 当数组为空或{@code null}时返回`true`
     */
    @JvmStatic fun isEmpty(array: BooleanArray?): Boolean {
        return array == null || array.isEmpty()
    }

    /**
     * 判断是否为空或者为`null`
     *
     * @param array 数组
     * @return 当数组为空或{@code null}时返回`true`
     */
    @JvmStatic fun isEmpty(array: ByteArray?): Boolean {
        return array == null || array.isEmpty()
    }

    /**
     * 判断是否为空或者为`null`
     *
     * @param array 数组
     * @return 当数组为空或{@code null}时返回`true`
     */
    @JvmStatic fun isEmpty(array: ShortArray?): Boolean {
        return array == null || array.isEmpty()
    }

    /**
     * 判断是否为空或者为`null`
     *
     * @param array 数组
     * @return 当数组为空或{@code null}时返回`true`
     */
    @JvmStatic fun isEmpty(array: IntArray?): Boolean {
        return array == null || array.isEmpty()
    }

    /**
     * 判断是否为空或者为`null`
     *
     * @param array 数组
     * @return 当数组为空或{@code null}时返回`true`
     */
    @JvmStatic fun isEmpty(array: LongArray?): Boolean {
        return array == null || array.isEmpty()
    }

    /**
     * 判断是否为空或者为`null`
     *
     * @param array 数组
     * @return 当数组为空或{@code null}时返回`true`
     */
    @JvmStatic fun isEmpty(array: FloatArray?): Boolean {
        return array == null || array.isEmpty()
    }

    /**
     *
     * 判断数组中是否包含了指定的元素
     * <pre class="code">
     * ObjectUtils.containsElement(new String[]{"aaaa","bbb","cc",null},null); //--- true
     * ObjectUtils.containsElement(new String[]{"aaaa","bbb","cc"},"cc"); //--- true
     * ObjectUtils.containsElement(new String[]{"aaaa","bbb","cc",null},"xx"); //--- false
    </pre> *
     *
     * @param array   数组
     * @param element 检查的元素对象
     * @param <T>     泛型类型声明
     * @return 如果数组中存在则返回{@code true},否则返回`false`
     * @see ObjectUtils.nullSafeEquals
    </T> */
    @JvmStatic fun <T> containsElement(array: Array<T?>, element: T): Boolean {
        if (isEmpty(array)) {
            return false
        }
        for (arrayEle in array) {
            if (Objects.equals(arrayEle, element)) {
                return true
            }
        }
        return false
    }
    /**
     * 判断是否为空或者为`null`
     *
     * @param array 数组
     * @return 当数组为空或{@code null}时返回`true`
     */
    @JvmStatic fun isEmpty(array: DoubleArray?): Boolean {
        return array == null || array.isEmpty()
    }

    /**
     * 判断是否为空或为`null`
     *
     * @param array 数组
     * @param <T>   泛型类
     * @return 当数组为空或{@code null}时返回`true`
    </T> */
    @JvmStatic fun <T> isEmpty(array: Array<T?>?): Boolean {
        return array == null || array.isEmpty()
    }

    /**
     * 判断是否不为空或不为`null`
     *
     * @param array 数组
     * @param <T>   泛型类
     * @return 当数组不为空且不是{@code null}时返回`true`
    </T> */
    @JvmStatic fun <T> isNotEmpty(array: Array<T>?): Boolean {
        return array != null && array.isNotEmpty()
    }

    /**
     * 判断数组里的元素是否为空
     *
     * @param array 数组
     * @param <T>   泛型类
     * @return 如果数组的元素中存在{@code null}或空则返回`true`
    </T> */
    @JvmStatic fun <T> isEmptyElement(array: Array<T?>): Boolean {
        var empty = isEmpty(array)
        if (!empty) {
            empty = false
            for (element in array) {
                if (element == null) {
                    empty = true
                } else {
                    if (element is String) {
                        empty = StringUtils.isEmpty(element as String)
                    }
                }
                if (empty) {
                    return true
                }
            }
        }
        return empty
    }

    /**
     * 将数组转为字符串，使用`delimiter`将元素连接起来
     *
     * @param array     数组
     * @param delimiter 数组元素分隔符
     * @return 如果数组为空则返回空字符串
     */
    @JvmOverloads
    @JvmStatic fun <T> toString(array: Array<T?>, d: String? = ","): String {
        var delimiter = d
        if (isEmpty(array)) {
            return ""
        }
        if (delimiter == null) {
            delimiter = ""
        }
        val builder = StringBuilder()
        val length = array.size - 1
        var i = 0
        while (true) {
            builder.append(array[i].toString())
            if (i == length) {
                return builder.toString()
            }
            builder.append(delimiter)
            i++
        }
    }

    /**
     * 将数组转为字符串，使用英文半角逗号连接
     *
     * @param array     数组
     * @param delimiter 数组元素分隔符
     * @return 如果数组为空则返回空字符串
     */
    @JvmOverloads
    @JvmStatic fun toString(array: CharArray, d: String? = ","): String {
        var delimiter = d
        if (isEmpty(array)) {
            return ""
        }
        if (delimiter == null) {
            delimiter = ""
        }
        val builder = StringBuilder()
        val length = array.size - 1
        var i = 0
        while (true) {
            builder.append(array[i])
            if (i == length) {
                return builder.toString()
            }
            builder.append(delimiter)
            i++
        }
    }

    /**
     * 将数组转为字符串，使用英文半角逗号连接
     *
     * @param array     数组
     * @param delimiter 数组元素分隔符
     * @return 如果数组为空则返回空字符串
     */
    @JvmOverloads
    @JvmStatic fun toString(array: BooleanArray, d: String? = ","): String {
        var delimiter = d
        if (isEmpty(array)) {
            return ""
        }
        if (delimiter == null) {
            delimiter = ""
        }
        val builder = StringBuilder()
        val length = array.size - 1
        var i = 0
        while (true) {
            builder.append(array[i])
            if (i == length) {
                return builder.toString()
            }
            builder.append(delimiter)
            i++
        }
    }

    /**
     * 将数组转为字符串，使用英文半角逗号连接
     *
     * @param array     数组
     * @param delimiter 数组元素分隔符
     * @return 如果数组为空则返回空字符串
     */
    @JvmOverloads
    @JvmStatic fun toString(array: ByteArray, d: String? = ","): String {
        var delimiter = d
        if (isEmpty(array)) {
            return ""
        }
        if (delimiter == null) {
            delimiter = ""
        }
        val builder = StringBuilder()
        val length = array.size - 1
        var i = 0
        while (true) {
            builder.append(array[i].toInt())
            if (i == length) {
                return builder.toString()
            }
            builder.append(delimiter)
            i++
        }
    }

    /**
     * 将数组转为字符串，使用英文半角逗号连接
     *
     * @param array     数组
     * @param delimiter 数组元素分隔符
     * @return 如果数组为空则返回空字符串
     */
    @JvmOverloads
    @JvmStatic fun toString(array: ShortArray, d: String? = ","): String {
        var delimiter = d
        if (isEmpty(array)) {
            return ""
        }
        if (delimiter == null) {
            delimiter = ""
        }
        val builder = StringBuilder()
        val length = array.size - 1
        var i = 0
        while (true) {
            builder.append(array[i].toInt())
            if (i == length) {
                return builder.toString()
            }
            builder.append(delimiter)
            i++
        }
    }

    /**
     * 将数组转为字符串，使用`delimiter`将元素连接起来
     *
     * @param array     数组
     * @param delimiter 数组元素分隔符
     * @return 如果数组为空则返回空字符串
     */
    @JvmOverloads
    @JvmStatic fun toString(array: IntArray, d: String? = ","): String {
        var delimiter = d
        if (isEmpty(array)) {
            return ""
        }
        if (delimiter == null) {
            delimiter = ""
        }
        val builder = StringBuilder()
        val length = array.size - 1
        var i = 0
        while (true) {
            builder.append(array[i])
            if (i == length) {
                return builder.toString()
            }
            builder.append(delimiter)
            i++
        }
    }

    /**
     * 将数组转为字符串，使用`delimiter`将元素连接起来
     *
     * @param array     数组
     * @param delimiter 数组元素分隔符
     * @return 如果数组为空则返回空字符串
     */
    @JvmOverloads
    @JvmStatic fun toString(array: LongArray, d: String? = ","): String {
        var delimiter = d
        if (isEmpty(array)) {
            return ""
        }
        if (delimiter == null) {
            delimiter = ""
        }
        val builder = StringBuilder()
        val length = array.size - 1
        var i = 0
        while (true) {
            builder.append(array[i])
            if (i == length) {
                return builder.toString()
            }
            builder.append(delimiter)
            i++
        }
    }

    /**
     * 将数组转为字符串，使用英文半角逗号连接
     *
     * @param array     数组
     * @param delimiter 数组元素分隔符
     * @return 如果数组为空则返回空字符串
     */
    @JvmOverloads
    @JvmStatic fun toString(array: FloatArray, d: String? = ","): String {
        var delimiter = d
        if (isEmpty(array)) {
            return ""
        }
        if (delimiter == null) {
            delimiter = ""
        }
        val builder = StringBuilder()
        val length = array.size - 1
        var i = 0
        while (true) {
            builder.append(array[i])
            if (i == length) {
                return builder.toString()
            }
            builder.append(delimiter)
            i++
        }
    }

    /**
     * 将数组转为字符串，使用英文半角逗号连接
     *
     * @param array     数组
     * @param delimiter 数组元素分隔符
     * @return 如果数组为空则返回空字符串
     */
    @JvmOverloads
    @JvmStatic fun toString(array: DoubleArray, d: String? = ","): String {
        var delimiter = d
        if (isEmpty(array)) {
            return ""
        }
        if (delimiter == null) {
            delimiter = ""
        }
        val builder = StringBuilder()
        val length = array.size - 1
        var i = 0
        while (true) {
            builder.append(array[i])
            if (i == length) {
                return builder.toString()
            }
            builder.append(delimiter)
            i++
        }
    }


    /**
     *
     *
     * 向数组中追加一个字符串，返回一个新的字符串数组.
     *
     * <pre class="code">
     * StringUtils.addStringToArray(new String[]{"hello"},"world"); //--- [hello,world]
     * StringUtils.addStringToArray(null,"hah"); //--- [haha]
    </pre> *
     *
     * @param array  原始数组
     * @param addStr 追加的字符串
     * @return 返回一个新的数组，该数组永远不为`null`
     */
    @JvmStatic fun addStringToArray(array: Array<String?>, addStr: String): Array<String?> {
        if (ArrayUtils.isEmpty(array)) {
            return arrayOf(addStr)
        }
        val newArray = arrayOfNulls<String>(array.size + 1)
        System.arraycopy(array, 0, newArray, 0, array.size)
        newArray[array.size] = addStr
        return newArray
    }

    /**
     *
     *
     * 将两个字符串数组对接,返回一个新的字符串数组.
     *
     * <pre class="code">
     * StringUtils.concatStringArrays(new String[]{"girl","women"},new String[]{"boy","girl"}); //--- [girl,women,boy,girl]
    </pre> *
     *
     * @param arr1 数组
     * @param arr2 数组
     * @return 返回一个新的字符串数组
     */
    @JvmStatic fun concatStringArrays(arr1: Array<String?>, arr2: Array<String?>): Array<String?> {
        if (ArrayUtils.isEmpty(arr1)) {
            return arr2
        }
        if (ArrayUtils.isEmpty(arr2)) {
            return arr1
        }
        val newArr = arrayOfNulls<String>(arr1.size + arr2.size)
        System.arraycopy(arr1, 0, newArr, 0, arr1.size)
        System.arraycopy(arr2, 0, newArr, arr1.size, arr2.size)
        return newArr
    }

    /**
     *
     *
     * 合并两个数组,其中数组元素重复的直计算一次.
     *
     * <pre class="code">
     * StringUtils.mergeStringArrays(new String[]{"girl","women"},new String[]{"boy","girl"}); //--- [girl,women,boy]
    </pre> *
     *
     * @param array1 数组
     * @param array2 数组
     * @return 返回合并后的新数组
     */
    @JvmStatic fun mergeStringArrays(array1: Array<String?>, array2: Array<String?>): Array<String?> {
        if (ArrayUtils.isEmpty(array1)) {
            return array2
        }
        if (ArrayUtils.isEmpty(array2)) {
            return array1
        }
        val result = ArrayList<String?>()
        result.addAll(array1.asList())
        for (str in array2) {
            if (!result.contains(str)) {
                result.add(str)
            }
        }
        return result.toTypedArray();
    }

    /**
     *
     *
     * 数组排序
     *
     * <pre class="code">
     * StringUtils.sortStringArray(new String[]{"hello","boy","amazing"}); //--- [amazing,boy,hello]
    </pre> *
     *
     * @param array 数组
     * @return 排序后的字符串数组
     */
    @JvmStatic fun sortStringArray(array: Array<String?>): Array<String?> {
        if (ArrayUtils.isEmpty(array)) {
            return arrayOfNulls(0)
        }
        Arrays.sort(array)
        return array
    }

}
