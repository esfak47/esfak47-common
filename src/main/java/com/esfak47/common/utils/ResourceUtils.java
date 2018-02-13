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


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * 文件资源工具类
 *
 * @author tony on 2016/4/11.
 */
public class ResourceUtils {
    private static Logger logger = LoggerFactory.getLogger(ResourceUtils.class);

    public static final String CLASSPATH_URL_PREFIX = "classpath:";

    public static final String FILE_URL_PREFIX = "file:";

    public static final String JAR_URL_PREFIX = "jar:";

    public static File getFile(String resourceLocation) {
        if (StringUtils.isEmpty(resourceLocation)) {
            logger.error("ResourceLocation must not be null.");
            return null;
        }
        if (resourceLocation.startsWith(CLASSPATH_URL_PREFIX)) {
            String realPath = resourceLocation.substring(CLASSPATH_URL_PREFIX.length());
            if (StringUtils.isEmpty(realPath)) {
                logger.error("The classpath has not a real path.");
                return null;
            }
            ClassLoader classLoader = ClassLoaderUtils.getDefaultClassLoader();
            URL url = classLoader.getResource(realPath);
            if (url == null) {
                url = ClassLoader.getSystemResource(realPath);
            }
            if (url == null) {
                logger.error("class path resource [" + realPath + "] cannot be resolved to absolute file path because it does not exist/");
                return null;
            }
            return getFile(url);
        }
        try {
            return getFile(new URL(resourceLocation));
        } catch (MalformedURLException e) {
            // no URL -> treat as file path
            return new File(resourceLocation);
        }
    }

    private static File getFile(URL resourceUrl) {
        if (resourceUrl == null) {
            logger.error("URL must not be null.");
            return null;
        }
        try {
            return new File(toURI(resourceUrl).getSchemeSpecificPart());
        } catch (URISyntaxException e) {
            return new File(resourceUrl.getFile());
        }
    }

    public static URI toURI(URL url) throws URISyntaxException {
        return toURI(url.toString());
    }

    /**
     * 创建{@linkplain URI}对象，空格会被替换为"%20"
     *
     * @param location 资源路径
     * @return {@linkplain URI}
     * @throws URISyntaxException 无效的路径描述
     */
    public static URI toURI(String location) throws URISyntaxException {
        return new URI(StringUtils.replace(location, " ", "%20"));
    }
}
