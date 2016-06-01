package com.fms.core.util;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.concurrent.CompletableFuture.supplyAsync;

public class React<T> {

    private final CompletableFuture<T> completableFuture;

    private React(final CompletableFuture<T> completableFuture) {
        this.completableFuture = completableFuture;
    }

    public static <T> React<T> of(final T t) {
        return of(completedFuture(t));
    }


    public static <T> React<T> of(final Promise<T> t) {
        return of(t.get());
    }

    public static <T> React<T> of(final React<T> t) {
        return of(t.get());
    }

    public static <T> React<T> of(final Supplier<T> t) {
        return of(supplyAsync(t));
    }

    public static <T> React<T> of(final CompletableFuture<T> t) {
        return new React<>(t);
    }

    public <U> React<U> then(final Function<T, U> function) {
        return thenWithCF(t ->
            supplyAsync(() -> function.apply(t)));
    }

    public React<T> thenWithVoid(final Consumer<T> function) {
        return then(t -> {
            function.accept(t);
            return t;
        });
    }

    public <U> React<U> thenWithCF(final Function<T, CompletableFuture<U>> function) {
        return new React<>(completableFuture.thenCompose(t -> function.apply(t)));
    }

    public <U> React<U> thenWithReact(final Function<T, React<U>> function) {
        return thenWithCF(t -> function.apply(t).get());
    }


    public CompletableFuture<T> get() {
        return completableFuture;
    }

    public Promise<T> getPromise() {
        return Promise.of(completableFuture);
    }

}
