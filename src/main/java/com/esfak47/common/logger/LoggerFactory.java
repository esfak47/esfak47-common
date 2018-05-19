/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.esfak47.common.logger;

import com.esfak47.common.logger.jdk.JdkLoggerAdapter;
import com.esfak47.common.logger.log4j.Log4jLoggerAdapter;
import com.esfak47.common.logger.slf4j.Slf4jLoggerAdapter;
import com.esfak47.common.logger.support.FailsafeLogger;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Logger factory
 *
 * @author tonywang
 */
public class LoggerFactory {

    public static final String JDK = "jdk";
    private static final ConcurrentMap<String, FailsafeLogger> LOGGERS
        = new ConcurrentHashMap<String, FailsafeLogger>();
    private static final String SLF4J = "slf4j";

    private static final String LOG4J = "log4j";
    private static volatile LoggerAdapter LOGGER_ADAPTER;

    // search common-used logging frameworks
    static {
        String logger = System.getProperty("application.logger");
        if (SLF4J.equals(logger)) {
            setLoggerAdapter(new Slf4jLoggerAdapter());
        } else if (LOG4J.equals(logger)) {
            setLoggerAdapter(new Log4jLoggerAdapter());
        } else if (JDK.equals(logger)) {
            setLoggerAdapter(new JdkLoggerAdapter());
        } else {
            try {
                setLoggerAdapter(new Log4jLoggerAdapter());
            } catch (Throwable e1) {
                try {
                    setLoggerAdapter(new Slf4jLoggerAdapter());
                } catch (Throwable e3) {
                    setLoggerAdapter(new JdkLoggerAdapter());
                }
            }
        }

    }

    private LoggerFactory() {
    }

    //    public static void setLoggerAdapter(String loggerAdapter) {
    //        if (loggerAdapter != null && loggerAdapter.length() > 0) {
    //            setLoggerAdapter(ExtensionLoader.getExtensionLoader(LoggerAdapter.class).getExtension(loggerAdapter));
    //        }
    //    }

    /**
     * Set logger provider
     *
     * @param loggerAdapter logger provider
     */
    public static void setLoggerAdapter(LoggerAdapter loggerAdapter) {
        if (loggerAdapter != null) {
            Logger logger = loggerAdapter.getLogger(LoggerFactory.class.getName());
            logger.info("using logger: " + loggerAdapter.getClass().getName());
            LoggerFactory.LOGGER_ADAPTER = loggerAdapter;
            for (Map.Entry<String, FailsafeLogger> entry : LOGGERS.entrySet()) {
                entry.getValue().setLogger(LOGGER_ADAPTER.getLogger(entry.getKey()));
            }
        }
    }

    /**
     * Get logger provider
     *
     * @param key the returned logger will be named after clazz
     * @return logger
     */
    public static Logger getLogger(Class<?> key) {
        FailsafeLogger logger = LOGGERS.get(key.getName());
        if (logger == null) {
            LOGGERS.putIfAbsent(key.getName(), new FailsafeLogger(LOGGER_ADAPTER.getLogger(key)));
            logger = LOGGERS.get(key.getName());
        }
        return logger;
    }

    /**
     * Get logger provider
     *
     * @param key the returned logger will be named after key
     * @return logger provider
     */
    public static Logger getLogger(String key) {
        FailsafeLogger logger = LOGGERS.get(key);
        if (logger == null) {
            LOGGERS.putIfAbsent(key, new FailsafeLogger(LOGGER_ADAPTER.getLogger(key)));
            logger = LOGGERS.get(key);
        }
        return logger;
    }

    /**
     * Get logging level
     *
     * @return logging level
     */
    public static Level getLevel() {
        return LOGGER_ADAPTER.getLevel();
    }

    /**
     * Set the current logging level
     *
     * @param level logging level
     */
    public static void setLevel(Level level) {
        LOGGER_ADAPTER.setLevel(level);
    }

    /**
     * Get the current logging file
     *
     * @return current logging file
     */
    public static File getFile() {
        return LOGGER_ADAPTER.getFile();
    }

}