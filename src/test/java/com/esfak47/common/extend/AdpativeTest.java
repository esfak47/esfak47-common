package com.esfak47.common.extend;

import com.esfak47.common.extension.SimpleAdaptive;
import com.esfak47.common.extension.URL;
import com.esfak47.common.extension.Adaptive;
import com.esfak47.common.extension.SPI;

/**
 * @author tony
 * @date 2018/4/25
 */
@SPI
public interface AdpativeTest {

    @Adaptive(value = "name")
    String adaptive(URL url);

    @Adaptive(value = "protocol")
    String method(URL url);

    @Adaptive(value = "protocol")
    String method(Parameter url);
    @SimpleAdaptive
    String method2(Parameter url);

}
