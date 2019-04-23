package com.esfak47.common;

import com.esfak47.common.lang.Assert;
import com.esfak47.common.lang.CodeMessageProvider;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

/**
 * @author tony
 */
public class Result<T> {

    private int code = 20000;
    private String message;

    private boolean success;
    private T data;

    public Result(boolean success) {
        this.success = success;
    }

    public static <T> Result<T> fail() {
        return new Result<>(false);
    }

    public static <T> Result<T> failWithReason(CodeMessageProvider provider) {
        Result<T> tResult = new Result<>(false);
        tResult.setData(null);
        tResult.setMessage(provider.getMessage());
        tResult.setCode(provider.getCode());
        return tResult;
    }

    public static <T> Result<T> failWithThrowable(Throwable throwable) {
        Result<T> tResult = new Result<>(false);
        tResult.setData(null);
        tResult.setMessage(throwable.getMessage());
        return tResult;
    }

    public static <T> Result<T> getFromAsync(Future<T> completableFuture) {
        return getFromAsync(completableFuture, 5000L);
    }

    public static <T> Result<T> success(T data) {
        Result<T> tResult = new Result<>(true);
        tResult.setData(data);
        return tResult;

    }

    public static Result<Void> success() {
        return new Result<>(true);
    }

    public static <T> Result<T> getFromAsync(Future<T> future, long timeOutInMillSeconds) {
        Assert.notNull(future, "completableFuture should not be null");
        try {
            T data = future.get(timeOutInMillSeconds, TimeUnit.MILLISECONDS);
            return Result.success(data);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return failWithThrowable(e);
        } catch (ExecutionException | TimeoutException e) {
            return failWithThrowable(e);
        }
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void ifPresent(Consumer<? super T> consumer) {
        if (data != null) {
            consumer.accept(data);
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Optional<T> toOptional() {
        return Optional.ofNullable(this.data);
    }


}
