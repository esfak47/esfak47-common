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
package com.esfak47.common.json.gson;

import com.esfak47.common.io.ArrayUtils;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * <p>
 * 属性过滤性
 * </p>
 *
 * @author tony on 2016/7/6.
 */
class PropertyNameExclusionStrategy implements ExclusionStrategy {

    private final String[] ignorePropertyNames;

    PropertyNameExclusionStrategy(String[] ignorePropertyNames) {
        this.ignorePropertyNames = ignorePropertyNames;
    }

    /**
     * @param fieldAttributes the field object that is under test
     * @return true if the field should be ignored; otherwise false
     */
    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        return this.ignorePropertyNames != null && ArrayUtils.containsElement(ignorePropertyNames, fieldAttributes.getName());
    }

    /**
     * @param clazz the class object that is under test
     * @return true if the class should be ignored; otherwise false
     */
    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
