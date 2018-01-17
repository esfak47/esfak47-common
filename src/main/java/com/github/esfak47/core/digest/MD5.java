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
package com.github.esfak47.core.digest;

import com.github.esfak47.core.exception.DigestException;
import com.github.esfak47.core.lang.Assert;


import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * MD5摘要计算
 *
 * @author mzlion on 2016/12/1.
 */
public class MD5 {

    /**
     * md5计算
     *
     * @param data 待计算的数据
     * @return md5计算结果
     */
    public static byte[] digest(final byte[] data) {
        Assert.notEmpty(data, "The data is null or empty.");
        return DigestUtils.getMd5Digest().digest(data);
    }

    /**
     * 将输入流的数据进行MD5计算
     *
     * @param data 待计算的数据
     * @return md5计算结果
     */
    public static byte[] digest(final InputStream data) {
        Assert.notNull(data, "The data is null.");
        try {
            return DigestUtils.digest(DigestUtils.getMd5Digest(), data);
        } catch (IOException e) {
            throw new DigestException(e);
        }
    }

    /**
     * 计算字符串的md5值，结果转为16进制的字符串返回,默认采用{@link StandardCharsets#UTF_8}编码
     *
     * @param data 待计算的数据
     * @return 16进制的字符串
     */
    public static String digestHex(final String data) {
        return digestHex(data, StandardCharsets.UTF_8);
    }

    /**
     * 计算字符串的md5值，结果转为16进制的字符串返回
     *
     * @param data    待计算的数据
     * @param charset 指定编码，为空时采用UTF8读取
     * @return 16进制的字符串
     */
    public static String digestHex(final String data, final Charset charset) {
        Assert.hasLength(data, "The data is null or empty.");
        byte[] salt = data.getBytes(charset == null ? StandardCharsets.UTF_8 : charset);
        byte[] md5Data = digest(salt);
        return Hex.encode2String(md5Data);
    }

    /**
     * 计算的流的md5值，结果转为16进制的字符串返回
     *
     * @param data 待计算的数据
     * @return 16进制的字符串
     */
    public static String digestHex(final InputStream data) {
        return Hex.encode2String(digest(data));
    }

    /**
     * 计算字符串(默认UTF8)的MD5值，将结果转为BASE64输出
     *
     * @param data 待计算的数据
     * @return BASE64的字符串
     */
    public static String digestBase64(final String data) {
        return digestBase64(data, StandardCharsets.UTF_8);
    }

    /**
     * 计算字符串的MD5值，将结果转为BASE64输出
     *
     * @param data    待计算的数据
     * @param charset 字符串编码
     * @return BASE64的字符串
     */
    public static String digestBase64(final String data, final Charset charset) {
        Assert.hasLength(data, "The data is null or empty.");
        byte[] salt = data.getBytes(charset == null ? StandardCharsets.UTF_8 : charset);
        byte[] md5Data = digest(salt);
        return Base64.encode(md5Data);
    }
}
