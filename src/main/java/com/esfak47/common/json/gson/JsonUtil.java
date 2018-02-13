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
package com.esfak47.common.json.gson;

import com.esfak47.common.json.AbstractTypeRef;
import com.esfak47.common.io.ArrayUtils;
import com.esfak47.common.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * <p>
 * {@code JsonUtil}工具类是基于<a href="https://github.com/google/gson">Google Gson</a>简单封装，提供了Java对象和Json字符串的相互转换。
 * </p>
 *
 * @author tony on 2016-04-14
 * @see Gson
 */
public final class JsonUtil {


    private static Gson gson;

    /**
     * 实例化Gson
     */
    static {
        gson = new GsonBuilder().create();
    }

    private JsonUtil() {
        throw new UnsupportedOperationException();
    }
    /**
     * 将对象转为Json字符串,中文自动转为Unicode码
     *
     * @param value 对象
     * @param <T>   泛型类
     * @return json字符串或{@code null}
     */
    public static <T> String toJson(T value) {
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            return (String) value;
        }
        UnicodeStringWriter writer = new UnicodeStringWriter();
        gson.toJson(value, writer);
        return writer.toString();
    }

    /**
     * 将对象转为Json字符串,支持忽略的属性列表.中文自动转为Unicode码
     *
     * @param value               对象
     * @param <T>                 泛型类
     * @param ignorePropertyNames 忽略的属性列表
     * @return json字符串或{@code null}
     */
    public static <T> String toJson(T value, final String... ignorePropertyNames) {
        if (ArrayUtils.isEmpty(ignorePropertyNames)) {
            return toJson(value);
        }
        UnicodeStringWriter writer = new UnicodeStringWriter();
        new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.STATIC)
                .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                .addSerializationExclusionStrategy(new PropertyNameExclusionStrategy(ignorePropertyNames))
                .create()
                .toJson(value, writer);
        return writer.toString();
    }

    /**
     * 将对象转为Json字符串
     * <p>
     * 将Javabean对象转为JSON字符串时希望非ASCII码(如中文)不Unicode转码，则可以调用该方法完成需求
     * </p>
     *
     * @param value               对象
     * @param toUnicode           如果为{@code true}则中文等非ASCII码自动转为Unicode码
     * @param ignorePropertyNames 忽略的属性列表，可为空
     * @param <T>                 泛型类型
     * @return 返回json字符串
     */
    public static <T> String toJson(T value, boolean toUnicode, final String... ignorePropertyNames) {
        if (toUnicode) {
            return toJson(value, ignorePropertyNames);
        }
        if (ArrayUtils.isEmpty(ignorePropertyNames)) {
            return gson.toJson(value);
        }
        return new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.STATIC)
                .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                .addSerializationExclusionStrategy(new PropertyNameExclusionStrategy(ignorePropertyNames))
                .create().toJson(value);
    }

    /**
     * 将对象转为Json字符串
     * <p>
     * 该方法除了更够控制是否转换Unicode之外，还可以支持值为{@code null}的属性名是否包含在JSON字符串中
     * </p>
     *
     * @param value               待转换的对象
     * @param toUnicode           如果为{@code true}则中文等非ASCII码自动转为Unicode码
     * @param ignoreNull          如果为{@code true}则为{@code null}的属性也会被序列化到JSON字符串中
     * @param ignorePropertyNames 忽略的属性列表，可为空
     * @param <T>                 泛型类型
     * @return 返回json字符串
     */
    public static <T> String toJson(T value, boolean toUnicode, boolean ignoreNull, final String... ignorePropertyNames) {
        if (ignoreNull) {
            return toJson(value, toUnicode, ignorePropertyNames);
        }
        return new GsonBuilder()
                .serializeNulls()
                .excludeFieldsWithModifiers(Modifier.STATIC)
                .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                .addSerializationExclusionStrategy(new PropertyNameExclusionStrategy(ignorePropertyNames))
                .create().toJson(value);
    }

    //---------------------------------------------------------------------
    // convert JSON String to Java instance
    // ---------------------------------------------------------------------

    /**
     * 将Json字符串转为对象
     *
     * @param json  Json字符串
     * @param clazz 泛型类类型
     * @param <T>   泛型类
     * @return Java对象
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        if (clazz == null) {
            return gson.fromJson(json, new TypeToken<Map<String, Object>>() {
            }.getType());
        }
        return gson.fromJson(json, clazz);
    }

    /**
     * 将Json字符串转为对象
     *
     * @param json    Json字符串
     * @param typeOfT Java的实际类型
     * @param <T>     泛型类型
     * @return 对象
     */
    public static <T> T fromJson(String json, Type typeOfT) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        return gson.fromJson(json, typeOfT);
    }

    /**
     * 将Json字符串转为对象
     *
     * @param json    Json字符串
     * @param <T>     泛型类
     * @param typeRef 泛型帮助类
     * @return 对象
     */
    public static <T> T fromJson(String json, AbstractTypeRef<T> typeRef) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        return gson.fromJson(json, typeRef.getType());
    }

}