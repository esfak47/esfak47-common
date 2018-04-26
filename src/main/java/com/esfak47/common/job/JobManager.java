package com.esfak47.common.job;

import com.esfak47.common.extension.SPI;
import com.esfak47.common.PageResult;

@SPI("default")
public interface JobManager {

    /**
     * 提交任务
     *
     * @param job
     * @return
     */
    boolean submit(Job job);

    /**
     * 取消任务
     *
     * @param jobId
     * @return
     */
    boolean cancel(String jobId);

    /**
     * 取消任务
     *
     * @param jobId
     * @return
     */
    boolean delete(String jobId);

    /**
     * 分页获取任务列表
     *
     * @param page     页
     * @param pageSize 页大小
     * @return {@link PageResult}
     */
    PageResult<Job> getJobs(int page, int pageSize);

    Job getJob(String id);

}
