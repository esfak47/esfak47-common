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

import com.esfak47.common.io.IOUtils;
import com.esfak47.common.utils.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * {@linkplain ResourceDescriptor}抽象实现
 *
 * @author tony on 2016/4/11.
 */
public abstract class AbstractResourceDescriptor implements ResourceDescriptor {

    /**
     * 判断资源是否存在
     */
    @Override
    public boolean exists() {
        File file;
        try {
            file = this.getFile();
        } catch (IOException e) {
            return false;
        }
        if (file == null) {
            try {
                InputStream in = this.getInputStream();
                in.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        }
        return file.exists();
    }

    /**
     * 返回{@code URL}资源
     *
     * @throws IOException I/O异常
     */
    @Override
    public URL getURL() throws IOException {
        throw new FileNotFoundException(this.getDescription() + " cannot be resolved to URL");
    }

    /**
     * 返回{@code URI}资源
     *
     * @throws IOException I/O异常
     */
    @Override
    public URI getURI() throws IOException {
        URL url = this.getURL();
        try {
            return ResourceUtils.toURI(url);
        } catch (URISyntaxException e) {
            throw new IOException("Invalid URI [" + url + "]", e);
        }
    }

    /**
     * 返回文件对象，如果对象不存在则返回{@code null}
     *
     * @return 文件对象
     */
    @Override
    public File getFile() throws IOException {
        return null;
    }

    /**
     * 返回资源的内容长度，如果资源不存在则返回{@code -1}
     *
     * @return 文件资源内容长度
     */
    @Override
    public long length() {
        InputStream in = this.getInputStream();
        if (in == null) {
            return -1;
        }
        long size = 0;
        byte[] buffer = new byte[1024];
        int read;
        try {
            while ((read = in.read(buffer)) != -1) {
                size += read;
            }
            return size;
        } catch (IOException e) {
            return -1;
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    /**
     * 返回文件名，如果资源不存在，则返回{@code null}
     *
     * @return 文件名
     */
    @Override
    public String getFilename() {
        return null;
    }

    @Override
    public String toString() {
        return this.getDescription();
    }
}
