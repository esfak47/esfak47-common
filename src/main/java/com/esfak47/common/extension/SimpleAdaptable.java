package com.esfak47.common.extension;

/**
 * @author tony
 * @date 2018/4/25
 */
public interface SimpleAdaptable {

    default URL getUrl() {
        return new URL(getAdaptableKey(), null, 0);
    }

    String getAdaptableKey();
}
