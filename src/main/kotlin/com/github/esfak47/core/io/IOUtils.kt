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

package com.github.esfak47.core.io

import com.github.esfak47.core.exception.StreamException
import com.github.esfak47.core.lang.Assert
import com.github.esfak47.core.utils.StringUtils
import org.slf4j.LoggerFactory
import java.io.*
import java.nio.charset.Charset
import java.util.*
import com.github.esfak47.core.digest.DigestUtils
import com.github.esfak47.core.digest.MD5
import java.io.FileInputStream





/**
 * IO流工具类,本工具类提供的方法都不会刷新或关闭流，所以需要调用者自己手动关闭。
 * 该工具类的部分实现参照了`commons-io`框架提供的方法。
 *
 * @author mzlion on 2016-04-11
 */
object IOUtils {
    private val logger = LoggerFactory.getLogger(IOUtils::class.java)

    /**
     * 文件结束标记
     */
    private val EOF = -1

    /**
     * The default buffer size ({@value}) to use for copy large file.
     */
    private val DEFAULT_BUFFER_SIZE = 1024 * 4

    /**
     * 关闭`Closeable`,该方法等效于[Closeable.close]
     *
     *
     * 该方法主要用于finally块中，并且忽略所有的异常
     *
     * Example code:
     * <pre>
     * Closeable closeable = null;
     * try {
     * closeable = new FileReader("foo.txt");
     * // process closeable
     * closeable.close();
     * } catch (Exception e) {
     * // error handling
     * } finally {
     * IOUtils.closeQuietly(closeable);
     * }
    </pre> *
     *
     * @param closeable the object to close, may be null or already closed
     */
    @JvmStatic fun closeQuietly(closeable: Closeable?) {
        if (closeable != null) {
            try {
                closeable.close()
            } catch (e: IOException) {
                // ignore
            }

        }
    }

    /**
     * 将输入流转为字节数组
     *
     * @param in 输入流
     * @return 如果转换成功则返回字节数组，否则返回`null`
     */
    @JvmStatic fun toByteArray(`in`: InputStream): ByteArray? {
        Assert.notNull(`in`, "Input stream must not be null.")
        val out = ByteArrayOutputStream(1024)
        return if (copy(`in`, out) == -1) {
            null
        } else out.toByteArray()
    }

    /**
     * 流的md5，结果由16进制字符串返回
     *
     * @param in 输入流
     * @return md5之后的16进制字符串
     */
    @JvmStatic  fun md5Hex(`in`: InputStream): String {
        return MD5.digestHex(`in`)
    }
    /**
     * 流的SHA-1，结果由16进制字符串返回
     *
     * @param in 输入流
     * @return SHA-1之后的16进制字符串
     */
    @JvmStatic fun sha1Hex(`in`: FileInputStream): String {
        return DigestUtils.sha1Hex(`in`)
    }
    /**
     * 将`Reader`的内容转为字节数组，否转换异常则返回`null`
     *
     * @param reader   read from
     * @param encoding 编码
     * @return 转换异常时返回{@code null}，否则返回字节数组
     */
    @JvmStatic fun toByteArray(reader: Reader, encoding: String): ByteArray? {
        return toByteArray(reader, if (StringUtils.isEmpty(encoding)) Charset.defaultCharset() else Charset.forName(encoding))
    }

    /**
     * 将`Reader`的内容转为字节数组，否转换异常则返回`null`
     *
     * @param reader   read from
     * @param encoding 编码
     * @return 转换异常时返回{@code null}，否则返回字节数组
     */
    @JvmOverloads
    @JvmStatic fun toByteArray(reader: Reader, e: Charset? = Charset.defaultCharset()): ByteArray? {
        var encoding = e
        Assert.notNull(reader, "Reader must not be null.")
        if (encoding == null) {
            encoding = Charset.defaultCharset()
        }
        val out = ByteArrayOutputStream(1024)
        return if (!copy(reader, out, encoding)) {
            null
        } else out.toByteArray()
    }



    @JvmStatic fun toString(`in`: InputStream, encoding: String): String? {
        return toString(`in`, if (StringUtils.isEmpty(encoding)) Charset.defaultCharset() else Charset.forName(encoding))
    }

    @JvmOverloads
    @JvmStatic fun toString(`in`: InputStream, encoding: Charset = Charset.defaultCharset()): String? {
        Assert.notNull(`in`, "InputStream must not be null.")
        val writer = StringWriter()
        return if (!copy(`in`, writer, encoding)) {
            null
        } else writer.toString()
    }

    @JvmStatic fun toString(reader: Reader): String? {
        Assert.notNull(reader, "Reader must not be null.")
        val writer = StringWriter()
        return if (copy(reader, writer) == -1) {
            null
        } else writer.toString()
    }


    // copy from InputStream
    //-----------------------------------------------------------------------

    /**
     * 流的拷贝，超大流(超过2GB)拷贝返回的结果为-1。如果是超大流拷贝请使用[.copyLarge]
     *
     * @param in  输入流
     * @param out 输出流
     * @return 返回流大小，如果拷贝失败或流过大均返回-1
     */
    @JvmStatic fun copy(`in`: InputStream, out: OutputStream): Int {
        val count = copyLarge(`in`, out)
        return if (count > Integer.MAX_VALUE) {
            -1
        } else count.toInt()
    }

    /**
     * 流的拷贝，如果拷贝流失败则返回-1.
     *
     * @param in     输入流
     * @param out    输出流
     * @param buffer 缓冲区
     * @return 返回流大小，如果拷贝失败则返回-1
     */
    @JvmOverloads
    @JvmStatic fun copyLarge(`in`: InputStream, out: OutputStream, buffer: ByteArray = ByteArray(DEFAULT_BUFFER_SIZE)): Long {
        Assert.notNull(`in`, "InputStream must not be null.")
        Assert.notNull(out, "OutputStream must not be null.")
        Assert.notEmpty(buffer, "The buffer array must not null or empty.")
        var count: Long = 0
        var n: Int
        try {
            n = `in`.read(buffer)
            while (EOF != n) {
                out.write(buffer, 0, n)
                count += n.toLong()
                n = `in`.read(buffer)
            }
            return count
        } catch (e: IOException) {
            throw StreamException("Copy bytes from a large InputStream to an OutputStream error", e)
        }

    }

    /**
     * 将输入流的字节数组转换为`Writer`字符内容，使用系统默认编码。
     *
     * @param in       字节输入流
     * @param writer   字符输出流
     * @param encoding 字符编码，如果为空则使用平台默认编码
     * @return 拷贝成功则返回{@code true},否则返回`false`
     */
    @JvmStatic fun copy(`in`: InputStream, writer: Writer, encoding: String): Boolean {
        return copy(`in`, writer, if (StringUtils.isEmpty(encoding)) Charset.defaultCharset() else Charset.forName(encoding))
    }

    /**
     * 将输入流的字节数组转换为`Writer`字符内容，使用系统默认编码。
     *
     * @param in       字节输入流
     * @param writer   字符输出流
     * @param encoding 字符编码，如果为空则使用平台默认编码
     * @return 拷贝成功则返回{@code true},否则返回`false`
     */
    @JvmOverloads
    @JvmStatic fun copy(`in`: InputStream, writer: Writer, encoding: Charset? = Charset.defaultCharset()): Boolean {
        Assert.notNull(`in`, "Input stream must not be null.")
        val reader = InputStreamReader(`in`, encoding ?: Charset.defaultCharset())
        return copy(reader, writer) > 0
    }


    // copy from Reader
    //-----------------------------------------------------------------------

    /**
     * 将字符输入流转换为字符输出流，如果字符输入流的大小超过2GB，则返回-1
     *
     * @param reader 字符输入流
     * @param writer 字符输出流
     * @return 拷贝失败或流超过2GB则返回-1，否则返回流的大小
     */
    @JvmStatic fun copy(reader: Reader, writer: Writer): Int {
        val count = copyLarge(reader, writer)
        return if (count > Integer.MAX_VALUE) {
            -1
        } else count.toInt()
    }

    /**
     * 字符流的拷贝，支持大字符流(超过2GB)拷贝
     *
     * @param reader 字符输入流
     * @param writer 字符输出流
     * @param buffer 缓冲区
     * @return 拷贝成功则返回流的大小，否则返回-1
     */
    @JvmOverloads
    @JvmStatic fun copyLarge(reader: Reader, writer: Writer, buffer: CharArray = CharArray(DEFAULT_BUFFER_SIZE)): Long {
        var count: Long = 0
        var n: Int
        try {
            do {
                n = reader.read(buffer)
                if (EOF != n) {
                    writer.write(buffer, 0, n)
                    count += n.toLong()
                }
            } while (EOF != n)
        } catch (e: IOException) {
            logger.error(" ===> Copy bytes from a large Reader to an Writer error", e)
            return -1
        }

        return count
    }

    /**
     * 将字符输入流转为字节输出流，使用指定编码
     *
     * @param reader   字符输入流
     * @param out      字节输出流
     * @param encoding 编码
     * @return 拷贝成功则返回{@code true},否则返回`false`
     */
    @JvmStatic fun copy(reader: Reader, out: OutputStream, encoding: String): Boolean {
        return copy(reader, out, if (StringUtils.isEmpty(encoding)) Charset.defaultCharset() else Charset.forName(encoding))
    }

    /**
     * 将字符输入流转为字节输出流，使用指定编码
     *
     * @param reader   字符输入流
     * @param out      字节输出流
     * @param encoding 编码
     * @return 拷贝成功则返回{@code true},否则返回`false`
     */
    @JvmOverloads
    @JvmStatic fun copy(reader: Reader, out: OutputStream, e: Charset? = Charset.defaultCharset()): Boolean {
        var encoding = e
        Assert.notNull(reader, "Reader must not be null.")
        Assert.notNull(out, "Output stream must not be null.")
        encoding = if (encoding == null) Charset.defaultCharset() else encoding
        val writer = OutputStreamWriter(out, encoding!!)
        if (copy(reader, writer) == -1) {
            return false
        }
        try {
            // we have to flush here.
            writer.flush()
        } catch (e: IOException) {
            logger.error(" ===> Flush outputStream error", e)
            return false
        }

        return true
    }


    /**
     * 从输入流中读取
     *
     * @param in      待读取的流
     * @param charset 字符编码
     * @return 读取的内容
     */
    @JvmOverloads
    @JvmStatic fun readLines(`in`: InputStream, charset: Charset = Charset.defaultCharset()): List<String> {
        Assert.notNull(`in`, "The parameter[in] is null.")
        val reader = InputStreamReader(`in`, charset)
        return readLines(reader)
    }

    /**
     * 从流中读取内容
     *
     * @param reader 待读取的流
     * @return 读取的内容
     */
    @JvmStatic fun readLines(reader: Reader): List<String> {
        val bufferedReader = to(reader)
        val lines = ArrayList<String>()
        try {
            var line: String? = bufferedReader.readLine()
            while (line != null) {
                lines.add(line)
                line = bufferedReader.readLine()
            }
            return lines
        } catch (e: IOException) {
            throw StreamException(e)
        }

    }

    private fun to(reader: Reader): BufferedReader {
        return reader as? BufferedReader ?: BufferedReader(reader)
    }
}
