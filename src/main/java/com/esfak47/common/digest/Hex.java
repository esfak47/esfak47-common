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



import com.esfak47.common.exception.DecodeException;
import com.esfak47.common.lang.Assert;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 将字节数组或字符数组转为16进制字符串
 *
 * @author tony on 2016-06-05 19:15
 */
public class Hex {

    private Hex() {
        throw new UnsupportedOperationException();
    }

    /**
     * Used to build output as Hex
     */
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * Used to build output as Hex
     */
    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};


    /**
     * 将字节数组转为16进制字符串
     *
     * @param data 字节数组内容
     * @return 16进制字符串
     */
    public static String encode2String(final byte[] data) {
        return new String(encode(data));
    }

    /**
     * 将字节数组转为字符数组
     *
     * @param data 字节数组
     * @return 字符数组
     */
    public static char[] encode(final byte[] data) {
        return encode(data, true);
    }

    /**
     * 将字节转为字符数组
     *
     * @param data        字节数组内容
     * @param toLowerCase {@code true}表示转小写，否则转大写
     * @return 字符数组
     */
    public static char[] encode(final byte[] data, final boolean toLowerCase) {
        return doEncode(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    /**
     * 解码,转为原始字符串是采用{@code UTF-8}编码
     *
     * @param data 16进制的字符串
     * @return 原始字符串内容
     */
    public static String decode2String(final String data) {
        return decode2String(data, StandardCharsets.UTF_8);
    }

    /**
     * 解码,转为原始字符串是采用{@code encoding}编码
     *
     * @param data     16进制的字符串
     * @param encoding 编码
     * @return 原始字符串内容
     */
    public static String decode2String(final String data, final Charset encoding) {
        byte[] decode = decode(data);
        return new String(decode, encoding);
    }

    /**
     * 解码
     *
     * @param data 16进制的字符串
     * @return 字节数组
     */
    public static byte[] decode(final String data) {
        Assert.hasLength(data, "Data must not be null.");
        return decode(data.toCharArray());
    }

    /**
     * 解码
     *
     * @param data 16进制的字符数组
     * @return 字节数组
     */
    public static byte[] decode(final char[] data) {

        return doDecode(data);
    }

    /**
     * 编码：将字节数组转为16进制的字符数组
     *
     * @param data   字节数组内容
     * @param digits 转码表
     * @return 16进制的字符数组
     */
    private static char[] doEncode(final byte[] data, final char[] digits) {
        final int length = data.length;
        final char[] out = new char[length << 1];
        //byte转hex原理：一个byte转成2个16进制
        for (int i = 0, j = 0; i < length; i++) {
            out[j++] = digits[(0xF0 & data[i]) >>> 4];
            out[j++] = digits[0x0F & data[i]];
        }
        return out;
    }

    /**
     * 解码：将16进制转为字节数组
     *
     * @param data 16进制的字符数组
     * @return 字节数组
     */
    private static byte[] doDecode(final char[] data) {
        final int length = data.length;
        if (isOdd(length)) {
            throw new DecodeException("Odd number of characters.");
        }

        final byte[] out = new byte[length >> 1];
        //hex转byte原理：两个hex得一个byte
        int temp;
        for (int i = 0, j = 0; j < length; i++) {
            temp = toDigit(data[j], j) << 4;
            j++;
            temp = temp | toDigit(data[j], j);
            j++;
            out[i] = (byte) (temp & 0XFF);
        }
        return out;
    }

    private static boolean isOdd(int data) {
        return (data & 0x01) != 0;
    }

    /**
     * 16进制转10进制
     *
     * @param ch    16进制的字符
     * @param index 位置
     * @return 10进制数字
     */
    private static int toDigit(final char ch, int index) {
        final int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new DecodeException("Illegal hexadecimal character " + ch + " at index " + index);
        }
        return digit;
    }

}
