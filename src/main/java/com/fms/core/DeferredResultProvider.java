package com.fms.core;

import com.fms.core.categorydoctype.CategoryDocTypeInfo;
import com.fms.core.common.Promise;
import com.fms.core.common.React;
import com.fms.core.common.TwoTrack;
import com.sun.tools.hat.internal.model.StackTrace;
import org.springframework.http.*;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Arrays;

public class DeferredResultProvider {

    public static <T> DeferredResult<ResponseEntity<T>> createDeferredResultTwoTrack(final Promise<TwoTrack<T>> task,
                                                                                     final HttpStatus httpStatus) {
        final DeferredResult<ResponseEntity<T>> deferredResult = new DeferredResult<>();
        task.success((t) -> {

            t.onSuccess(v -> deferredResult.setResult(new ResponseEntity<>(v, httpStatus)));
            t.onFailure(e -> {
                System.out.println(e.getErrorCode() +" : " + e.getHttpStatus() +" : "+ Arrays.asList(e.getParams()));
                for(StackTraceElement stackTraceElement : e.getStackTrace()){
                    System.out.println(stackTraceElement);
                }
                deferredResult.setErrorResult(new ResponseEntity<>(e, e.getHttpStatus()));
            });
        }).failure(deferredResult::setErrorResult);
        return deferredResult;
    }

    public static <T> DeferredResult<ResponseEntity<T>> createDeferredResult(final Promise<T> task,
                                                                             final HttpStatus httpStatus) {
        return createDeferredResultTwoTrack(React.of(task).then(TwoTrack::of).getPromise(), httpStatus);
    }

}
