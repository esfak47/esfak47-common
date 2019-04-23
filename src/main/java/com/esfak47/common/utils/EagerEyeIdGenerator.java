package com.esfak47.common.utils;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

/**
 * @author tony
 * @date 2018/7/4
 */
public class EagerEyeIdGenerator implements IdGenerator {
    private static final Pattern PATTERN = Pattern.compile(
            "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.("
                    + "(?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
    private static String IP_16 = "ffffffff";
    private static String IP_int = "255255255255";
    private static String PID = "0000";
    private static AtomicInteger count = new AtomicInteger(100000);

    static {
        try {
            String e = SystemUtils.getLocalAddress();
            if (e != null) {
                IP_16 = getip16(e);
                IP_int = getipInt(e);
            }

            PID = getHexPid(SystemUtils.getCurrentPid());
        } catch (Throwable ignored) {
        }

    }

    public EagerEyeIdGenerator() {
    }

    private static String getHexPid(int pid) {
        if (pid < 0) {
            pid = 0;
        } else if (pid > '\uffff') {
            pid %= '\uea60';
        }

        String str;
        for (str = Integer.toHexString(pid); str.length() < 4; ) {
            str = '0' + str;
        }

        return str;
    }

    private static String getTraceId(String ip, long timestamp, int nextId) {
        char pidFlag = 100;
        return ip + timestamp + nextId + pidFlag + PID;
    }

    private static String generate() {
        return getTraceId(IP_16, System.currentTimeMillis(), getNextId());
    }

    static String generate(String ip) {
        return ip != null && !ip.isEmpty() && validate(ip) ? getTraceId(getip16(ip), System.currentTimeMillis(),
                getNextId()) : generate();
    }

    static String generateIpv4Id() {
        return IP_int;
    }

    static String generateIpv4Id(String ip) {
        return ip != null && !ip.isEmpty() && validate(ip) ? getipInt(ip) : IP_int;
    }

    private static boolean validate(String ip) {
        try {
            return PATTERN.matcher(ip).matches();
        } catch (Throwable var2) {
            return false;
        }
    }

    private static String getip16(String ip) {
        String[] ips = ip.split("\\.");
        StringBuilder sb = new StringBuilder();
        for (String column : ips) {
            String hex = Integer.toHexString(Integer.parseInt(column));
            if (hex.length() == 1) {
                sb.append('0').append(hex);
            } else {
                sb.append(hex);
            }
        }

        return sb.toString();
    }

    private static String getipInt(String ip) {
        return ip.replace(".", "");
    }

    private static int getNextId() {
        int current;
        int next;
        do {
            current = count.get();
            next = current > 900000 ? 100000 : current + 1;
        } while (!count.compareAndSet(current, next));

        return next;
    }

    @Override
    public String generateId() {
        return generate();
    }

    @Override
    public UUID generateUUID() {
        final String generate = generate();
        final String str1 = generate.substring(0, 8);
        final String str2 = generate.substring(8, 12);
        final String str3 = generate.substring(12, 16);
        final String str4 = generate.substring(16, 20);
        final String str5 = generate.substring(20);
        final String join = StringUtils.join("-", str1, str2, str3, str4, str5);

        return UUID.fromString(join);
    }
}
