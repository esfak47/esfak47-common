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


import com.esfak47.common.utils.ResourceUtils;

/**
 * <p>
 * 资源描述加载接口
 * </p>
 *
 * @author tony on 2016/5/6.
 */
public interface ResourceDescriptorLoader {

    String CLASSPATH_URL_PREFIX = ResourceUtils.CLASSPATH_URL_PREFIX;

    /**
     * 根据路径获得资源描述符
     * @param location
     * @return
     */
    ResourceDescriptor getResourceDescriptor(String location);

    /**
     * 获得classloader
     * @return
     */
    ClassLoader getClassLoader();

}
