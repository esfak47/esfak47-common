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

package com.github.esfak47.core.exception

/**
 * @author Tony
 * Created by tony on 2018/1/16.
 */
class FileException : RuntimeException {
    /**
     * Create a new FatalFileException with the specified message.
     *
     * @param message the detail message
     */
    constructor(message: String) : super(message) {}

    /**
     * Create a new FatalFileException with the root cause.
     *
     * @param cause the root cause
     */
    constructor(cause: Throwable) : super(cause) {}

    /**
     * Create a new FatalFileException with the specified message and root cause.
     *
     * @param message the detail message
     * @param cause   the root cause
     */
    constructor(message: String, cause: Throwable) : super(message, cause) {}

}
