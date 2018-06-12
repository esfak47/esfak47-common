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

package com.esfak47.common.extension;

import java.util.concurrent.ExecutorService;
import java.util.regex.Pattern;

/**
 * Constants
 */
public interface Constants {

    String PROVIDER = "provider";

    String CONSUMER = "consumer";

    String REGISTER = "register";

    String UNREGISTER = "unregister";

    String SUBSCRIBE = "subscribe";

    String UNSUBSCRIBE = "unsubscribe";

    String CATEGORY_KEY = "category";

    String PROVIDERS_CATEGORY = "providers";

    String CONSUMERS_CATEGORY = "consumers";

    String ROUTERS_CATEGORY = "routers";

    String CONFIGURATORS_CATEGORY = "configurators";

    String DEFAULT_CATEGORY = PROVIDERS_CATEGORY;

    String ENABLED_KEY = "enabled";

    String DISABLED_KEY = "disabled";

    String VALIDATION_KEY = "validation";

    String CACHE_KEY = "cache";

    String DYNAMIC_KEY = "dynamic";

    String DUBBO_PROPERTIES_KEY = "dubbo.properties.file";

    String DEFAULT_DUBBO_PROPERTIES = "dubbo.properties";

    String SENT_KEY = "sent";

    boolean DEFAULT_SENT = false;

    String REGISTRY_PROTOCOL = "registry";

    String $INVOKE = "$invoke";

    String $ECHO = "$echo";

    int DEFAULT_IO_THREADS = Math.min(Runtime.getRuntime().availableProcessors() + 1, 32);

    String DEFAULT_PROXY = "javassist";

    int DEFAULT_PAYLOAD = 8 * 1024 * 1024;                      // 8M

    String DEFAULT_CLUSTER = "failover";

    String DEFAULT_DIRECTORY = "dubbo";

    String DEFAULT_LOADBALANCE = "random";

    String DEFAULT_PROTOCOL = "dubbo";

    String DEFAULT_EXCHANGER = "header";

    String DEFAULT_TRANSPORTER = "netty";

    String DEFAULT_REMOTING_SERVER = "netty";

    String DEFAULT_REMOTING_CLIENT = "netty";

    String DEFAULT_REMOTING_CODEC = "dubbo";

    String DEFAULT_REMOTING_SERIALIZATION = "hessian2";

    String DEFAULT_HTTP_SERVER = "servlet";

    String DEFAULT_HTTP_CLIENT = "jdk";

    String DEFAULT_HTTP_SERIALIZATION = "json";

    String DEFAULT_CHARSET = "UTF-8";

    int DEFAULT_WEIGHT = 100;

    int DEFAULT_FORKS = 2;

    String DEFAULT_THREAD_NAME = "Dubbo";

    int DEFAULT_CORE_THREADS = 0;

    int DEFAULT_THREADS = 200;

    boolean DEFAULT_KEEP_ALIVE = true;

    int DEFAULT_QUEUES = 0;

    int DEFAULT_ALIVE = 60 * 1000;

    int DEFAULT_CONNECTIONS = 0;

    int DEFAULT_ACCEPTS = 0;

    int DEFAULT_IDLE_TIMEOUT = 600 * 1000;

    int DEFAULT_HEARTBEAT = 60 * 1000;

    int DEFAULT_TIMEOUT = 1000;

    int DEFAULT_CONNECT_TIMEOUT = 3000;

    //    public static final int DEFAULT_REGISTRY_CONNECT_TIMEOUT = 5000;

    int DEFAULT_RETRIES = 2;

    // default buffer size is 8k.
    int DEFAULT_BUFFER_SIZE = 8 * 1024;

    int MAX_BUFFER_SIZE = 16 * 1024;

    int MIN_BUFFER_SIZE = 1 * 1024;

    String REMOVE_VALUE_PREFIX = "-";

    String HIDE_KEY_PREFIX = ".";

    String DEFAULT_KEY_PREFIX = "default.";

    String DEFAULT_KEY = "default";

    String LOADBALANCE_KEY = "loadbalance";

    // key for router type, for e.g., "script"/"file",  corresponding to ScriptRouterFactory.NAME, FileRouterFactory
    // .NAME
    String ROUTER_KEY = "router";

    String CLUSTER_KEY = "cluster";

    String REGISTRY_KEY = "registry";

    String MONITOR_KEY = "monitor";

    String SIDE_KEY = "side";

    String PROVIDER_SIDE = "provider";

    String CONSUMER_SIDE = "consumer";

    String DEFAULT_REGISTRY = "dubbo";

    String BACKUP_KEY = "backup";

    String DIRECTORY_KEY = "directory";

    String DEPRECATED_KEY = "deprecated";

    String ANYHOST_KEY = "anyhost";

    String ANYHOST_VALUE = "0.0.0.0";

    String LOCALHOST_KEY = "localhost";

    String LOCALHOST_VALUE = "127.0.0.1";

    String APPLICATION_KEY = "application";

    String LOCAL_KEY = "local";

    String STUB_KEY = "stub";

    String MOCK_KEY = "mock";

    String PROTOCOL_KEY = "protocol";

    String PROXY_KEY = "proxy";

    String WEIGHT_KEY = "weight";

    String FORKS_KEY = "forks";

    String DEFAULT_THREADPOOL = "limited";

    String DEFAULT_CLIENT_THREADPOOL = "cached";

    String THREADPOOL_KEY = "threadpool";

    String THREAD_NAME_KEY = "threadname";

    String IO_THREADS_KEY = "iothreads";

    String CORE_THREADS_KEY = "corethreads";

    String THREADS_KEY = "threads";

    String QUEUES_KEY = "queues";

    String ALIVE_KEY = "alive";

    String EXECUTES_KEY = "executes";

    String BUFFER_KEY = "buffer";

    String PAYLOAD_KEY = "payload";

    String REFERENCE_FILTER_KEY = "reference.filter";

    String INVOKER_LISTENER_KEY = "invoker.listener";

    String SERVICE_FILTER_KEY = "service.filter";

    String EXPORTER_LISTENER_KEY = "exporter.listener";

    String ACCESS_LOG_KEY = "accesslog";

    String ACTIVES_KEY = "actives";

    String CONNECTIONS_KEY = "connections";

    String ACCEPTS_KEY = "accepts";

    String IDLE_TIMEOUT_KEY = "idle.timeout";

    String HEARTBEAT_KEY = "heartbeat";

    String HEARTBEAT_TIMEOUT_KEY = "heartbeat.timeout";

    String CONNECT_TIMEOUT_KEY = "connect.timeout";

    String TIMEOUT_KEY = "timeout";

    String RETRIES_KEY = "retries";

    String PROMPT_KEY = "prompt";

    String DEFAULT_PROMPT = "dubbo>";

    String CODEC_KEY = "codec";

    String SERIALIZATION_KEY = "serialization";

    String EXTENSION_KEY = "extension";

    String KEEP_ALIVE_KEY = "keepalive";

    String OPTIMIZER_KEY = "optimizer";

    String EXCHANGER_KEY = "exchanger";

    String TRANSPORTER_KEY = "transporter";

    String SERVER_KEY = "server";

    String CLIENT_KEY = "client";

    String ID_KEY = "id";

    String ASYNC_KEY = "async";

    String RETURN_KEY = "return";

    String TOKEN_KEY = "token";

    String METHOD_KEY = "method";

    String METHODS_KEY = "methods";

    String CHARSET_KEY = "charset";

    String RECONNECT_KEY = "reconnect";

    String SEND_RECONNECT_KEY = "send.reconnect";

    int DEFAULT_RECONNECT_PERIOD = 2000;

    String SHUTDOWN_TIMEOUT_KEY = "shutdown.timeout";

    int DEFAULT_SHUTDOWN_TIMEOUT = 1000 * 60 * 15;

    String PID_KEY = "pid";

    String TIMESTAMP_KEY = "timestamp";

    String REMOTE_TIMESTAMP_KEY = "remote.timestamp";

    String WARMUP_KEY = "warmup";

    int DEFAULT_WARMUP = 10 * 60 * 1000;

    String CHECK_KEY = "check";

    String REGISTER_KEY = "register";

    String SUBSCRIBE_KEY = "subscribe";

    String GROUP_KEY = "group";

    String PATH_KEY = "path";

    String INTERFACE_KEY = "interface";

    String GENERIC_KEY = "generic";

    String FILE_KEY = "file";

    String DUMP_DIRECTORY = "dump.directory";

    String WAIT_KEY = "wait";

    String CLASSIFIER_KEY = "classifier";

    String VERSION_KEY = "version";

    String REVISION_KEY = "revision";

    String DUBBO_VERSION_KEY = "dubbo";

    String HESSIAN_VERSION_KEY = "hessian.version";

    String DISPATCHER_KEY = "dispatcher";

    String CHANNEL_HANDLER_KEY = "channel.handler";

    String DEFAULT_CHANNEL_HANDLER = "default";

    String ANY_VALUE = "*";

    String COMMA_SEPARATOR = ",";

    Pattern COMMA_SPLIT_PATTERN = Pattern
            .compile("\\s*[,]+\\s*");

    String PATH_SEPARATOR = "/";

    String REGISTRY_SEPARATOR = "|";

    Pattern REGISTRY_SPLIT_PATTERN = Pattern
            .compile("\\s*[|;]+\\s*");

    String SEMICOLON_SEPARATOR = ";";

    Pattern SEMICOLON_SPLIT_PATTERN = Pattern
            .compile("\\s*[;]+\\s*");

    String CONNECT_QUEUE_CAPACITY = "connect.queue.capacity";

    String CONNECT_QUEUE_WARNING_SIZE = "connect.queue.warning.size";

    int DEFAULT_CONNECT_QUEUE_WARNING_SIZE = 1000;

    String CHANNEL_ATTRIBUTE_READONLY_KEY = "channel.readonly";

    String CHANNEL_READONLYEVENT_SENT_KEY = "channel.readonly.sent";

    String CHANNEL_SEND_READONLYEVENT_KEY = "channel.readonly.send";

    String COUNT_PROTOCOL = "count";

    String TRACE_PROTOCOL = "trace";

    String EMPTY_PROTOCOL = "empty";

    String ADMIN_PROTOCOL = "admin";

    String PROVIDER_PROTOCOL = "provider";

    String CONSUMER_PROTOCOL = "consumer";

    String ROUTE_PROTOCOL = "route";

    String SCRIPT_PROTOCOL = "script";

    String CONDITION_PROTOCOL = "condition";

    String MOCK_PROTOCOL = "mock";

    String RETURN_PREFIX = "return ";

    String THROW_PREFIX = "throw";

    String FAIL_PREFIX = "fail:";

    String FORCE_PREFIX = "force:";

    String FORCE_KEY = "force";

    String MERGER_KEY = "merger";

    /**
     * To decide whether to exclude unavailable invoker from the cluster
     */
    String CLUSTER_AVAILABLE_CHECK_KEY = "cluster.availablecheck";

    /**
     * The default value of cluster.availablecheck
     *
     * @see #CLUSTER_AVAILABLE_CHECK_KEY
     */
    boolean DEFAULT_CLUSTER_AVAILABLE_CHECK = true;

    /**
     * To decide whether to enable sticky strategy for cluster
     */
    String CLUSTER_STICKY_KEY = "sticky";

    /**
     * The default value of sticky
     *
     * @see #CLUSTER_STICKY_KEY
     */
    boolean DEFAULT_CLUSTER_STICKY = false;

    /**
     * To decide whether to make connection when the client is created
     */
    String LAZY_CONNECT_KEY = "lazy";

    /**
     * The initial state for lazy connection
     */
    String LAZY_CONNECT_INITIAL_STATE_KEY = "connect.lazy.initial.state";

    /**
     * The default value of lazy connection's initial state: true
     *
     * @see #LAZY_CONNECT_INITIAL_STATE_KEY
     */
    boolean DEFAULT_LAZY_CONNECT_INITIAL_STATE = true;

    /**
     * To decide whether register center saves file synchronously, the default value is asynchronously
     */
    String REGISTRY_FILESAVE_SYNC_KEY = "save.file";

    /**
     * Period of registry center's retry interval
     */
    String REGISTRY_RETRY_PERIOD_KEY = "retry.period";

    /**
     * Default value for the period of retry interval in milliseconds: 5000
     */
    int DEFAULT_REGISTRY_RETRY_PERIOD = 5 * 1000;

    /**
     * Reconnection period in milliseconds for register center
     */
    String REGISTRY_RECONNECT_PERIOD_KEY = "reconnect.period";

    int DEFAULT_REGISTRY_RECONNECT_PERIOD = 3 * 1000;

    String SESSION_TIMEOUT_KEY = "session";

    int DEFAULT_SESSION_TIMEOUT = 60 * 1000;

    /**
     * The key name for export URL in register center
     */
    String EXPORT_KEY = "export";

    /**
     * The key name for reference URL in register center
     */
    String REFER_KEY = "refer";

    /**
     * callback inst id
     */
    String CALLBACK_SERVICE_KEY = "callback.service.instid";

    /**
     * The limit of callback service instances for one interface on every client
     */
    String CALLBACK_INSTANCES_LIMIT_KEY = "callbacks";

    /**
     * The default limit number for callback service instances
     *
     * @see #CALLBACK_INSTANCES_LIMIT_KEY
     */
    int DEFAULT_CALLBACK_INSTANCES = 1;

    String CALLBACK_SERVICE_PROXY_KEY = "callback.service.proxy";

    String IS_CALLBACK_SERVICE = "is_callback_service";

    /**
     * Invokers in channel's callback
     */
    String CHANNEL_CALLBACK_KEY = "channel.callback.invokers.key";

    @Deprecated
    String SHUTDOWN_WAIT_SECONDS_KEY = "dubbo.service.shutdown.wait.seconds";

    String SHUTDOWN_WAIT_KEY = "dubbo.service.shutdown.wait";

    String IS_SERVER_KEY = "isserver";

    /**
     * Default timeout value in milliseconds for server shutdown
     */
    int DEFAULT_SERVER_SHUTDOWN_TIMEOUT = 10000;

    String ON_CONNECT_KEY = "onconnect";

    String ON_DISCONNECT_KEY = "ondisconnect";

    String ON_INVOKE_METHOD_KEY = "oninvoke.method";

    String ON_RETURN_METHOD_KEY = "onreturn.method";

    String ON_THROW_METHOD_KEY = "onthrow.method";

    String ON_INVOKE_INSTANCE_KEY = "oninvoke.instance";

    String ON_RETURN_INSTANCE_KEY = "onreturn.instance";

    String ON_THROW_INSTANCE_KEY = "onthrow.instance";

    String OVERRIDE_PROTOCOL = "override";

    String PRIORITY_KEY = "priority";

    String RULE_KEY = "rule";

    String TYPE_KEY = "type";

    String RUNTIME_KEY = "runtime";

    /**
     * when ROUTER_KEY's value is set to ROUTER_TYPE_CLEAR, RegistryDirectory will clean all current routers
     */
    String ROUTER_TYPE_CLEAR = "clean";

    String DEFAULT_SCRIPT_TYPE_KEY = "javascript";

    String STUB_EVENT_KEY = "dubbo.stub.event";

    boolean DEFAULT_STUB_EVENT = false;

    String STUB_EVENT_METHODS_KEY = "dubbo.stub.event.methods";

    /**
     * When this attribute appears in invocation's attachment, mock invoker will be used
     */
    String INVOCATION_NEED_MOCK = "invocation.need.mock";

    String LOCAL_PROTOCOL = "injvm";

    String AUTO_ATTACH_INVOCATIONID_KEY = "invocationid.autoattach";

    String SCOPE_KEY = "scope";

    String SCOPE_LOCAL = "local";

    String SCOPE_REMOTE = "remote";

    String SCOPE_NONE = "none";

    String RELIABLE_PROTOCOL = "napoli";

    String TPS_LIMIT_RATE_KEY = "tps";

    String TPS_LIMIT_INTERVAL_KEY = "tps.interval";

    long DEFAULT_TPS_LIMIT_INTERVAL = 60L * 1000;

    String DECODE_IN_IO_THREAD_KEY = "decode.in.io";

    boolean DEFAULT_DECODE_IN_IO_THREAD = true;

    String INPUT_KEY = "input";

    String OUTPUT_KEY = "output";

    String EXECUTOR_SERVICE_COMPONENT_KEY = ExecutorService.class.getName();

    String GENERIC_SERIALIZATION_NATIVE_JAVA = "nativejava";

    String GENERIC_SERIALIZATION_DEFAULT = "true";

    String GENERIC_SERIALIZATION_BEAN = "bean";

    String DUBBO_IP_TO_REGISTRY = "DUBBO_IP_TO_REGISTRY";

    String DUBBO_PORT_TO_REGISTRY = "DUBBO_PORT_TO_REGISTRY";

    String DUBBO_IP_TO_BIND = "DUBBO_IP_TO_BIND";

    String DUBBO_PORT_TO_BIND = "DUBBO_PORT_TO_BIND";

    String BIND_IP_KEY = "bind.ip";

    String BIND_PORT_KEY = "bind.port";

    String REGISTER_IP_KEY = "register.ip";

    String QOS_ENABLE = "qos.enable";

    String QOS_PORT = "qos.port";

    String ACCEPT_FOREIGN_IP = "qos.accept.foreign.ip";

    String HESSIAN2_REQUEST_KEY = "hessian2.request";

    boolean DEFAULT_HESSIAN2_REQUEST = false;

    String HESSIAN_OVERLOAD_METHOD_KEY = "hessian.overload.method";

    boolean DEFAULT_HESSIAN_OVERLOAD_METHOD = false;

    String MULTICAST = "multicast";

    /*
     * private Constants(){ }
     */

}
