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

import com.esfak47.common.lang.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * <p> URL资源加载 </p>
 *
 * @author tony on 2016/5/6.
 */
public class UrlResourceDescriptor extends AbstractResourceDescriptor {

    private final URL url;

    public UrlResourceDescriptor(URL url) {
        Assert.notNull(url, "URL must not be null");
        this.url = url;
    }

    public UrlResourceDescriptor(String path) throws MalformedURLException {
        Assert.hasLength(path, "Path must not be null");
        this.url = new URL(path);
    }

    /**
     * 获取输入流，该输入流支持多次读取
     */
    @Override
    public InputStream getInputStream() {
        URLConnection con = null;
        try {
            con = this.url.openConnection();
            return con.getInputStream();
        } catch (IOException e) {
            if (con instanceof HttpURLConnection) {
                ((HttpURLConnection)con).disconnect();
            }
        }
        return null;
    }

    /**
     * 返回{@code URL}资源
     *
     * @throws IOException I/O异常
     */
    @Override
    public URL getURL() throws IOException {
        return this.url;
    }

    /**
     * Return a description for this resource, to be used for error output when working with the resource.
     * <p>Implementations are also encouraged to return this value from their {@code toString} method.
     *
     * @see Object#toString()
     */
    @Override
    public String getDescription() {
        return "URL [" + this.url + "]";
    }
}
