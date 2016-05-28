package com.fms.core;

import com.fms.core.util.OptionalExt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;

public class DeferredResultProvider {

    public static <T> DeferredResult<ResponseEntity<T>> createDeferredResult(final CompletableFuture<T> task,
        final HttpStatus httpStatus) {

        final DeferredResult<ResponseEntity<T>> deferredResult = new DeferredResult<>();
        task.whenCompleteAsync((result, error) ->
            OptionalExt.of(error)
                .ifPresentOrElse(
                    deferredResult::setErrorResult,
                    () -> deferredResult.setResult(new ResponseEntity<T>(result, httpStatus))));
        return deferredResult;
    }
}
