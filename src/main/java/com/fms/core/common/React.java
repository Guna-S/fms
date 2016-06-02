package com.fms.core.common;

import static java.util.concurrent.CompletableFuture.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 *
 * A monad Reactive sequential execution of functions
 *
 * Created by Ganesan on 29/05/16.
 */
public class React<T> {

    private CompletableFuture<T> completableFuture;

    private React(CompletableFuture<T> completableFuture) {
        this.completableFuture = completableFuture;
    }

    public static <T> React<T> of(T t) {
        return of(completedFuture(t));
    }

    public static <T> React<T> of(React<T> t) {
        return of(t.get());
    }

    public static <T> React<T> of(Supplier<T> t) {
        return new React<>(supplyAsync(t));
    }

    public static <T> React<T> of(CompletableFuture<T> t) {
        return new React<>(t);
    }

    public static <T> React<T> of(Promise<T> t) {
        return of(t.getFuture());
    }

    public <U> React<U> then(Do<T, U> doNext) {
        return then(doNext.get());
    }

    public <U> React<U> then(Function<T, U> function) {
         return thenCF(t -> supplyAsync(() -> function.apply(t)));
    }

    public React<T> thenV(Consumer<T> function) {
        return then(t -> { function.accept(t); return t; });
    }

    public React<Void> thenVoid(Consumer<T> function) {
        return then(t -> { function.accept(t); return null; });
    }

    public <U> React<U> thenCF(Function<T, CompletableFuture<U>> function) {
        return new React<>(completableFuture.thenCompose(t -> function.apply(t)));
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