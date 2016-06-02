package com.fms.core.util;

import static java.util.concurrent.CompletableFuture.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by Ganesan on 29/05/16.
 */
public class React<T> {

    private CompletableFuture<T> completableFuture;

    private React(CompletableFuture<T> completableFuture) {
        this.completableFuture = completableFuture;
    }

    public static <T> React<T> of(React<T> t) {
        return new React<>(t.get());
    }

    public static <T> React<T> of(Supplier<T> t) {
        return new React<>(supplyAsync(t));
    }

    public static <T> React<T> of(CompletableFuture<T> t) {
        return new React<>(t);
    }

    public static <T> React<T> of(Promise<T> t) {
        return new React<>(t.getFuture());
    }

    public <U> React<U> then(Function<T, U> function) {
         return thenCF(t -> supplyAsync(() -> function.apply(t)));
    }

    public React<T> thenV(Consumer<T> function) {
        return then(t -> { function.accept(t); return t; });
    }

    public <U> React<U> thenCF(Function<T, CompletableFuture<U>> function) {
        return new React<>(completableFuture.thenComposeAsync(t -> function.apply(t)));
    }

    public <U> React<U> thenR(Function<T, React<U>> function) {
        return thenCF(t -> function.apply(t).get());
    }

    public <U> React<U> thenP(Function<T, Promise<U>> function) {
        return thenCF(t -> function.apply(t).getFuture());
    }


    public CompletableFuture<T> get() {
        return completableFuture;
    }

    public Promise<T> getPromise() {
        return  Promise.of(completableFuture);
    }

}
