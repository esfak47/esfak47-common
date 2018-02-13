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
package com.esfak47.common.digest;



import com.esfak47.common.exception.DigestException;
import com.esfak47.common.lang.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 摘要计算工具类，提供了MD5,SHA-1,SHA-256,SHA-384,SHA-512等常用摘要算法方法
 *
 * @author tony on 2016-04-16
 */
public final class DigestUtils {
    private DigestUtils(){}

    public static final String ALGORITHM_MD5 = "MD5";
    public static final String ALGORITHM_SHA_1 = "SHA-1";
    public static final String ALGORITHM_SHA_256 = "SHA-256";
    public static final String ALGORITHM_SHA_384 = "SHA-384";
    public static final String ALGORITHM_SHA_512 = "SHA-512";
    public static final String THE_DATA_IS_NULL_OR_EMPTY = "The data is null or empty.";
    public static final String THE_INPUT_STREAM_DATA_IS_NULL = "The inputStream data is null.";
    public static final String ENCODING_IS_NULL = "Encoding is null.";


    /**
     * 根据算法名称返回{@code MessageDigest}实例
     *
     * @param algorithm 摘要算法名称
     * @return 返回摘要实例
     * @see MessageDigest#getInstance(String)
     */
    private static MessageDigest getMessageDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            // safe to swallow
            throw new DigestException(e);
        }
    }

    /**
     * Returns an MD5 MessageDigest.
     *
     * @return An MD5 digest instance.
     */
    static MessageDigest getMd5Digest() {
        return getMessageDigest(ALGORITHM_MD5);
    }

    /**
     * Returns a SHA-1 MessageDigest.
     *
     * @return A SHA-1 digest instance.
     */
    private static MessageDigest getSha1Digest() {
        return getMessageDigest(ALGORITHM_SHA_1);
    }

    /**
     * Returns a SHA-256 MessageDigest.
     *
     * @return A SHA-1256 digest instance.
     */
    private static MessageDigest getSha256Digest() {
        return getMessageDigest(ALGORITHM_SHA_256);
    }

    /**
     * Returns a SHA-384 MessageDigest.
     *
     * @return A SHA-384 digest instance.
     */
    private static MessageDigest getSha384Digest() {
        return getMessageDigest(ALGORITHM_SHA_384);
    }

    /**
     * Returns a SHA-512 MessageDigest.
     *
     * @return A SHA-512 digest instance.
     */
    private static MessageDigest getSha512Digest() {
        return getMessageDigest(ALGORITHM_SHA_512);
    }

    //region-----------------------SHA1-----------------------//

    /**
     * SHA1摘要计算
     *
     * @param data 待处理的数据
     * @return 计算后的数据
     */
    public static byte[] sha1(final byte[] data) {
        Assert.notEmpty(data, THE_DATA_IS_NULL_OR_EMPTY);
        return getSha1Digest().digest(data);
    }

    /**
     * SHA1摘要计算
     *
     * @param data 待处理的数据
     * @return 计算后的数据
     */
    public static byte[] sha1(final InputStream data) {
        Assert.notNull(data, THE_INPUT_STREAM_DATA_IS_NULL);
        try {
            return digest(getSha1Digest(), data);
        } catch (IOException e) {
            throw new DigestException(e);
        }
    }

    /**
     * SHA1摘要计算,计算结果转为16进制字符串返回
     *
     * @param data 待处理的数据
     * @return 16进制的字符串
     */
    public static String sha1Hex(final String data) {
        return sha1Hex(data, StandardCharsets.UTF_8);
    }

    /**
     * SHA1摘要计算,计算结果转为16进制字符串返回
     *
     * @param data     待处理的数据
     * @param encoding 字符串编码
     * @return 16进制的字符串
     */
    public static String sha1Hex(final String data, final Charset encoding) {
        Assert.hasLength(data, THE_DATA_IS_NULL_OR_EMPTY);
        Assert.notNull(encoding, "The encoding is null.");
        return sha1Hex(data.getBytes(encoding));
    }

    /**
     * SHA1摘要计算,计算结果转为16进制字符串返回
     *
     * @param data 待处理的数据
     * @return 16进制的字符串
     */
    public static String sha1Hex(final byte[] data) {
        return Hex.encode2String(sha1(data));
    }

    /**
     * SHA1摘要计算,计算结果转为16进制字符串返回
     *
     * @param data 待处理的数据
     * @return 16进制的字符串
     */
    public static String sha1Hex(final InputStream data) {
        return Hex.encode2String(sha1(data));
    }
    //endregion-----------------------SHA1-----------------------//

    //region-----------------------SHA256-----------------------//

    /**
     * SHA256摘要计算
     *
     * @param data 待处理的数据
     * @return 计算后的数据
     */
    public static byte[] sha256(final String data) {
        Assert.hasLength(data, THE_DATA_IS_NULL_OR_EMPTY);
        return sha256(data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * SHA256摘要计算
     *
     * @param data 待处理的数据
     * @return 计算后的数据
     */
    public static byte[] sha256(final byte[] data) {
        Assert.notEmpty(data, THE_DATA_IS_NULL_OR_EMPTY);
        return getSha256Digest().digest(data);
    }

    /**
     * SHA256摘要计算
     *
     * @param data 待处理的数据
     * @return 计算后的数据
     */
    public static byte[] sha256(final InputStream data) {
        Assert.notNull(data, THE_INPUT_STREAM_DATA_IS_NULL);
        try {
            return digest(getSha256Digest(), data);
        } catch (IOException e) {
            throw new DigestException(e);
        }
    }

    /**
     * SHA256摘要计算,计算结果转为16进制字符串返回
     *
     * @param data 待处理的数据
     * @return 16进制的字符串
     */
    public static String sha256Hex(final String data) {
        return Hex.encode2String(sha256(data));
    }

    /**
     * SHA256摘要计算,计算结果转为16进制字符串返回
     *
     * @param data     待处理的数据
     * @param encoding 字符串编码
     * @return 16进制的字符串
     */
    public static String sha256Hex(final String data, final Charset encoding) {
        Assert.hasLength(data, THE_DATA_IS_NULL_OR_EMPTY);
        Assert.notNull(encoding, ENCODING_IS_NULL);
        return sha256Hex(data.getBytes(encoding));
    }

    /**
     * SHA256摘要计算,计算结果转为16进制字符串返回
     * x
     *
     * @param data 待处理的数据
     * @return 16进制的字符串
     */
    public static String sha256Hex(final byte[] data) {
        return Hex.encode2String(sha256(data));
    }

    /**
     * SHA256摘要计算,计算结果转为16进制字符串返回
     *
     * @param data 待处理的数据
     * @return 16进制的字符串
     */
    public static String sha256Hex(final InputStream data) {
        return Hex.encode2String(sha256(data));
    }
    //endregion-----------------------SHA256-----------------------//

    //region-----------------------SHA384-----------------------//

    /**
     * SHA384摘要计算
     *
     * @param data 待处理的数据
     * @return 计算后的数据
     * @see #sha384(String, Charset)
     */
    public static byte[] sha384(final String data) {
        return sha384(data, StandardCharsets.UTF_8);
    }

    /**
     * SHA384摘要计算
     *
     * @param data     待处理的数据
     * @param encoding 字符串编码
     * @return 计算后的数据
     */
    public static byte[] sha384(final String data, final Charset encoding) {
        Assert.notNull(encoding, ENCODING_IS_NULL);
        Assert.hasLength(data, THE_DATA_IS_NULL_OR_EMPTY);
        return sha384(data.getBytes(encoding));
    }

    /**
     * SHA384摘要计算
     *
     * @param data 待处理的数据
     * @return 计算后的数据
     */
    public static byte[] sha384(final byte[] data) {
        Assert.notEmpty(data, THE_DATA_IS_NULL_OR_EMPTY);
        return getSha384Digest().digest(data);
    }

    /**
     * SHA384摘要计算
     *
     * @param data 待处理的数据
     * @return 计算后的数据
     */
    public static byte[] sha384(final InputStream data) {
        Assert.notNull(data, THE_INPUT_STREAM_DATA_IS_NULL);
        try {
            return digest(getSha384Digest(), data);
        } catch (IOException e) {
            throw new DigestException(e);
        }
    }

    /**
     * SHA384摘要计算,计算结果转为16进制字符串返回
     *
     * @param data 待处理的数据
     * @return 16进制的字符串
     */
    public static String sha384Hex(final String data) {
        return Hex.encode2String(sha384(data));
    }

    /**
     * SHA384摘要计算,计算结果转为16进制字符串返回
     *
     * @param data     待处理的数据
     * @param encoding 字符串编码
     * @return 16进制的字符串
     */
    public static String sha384Hex(final String data, final Charset encoding) {
        return Hex.encode2String(sha384(data, encoding));
    }

    /**
     * SHA384摘要计算,计算结果转为16进制字符串返回
     *
     * @param data 待处理的数据
     * @return 16进制的字符串
     */
    public static String sha384Hex(final byte[] data) {
        return Hex.encode2String(sha384(data));
    }

    /**
     * SHA384摘要计算,计算结果转为16进制字符串返回
     *
     * @param data 待处理的数据
     * @return 16进制的字符串
     */
    public static String sha384Hex(final InputStream data) {
        return Hex.encode2String(sha384(data));
    }
    //endregion-----------------------SHA384-----------------------//

    //region-----------------------SHA512-----------------------//

    /**
     * SHA512摘要计算
     *
     * @param data 待处理的数据
     * @return 计算后的数据
     * @see #sha512(String, Charset)
     */
    public static byte[] sha512(final String data) {
        return sha512(data, StandardCharsets.UTF_8);
    }

    /**
     * SHA512摘要计算
     *
     * @param data     待处理的数据
     * @param encoding 字符串编码
     * @return 计算后的数据
     */
    public static byte[] sha512(final String data, final Charset encoding) {
        Assert.notNull(encoding, ENCODING_IS_NULL);
        Assert.hasLength(data, THE_DATA_IS_NULL_OR_EMPTY);
        return sha512(data.getBytes(encoding));
    }

    /**
     * SHA512摘要计算
     *
     * @param data 待处理的数据
     * @return 计算后的数据
     */
    public static byte[] sha512(final byte[] data) {
        Assert.notEmpty(data, THE_DATA_IS_NULL_OR_EMPTY);
        return getSha512Digest().digest(data);
    }

    /**
     * SHA512摘要计算
     *
     * @param data 待处理的数据
     * @return 计算后的数据
     */
    public static byte[] sha512(final InputStream data) {
        Assert.notNull(data, THE_INPUT_STREAM_DATA_IS_NULL);
        try {
            return digest(getSha512Digest(), data);
        } catch (IOException e) {
            throw new DigestException(e);
        }
    }

    /**
     * SHA512摘要计算,计算结果转为16进制字符串返回
     *
     * @param data 待处理的数据
     * @return 16进制的字符串
     */
    public static String sha512Hex(final String data) {
        return Hex.encode2String(sha512(data));
    }

    /**
     * SHA512摘要计算,结果16进制字符串输出
     *
     * @param data     待处理的数据
     * @param encoding 字符串编码
     * @return 16进制的字符串
     */
    public static String sha512Hex(final String data, final Charset encoding) {
        return Hex.encode2String(sha512(data, encoding));
    }

    /**
     * SHA512摘要计算,结果16进制字符串输出
     *
     * @param data 待处理的数据
     * @return 16进制的字符串
     */
    public static String sha512Hex(final byte[] data) {
        return Hex.encode2String(sha512(data));
    }

    /**
     * SHA512摘要计算,结果16进制字符串输出
     *
     * @param data 待处理的数据
     * @return 16进制的字符串
     */
    public static String sha512Hex(final InputStream data) {
        return Hex.encode2String(sha512(data));
    }
    //endregion-----------------------SHA512-----------------------//

    /**
     * 将输入流的数据分段计算
     *
     * @param digest 某种摘要算法
     * @param data   待计算的数据
     * @return 返回计算后的结果
     * @throws IOException IO异常
     */
    static byte[] digest(final MessageDigest digest, final InputStream data) throws IOException {
        byte[] buffer = new byte[1024];
        for (int read; (read = data.read(buffer, 0, 1024)) != -1; ) {
            digest.update(buffer, 0, read);
        }
        return digest.digest();
    }
}
