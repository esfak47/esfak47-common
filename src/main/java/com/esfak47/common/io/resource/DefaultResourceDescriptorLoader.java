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
import com.esfak47.common.utils.ClassLoaderUtils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 默认资源载入处理器
 *
 * @author tony on 2016/5/6.
 */
public class DefaultResourceDescriptorLoader implements ResourceDescriptorLoader {

    private ClassLoader classLoader;

    public DefaultResourceDescriptorLoader() {
        this(ClassLoaderUtils.getDefaultClassLoader());
    }

    public DefaultResourceDescriptorLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public ResourceDescriptor getResourceDescriptor(String location) {
        Assert.hasLength(location, "Location must not be null");
        if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            return new ClassPathResourceDescriptor(location.substring(CLASSPATH_URL_PREFIX.length()), getClassLoader());
        } else {
            try {
                // Try to parse the location as a URL...
                URL url = new URL(location);
                return new UrlResourceDescriptor(url);
            } catch (MalformedURLException ex) {
                return new FileSystemResourceDescriptor(location);
            }
        }
    }

    @Override
    public ClassLoader getClassLoader() {
        return (this.classLoader != null ? this.classLoader : ClassLoaderUtils.getDefaultClassLoader());
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
