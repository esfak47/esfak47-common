package com.esfak47.common.utils;

import sun.misc.Unsafe;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.management.*;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.PrivilegedExceptionAction;
import java.util.Enumeration;
import java.util.List;

/**
 * @author tony
 * @date 2018/7/4
 */
public final class SystemUtils {

    public static final String NEWLINE = "\r\n";
    private static final String LOCAL_IP_ADDRESS = getLocalInetAddress();
    private static final int PID = getCurrentPid();
    private static final String PID_STR = String.valueOf(PID);
    // Unsafe mechanics
    @SuppressWarnings("restriction")
    private static final sun.misc.Unsafe UNSAFE = doGetUnsafe();
    private static final ThreadMXBean threadMXBean = getThreadMXBean0();

    private SystemUtils() {
        throw new UnsupportedOperationException();
    }

    public static String getLocalAddress() {
        return LOCAL_IP_ADDRESS;
    }

    private static String getLocalInetAddress() {
        String localIp = ConfigUtils.getSystemProperty("LOCAL.IP");
        if (StringUtils.isNotBlank(localIp) && localIp.length() >= 7
            && Character.isDigit(localIp.charAt(0))
            && Character.isDigit(localIp.charAt(localIp.length() - 1))) {
            return localIp;
        }
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress address = null;
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                // 排除掉虚拟网卡的 IP，@since 1.3.5.2
                String displayName = ni.getDisplayName();
                if (displayName != null && displayName.startsWith("virbr")) {
                    continue;
                }
                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    address = addresses.nextElement();
                    if (!address.isLoopbackAddress() && !address.getHostAddress().contains(":")) {
                        return address.getHostAddress();
                    }
                }
            }
        } catch (Throwable ignored) {
        }
        return "127.0.0.1";
    }

    /**
     * get current pid,max pid 32 bit systems 32768, for 64 bit 4194304
     *
     * @return
     */
    public static int getCurrentPid() {
        // @since 1.4.8 优先获取应用自己配置的 PID
        String pidStr = ConfigUtils.getSystemProperty("LOCAL.PID");
        if (StringUtils.isNotBlank(pidStr) && StringUtils.isNumeric(pidStr)) {
            try {
                long pidLong = Long.parseLong(pidStr);
                return Math.abs((int)pidLong);
            } catch (Throwable t) {
                // quietly
            }
        }

        try {
            RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
            String name = runtime.getName();
            return Integer.parseInt(name.substring(0, name.indexOf('@')));
        } catch (Throwable t) {
            return 0;
        }
    }

    public static void printMemPoolUsage(String tag, OutputStream out) {
        List<MemoryPoolMXBean> memoryPools = ManagementFactory.getMemoryPoolMXBeans();
        for (MemoryPoolMXBean memoryPool : memoryPools) {
            if (memoryPool == null) {continue;}
            MemoryUsage usage = memoryPool.getUsage();
            if (usage == null) {continue;}

            String poolName = memoryPool.getName();
            String message = tag + " " + poolName + ": init=" + toMbSize(usage.getInit()) +
                ", used=" + toMbSize(usage.getUsed()) + ", committed=" + toMbSize(usage.getCommitted()) +
                ", max=" + toMbSize(usage.getMax()) + NEWLINE;
            try {
                out.write(message.getBytes());
            } catch (IOException e) {
                // quietly
            }
        }
    }

    @SuppressWarnings("restriction")
    public static sun.misc.Unsafe getUnsafe() {
        return UNSAFE;
    }

    /**
     * Returns a sun.misc.Unsafe. Suitable for use in a 3rd party package. Replace with a simple call to
     * Unsafe.getUnsafe when integrating into a jdk.
     *
     * @return a sun.misc.Unsafe
     */
    @SuppressWarnings("restriction")
    private static sun.misc.Unsafe doGetUnsafe() {
        try {
            return sun.misc.Unsafe.getUnsafe();
        } catch (Throwable ignored) {}
        try {
            return java.security.AccessController.doPrivileged
                ((PrivilegedExceptionAction<Unsafe>)() -> {
                    Class<Unsafe> k = Unsafe.class;
                    for (Field f : k.getDeclaredFields()) {
                        f.setAccessible(true);
                        Object x = f.get(null);
                        if (k.isInstance(x)) {return k.cast(x);}
                    }
                    throw new NoSuchFieldError("the Unsafe");
                });
        } catch (Throwable t) {
            return null;
        }
    }

    private static ThreadMXBean getThreadMXBean0() {
        try {
            ThreadMXBean instance = ManagementFactory.getThreadMXBean();
            if (instance != null
                && instance.isCurrentThreadCpuTimeSupported()
                && instance.isThreadCpuTimeEnabled()
                && instance.getCurrentThreadCpuTime() != -1) {
                return instance;
            }
        } catch (Throwable t) {
            // quietly
        }
        // 没有正常取得时，直接设置成 null，以后不再尝试
        return null;
    }

    /**
     * 获取 ThreadMXBean，如果没有，返回 <code>null</code>
     *
     * @return
     */
    public static ThreadMXBean getThreadMXBean() {
        return threadMXBean;
    }

    private static String toMbSize(long value) {
        return (value / 1024 / 1024) + "MB";
    }

}
