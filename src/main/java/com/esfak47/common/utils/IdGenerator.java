package com.esfak47.common.utils;

import com.esfak47.common.extension.ExtensionLoader;
import com.esfak47.common.extension.SPI;

import java.util.UUID;

@SPI
public interface IdGenerator {

    static String generateIdWithProvider(String providerName) {
        return generateIdWithProvider(providerName, ClassLoaderUtils.getDefaultClassLoader());
    }

    static UUID generateUUIDWithProvider(String providerName) {
        return generateUUIDWithProvider(providerName, ClassLoaderUtils.getDefaultClassLoader());
    }

    static UUID generateUUIDWithProvider(String providerName, ClassLoader classLoader) {
        final IdGenerator extension;
        extension = ExtensionLoader
            .getExtensionLoader(IdGenerator.class, classLoader)
            .getExtension(providerName);
        return extension.generateUUID();
    }

    static String generateIdWithProvider(String providerName, ClassLoader classLoader) {
        final IdGenerator extension = ExtensionLoader
            .getExtensionLoader(IdGenerator.class, classLoader)
            .getExtension(providerName);
        return extension.generateId();
    }

    /**
     * Generate a new identifier.
     *
     * @return the generated identifier
     */
    default String generateId() {
        return StringUtils.replace(generateUUID().toString(), "-", "");
    }

    UUID generateUUID();

}