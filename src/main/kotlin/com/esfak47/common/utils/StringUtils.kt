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

package com.esfak47.common.utils

import com.esfak47.common.io.UnsafeStringWriter
import java.io.PrintWriter
import java.util.*
import java.util.regex.Pattern

/**
 * @author Tony
 * * Created by tony on 2018/1/16.
 */
object StringUtils {
    const val EMPTY_STRING = ""
    private val KVP_PATTERN = Pattern.compile("([_.a-zA-Z0-9][-_.a-zA-Z0-9]*)[=](.*)") //key value pair pattern.

    @JvmStatic
    fun isEmpty(string: String?): Boolean {
        return string == null || "" == string.trim { it <= ' ' }
    }

    /**
     * @param e
     * @return string
     */
    @JvmStatic
    fun toString(e: Throwable): String {
        val w = UnsafeStringWriter()
        val p = PrintWriter(w)
        p.print(e.javaClass.name)
        if (e.message != null) {
            p.print(": " + e.message)
        }
        p.println()
        try {
            e.printStackTrace(p)
            return w.toString()
        } finally {
            p.close()
        }
    }

    /**
     *
     *
     * 判断字符串是否为既不为`null`，字符串长度也不为0.当传入参数是一个空白符时也返回`true`
     *
     * <pre class="code">
     * StringUtils.hasLength(null);// --- false
     * StringUtils.hasLength("");// --- false
     * StringUtils.hasLength(" ");// --- true
     * StringUtils.hasLength("Hi");// --- true
    </pre> *
     *
     * @param str 字符串
     * @return 当且仅当字符串类型不为{@code null},长度也不为0时就返回`true`,反之则返回`false`.
     */
    @JvmStatic
    fun hasLength(str: CharSequence?): Boolean {
        return str != null && str.isNotEmpty()
    }

    @JvmStatic
    fun isBlank(str: CharSequence?): Boolean {
        return !hasLength(str)
    }

    /**
     * parse key-value pair.
     *
     * @param str           string.
     * @param itemSeparator item separator.
     * @return key-value map;
     */
    private fun parseKeyValuePair(str: String, itemSeparator: String): Map<String, String> {
        val tmp = str.split(itemSeparator.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val map = HashMap<String, String>(tmp.size)
        for (i in tmp.indices) {
            val matcher = KVP_PATTERN.matcher(tmp[i])
            if (matcher.matches() == false)
                continue
            map[matcher.group(1)] = matcher.group(2)
        }
        return map
    }


    /**
     * parse query string to Parameters.
     *
     * @param qs query string.
     * @return Parameters instance.
     */
    @JvmStatic
    fun parseQueryString(qs: String?): Map<String, String> {
        return if (qs == null || qs.length == 0) HashMap() else parseKeyValuePair(qs, "\\&")
    }

    /**
     *
     *
     * 判断字符串是否存在文本字符，即字符只是存在一个非空白字符。
     *
     * <pre>
     * StringUtils.hasText(null); //--- false
     * StringUtils.hasText(""); //--- false
     * StringUtils.hasText(" "); //--- false
     * StringUtils.hasText(" abc"); //--- true
     * StringUtils.hasText("a"); //--- true
    </pre> *
     *
     * @param str 字符串
     * @return 当字符串至少存在一个不为空白字符时返回{@code true},否则返回`false`
     * @see Character.isWhitespace
     */
    @JvmStatic
    fun hasText(str: CharSequence): Boolean {
        if (!hasLength(str)) {
            return false
        }
        val len = str.length
        var i = 0
        while (i < len) {
            if (!Character.isWhitespace(str[i])) {
                return true
            }
            i++
        }
        return false
    }

    /**
     *
     *
     * 将字符串中所有出现`oldPattern`替换为`newPattern`.
     *
     * <pre class="code">
     * StringUtils.replace("com.mzlion.utility.","l","x"); //--- "hexxo worxd"
    </pre> *
     *
     * @param str        字符串
     * @param oldPattern 需要替换的字符串
     * @param newPattern 新的字符串
     * @return 返回替换后的字符串
     */
    @JvmStatic
    fun replace(str: String, oldPattern: String, newPattern: String?): String {
        if (!hasLength(str) || !hasLength(oldPattern) || null == newPattern) {
            return str
        }

        val sb = StringBuilder()
        var pos = 0
        var index = str.indexOf(oldPattern, pos)
        val patternLen = oldPattern.length

        while (index >= 0) {
            sb.append(str.substring(pos, index))
            sb.append(newPattern)
            pos = index + patternLen
            index = str.indexOf(oldPattern, pos)
        }
        sb.append(str.substring(pos))
        return sb.toString()
    }
}
