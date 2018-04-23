package com.esfak47.common.utils;

import com.esfak47.common.logger.Logger;
import com.esfak47.common.logger.LoggerFactory;
import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author tony
 * @date 2018/4/24
 */
public class PromiseTest {

    private Logger logger = LoggerFactory.getLogger(PromiseTest.class);
    @Test
    public void testPromise() {
        Promise.promise((PromiseInterface<Long, String>)(resolve, reject) -> {
            try {
                logger.info("start");
                Thread.sleep(1000);
                logger.info("end");
                resolve.accept(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }).then(aLong -> {
            return aLong.intValue();
        }).then(integer -> {
            logger.info(String.valueOf(integer));
        })
        .join();


    }

}