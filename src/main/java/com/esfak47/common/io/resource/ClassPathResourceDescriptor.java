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
package com.esfak47.common.io.resource;


import com.esfak47.common.io.FilenameUtils;
import com.esfak47.common.utils.ClassLoaderUtils;
import com.esfak47.common.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Classpath的资源描述
 *
 * @author mzlion on 2016/4/11.
 */
public class ClassPathResourceDescriptor extends AbstractResourceDescriptor {


    private final String path;

    private ClassLoader classLoader;

    private Class<?> clazz;

    public ClassPathResourceDescriptor(String path) {
        this(path, (ClassLoader) null);
    }

    public ClassPathResourceDescriptor(String path, ClassLoader classLoader) {
        if (StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException("Path must not be null.");
        }
        this.path = path;
        this.classLoader = (classLoader == null) ? ClassLoaderUtils.getDefaultClassLoader() : classLoader;
    }

    public ClassPathResourceDescriptor(String path, Class<?> clazz) {
        if (StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException("Path must not be null.");
        }
        this.path = path;
        this.clazz = clazz;
    }

    public ClassPathResourceDescriptor(String path, ClassLoader classLoader, Class<?> clazz) {
        if (StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException("Path must not be null.");
        }
        this.path = path;
        this.classLoader = classLoader;
        this.clazz = clazz;
    }

    public final String getPath() {
        return path;
    }

    /**
     * 判断资源是否存在
     */
    @Override
    public boolean exists() {
        URL url;
        if (this.clazz != null) {
            url = this.clazz.getResource(this.path);
        } else {
            url = this.classLoader.getResource(this.path);
        }
        return url != null;
    }

    /**
     * 获取输入流，该输入流支持多次读取
     */
    @Override
    public InputStream getInputStream() {
        InputStream in;
        if (this.clazz != null) {
            in = this.clazz.getResourceAsStream(this.path);
        } else {
            in = this.classLoader.getResourceAsStream(this.path);
        }
        return in;
    }

    /**
     * 返回{@code URL}资源
     *
     * @throws IOException IO异常
     */
    @Override
    public URL getURL() throws IOException {
        URL url;
        if (this.clazz != null) {
            url = this.clazz.getResource(this.path);
        } else {
            url = this.classLoader.getResource(this.path);
        }
        return url;
    }

    /**
     * 返回文件名，如果资源不存在，则返回{@code null}
     */
    @Override
    public String getFilename() {
        return FilenameUtils.getFilename(this.path);
    }

    /**
     * 返回文件对象，如果对象不存在则返回{@code null}
     *
     * @return 文件对象
     */
    @Override
    public File getFile() throws IOException {
        URL url = this.getURL();
        return new File(url.getFile());
    }

    /**
     * Return a description for this resource,
     * to be used for error output when working with the resource.
     * <p>Implementations are also encouraged to return this value
     * from their {@code toString} method.
     *
     * @see Object#toString()
     */
    @Override
    public String getDescription() {
        StringBuilder builder = new StringBuilder("class path resource [");
        String pathToUse = path;
        if (this.clazz != null && !pathToUse.startsWith("/")) {
            //builder.append(ClassUtils.classPackageAsResourcePath(this.clazz));
            builder.append('/');
        }
        if (pathToUse.startsWith("/")) {
            pathToUse = pathToUse.substring(1);
        }
        builder.append(pathToUse);
        builder.append(']');
        return builder.toString();
    }
}
