package com.fms.core.util;

import java.util.function.Consumer;
import java.util.function.Function;

public interface TwoTrack<T> {

    static <T> TwoTrack<T> of(final T val) {
        return new SuccessTrack<>(val);
    }

    static <T> TwoTrack<T> of(final ErrorCode error) {
        return new FailureTrack<>(error);
    }

    T get();

    ErrorCode getErrorCode();

    boolean isSuccess();

    <R> TwoTrack<R> map(Function<T, R> function);

    void onSuccess(Consumer<T> success);
    void onFailure(Consumer<ErrorCode> failure);

    class SuccessTrack<T> implements TwoTrack<T> {

        private final T val;

        private SuccessTrack(final T val) {
            this.val = val;
        }

        @Override
        public T get() {
            return val;
        }

        @Override
        public ErrorCode getErrorCode() {
            return null;
        }

        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        public <R> TwoTrack<R> map(final Function<T, R> function) {
            return TwoTrack.of(function.apply(get()));
        }

        @Override
        public void onSuccess(final Consumer<T> success) {
            success.accept(val);
        }

        @Override
        public void onFailure(final Consumer<ErrorCode> success) {

        }


    }

    class FailureTrack<T> implements TwoTrack<T> {

        private final ErrorCode errorCode;

        private FailureTrack(final ErrorCode errorCode) {
            this.errorCode = errorCode;
        }

        @Override
        public T get() {
            return null;
        }

        @Override
        public ErrorCode getErrorCode() {
            return errorCode;
        }

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public <R> TwoTrack<R> map(final Function<T, R> function) {
            return TwoTrack.of(errorCode);
        }

        @Override
        public void onSuccess(final Consumer<T> success) {

        }

        @Override
        public void onFailure(final Consumer<ErrorCode> failure) {
            failure.accept(errorCode);
        }
    }


}
