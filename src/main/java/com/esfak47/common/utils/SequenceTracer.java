package com.esfak47.common.utils;

import com.esfak47.common.logger.Logger;
import com.esfak47.common.logger.LoggerFactory;
import com.esfak47.common.utils.StopWatch.TaskInfo;

/**
 * @author tony
 * @date 2018/6/13 Tracing program as a Sequence that can be print as plantuml or markdown sequence diagram. This class
 * is thread safe, but may lower the performance
 */

public class SequenceTracer {

    private final StopWatch<SequenceInfo> stopWatcher;
    private final Logger logger;

    private SequenceTracer() {
        stopWatcher = new StopWatch<>();
        logger = LoggerFactory.getLogger(SequenceTracer.class);
    }

    private SequenceTracer(String id) {
        stopWatcher = new StopWatch<>(id);
        logger = LoggerFactory.getLogger(SequenceTracer.class);
    }

    private SequenceTracer(String id, Logger logger) {
        stopWatcher = new StopWatch<>(id);
        this.logger = logger;
    }

    private static void note(boolean left, SequenceInfo info, StringBuilder builder) {
        builder.append(String
            .format("note %s : line %d\n", left ? "left" : "right",
                info.elementStart.getLineNumber()));
    }

    private void start(StackTraceElement element, String s, Object... objects) {
        stopWatcher.start(new SequenceInfo(String.format(s, objects), element));
    }

    private void stop() {
        stopWatcher.stop();
    }

    @Deprecated
    public void trace(String s, Object... objects) {
        StackTraceElement element = Thread.currentThread().getStackTrace()[2];
        trace(element, s, objects);
    }

    private void trace(StackTraceElement element, String s, Object... objects) {

        if (logger.isDebugEnabled()) {
            logger.debug(String.format(s, objects));
        }

        if (stopWatcher.isRunning()) {
            final SequenceInfo currentTaskInfo = stopWatcher.currentTaskInfo();
            currentTaskInfo.next = element;
            stop();
            start(element, s, objects);
        } else {
            start(element, s, objects);
        }
    }

    @Deprecated
    public void traceStop() {
        StackTraceElement element = Thread.currentThread().getStackTrace()[2];
        if (stopWatcher.isRunning()) {
            final SequenceInfo currentTaskInfo = stopWatcher.currentTaskInfo();
            currentTaskInfo.next = element;
            stop();
        }
    }

    private void traceStop(StackTraceElement element) {
        if (stopWatcher.isRunning()) {
            final SequenceInfo currentTaskInfo = stopWatcher.currentTaskInfo();
            currentTaskInfo.next = element;
            stop();
        }
    }

    public void newCallRequest(String reason) {
        StackTraceElement element = Thread.currentThread().getStackTrace()[2];
        trace(element, reason);
    }

    public void selfCall(String reason) {
        StackTraceElement element = Thread.currentThread().getStackTrace()[2];
        trace(element, reason);
    }

    public void selfCallSuccess() {
        StackTraceElement element = Thread.currentThread().getStackTrace()[2];
        traceStop(element);
    }

    public void selfCallFailed(String reason) {
        StackTraceElement element = Thread.currentThread().getStackTrace()[2];
        traceStopWithError(element, reason);
    }

    public void callSuccess() {
        StackTraceElement element = Thread.currentThread().getStackTrace()[2];
        traceStop(element);
    }

    public void callFailed(String reason) {
        //StackTraceElement element = Thread.currentThread().getStackTrace()[2];
        //traceStopWithError(element, reason);
        if (logger.isInfoEnabled()) {
            logger.info("call failed due to " + reason);
        }
    }

    public void acceptSuccess() {
        StackTraceElement element = Thread.currentThread().getStackTrace()[2];
        traceStop(element);
    }

    public void acceptFailed(String reason) {
        StackTraceElement element = Thread.currentThread().getStackTrace()[2];
        traceStopWithError(element, reason);
    }

    public void returnSuccess(String returnInfo) {
        StackTraceElement element = Thread.currentThread().getStackTrace()[2];
        trace(element, returnInfo);
    }

    public void returnFailed(String errorReason) {
        StackTraceElement element = Thread.currentThread().getStackTrace()[2];
        traceStopWithError(element, errorReason);
    }

    @Deprecated
    public void traceStopWithError(String errorReason) {
        StackTraceElement element = Thread.currentThread().getStackTrace()[2];
        traceStopWithError(element, errorReason);
    }

    private void traceStopWithError(StackTraceElement element, String errorReason) {
        if (stopWatcher.isRunning()) {
            final SequenceInfo currentTaskInfo = stopWatcher.currentTaskInfo();
            currentTaskInfo.next = element;
            currentTaskInfo.success = false;
            currentTaskInfo.errorDescription = errorReason;

            stop();
        }
    }

    public String printAsPlantuml() {

        if (stopWatcher.isRunning()) {
            stopWatcher.stop();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("@startuml\n");
        stringBuilder.append("autonumber\n \n");
        boolean left = true;
        final TaskInfo<SequenceInfo>[] taskInfos = stopWatcher.getCurrentTaskInfo();
        if (taskInfos.length >= 1) {

            for (TaskInfo<SequenceInfo> taskInfo : taskInfos) {
                final SequenceInfo sequenceInfo = taskInfo.getTaskInfo();
                String actor = String.format("\"%s.%s\"", sequenceInfo.elementStart.getClassName(),
                    sequenceInfo.elementStart.getMethodName());
                String actor2 = String.format("\"%s.%s\"", sequenceInfo.next.getClassName(),
                    sequenceInfo.next.getMethodName());
                if (sequenceInfo.success) {
                    stringBuilder.append(String.format("%s -> %s : %s\n", actor, actor2, sequenceInfo.intention));
                } else {
                    stringBuilder.append(String
                        .format("%s -x %s : %s (failed:%s)\n", actor, actor2, sequenceInfo.intention,
                            sequenceInfo.errorDescription));
                }

                note(left, sequenceInfo, stringBuilder);
                left = !left;
            }
        }

        stringBuilder.append("@enduml\n");
        return stringBuilder.toString();

    }

    public final static class SequenceTracerFactory {
        private final static ThreadLocal<SequenceTracer> SEQUENCE_TRACER_THREAD_LOCAL
            = ThreadLocal.withInitial(SequenceTracer::new);
        private final static LRUCache<String, SequenceTracer> SEQUENCE_TRACER_CONCURRENT_HASH_MAP
            = new LRUCache<>();

        public static SequenceTracer getSequenceTracer() {
            return SEQUENCE_TRACER_THREAD_LOCAL.get();
        }

        public static SequenceTracer getSequenceTracerWithTraceId(String id) {
            if (SEQUENCE_TRACER_CONCURRENT_HASH_MAP.containsKey(id)) {
                return SEQUENCE_TRACER_CONCURRENT_HASH_MAP.get(id);
            } else {
                SequenceTracer sequenceTracer = new SequenceTracer(id);
                SEQUENCE_TRACER_CONCURRENT_HASH_MAP.put(id, sequenceTracer);
                return sequenceTracer;
            }
        }

    }

    public static class SequenceInfo {
        private final String intention;
        private final StackTraceElement elementStart;
        private StackTraceElement next;
        private boolean success = true;
        private String errorDescription;

        private SequenceInfo(String intention, StackTraceElement elementStart) {
            this.intention = intention;
            this.elementStart = elementStart;
            this.next = elementStart;
        }

        @Override
        public String toString() {
            return "SequenceInfo{" + "intention='" + intention + '\'' +
                    ", success=" + success +
                    '}';
        }
    }
}
