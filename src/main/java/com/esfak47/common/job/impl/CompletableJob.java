package com.esfak47.common.job.impl;

import com.esfak47.common.job.Job;
import com.esfak47.common.lang.Assert;

import java.util.Date;
import java.util.function.Consumer;

/**
 * @author tony
 */
public class CompletableJob implements Job {
    private final String id;

    private transient Runnable runnable;
    private boolean finish;

    private boolean cancelled;
    private boolean exitUnexpectedly;
    private int process = 0;
    private Date createTime;
    private Date finishTime;
    private String name;

    private CompletableJob(String id) {
        this.id = id;
        createTime = new Date();
    }

    public static CompletableJob create(String name, String id, Consumer<CompletableJob> consumer) {
        Assert.notNull(consumer, "consumer should not be null");
        CompletableJob completableJob = new CompletableJob(id);
        completableJob.setName(name);
        completableJob.setRunnable(() -> {
            consumer.accept(completableJob);
        });

        return completableJob;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getProcess() {
        return process;
    }

    @Override
    public void setProcess(int process) {
        this.process = process;
    }

    @Override
    public boolean isFinish() {
        return finish;
    }

    @Override
    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isExitUnexpectedly() {
        return exitUnexpectedly;
    }

    @Override
    public void setExitUnexpectedly(boolean exitUnexpectedly) {
        this.exitUnexpectedly = exitUnexpectedly;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setFinishTime() {
        this.finishTime = new Date();
    }

    @Override
    public boolean isAsync() {
        return false;
    }

    @Override
    public void run() {
        if (runnable != null) {
            runnable.run();
        }
    }

    @Override
    public Date getFinishTime() {
        return finishTime;
    }

}
