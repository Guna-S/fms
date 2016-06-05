package com.fms.core;

import com.fms.core.common.*;
import com.google.common.base.Function;

/**
 * Created by Ganesan on 04/06/16.
 */
public class TwpTraclTest {

    private static TwoTrack<String> validate(String testString) {
        if(testString.equals("test")){
            return  TwoTrack.of(new ErrorCodeAndParam(new Exception("test error"), ErrorCode.FILE_WRTING_FAILED));
        } else {
            return TwoTrack.of(testString);
        }

    }

    public static void main(String[] args) {

       TwoTrack<String> twoTrack = Do.of("test1")
                .then(TwpTraclTest::validate)
                .then(FunctionUtils.asTwoTrack(String::toLowerCase))
                .getEmmpty();

        System.out.println(twoTrack.get(ec -> ec.getCause().getMessage()));
    }
}
