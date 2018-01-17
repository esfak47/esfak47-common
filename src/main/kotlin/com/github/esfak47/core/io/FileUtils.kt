/*
 *    Copyright 2018 esfak47
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

import com.github.esfak47.core.exception.FileException
import com.github.esfak47.core.lang.Assert
import com.github.esfak47.core.utils.StringUtils

import java.io.*
import java.nio.channels.FileChannel
import java.nio.charset.Charset
import java.io.FileInputStream
import java.io.File



/**
 * @author Tony
 * Created by tony on 2018/1/16.
 */
object FileUtils {
    /**
     * 1KB
     */
    val ONE_KB: Long = 1024

    /**
     * 1MB
     */
    val ONE_MB = ONE_KB * ONE_KB

    /**
     * The file copy buffer size (30 MB)
     */
    private val FILE_COPY_BUFFER_SIZE = ONE_MB * 30

    /**
     * 平台临时目录
     *
     * @return 目录文件对象
     */
    val tempDirectory: File
        get() = File(tempDirectoryPath)

    /**
     * 平台的临时目录
     *
     * @return 目录路径字符串
     */
    val tempDirectoryPath: String
        get() = System.getProperty("java.io.tmpdir")

    /**
     * 用户主目录
     *
     * @return 用户主目录{@code File}
     */
    val userDirectory: File
        get() = File(userDirectoryPath)

    /**
     * 返回用户的主目录
     *
     * @return 用户主目录路径
     */
    val userDirectoryPath: String
        get() = System.getProperty("user.home")

    /**
     * 将文件的内容全部读取
     *
     * @param file    待读的文件
     * @param charset 字符编码
     * @return 文件内容
     */
    @JvmOverloads
    @JvmStatic fun readLines(file: File, charset: Charset = Charset.defaultCharset()): List<String> {
        Assert.notNull(file, "The parameter[destFile] is null.")
        Assert.notNull(charset, "The parameter[charset] is null.")
        var `in`: FileInputStream? = null
        try {
            `in` = openFileInputStream(file)
            return IOUtils.readLines(`in`, charset)
        } finally {
            IOUtils.closeQuietly(`in`)
        }
    }
    //endregion===================== readLines ========================

    //region======================== FileCopy =========================

    /**
     * 文件内容拷贝指定的输出流中
     *
     * @param srcFile 原文件
     * @param output  输出流
     */
    fun copyFile(srcFile: File, output: OutputStream) {
        val `in` = openFileInputStream(srcFile)
        try {
            IOUtils.copyLarge(BufferedInputStream(`in`), output)
        } finally {
            IOUtils.closeQuietly(`in`)
        }
    }

    /**
     * 文件拷贝
     *
     * @param srcFile      原文件
     * @param destFile     目标文件
     * @param holdFileDate 保持最后修改日期不变
     */
    @JvmOverloads
    fun copyFile(srcFile: File, destFile: File, holdFileDate: Boolean = true) {
        Assert.notNull(srcFile, "Source file must not be null.")
        Assert.notNull(destFile, "Destination file must not be null.")
        if (!srcFile.exists()) {
            throw FileException("Source [$srcFile] does not exist.")
        }
        if (srcFile.isDirectory) {
            throw FileException("Source [$srcFile] exists but it is a directory.")
        }

        try {
            if (srcFile.canonicalPath == destFile.canonicalPath) {
                throw FileException(String.format("Source [%s] and destination [%s] are the same.", srcFile, destFile))
            }
            val parentFile = destFile.parentFile
            if (parentFile != null) {
                if (!parentFile.mkdirs() && !parentFile.isDirectory) {
                    throw FileException("Destination [$parentFile] directory cannot be created.")
                }
            }
            if (destFile.exists() && !destFile.canWrite()) {
                throw FileException(" ===> Destination [$parentFile] directory cannot be written.")
            }
            doCopyFile(srcFile, destFile, holdFileDate)
        } catch (e: IOException) {
            throw FileException(e)
        }

    }

    /**
     * 文件复制内部方法
     *
     * @param srcFile      原文件
     * @param destFile     目标文件
     * @param holdFileDate 保持最后修改日期不变
     * @throws IOException I/O异常
     */
    @Throws(IOException::class)
    private fun doCopyFile(srcFile: File, destFile: File, holdFileDate: Boolean) {
        if (destFile.exists() && destFile.isDirectory) {
            throw FileException("Destination [$destFile] exists but it is a directory.")

        }

        FileInputStream(srcFile).use { `in` ->
            FileOutputStream(destFile).use { out ->
                `in`.channel.use { inChannel ->
                    out.channel.use { outChannel ->
                        val size = inChannel.size()
                        var pos: Long = 0
                        var count: Long
                        while (pos < size) {
                            count = if (size - pos > FILE_COPY_BUFFER_SIZE) FILE_COPY_BUFFER_SIZE else size - pos
                            pos += outChannel.transferFrom(inChannel, pos, count)
                        }
                    }
                }
            }
        }
        //必须放在try(){}之外，否则该修改无效
        if (srcFile.length() != destFile.length()) {
            throw IOException(String.format("Failed to copy full contents from [%s] to [%s]", srcFile.path, destFile.path))
        }
        if (holdFileDate) {
            destFile.setLastModified(srcFile.lastModified())
        }
    }

    /**
     * 将文件拷贝到目录
     *
     * @param srcFile 原文件
     * @param destDir 目标目录
     */
    fun copyFileToDirectory(srcFile: File, destDir: File) {
        copyFileToDirectory(srcFile, destDir, true)
    }

    /**
     * 将文件拷贝到目录
     *
     * @param srcFile      原文件
     * @param destDir      目标目录
     * @param holdFileDate 保持最后修改日期不变
     */
    private fun copyFileToDirectory(srcFile: File, destDir: File, holdFileDate: Boolean) {
        Assert.notNull(srcFile, "Source file must not be null.")
        Assert.notNull(destDir, "Destination Directory must not be null.")
        if (destDir.exists() && !destDir.isDirectory)
            throw FileException("Destination [$destDir] is not a directory.")

        val destFile = File(destDir, srcFile.name)
        copyFile(srcFile, destFile, holdFileDate)
    }

    /**
     * 将输入流的数据输出到文件中
     *
     * @param in       输入流,非空
     * @param destFile 目标文件,非空
     */
    @JvmStatic fun copyStream(`in`: InputStream, destFile: File) {
        Assert.notNull(`in`, "The parameter[in] is null.")
        Assert.notNull(destFile, "The parameter[destFile] is null.")
        var fos: FileOutputStream? = null
        try {
            fos = openFileOutputStream(destFile)
            val bos = BufferedOutputStream(fos)
            IOUtils.copy(`in`, bos)
            IOUtils.closeQuietly(bos)
        } finally {
            IOUtils.closeQuietly(fos)
            IOUtils.closeQuietly(`in`)
        }

    }

    /**
     * 目录复制
     *
     * @param srcDir       原目录
     * @param destDir      目标目录
     * @param holdFileDate 保持最后修改日期不变
     * @param filter       文件过滤器
     */
    @JvmOverloads
    @JvmStatic fun copyDirectory(srcDir: File, destDir: File, holdFileDate: Boolean = true, filter: FileFilter? = null) {
        Assert.notNull(srcDir, "Source Directory must not be null.")
        Assert.notNull(destDir, "Destination Directory must not be null.")
        if (!srcDir.exists()) {
            throw FileException("Source [$srcDir] does not exist.")
        }
        if (destDir.isFile) {
            throw FileException("Destination [$destDir] exists but is not a directory.")
        }

        try {
            if (srcDir.canonicalPath == destDir.canonicalPath) {
                throw FileException(String.format("Source [%s] and destination [%s] are the same.", srcDir, destDir))
            }
            //当目标目录是原目录的子目录时,不支持复制.
            if (destDir.canonicalPath.startsWith(srcDir.canonicalPath + File.separator)) {
                throw FileException(String.format("Destination [%s] is child directory of source [%s].", destDir, srcDir))
            }
            doCopyDirectory(srcDir, destDir, holdFileDate, filter)
        } catch (e: IOException) {
            throw FileException(e)
        }

    }

    /**
     * 目录复制
     *
     * @param srcDir       原目录
     * @param destDir      目标目录
     * @param holdFileDate 保持最后修改日期不变
     * @param filter       文件过滤器
     * @throws FileException 拷贝异常
     */
    @Throws(IOException::class)
    private fun doCopyDirectory(srcDir: File, destDir: File, holdFileDate: Boolean, filter: FileFilter?) {
        val srcFiles = (if (filter == null) srcDir.listFiles() else srcDir.listFiles(filter)) ?: throw IOException("Failed to list contents of [$srcDir]")
        if (destDir.exists() && !destDir.isDirectory) {
            throw IOException("Destination [$destDir] exists but is not a directory.")
        }
        if (!destDir.mkdirs() && !destDir.isDirectory) {
            throw IOException("Destination [$destDir] directory cannot be created.")
        }
        if (!destDir.canWrite()) {
            throw IOException("Destination [$destDir] cannot be written to.")
        }

        for (srcFile in srcFiles) {
            val destFile = File(destDir, srcFile.name)
            if (srcFile.isDirectory) {
                doCopyDirectory(srcFile, destFile, holdFileDate, filter)
            } else {
                doCopyFile(srcFile, destFile, holdFileDate)
            }
        }

        if (holdFileDate) {
            destDir.setLastModified(srcDir.lastModified())
        }
    }


    //--------------------------------move--------------------------------

    /**
     * 移动文件
     *
     * @param srcFile  原文件
     * @param destFile 目标文件
     * @throws FileException 文件处理异常
     */
     @JvmStatic fun moveFile(srcFile: File, destFile: File) {
        Assert.notNull(srcFile, "Source must not be null.")
        Assert.notNull(destFile, "Destination must not be null.")
        if (!srcFile.exists()) {
            throw FileException("Source [$srcFile] does not exist.")
        }
        if (srcFile.isDirectory) {
            throw FileException("Source [$srcFile] is a directory.")
        }
        //        if (!destFile.exists()) throw new FileException("Destination [" + destFile + "] does not exist.");
        if (destFile.isFile && destFile.exists()) {
            throw FileException("Destination [$destFile] already exists.")
        }
        if (destFile.isDirectory && !destFile.canWrite()) {
            throw FileException("Destination [$destFile] cannot be written to.")
        }

        val targetFile: File
        if (destFile.isDirectory) {
            targetFile = File(destFile, srcFile.name)
        } else {
            targetFile = destFile
        }
        val renameTo = srcFile.renameTo(targetFile)
        if (!renameTo) {
            //调用系统的重命名失败(移动),可能属于不同FS文件系统
            copyFile(srcFile, targetFile)
            if (!srcFile.delete()) {
                targetFile.delete()
                throw FileException(String.format("Failed to delete original file [%s], after copy to [%s]", srcFile, destFile))
            }
        }
    }

    /**
     * 移动目录
     *
     * @param source  原文件或目录
     * @param destDir 目标目录
     * @throws FileException 文件处理异常
     */
     @JvmStatic fun moveDirectory(source: File, destDir: File) {
        moveDirectory(source, destDir, false)
    }

    /**
     * 移动目录
     *
     * @param srcDir  原文件或目录
     * @param destDir 目标目录
     * @param toDir   如果目录不存在，是否创建
     * @throws FileException 文件处理异常
     */
     @JvmStatic fun moveDirectory(srcDir: File, destDir: File, toDir: Boolean) {
        Assert.notNull(srcDir, "Source must not be null.")
        Assert.notNull(destDir, "Destination must not be null.")
        if (!srcDir.exists()) {
            throw FileException("Source [$srcDir] does not exist.")
        }
        if (!srcDir.isDirectory) {
            throw FileException("Destination [$srcDir] is not a directory.")
        }
        if (destDir.exists() && !destDir.isDirectory) {
            throw FileException("Destination [$destDir] is not a directory.")
        }

        val targetDir = if (toDir) File(destDir, srcDir.name) else destDir

        if (!targetDir.mkdirs()) {
            throw FileException("Directory [$targetDir] could not be created.")
        }
        val renameTo = srcDir.renameTo(targetDir)
        if (!renameTo) {
            copyDirectory(srcDir, targetDir)
            delete(srcDir)
            if (srcDir.exists()) {
                throw FileException(String.format("Failed to delete original directory '%s' after copy to '%s'", srcDir, destDir))
            }
        }
    }


    //--------------------------------delete--------------------------------

    /**
     * 文件删除，支持目录删除
     *
     * @param file 文件
     * @throws FileException 文件处理异常
     */
    @JvmStatic  fun delete(file: File) {
        Assert.notNull(file, "File must not be null.")
        if (!file.exists()) return
        if (file.isDirectory) {
            cleanDirectory(file)
        }
        if (!file.delete()) {
            throw FileException("Unable to delete file: " + file)
        }
    }

    /**
     * 清理目录
     *
     * @param directory 目录
     * @throws FileException 文件处理异常
     */
    @JvmStatic  fun cleanDirectory(directory: File) {
        Assert.notNull(directory, "Directory must not be null.")
        if (!directory.exists()) throw FileException("Directory [$directory] does not exist.")
        if (!directory.isDirectory) throw FileException("The [$directory] is not a directory.")
        val listFiles = directory.listFiles() ?: throw FileException("Failed to list contents of " + directory)
        for (listFile in listFiles) {
            if (listFile.isDirectory) {
                cleanDirectory(listFile)
            }
            if (!listFile.delete()) {
                throw FileException("Unable to delete file: " + listFile)
            }
        }
    }

    /**
     * 打开文件的输入流，提供了比`new FileInputStream(file)`更好更优雅的方式.
     *
     * @param file 文件
     * @return [FileInputStream]
     * @throws FileException 文件处理异常
     */
    @JvmStatic fun openFileInputStream(file: File): FileInputStream {
        Assert.notNull(file, "File must not be null.")
        if (file.exists()) {
            if (file.isDirectory) {
                throw FileException("File '$file' exists but is a directory")
            }
            if (!file.canRead()) {
                throw FileException("File '$file' cannot be read")
            }
            try {
                return FileInputStream(file)
            } catch (e: IOException) {
                throw FileException(e)
            }

        }
        throw FileException("File '$file' does not exist")
    }

    /**
     * 打开件输出流
     *
     * @param file 文件
     * @return [FileOutputStream]
     */
    @JvmStatic fun openFileOutputStream(file: File): FileOutputStream {
        return openFileOutputStream(file, false)
    }

    /**
     * 打开件输出流
     *
     * @param file   文件
     * @param append 附加
     * @return [FileOutputStream]
     */
    private fun openFileOutputStream(file: File, append: Boolean): FileOutputStream {
        Assert.notNull(file, "File must not be null.")
        if (file.exists()) {
            if (file.isDirectory)
                throw FileException("Destination [$file] exists but is a directory.")
            if (!file.canWrite()) {
                throw FileException(String.format("Destination [%s] exists but cannot write.", file))
            }
        } else {
            val parent = file.parentFile
            if (parent != null) {
                if (!parent.mkdirs() && !parent.isDirectory) {
                    throw FileException("Directory [$parent] could not be created.")
                }
            }
        }
        try {
            return FileOutputStream(file, append)
        } catch (e: IOException) {
            throw FileException(e)
        }

    }

    /**
     * 计算文件的MD5值
     *
     * @param file 文件
     * @return 返回文件的md5
     */
    @JvmStatic fun md5Hex(file: File): String {
        var `in`: FileInputStream? = null
        try {
            `in` = openFileInputStream(file)
            return IOUtils.md5Hex(`in`)
        } finally {
            IOUtils.closeQuietly(`in`)
        }
    }

    /**
     * 计算文件的SHA-1值
     *
     * @param file 文件
     * @return 返回文件的sha-1
     */
    @JvmStatic fun sha1Hex(file: File): String {
        var `in`: FileInputStream? = null
        try {
            `in` = openFileInputStream(file)
            return IOUtils.sha1Hex(`in`)
        } finally {
            IOUtils.closeQuietly(`in`)
        }
    }
    /**
     * 将文件大小格式化输出
     *
     * @param fileSize 文件大小，单位为`Byte`
     * @return 格式化的大小
     */
    @JvmStatic fun formatSize(fileSize: Long): String {
        return if (fileSize < 0) StringUtils.EMPTY_STRING else formatSize(fileSize.toDouble())
    }

    /**
     * 将文件大小格式化输出
     *
     * @param fileSize 文件大小，单位为`Byte`
     * @return 格式化的大小
     */
    @JvmStatic fun formatSize(fileSize: Double): String {
        if (fileSize < 0) {
            return StringUtils.EMPTY_STRING
        }
        //byte
        var size = fileSize
        if (size < 1024) {
            return size.toString() + "Byte"
        }
        size /= 1024.0
        if (size < 1024) {
            return (Math.round(size * 100) / 100.0).toString() + "KB"
        }
        size /= 1024.0
        if (size < 1024) {
            return (Math.round(size * 100) / 100.0).toString() + "MB"
        }
        size /= 1024.0
        return if (size < 1024) {
            (Math.round(size * 100) / 100.0).toString() + "GB"
        } else (Math.round(size * 100) / 100.0).toString() + "TB"
    }

    /**
     * 将文件大小格式化输出
     *
     * @param fileSize 文件大小，单位为`Byte`
     * @return 格式化的大小
     */
    @JvmStatic fun formatSizeAsString(fileSize: String): String {
        if (StringUtils.isEmpty(fileSize)) {
            return StringUtils.EMPTY_STRING
        }
        val size = java.lang.Double.parseDouble(fileSize)
        return formatSize(size)
    }

    /**
     * 创建目录
     *
     * @param directoryPath 目录地址
     */
    @JvmStatic fun forceMakeDir(directoryPath: String) {
        forceMakeDir(File(directoryPath))
    }

    /**
     * 创建目录
     *
     * @param directory 目录地址
     */
    @JvmStatic fun forceMakeDir(directory: File) {
        if (directory.exists()) {
            if (!directory.isDirectory) {
                throw FileException("The file[$directory] exists and is not a directory.Unable to create directory.")
            }

        } else if (!directory.mkdirs() && !directory.isDirectory) {
            throw FileException("Unable to create directory[$directory]")
        }

    }
}

