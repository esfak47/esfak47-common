package com.esfak47.common.job.impl;

import com.esfak47.common.job.Job;
import com.esfak47.common.job.JobManager;
import com.esfak47.common.lang.Assert;
import com.esfak47.common.PageResult;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

/**
 * @author tony
 */
public class DefaultJobManagerImpl implements JobManager {

    private Map<String, Job> jobContainer;

    private Map<String, CompletableFuture> futureMap;

    private Executor executor;

    private static <T> List<T> getPartOf(Collection<T> collection, int start, int end) {
        Assert.notNull(collection,"collection should not be null");

        int size = collection.size();
        if (size <= start) {
            return Collections.emptyList();
        } else if (size > start && size < end + 1) {
            int i = 0;
            List<T> list = new ArrayList<>();
            Iterator<T> iterator = collection.iterator();
            while (iterator.hasNext() && i < start) {
                i++;
                iterator.next();
            }
            while (iterator.hasNext()) {
                list.add(iterator.next());
            }
            return list;

        } else {
            int i = 0;
            List<T> list = new ArrayList<>();
            Iterator<T> iterator = collection.iterator();
            while (iterator.hasNext() && i < start) {
                i++;
                iterator.next();
            }
            while (iterator.hasNext() && i <= end) {
                list.add(iterator.next());
            }
            return list;
        }

    }


    public void init() {

        jobContainer = new ConcurrentHashMap<>();
        futureMap = new ConcurrentHashMap<>();

    }

    @Override
    public boolean submit(Job job) {
        Assert.notNull(job,"job should not be null");
        String jobId = job.getId();
        Assert.notNull(jobId,"jobId should not be null");
        if (jobContainer.containsKey(jobId) || futureMap.containsKey(jobId)) {
            return false;
        }
        jobContainer.put(jobId, job);
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(job, executor);
        futureMap.put(jobId, voidCompletableFuture);
        voidCompletableFuture.whenComplete((aVoid, throwable) -> {
            job.setFinish(voidCompletableFuture.isDone());
            job.setExitUnexpectedly(voidCompletableFuture.isCompletedExceptionally());
            job.setCancelled(voidCompletableFuture.isCancelled());
            job.setFinishTime();
            futureMap.remove(jobId);
        });

        return true;
    }

    @Override
    public boolean cancel(String jobId) {
        Assert.notNull(jobId,"jobId should not be null");
        if (!jobContainer.containsKey(jobId) || !futureMap.containsKey(jobId)) {
            return false;
        }
        CompletableFuture completableFuture = futureMap.get(jobId);
        return completableFuture.cancel(true);
    }

    @Override
    public boolean delete(String jobId) {
        Assert.notNull(jobId,"jobId should not be null");
        if (!jobContainer.containsKey(jobId) || !futureMap.containsKey(jobId)) {
            return true;
        }
        boolean cancel = this.cancel(jobId);

        if (cancel) {
            jobContainer.remove(jobId);
            return true;
        }

        return false;
    }

    @Override
    public PageResult<Job> getJobs(int page, int pageSize) {

        int start = (page - 1) * pageSize;
        int end = page * pageSize - 1;

        Collection<Job> values = jobContainer.values();

        return PageResult.createPageResult(page, pageSize, values.size(), getPartOf(values, start, end));

    }

    @Override
    public Job getJob(String id) {
        return jobContainer.get(id);
    }
}