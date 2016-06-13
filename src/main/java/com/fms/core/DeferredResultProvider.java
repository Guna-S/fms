package com.fms.core;

import com.fms.core.categorydoctype.CategoryDocTypeInfo;
import com.fms.core.common.Promise;
import com.fms.core.common.React;
import com.fms.core.common.TwoTrack;
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
            t.onFailure(e -> deferredResult.setErrorResult(new ResponseEntity<>(e,e.getHttpStatus())));
        }).failure(deferredResult::setErrorResult);
        return deferredResult;
    }

    public static <T> DeferredResult<ResponseEntity<T>> createDeferredResult(final Promise<T> task,
                                                                             final HttpStatus httpStatus) {
        return createDeferredResultTwoTrack(React.of(task).then(TwoTrack::of).getPromise(), httpStatus);
    }

    public static void main(final String[] args) {
        final AsyncRestTemplate asycTemp = new AsyncRestTemplate();
        final String url ="http://localhost:8091/fms/categorydoctypes/4";
        final HttpMethod method = HttpMethod.GET;
        final HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        final HttpEntity<String> requestEntity = new HttpEntity<String>("params", requestHeaders);
        final ListenableFuture<ResponseEntity<CategoryDocTypeInfo>>   future = asycTemp.exchange(url, method, requestEntity,
            CategoryDocTypeInfo.class);
        future.addCallback(new ListenableFutureCallback<ResponseEntity<CategoryDocTypeInfo>>() {
            @Override
            public void onFailure(final Throwable ex) {
                System.out.println("fail"+ex);
            }

            @Override
            public void onSuccess(final ResponseEntity<CategoryDocTypeInfo> result) {
                System.out.println("success");
            }
        });
    }
}
