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

import java.util.*

/**
 * 日期计算工具类，该类主要值对`Date`增改查【添加、修改、获取年月日时分秒】。还提供了两个日期之间相差月、天数计算。
 * 如果需要使用日期格式化、解析请查看[DateUtils].
 *
 * @author tony on 2016-04-16
 */
object DateCalcUtils {


    /**
     * 给指定时间加/减毫秒数
     *
     * @param date        日期
     * @param milliSecond 毫秒数
     * @return [Date]
     */
    @JvmStatic
    fun addMilliSecond(date: Date, milliSecond: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.MILLISECOND, milliSecond)
        return calendar.time
    }

    /**
     * 给指定日期加（减）秒数
     * <pre>
     * 比如时间2014-05-12 12:00:00  DateCalcUtils.addSecond(date,2); 2014-05-12 12:00:02
     * 比如时间2014-05-12 12:00:00  DateCalcUtils.addSecond(date,-2); 2014-05-12 11:59:58
    </pre> *
     *
     * @param date    日期
     * @param seconds 秒，如果为正整数则添加，否则相减
     * @return [Date]
     */
    @JvmStatic
    fun addSecond(date: Date, seconds: Int): Date {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.SECOND, seconds)
        return cal.time
    }

    /**
     * 给指定日期加（减）分钟数
     * <pre>
     * 比如时间2014-05-12 12:00:00  DateCalcUtils.addMinute(date,2); 2014-05-12 12:02:00
     * 比如时间2014-05-12 12:00:00  DateCalcUtils.addMinute(date,-2); 2014-05-12 11:58:00
    </pre> *
     *
     * @param date    日期
     * @param minutes 分钟，如果为正整数则添加，否则相减
     * @return [Date]
     */
    @JvmStatic
    fun addMinute(date: Date, minutes: Int): Date {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.MINUTE, minutes)
        return cal.time
    }

    /**
     * 给指定日期加（减）小时数
     * <pre>
     * 比如时间2014-05-12 12:00:00  DateCalcUtils.addHour(date,1); 2014-05-12 13:00:00
     * 比如时间2014-05-12 12:00:00  DateCalcUtils.addHour(date,-1); 2014-05-12 11:00:00
    </pre> *
     *
     * @param date  日期
     * @param hours 小时，如果为正整数则添加，否则相减
     * @return [Date]
     */
    @JvmStatic
    fun addHour(date: Date, hours: Int): Date {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.HOUR_OF_DAY, hours)
        return cal.time
    }

    /**
     * 给指定日期加（减）天数
     * <pre>
     * 比如时间2014-05-12 12:00:00  DateCalcUtils.addDate(date,1); 2014-05-11 12:00:00
     * 比如时间2014-05-12 12:00:00  DateCalcUtils.addDate(date,-1); 2014-05-13 12:00:00
    </pre> *
     *
     * @param date 日期
     * @param day  天，如果为正整数则添加，否则相减
     * @return [Date]
     */
    @JvmStatic
    fun addDate(date: Date, day: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DATE, day)
        return calendar.time
    }

    /**
     * 给指定日期加（减）月数
     * <pre>
     * 比如时间2014-05-12 12:10:00  DateCalcUtils.addMonth(date,1,false); 2014-06-12 12:10:00
     * 比如时间2014-05-12 12:10:00  DateCalcUtils.addMonth(date,1,true);  2014-06-01 00:00:00
    </pre> *
     *
     * @param date       日期
     * @param months     月，如果为正整数则添加，否则相减
     * @param escapeDays 如果值为`true`则表示会清空时分秒毫秒，并且日期跳到当月的第一天；如果是`false`则不会
     * @return [Date]
     */
    @JvmOverloads
    @JvmStatic
    fun addMonth(date: Date, months: Int, escapeDays: Boolean = false): Date {
        val cal = Calendar.getInstance()
        cal.time = date
        if (escapeDays) {
            cal.set(Calendar.DATE, 1)
            cal.set(Calendar.HOUR_OF_DAY, 0)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MILLISECOND, 0)
        }
        cal.add(Calendar.MONTH, months)
        return cal.time
    }

    /**
     * 给指定日期加（减）年份数
     * <pre>
     * 比如时间2014-05-12 12:10:00  DateCalcUtils.addYear(date,1); 2015-06-12 12:10:00
     * 比如时间2014-05-12 12:10:00  DateCalcUtils.addYear(date,-1); 2013-04-12 12:10:00
    </pre> *
     *
     * @param date  日期
     * @param years 年，如果为正整数则添加，否则相减
     * @return [Date]
     */
    @JvmStatic
    fun addYear(date: Date, years: Int): Date {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.YEAR, years)
        return cal.time
    }

    /**
     * 从日期中获取年份
     * <pre>
     * 比如时间2014-05-12 12:10:00  DateCalcUtils.getYear(date); 2014
    </pre> *
     *
     * @param date 日期对象
     * @return 年份
     */
    @JvmStatic
    fun getYear(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(1)
    }

    /**
     * 从日期中获取月份
     * <pre>
     * 比如时间2014-05-12 12:10:00  DateCalcUtils.getMonth(date); 5
    </pre> *
     *
     * @param date 日期对象
     * @return 月份
     */
    @JvmStatic
    fun getMonth(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.MONTH) + 1
    }

    /**
     * 从日期中获取天
     * <pre>
     * 比如时间2014-05-12 12:10:00  DateCalcUtils.getDay(date); 12
    </pre> *
     *
     * @param date 日期对象
     * @return 天
     */
    @JvmStatic
    fun getDay(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.DAY_OF_MONTH)
    }

    /**
     * 从日期中获取小时（24制）
     * <pre>
     * 比如时间2014-05-12 22:10:00  DateCalcUtils.get24Hour(date); 22
    </pre> *
     *
     * @param date 日期对象
     * @return 小时（24制）
     */
    @JvmStatic
    fun get24Hour(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.HOUR_OF_DAY)
    }

    /**
     * 从日期中获取小时（12制）
     * <pre>
     * 比如时间2014-05-12 22:10:00  DateCalcUtils.get12Hour(date); 10
    </pre> *
     *
     * @param date 日期对象
     * @return 小时（12制）
     */
    @JvmStatic
    fun get12Hour(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.HOUR)
    }

    /**
     * 从日期中获取分钟
     * <pre>
     * 比如时间2014-05-12 22:10:00  DateCalcUtils.getMinute(date); 10
    </pre> *
     *
     * @param date 日期对象
     * @return 分钟
     */
    @JvmStatic
    fun getMinute(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.MINUTE)
    }

    /**
     * 从日期中获取秒数
     * <pre>
     * 比如时间2014-05-12 22:10:00  DateCalcUtils.getSecond(date); 0
    </pre> *
     *
     * @param date 日期对象
     * @return 秒数
     */
    @JvmStatic
    fun getSecond(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.SECOND)
    }

    /**
     * 从日期中获取毫秒
     * <pre>
     * 比如时间2014-05-12 22:10:00  DateCalcUtils.getMillisecond(date); 151
    </pre> *
     *
     * @param date 日期对象
     * @return 毫秒
     */
    @JvmStatic
    fun getMillisecond(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        System.currentTimeMillis()
        return calendar.get(Calendar.MILLISECOND)
    }

    /**
     * 获取时间的毫秒数，该毫秒是时间转换为毫秒，而不是日期时间中的转动的毫秒。
     * <pre>
     * 比如时间2014-05-12 22:10:00  DateCalcUtils.getTimeMillis(date); 1402582200732L
    </pre> *
     *
     * @param date 日期对象
     * @return 毫秒数
     */
    @JvmStatic
    fun getTimeMillis(date: Date): Long {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.timeInMillis
    }

    /**
     * 判断是否是闰年，闰年规则：[闰年查看](http://zh.wikipedia.org/wiki/%E9%97%B0%E5%B9%B4)
     * <pre>
     * 比如时间2014-05-12 22:10:00  DateCalcUtils.isLeapYear(date); false
    </pre> *
     *
     * @param date 日期对象
     * @return 是否为闰年
     */
    @JvmStatic
    fun isLeapYear(date: Date): Boolean {
        val cal = Calendar.getInstance()
        cal.time = date
        val year = cal.get(Calendar.YEAR)
        var leap = false

        if (year % 400 == 0) {
            leap = true
        } else if (year % 100 == 0) {
            leap = false
        } else if (year % 4 == 0) {
            leap = true
        }
        return leap
    }

    /**
     * 从日期中获取月份第一天
     * <pre>
     * 比如时间2014-05-12  DateCalcUtils.getBeginDayInMonth("2014-05-12","yyyy-MM-dd"); 204-05-01
    </pre> *
     *
     * @param date 日期
     * @return 每月第一天
     */
    @JvmStatic
    fun getBeginDayInMonth(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.DATE, 1)
        return calendar.time
    }

    /**
     * 从日期中获取月份最后一天
     * <pre>
     * 比如时间2014-05-12  DateCalcUtils.getEndDayInMonth(date); 204-05-31
    </pre> *
     *
     * @param date 日期对象
     * @return 每月最后一天
     */
    @JvmStatic
    fun getEndDayInMonth(date: Date): Date {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.set(Calendar.DATE, 1)
        cal.add(Calendar.MONTH, 1)
        cal.add(Calendar.DATE, -1)
        return cal.time
    }

    /**
     * 计算两个日期之间相差的天数,测试发现还是有问题(没问题)
     * <pre>
     * 比如Date start = 2012-2-12
     * Date end = 2012-3-1
     * DateCalcUtils.getDaysBetween(start,end)  18
    </pre> *
     *
     * @param start 日期对象
     * @param end   日期对象
     * @return 天数
     */
    @JvmStatic
    fun getDaysBetween(start: Date, end: Date): Int {
        var srcCalendar = Calendar.getInstance()
        srcCalendar.time = start
        srcCalendar.set(Calendar.HOUR_OF_DAY, 0)
        srcCalendar.set(Calendar.MINUTE, 0)
        srcCalendar.set(Calendar.SECOND, 0)

        var destCalendar = Calendar.getInstance()
        destCalendar.time = end
        destCalendar.set(Calendar.HOUR_OF_DAY, 0)
        destCalendar.set(Calendar.MINUTE, 0)
        destCalendar.set(Calendar.SECOND, 0)

        if (srcCalendar.after(destCalendar)) {
            val tempCalendar = srcCalendar
            srcCalendar = destCalendar
            destCalendar = tempCalendar
        }

        //        return (int) TimeUnit.DAYS.convert(destCalendar.getTimeInMillis() - srcCalendar.getTimeInMillis(), TimeUnit.MILLISECONDS);
        return ((destCalendar.timeInMillis - srcCalendar.timeInMillis) / 86400000L).toInt()
    }

    /**
     * 计算两个日期之间相差的月份
     * <pre>
     * 比如Date start = 2012-2-12
     * Date end = 2012-3-1
     * DateCalcUtils.getMonthsBetween(start,end)  1
    </pre> *
     *
     * @param start 日期对象
     * @param end   日期对象
     * @return 月份
     */
    @JvmStatic
    fun getMonthsBetween(start: Date, end: Date): Int {
        var srcCalendar = Calendar.getInstance()
        srcCalendar.time = start

        var destCalendar = Calendar.getInstance()
        destCalendar.time = end

        if (srcCalendar.after(destCalendar)) {
            val tempCalendar = srcCalendar
            srcCalendar = destCalendar
            destCalendar = tempCalendar
        }

        //获取月份之差
        var months = destCalendar.get(Calendar.MONTH) - srcCalendar.get(Calendar.MONTH)

        val destYear = destCalendar.get(Calendar.YEAR)
        if (srcCalendar.get(Calendar.YEAR) != destYear) {
            srcCalendar = srcCalendar.clone() as Calendar
            do {
                months += srcCalendar.getActualMaximum(Calendar.MONTH) + 1//getActualMaximum最大值11
                srcCalendar.add(Calendar.YEAR, 1)
            } while (srcCalendar.get(Calendar.YEAR) != destYear)
            return months
        }
        return months
    }


}

