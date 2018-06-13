package com.esfak47.common.utils;

import com.esfak47.common.lang.Assert;
import com.esfak47.common.logger.Logger;
import com.esfak47.common.logger.LoggerFactory;
import com.esfak47.common.utils.StopWatch.TaskInfo;

/**
 * @author tony
 * @date 2018/6/13 Tracing program as a Sequence that can be print as plantuml or markdown sequence diagram. This class
 * is thread safe, but may lower the performance
 */

public final class SequenceTracer {

    private final static ThreadLocal<StopWatch<SequenceInfo>> STOP_WATCH_THREAD_LOCAL = ThreadLocal.withInitial(
        () -> new StopWatch<SequenceInfo>(String.valueOf(Thread.currentThread().getId())));

    ;
    private static Logger logger = LoggerFactory.getLogger(SequenceTracer.class);

    private SequenceTracer() {
        throw new UnsupportedOperationException();
    }

    public static void changeLogger(Logger l) {
        Assert.notNull(l, "logger should not be null");
        logger = l;
    }

    private static void start(StackTraceElement element, String s, Object... objects) {

        STOP_WATCH_THREAD_LOCAL.get().start(new SequenceInfo(String.format(s, objects), element));
    }

    private static void stop() {

        final StopWatch<SequenceInfo> sequenceInfoStopWatch = STOP_WATCH_THREAD_LOCAL.get();
        sequenceInfoStopWatch.stop();

    }

    public static void failed() {
        StackTraceElement element = Thread.currentThread().getStackTrace()[2];

        final StopWatch<SequenceInfo> sequenceInfoStopWatch = STOP_WATCH_THREAD_LOCAL.get();
        if (sequenceInfoStopWatch.isRunning()) {
            final SequenceInfo currentTaskInfo = sequenceInfoStopWatch.currentTaskInfo();
            currentTaskInfo.success = false;
            currentTaskInfo.next = element;
            stop();
        }
    }

    public static void trace(String s, Object... objects) {
        StackTraceElement element = Thread.currentThread().getStackTrace()[2];
        if (logger.isDebugEnabled()) {
            logger.debug(String.format(s, objects));
        }
        final StopWatch<SequenceInfo> sequenceInfoStopWatch = STOP_WATCH_THREAD_LOCAL.get();
        if (sequenceInfoStopWatch.isRunning()) {
            final SequenceInfo currentTaskInfo = sequenceInfoStopWatch.currentTaskInfo();
            currentTaskInfo.next = element;
            stop();
            start(element, s, objects);
        } else {
            start(element, s, objects);
        }
    }

    public static void traceStop() {
        StackTraceElement element = Thread.currentThread().getStackTrace()[2];
        final StopWatch<SequenceInfo> sequenceInfoStopWatch = STOP_WATCH_THREAD_LOCAL.get();
        if (sequenceInfoStopWatch.isRunning()) {
            final SequenceInfo currentTaskInfo = sequenceInfoStopWatch.currentTaskInfo();
            currentTaskInfo.next = element;
            stop();
        }
    }

    public static String printAsPlantuml() {

        final StopWatch<SequenceInfo> sequenceInfoStopWatch = STOP_WATCH_THREAD_LOCAL.get();
        if (sequenceInfoStopWatch.isRunning()) {
            sequenceInfoStopWatch.stop();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("@startuml\n");
        stringBuilder.append("autonumber\n \n");
        boolean left = true;
        final TaskInfo<SequenceInfo>[] taskInfos = STOP_WATCH_THREAD_LOCAL.get().getCurrentTaskInfo();
        if (taskInfos.length >= 1) {

            for (TaskInfo<SequenceInfo> taskInfo : taskInfos) {
                final SequenceInfo sequenceInfo = taskInfo.getTaskInfo();
                String actor = String.format("\"%s.%s\"", sequenceInfo.elementStart.getClassName(),
                    sequenceInfo.elementStart.getMethodName());
                String actor2 = String.format("\"%s.%s\"", sequenceInfo.next.getClassName(),
                    sequenceInfo.next.getMethodName());
                stringBuilder.append(String.format("%s -> %s : %s\n", actor, actor2, sequenceInfo.intention));
                note(left, sequenceInfo, stringBuilder);
                left = !left;
            }
        }

        stringBuilder.append("@enduml\n");
        return stringBuilder.toString();

    }

    private static void note(boolean left, SequenceInfo info, StringBuilder builder) {
        builder.append(String
            .format("note %s : line %d\n", left ? "left" : "right",
                info.elementStart.getLineNumber()));
    }

    public static class SequenceInfo {
        private final String intention;
        private final StackTraceElement elementStart;
        private StackTraceElement next;
        private boolean success = true;

        private SequenceInfo(String intention, StackTraceElement elementStart) {
            this.intention = intention;
            this.elementStart = elementStart;
            this.next = elementStart;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("SequenceInfo{");
            sb.append("intention='").append(intention).append('\'');
            sb.append(", success=").append(success);
            sb.append('}');
            return sb.toString();
        }
    }
}
