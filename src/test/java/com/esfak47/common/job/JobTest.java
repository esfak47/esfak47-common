package com.esfak47.common.job;

import org.junit.Test;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author tony
 * @date 2018/4/24
 */
public class JobTest {

    @Test
    public void test() {
        ServiceLoader<JobManager> jobManagers = ServiceLoader.load(JobManager.class);
        Iterator<JobManager> jobManagerIterator = jobManagers.iterator();


    }

}
