package com.esfak47.common.extension;

/**
 * @author tony
 * @date 2018/4/25
 */
public interface SimpleAdaptable {

    default URL getUrl(){
        return new URL(null,null,0,new String[]{"adaptableKey",getAdaptableKey()});
    }

    String getAdaptableKey();
}
