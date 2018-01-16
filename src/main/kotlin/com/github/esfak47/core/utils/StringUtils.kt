package com.github.esfak47.core.utils

/**
 * @author Tony
 * * Created by tony on 2018/1/16.
 */
class StringUtils {
    fun isEmpty(string: String?): Boolean {
        return string != null && "" == string.trim { it <= ' ' }
    }
}
