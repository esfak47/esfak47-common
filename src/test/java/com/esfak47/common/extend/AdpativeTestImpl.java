package com.esfak47.common.extend;

import com.esfak47.common.extension.URL;

/**
 * @author tony
 * @date 2018/4/25
 */

public class AdpativeTestImpl implements AdpativeTest {

    @Override
    public String adaptive(URL url) {
        return this.getClass().getSimpleName();
    }


    public String method(URL url) {
        return this.getClass().getSimpleName();
    }

    @Override
    public String method(Parameter url) {
        return this.getClass().getSimpleName();
    }

    @Override
    public String method2(Parameter url) {
        return this.getClass().getSimpleName();
    }
}
