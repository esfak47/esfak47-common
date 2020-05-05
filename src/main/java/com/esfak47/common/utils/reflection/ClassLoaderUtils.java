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
package com.esfak47.common.utils.reflection;

/**
 * {@linkplain ClassLoader}相关的工具类
 *
 * @author tony on 2016-05-05 22:14
 */
public final class ClassLoaderUtils {

    private ClassLoaderUtils() {
    }

    /**
     * 返回一个默认的类加载器：首先会从当前线程获取类加载，如果获取失败则获取当前类的类加载器。
     *
     * @return 返回类类加载器
     * @see Thread#getContextClassLoader()
     */
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader classLoader = null;
        try {
            classLoader = Thread.currentThread().getContextClassLoader();
        } catch (Exception ignored) {
            //无法从当前线程获取到类加载器
        }
        if (classLoader == null) {
            classLoader = ClassLoaderUtils.class.getClassLoader();
            //从当前类的加载器
            if (classLoader == null) {
                classLoader = ClassLoader.getSystemClassLoader();
            }
        }
        return classLoader;
    }
}
