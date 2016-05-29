package com.fms.core;

import com.fms.core.util.OptionalExt;
import com.fms.core.util.Promise;
import com.fms.core.util.React;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;

public class DeferredResultProvider {

    public static <T> DeferredResult<ResponseEntity<T>> createDeferredResult(final Promise<T> task, final HttpStatus httpStatus) {
        final DeferredResult<ResponseEntity<T>> deferredResult = new DeferredResult<>();
        task.success((t) -> deferredResult.setResult(new ResponseEntity<>(t, httpStatus)))
                .failure(deferredResult::setErrorResult);
        return deferredResult;
    }
}
