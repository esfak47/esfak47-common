package com.esfak47.common.lang;

import java.io.Serializable;

public interface CodeMessageProvider extends Serializable {
    /**
     * 返回结果编号
     * @return 结果标号
     */
    int getCode();

    /**
     * 返回结果详细信息
     * @return 结果详细信息
     */
    String getMessage();



}