package com.fms.core.facade;

import com.fms.core.converter.DocumentConverter;
import com.fms.core.dto.DocumentInfo;
import com.fms.core.model.CategoryDocType;
import com.google.common.base.Function;
import javaslang.Tuple2;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DocumentUtil {

    public static Function<Tuple2<String, CategoryDocType>, DocumentInfo> getDocumentWithFileLocation(
        final DocumentInfo info) {
        return (tuple) -> DocumentConverter.build(info).apply(Stream.of(tuple._1,
                tuple._2.getUploadCategory().getName(),
                info.getUploaderId(),
                tuple._2.getType(),
                info.getFileName()).collect(Collectors.joining("/")));

    }
}
