/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.esfak47.common.utils.system;

import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * generify the StopWatch that implement in spring core utils
 *
 * @author tony
 */
public class StopWatch<T> {

    /**
     * Identifier of this stop watch. Handy when we have output from multiple stop watches and need to distinguish
     * between them in log or console output.
     */
    private final String id;
    private final List<TaskInfo<T>> taskList = new LinkedList<TaskInfo<T>>();
    private boolean keepTaskList = true;
    /**
     * Start time of the current task
     */
    private long startTimeMillis;

    /**
     * Is the stop watch currently running?
     */
    private boolean running;

    /**
     * Name of the current task
     */
    private T currentTaskInfo;

    private TaskInfo<T> lastTaskInfo;

    private int taskCount;

    /**
     * Total running time
     */
    private long totalTimeMillis;

    /**
     * Construct a new stop watch. Does not start any task.
     */
    public StopWatch() {
        this("");
    }

    /**
     * Construct a new stop watch with the given id. Does not start any task.
     *
     * @param id identifier for this stop watch. Handy when we have output from multiple stop watches and need to
     *           distinguish between them.
     */
    public StopWatch(String id) {
        this.id = id;
    }

    /**
     * Return the id of this stop watch, as specified on construction.
     *
     * @return the id (empty String by default)
     * @see #StopWatch(String)
     * @since 4.2.2
     */
    public String getId() {
        return this.id;
    }

    /**
     * Determine whether the TaskInfo array is built over time. Set this to "false" when using a StopWatch for millions
     * of intervals, or the task info structure will consume excessive memory. Default is "true".
     */
    public void setKeepTaskList(boolean keepTaskList) {
        this.keepTaskList = keepTaskList;
    }

    /**
     * Start an unnamed task. The results are undefined if {@link #stop()} or timing methods are called without invoking
     * this method.
     *
     * @see #stop()
     */
    public void start() throws IllegalStateException {
        start(null);
    }

    /**
     * Start a named task. The results are undefined if {@link #stop()} or timing methods are called without invoking
     * this method.
     *
     * @param taskInfo the name of the task to start
     * @see #stop()
     */
    public void start(T taskInfo) throws IllegalStateException {
        if (this.running) {
            throw new IllegalStateException("Can't start StopWatch: it's already running");
        }
        this.running = true;
        this.currentTaskInfo = taskInfo;
        this.startTimeMillis = System.currentTimeMillis();
    }

    /**
     * Stop the current task. The results are undefined if timing methods are called without invoking at least one pair
     * {@code start()} / {@code stop()} methods.
     *
     * @see #start()
     */
    public void stop() throws IllegalStateException {
        if (!this.running) {
            throw new IllegalStateException("Can't stop StopWatch: it's not running");
        }
        long lastTime = System.currentTimeMillis() - this.startTimeMillis;
        this.totalTimeMillis += lastTime;
        this.lastTaskInfo = new TaskInfo(this.currentTaskInfo, lastTime);
        if (this.keepTaskList) {
            this.taskList.add(lastTaskInfo);
        }
        ++this.taskCount;
        this.running = false;
        this.currentTaskInfo = null;
    }

    /**
     * Return whether the stop watch is currently running.
     *
     * @see #currentTaskInfo()
     */
    public boolean isRunning() {
        return this.running;
    }

    /**
     * Return the name of the currently running task, if any.
     *
     * @see #isRunning()
     * @since 4.2.2
     */
    public T currentTaskInfo() {
        return this.currentTaskInfo;
    }

    /**
     * Return the time taken by the last task.
     */
    public long getLastTaskTimeMillis() throws IllegalStateException {
        if (this.lastTaskInfo == null) {
            throw new IllegalStateException("No tasks run: can't get last task interval");
        }
        return this.lastTaskInfo.getTimeMillis();
    }

    /**
     * Return the name of the last task.
     */
    public T getLastTaskInfoInner() throws IllegalStateException {
        if (this.lastTaskInfo == null) {
            throw new IllegalStateException("No tasks run: can't get last task name");
        }
        return this.lastTaskInfo.getTaskInfo();
    }

    /**
     * Return the last task as a TaskInfo object.
     */
    public TaskInfo getLastTaskInfo() throws IllegalStateException {
        if (this.lastTaskInfo == null) {
            throw new IllegalStateException("No tasks run: can't get last task info");
        }
        return this.lastTaskInfo;
    }

    /**
     * Return the total time in milliseconds for all tasks.
     */
    public long getTotalTimeMillis() {
        return this.totalTimeMillis;
    }

    /**
     * Return the total time in seconds for all tasks.
     */
    public double getTotalTimeSeconds() {
        return this.totalTimeMillis / 1000.0;
    }

    /**
     * Return the number of tasks timed.
     */
    public int getTaskCount() {
        return this.taskCount;
    }

    /**
     * Return an array of the data for tasks performed.
     */
    public TaskInfo<T>[] getCurrentTaskInfo() {
        if (!this.keepTaskList) {
            throw new UnsupportedOperationException("Task info is not being kept!");
        }
        return this.taskList.toArray(new TaskInfo[this.taskList.size()]);
    }

    /**
     * Return a short description of the total running time.
     */
    public String shortSummary() {
        return "StopWatch '" + getId() + "': running time (millis) = " + getTotalTimeMillis();
    }

    /**
     * Return a string with a table describing all tasks performed. For custom reporting, call getCurrentTaskInfo() and
     * use the task info directly.
     */
    public String prettyPrint() {
        StringBuilder sb = new StringBuilder(shortSummary());
        sb.append('\n');
        if (!this.keepTaskList) {
            sb.append("No task info kept");
        } else {
            sb.append("-----------------------------------------\n");
            sb.append("ms     %     Task name\n");
            sb.append("-----------------------------------------\n");
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMinimumIntegerDigits(5);
            nf.setGroupingUsed(false);
            NumberFormat pf = NumberFormat.getPercentInstance();
            pf.setMinimumIntegerDigits(3);
            pf.setGroupingUsed(false);
            for (TaskInfo task : getCurrentTaskInfo()) {
                sb.append(nf.format(task.getTimeMillis())).append("  ");
                sb.append(pf.format(task.getTimeSeconds() / getTotalTimeSeconds())).append("  ");
                sb.append(task.getTaskInfoAsString()).append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Return an informative string describing all tasks performed For custom reporting, call {@code
     * getCurrentTaskInfo()} and use the task info directly.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(shortSummary());
        if (this.keepTaskList) {
            for (TaskInfo<T> task : getCurrentTaskInfo()) {
                sb.append("; [").append(task.getTaskInfoAsString()).append("] took ").append(task.getTimeMillis());
                long percent = Math.round((100.0 * task.getTimeSeconds()) / getTotalTimeSeconds());
                sb.append(" = ").append(percent).append("%");
            }
        } else {
            sb.append("; no task info kept");
        }
        return sb.toString();
    }

    /**
     * Inner class to hold data about one task executed within the stop watch.
     */
    public static final class TaskInfo<T> {

        private final T taskInfo;

        private final long timeMillis;

        TaskInfo(T taskInfo, long timeMillis) {
            this.taskInfo = taskInfo;
            this.timeMillis = timeMillis;
        }

        /**
         * Return the name of this task.
         */
        public T getTaskInfo() {
            return this.taskInfo;
        }

        public String getTaskInfoAsString() {
            if (this.taskInfo != null) {
                return this.taskInfo.toString();
            }
            return "";
        }

        /**
         * Return the time in milliseconds this task took.
         */
        public long getTimeMillis() {
            return this.timeMillis;
        }

        /**
         * Return the time in seconds this task took.
         */
        public double getTimeSeconds() {
            return (this.timeMillis / 1000.0);
        }
    }

}
