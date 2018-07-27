package com.esfak47.common.extension.factory;

import com.esfak47.common.extension.ExtensionFactory;

/**
 * @author tony
 * @date 2018/7/26
 */
public class JdkSpiExtensionFactory implements ExtensionFactory {
    @Override
    public <T> T getExtension(Class<T> type, String name) {
        return SpiLoader.load(type,name);
    }
}
