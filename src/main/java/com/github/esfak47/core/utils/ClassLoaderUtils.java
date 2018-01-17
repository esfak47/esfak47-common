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

/**
 * {@linkplain ClassLoader}相关的工具类
 *
 * @author mzlion on 2016-05-05 22:14
 */
public class ClassLoaderUtils {
    //logger
//    private static Logger logger = LoggerFactory.getLogger(ClassLoaderUtils.class);

//    /**
//     * 得到自定义类加载器
//     *
//     * @param pathEntries 类或jar的路径
//     * @return 返回类加载器
//     */
//    public static ClassLoader getCustomClassLoader(String... pathEntries) {
//        if (ArrayUtils.isEmpty(pathEntries)) {
//            throw new IllegalArgumentException("Array is null or empty.");
//        }
//
//        int i = 0, length = pathEntries.length;
//        URL[] urls = new URL[length];
//        File file;
//        String pathEntry;
//        for (; i < length; i++) {
//            pathEntry = pathEntries[i];
//            if (StringUtils.isEmpty(pathEntry)) {
//                logger.error("数组元素中存在空元素.");
//                return null;
//            }
//            file = new File(pathEntry);
//            /*if (!file.exists()) {
//                logger.error("该文件不存在->{}", pathEntry);
//                return null;
//            }*/
//            try {
//                urls[i] = file.toURI().toURL();
//            } catch (MalformedURLException e) {
//                logger.error("解析文件失败", e);
//                return null;
//            }
//        }
//
//        ClassLoader parent = getDefaultClassLoader();
//        URLClassLoader urlClassLoader = new URLClassLoader(urls, parent);
//        logger.debug("得到的类加载器为->{}", Arrays.toString(urls));
//        return urlClassLoader;
//    }

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
        } catch (Throwable e) {
            //无法从当前线程获取到类加载器
        }
        if (classLoader == null) {
            classLoader = ClassLoaderUtils.class.getClassLoader();//从当前类的加载器
            if (classLoader == null) {
                classLoader = ClassLoader.getSystemClassLoader();
            }
        }
        return classLoader;
    }
}
