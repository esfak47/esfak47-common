package com.esfak47.common.utils;

import com.esfak47.common.lang.Assert;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author tony
 * @date 2018/4/24
 */
public class Promise<T> {

    private CompletableFuture<T> completableFuture;
    private Consumer<Throwable> errorConsumer = throwable -> {
    };
    private Executor executor;

    private Promise(CompletableFuture<T> completableFuture) {
        this.completableFuture = completableFuture;
    }

    public static <T> Promise<T> promise(PromiseInterface<T> promiseInterface) {
        return promise(promiseInterface, PromiseExecutorHolder.executor);
    }

    public static <T> Promise<T> promise(PromiseInterface<T> promiseInterface, Executor executor) {
        Assert.notNull(promiseInterface, "promiseInterface should not be null");
        Assert.notNull(executor, "executor should not be null");
        CompletableFuture<T> completableFuture = new CompletableFuture<T>();
        Promise<T> promise = new Promise<>(completableFuture);
        completableFuture.exceptionally(throwable -> {
            promise.errorConsumer.accept(throwable);
            return null;
        });
        promise.executor = executor;
        executor.execute(() -> {
            promiseInterface.go(t -> completableFuture.complete(t),
                    u -> completableFuture
                            .completeExceptionally(u instanceof CompletionException ? u : new CompletionException(u)));
        });

        return promise;

    }

    public void join() {
        try {
            this.completableFuture.join();
        } catch (Throwable throwable) {
            //this.errorConsumer.accept(throwable);
        }

    }

    public <R> Promise<R> then(Function<T, R> function) {

        CompletableFuture<R> completableFuture = this.completableFuture.thenComposeAsync(
                t -> CompletableFuture.completedFuture(function.apply(t)), this.executor);

        Promise<R> promise = new Promise<>(completableFuture);
        promise.errorConsumer = this.errorConsumer;
        promise.executor = this.executor;

        return promise;
    }

    public Promise<Void> then(Consumer<T> consumer) {
        CompletableFuture<Void> completableFuture = this.completableFuture.thenAcceptAsync(consumer, this.executor);

        Promise<Void> promise = new Promise<>(completableFuture);
        promise.errorConsumer = this.errorConsumer;
        promise.executor = this.executor;
        return promise;
    }

    public Promise<T> catchEx(Consumer<Throwable> throwableConsumer) {
        this.errorConsumer = this.errorConsumer.andThen(throwableConsumer);
        return this;
    }

    private static final class PromiseExecutorHolder {
        private static Executor executor = Executors.newFixedThreadPool(100);
    }

}
