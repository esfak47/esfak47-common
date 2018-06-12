package com.esfak47.common.extend;

import com.esfak47.common.extension.SimpleAdaptable;

/**
 * @author tony
 * @date 2018/4/25
 */
public class Parameter implements SimpleAdaptable {

    private String type;

    public Parameter(String type) {
        this.type = type;
    }

    @Override
    public String getAdaptableKey() {
        return type;
    }
}
