package com.esfak47.common.utils;

import com.esfak47.common.lang.Assert;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author tony
 * @date 2018/4/24
 */
public class Promise<T, U> implements BiConsumer<T, Throwable> {

    private CompletableFuture<T> completableFuture;

    private Consumer<U> errorConsumer = throwable -> {
    };
    private Executor executor;

    private Promise(CompletableFuture<T> completableFuture) {this.completableFuture = completableFuture;}

    public static <T, U> Promise<T, U> promise(PromiseInterface<T, U> promiseInterface) {
        return promise(promiseInterface, PromiseExecutorHolder.executor);
    }
    public void join(){
        this.completableFuture.join();
    }

    public static <T, U> Promise<T, U> promise(PromiseInterface<T, U> promiseInterface, Executor executor) {
        Assert.notNull(promiseInterface, "promiseInterface should not be null");
        Assert.notNull(executor, "executor should not be null");
        CompletableFuture<T> completableFuture = new CompletableFuture<T>();
        Promise<T, U> promise = new Promise<>(completableFuture);
        completableFuture.whenCompleteAsync(promise, executor);
        promise.executor = executor;
        executor.execute(() -> promiseInterface.go(t -> completableFuture.complete(t),
            u -> completableFuture.completeExceptionally(new PromiseException(u))));
        return promise;

    }

    public <R> Promise<R, U> then(Function<T, R> function) {

        CompletableFuture<R> completableFuture = this.completableFuture.thenComposeAsync(
            t -> CompletableFuture.completedFuture(function.apply(t)),this.executor);
        Promise<R, U> promise = new Promise<>(completableFuture);
        promise.errorConsumer = this.errorConsumer;
        promise.executor=this.executor;
        return promise;
    }

    public Promise<Void, U> then(Consumer<T> function) {

        CompletableFuture<Void> completableFuture = this.completableFuture.thenAcceptAsync(function,this.executor);
        Promise<Void, U> promise = new Promise<>(completableFuture);
        promise.errorConsumer = this.errorConsumer;
        promise.executor=this.executor;
        return promise;
    }

    public Promise<T, U> catchEx(Consumer<U> throwableConsumer) {
        this.errorConsumer = throwableConsumer;
        return this;
    }

    @Override
    public void accept(T t, Throwable throwable) {
        if (throwable != null) {
            if (throwable instanceof PromiseException) {
                PromiseException throwable1 = (PromiseException)throwable;
                this.errorConsumer.accept(((U)((PromiseException)throwable).data));
            }

        }
    }

    private static final class PromiseExecutorHolder {
        private static Executor executor = Executors.newFixedThreadPool(100);
    }

    private final static class PromiseException extends RuntimeException {

        Object data;

        public PromiseException(Object data) {
            this.data = data;
        }
    }
}
