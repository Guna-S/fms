package com.fms.core;

import com.fms.core.common.*;

/**
 * Created by Ganesan on 04/06/16.
 */
public class TwpTraclTest {

    private static TwoTrack<String> validate(final String testString) {
        if(testString.equals("test")){
            return  TwoTrack.of(new ErrorCodeAndParam(new Exception("test error"), ErrorCode.FILE_WRITING_FAILED));
        } else {
            return TwoTrack.of(testString);
        }

    }

    public static void main(final String[] args) {

       final TwoTrack<String> twoTrack = Do.of("test1")
                .then(TwpTraclTest::validate)
                .then(FunctionUtils.asTwoTrack(String::toLowerCase))
                .getEmmpty();

        System.out.println(twoTrack.get(ec -> ec.getCause().getMessage()));
    }
}
