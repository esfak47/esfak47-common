package com.esfak47.common.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author tony
 * @date 2018/5/3
 */
public class PluginsClassLoader extends URLClassLoader {
    public PluginsClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public PluginsClassLoader(File... files) throws IOException {
        this(new URL[]{}, ClassLoader.getSystemClassLoader());
        if (files != null && files.length != 0) {
            attachJars(Arrays.stream(files).collect(Collectors.toList()));
        }

    }

    public PluginsClassLoader(String... path) throws IOException {
        this(new URL[]{}, ClassLoader.getSystemClassLoader());
        attachJars(Arrays.stream(path).map(File::new).collect(Collectors.toList()));

    }

    private void attachJars(Collection<File> files) throws IOException {
        if (files != null && files.size() != 0) {
            Set<URL> urls = new LinkedHashSet<>();
            for (File file : files) {
                if (file.exists() && file.isDirectory()) {
                    DirectoryStream<Path> jarStream = Files.newDirectoryStream(file.toPath(), "*.jar");
                    for (Path jar : jarStream) {
                        // normalize with toRealPath to get symlinks out of our hair
                        URL url = jar.toRealPath().toUri().toURL();
                        System.out.println(url);
                        if (!urls.add(url)) {
                            throw new IllegalStateException("duplicate codebase: " + url);
                        }
                    }
                } else if (file.exists() && file.getName().endsWith(".jar")) {
                    URL url = file.toPath().toRealPath().toUri().toURL();
                    if (!urls.add(url)) {
                        throw new IllegalStateException("duplicate codebase: " + url);
                    }
                }
            }
            urls.forEach(this::addURL);
        }
    }

}
