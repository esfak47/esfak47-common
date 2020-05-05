package com.esfak47.common.utils;

import com.esfak47.common.utils.thread.Promise;
import com.esfak47.common.utils.thread.PromiseInterface;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletionException;

/**
 * @author tony
 * @date 2018/4/24
 */
public class PromiseTest {

    private Logger logger = LoggerFactory.getLogger(PromiseTest.class);

    @Test
    public void testPromise() {
        Promise.promise((PromiseInterface<Long>) (resolve, reject) -> {
            try {
                logger.info("start");
                Thread.sleep(1000);
                logger.info("end");
                resolve.accept(1000L);
            } catch (InterruptedException ignored) {

            }

        }).then(Long::intValue).then(integer -> {
            logger.info(String.valueOf(integer));
        })
                .join();

    }

    @Test
    public void testPromiseError() throws InterruptedException {
        Promise.promise((PromiseInterface<Long>) (resolve, reject) -> {
            try {
                logger.info("start");
                Thread.sleep(1000);
                reject.accept(new NullPointerException());
            } catch (InterruptedException e) {
                reject.accept(e);
            }

        }).catchEx(throwable -> {

            Assert.assertTrue(throwable instanceof NullPointerException);
            logger.error("assset error",throwable);
        }).then(Long::intValue).then(integer -> {
            logger.info(String.valueOf(integer));
        });
        Thread.sleep(5000);

    }

    @Test
    public void testPromiseErrorJoin() {
        Promise.promise((PromiseInterface<Long>) (resolve, reject) -> {
            try {
                logger.info("start");
                Thread.sleep(1000);
                reject.accept(new NullPointerException());
            } catch (InterruptedException e) {
                reject.accept(e);
            }

        }).catchEx(throwable -> {
            logger.error("catch error",throwable);
            Assert.assertTrue(throwable instanceof CompletionException);

        })
                .then(Long::intValue).then(integer -> {
            logger.info(String.valueOf(integer));
        }).join();

    }

}