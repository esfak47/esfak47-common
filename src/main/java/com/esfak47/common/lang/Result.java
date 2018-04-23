package com.esfak47.common.lang;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public class Result<T> {
    private final static Result FAIL = new Result(false);

    private int code = 20000;
    private String message;

    private boolean success;
    private T data;
    private Throwable throwable;

    public Result(boolean success) {
        this.success = success;
    }

    public static <T> Result<T> fail() {
        return FAIL;
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
        tResult.throwable = throwable;
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

    public static <T> Result<T> getFromAsync(Future<T> completableFuture, long timeOutInMillSeconds) {
        Assert.notNull(completableFuture, "completableFuture should not be null");
        try {
            T data = completableFuture.get(timeOutInMillSeconds, TimeUnit.MILLISECONDS);
            return Result.success(data);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            return failWithThrowable(e);
        }
    }

    public void ifPresent(Consumer<? super T> consumer) {
        if (data != null) {consumer.accept(data);}
    }

    public void setData(T data) {
        this.data = data;
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

    public Throwable getThrowable() {
        return throwable;
    }
}
