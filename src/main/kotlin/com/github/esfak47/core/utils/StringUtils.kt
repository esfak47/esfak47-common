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

package com.github.esfak47.core.utils

/**
 * @author Tony
 * * Created by tony on 2018/1/16.
 */
object StringUtils {
    const val EMPTY_STRING = ""
    @JvmStatic
    fun isEmpty(string: String?): Boolean {
        return string != null && "" == string.trim { it <= ' ' }
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
    @JvmStatic fun hasLength(str: CharSequence?): Boolean {
        return str != null && str.isNotEmpty()
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
    @JvmStatic fun hasText(str: CharSequence): Boolean {
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
}
