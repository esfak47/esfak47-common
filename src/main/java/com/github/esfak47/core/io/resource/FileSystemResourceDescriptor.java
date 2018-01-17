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
package com.github.esfak47.core.io.resource;



import com.github.esfak47.core.utils.StringUtils;

import java.io.*;
import java.net.URI;
import java.net.URL;

/**
 * 文件系统的资源描述
 *
 * @author mzlion on 2016/4/11.
 */
/*public*/ class FileSystemResourceDescriptor extends AbstractResourceDescriptor {

    private final File file;

    private final String path;

    public FileSystemResourceDescriptor(File file) {
        if (file == null) {
            throw new IllegalArgumentException("File must not be null.");
        }
        this.file = file;
        this.path = file.getPath();
    }

    public FileSystemResourceDescriptor(String path) {
        if (StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException("Path must not be null.");
        }
        this.path = path;
        this.file = new File(path);
    }

    public final String getPath() {
        return path;
    }

    /**
     * 判断资源是否存在
     */
    @Override
    public boolean exists() {
        return file.exists();
    }

    /**
     * 获取输入流，该输入流支持多次读取
     */
    @Override
    public InputStream getInputStream() {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 返回{@code URL}资源
     *
     * @throws IOException
     */
    @Override
    public URL getURL() throws IOException {
        return this.getURI().toURL();
    }

    /**
     * 返回{@code URI}资源
     *
     * @throws IOException
     */
    @Override
    public URI getURI() throws IOException {
        return this.file.toURI();
    }

    /**
     * 返回文件对象，如果对象不存在则返回{@code null}
     */
    @Override
    public File getFile() {
        return this.file;
    }

    /**
     * 返回资源的内容长度，如果资源不存在则返回{@code -1}
     */
    @Override
    public long length() {
        return this.file.length();
    }

    /**
     * 返回文件名，如果资源不存在，则返回{@code null}
     */
    @Override
    public String getFilename() {
        return this.file.getName();
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
        return String.format("file [%s]", this.file.getAbsolutePath());
    }
}
