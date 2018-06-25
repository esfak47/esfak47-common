package com.esfak47.common.utils;

import com.esfak47.common.extension.ExtensionLoader;
import com.esfak47.common.extension.SPI;

import java.util.UUID;

@SPI
public interface IdGenerator {

    static UUID generateIdWithProvider(String providerName) {
        final IdGenerator extension = ExtensionLoader
            .getExtensionLoader(IdGenerator.class)
            .getExtension(providerName);
        return extension.generateId();
    }

    static UUID generateIdWithProvider(String providerName, ClassLoader classLoader) {
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
    UUID generateId();

}