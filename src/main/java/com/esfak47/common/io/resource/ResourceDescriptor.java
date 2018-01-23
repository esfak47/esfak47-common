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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

/**
 * 文件或classpath的资源描述接口
 *
 * @author mzlion on 2016/4/11.
 */
public interface ResourceDescriptor {

    /**
     * 判断资源是否存在
     *
     * @return 存在则返回{@code true}，否则返回{@code false}
     */
    boolean exists();

    /**
     * 返回{@code URL}资源
     *
     * @return URL资源信息
     * @throws IOException I/O异常
     */
    URL getURL() throws IOException;

    /**
     * 返回{@code URI}资源
     *
     * @return URI资源信息
     * @throws IOException I/O异常
     */
    URI getURI() throws IOException;

    /**
     * 返回文件对象，如果对象不存在则返回{@code null}
     *
     * @return 返回文件对象
     * @throws IOException IO异常
     */
    File getFile() throws IOException;

    /**
     * 返回资源的内容长度，如果资源不存在则返回{@code -1}
     *
     * @return 返回资源长度
     */
    long length();

    /**
     * 返回文件名，如果资源不存在，则返回{@code null}
     *
     * @return 返回文件名
     */
    String getFilename();

    /**
     * 获取输入流，该输入流支持多次读取
     *
     * @return {@link InputStream}
     */
    InputStream getInputStream();


    /**
     * Return a description for this resource,
     * to be used for error output when working with the resource.
     * <p>
     * Implementations are also encouraged to return this value from their {@code toString} method.
     * </p>
     *
     * @return 返回资源的描述信息
     * @see Object#toString()
     */
    String getDescription();

}
