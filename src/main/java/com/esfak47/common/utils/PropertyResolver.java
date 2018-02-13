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
package com.esfak47.common.utils;

import java.util.Map;

/**
 * 定义处理{@code properties}文件处理接口
 *
 * @author tony on 2016/4/11.
 */
public interface PropertyResolver {

    /**
     * 判断{@code key}是否存在
     *
     * @param key 键名，非空
     * @return 如果存在则返回{@code true}，否则返回{@code false}
     */
    boolean containsProperty(String key);

    /**
     * 返回所有的键值对
     *
     * @return 返回所有键值对
     */
    Map<String, String> getAllProperties();

    /**
     * 返回{@code key}对应的值
     *
     * @param key 键名，非空
     * @return 如果存在则返回值，否则返回{@code null}
     */
    String getProperty(String key);

    /**
     * 返回{@code key}对应的值，如果值不存在，则返回{@code defaultValue}
     *
     * @param key          键名，非空
     * @param defaultValue 当值为空是则返回该值
     * @return 如果存在则返回值，否则返回{@code defaultValue}
     */
    String getProperty(String key, String defaultValue);

    /**
     * 返回{@code key}对应的值，并且转换为对应类型{@code targetType}的值
     *
     * @param key        键名，非空
     * @param targetType 返回值类型
     * @param <T>        泛型
     * @return 如果存在则返回值，否则返回{@code null}
     */
    <T> T getProperty(String key, Class<T> targetType);

    /**
     * 返回{@code key}对应的值，并且转换为对应类型{@code targetType}的值
     *
     * @param key          键名，非空
     * @param targetType   返回值类型
     * @param <T>          泛型
     * @param defaultValue 当值为空是则返回该值
     * @return 如果存在则返回值，否则返回默认值{@code defaultValue}
     */
    <T> T getProperty(String key, Class<T> targetType, T defaultValue);

    /**
     * 处理占位符{@code ${...}}字符串，通过调用{@linkplain #getProperty(String)}替换为对应的值。
     * 如果无法替换则忽略
     *
     * @param text 待处理的字符串
     * @return 返回已处理的字符串
     */
    String resolvePlaceholders(String text);
}
