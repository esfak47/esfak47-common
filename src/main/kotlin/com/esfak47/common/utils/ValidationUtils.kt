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


import java.util.regex.Pattern

/**
 * @author Tony
 * Created by tony on 2018/1/16.
 */
object ValidationUtils {
    /**
     * 手机号简单校验正则
     */
    val MOBILE = Pattern.compile("1\\d{10}")
    /**
     * 手机号较为严格校验正则
     */
    val STRICT_MOBILE = Pattern.compile("1[34578]\\d{9}")
    /**
     * IPv4的正则
     */
    val IPV4_REGEX = Pattern.compile("((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)")

    /**
     * IPv6的正则
     */
    val IPV6_REGEX = Pattern.compile("^([\\da-fA-F]{1,4}:){6}((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$|^::([\\da-fA-F]{1,4}:){0,4}((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$|^([\\da-fA-F]{1,4}:):([\\da-fA-F]{1,4}:){0,3}((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$|^([\\da-fA-F]{1,4}:){2}:([\\da-fA-F]{1,4}:){0,2}((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$|^([\\da-fA-F]{1,4}:){3}:([\\da-fA-F]{1,4}:){0,1}((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$|^([\\da-fA-F]{1,4}:){4}:((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$|^([\\da-fA-F]{1,4}:){7}[\\da-fA-F]{1,4}$|^:((:[\\da-fA-F]{1,4}){1,6}|:)$|^[\\da-fA-F]{1,4}:((:[\\da-fA-F]{1,4}){1,5}|:)$|^([\\da-fA-F]{1,4}:){2}((:[\\da-fA-F]{1,4}){1,4}|:)$|^([\\da-fA-F]{1,4}:){3}((:[\\da-fA-F]{1,4}){1,3}|:)$|^([\\da-fA-F]{1,4}:){4}((:[\\da-fA-F]{1,4}){1,2}|:)$|^([\\da-fA-F]{1,4}:){5}:([\\da-fA-F]{1,4})?$|^([\\da-fA-F]{1,4}:){6}:$")


    /**
     * 判断是否是手机号(中国)
     *
     * @param mobile 手机号
     * @return 验证成功则返回{@code true},否则返回`false`
     */
    @JvmStatic fun isMobile(mobile: String): Boolean {
        return isMatchRegex(MOBILE, mobile)
    }

    /**
     * 判断是否是手机号(中国),使用较为严格的规则,但正则具有时效性,如果新增了号段则校验不通过.
     *
     * @param mobile 手机号
     * @return 验证成功则返回{@code true},否则返回`false`
     */
    @JvmStatic fun isStrictMobile(mobile: String): Boolean {
        return isMatchRegex(STRICT_MOBILE, mobile)
    }

    /**
     * 判断是否是IPv4
     *
     * @param ip ip
     * @return 验证成功则返回{@code true},否则返回`false`
     */
    @JvmStatic fun isIPv4(ip: String): Boolean {
        return isMatchRegex(IPV4_REGEX, ip)
    }

    @JvmStatic fun isIPv6(ip: String): Boolean {
        return isMatchRegex(IPV6_REGEX, ip)
    }

    /**
     * 校验
     *
     * @param pattern 正则
     * @param value   待验证的字符串
     * @return 验证成功则返回{@code true},否则返回`false`
     */
    private fun isMatchRegex(pattern: Pattern?, value: String): Boolean {
        return if (pattern == null || StringUtils.isEmpty(value)) {
            false//空字符串则直接返回{@code false}
        } else pattern.matcher(value).matches()
        //if () return true; //正则表达式为{@code null}则全匹配
    }
}
