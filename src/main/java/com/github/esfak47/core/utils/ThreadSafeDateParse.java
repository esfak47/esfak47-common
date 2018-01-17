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
package com.github.esfak47.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 线程安全的日期解析和格式化类,解决JDK提供的日期格式化类无法在多线程正确处理日期。
 *
 * @author mzlion on 2016-04-14
 */
class ThreadSafeDateParse {
    //logger
    private static final Logger logger = LoggerFactory.getLogger(ThreadSafeDateParse.class);

    //线程
    private static final ThreadLocal<Map<String, DateFormat>> PARSERS = new ThreadLocal<Map<String, DateFormat>>() {
        @Override
        protected Map<String, DateFormat> initialValue() {
            return new HashMap<>();
        }
    };


    /**
     * 得到日期格式化类
     *
     * @param pattern 日期格式化风格
     * @return {@linkplain DateFormat}
     */
    private static DateFormat getParser(String pattern) {
        Map<String, DateFormat> parserMap = PARSERS.get();
        DateFormat df = parserMap.get(pattern);
        if (df == null) {
            logger.debug("Date Format Pattern {} was not found in the current thread:{}", pattern, Thread.currentThread().getId());
            df = new SimpleDateFormat(pattern);
            parserMap.put(pattern, df);
        }
        return df;
    }

    /**
     * 解析日期字符串
     *
     * @param srcDate 日期字符串
     * @param pattern 解析格式
     * @return 日期对象
     * @throws ParseException
     */
    public static Date parse(String srcDate, String pattern) throws ParseException {
        return getParser(pattern).parse(srcDate);
    }

    /**
     * 解析日期字符串
     *
     * @param srcDate 日期字符串
     * @param pattern 解析格式
     * @return 日期
     * @throws ParseException
     */
    public static long parseLongDate(String srcDate, String pattern) throws ParseException {
        return parse(srcDate, pattern).getTime();
    }

    /**
     * 格式化日期，转为字符串
     *
     * @param theDate 日期
     * @param pattern 格式化规则
     * @return 格式化后的日期字符串
     */
    public static String format(Date theDate, String pattern) {
        return getParser(pattern).format(theDate);
    }

    /**
     * 格式化日期，转为字符串
     *
     * @param theDate 日期
     * @param pattern 格式化规则
     * @return 格式化后的日期字符串
     */
    public static String format(long theDate, String pattern) {
        return getParser(pattern).format(new Date(theDate));
    }
}
