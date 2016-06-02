package com.fms.core.document;

import com.fms.core.common.ErrorCodeAndParam;
import com.fms.core.model.CategoryDocType;
import com.fms.core.common.ErrorCode;
import com.fms.core.common.TwoTrack;
import com.fms.core.model.DocumentModel;
import javaslang.Tuple2;
import javaslang.control.Try;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Ganesan on 02/06/16.
 */
public class DocumentUtil {

    public static Function<Tuple2<String, CategoryDocType>, DocumentInfo> getDocumentWithFileLocation(
            final UploadInfo info) {
        return getDocumentWithFileLocation(DocumentInfo.builder(info).build());

    }

    public static Function<Tuple2<String, CategoryDocType>, DocumentInfo> getDocumentWithFileLocation(
            final DocumentInfo info) {
        return (tuple) -> DocumentConverter.build(info).apply(Stream.of(tuple._1,
                tuple._2.getUploadCategory().getName(),
                info.getUploaderId(),
                tuple._2.getType(),
                info.getFileName()).collect(Collectors.joining("/")));

    }

    public static Function<DocumentModel, TwoTrack<DocumentModel>> fileWritter(MultipartFile file) {
        return (doc) -> Try.of(() -> file.getInputStream())
                .mapTry(is -> Files.copy(is, Paths.get(doc.getFileLocation())))
                .mapTry(l -> TwoTrack.of(doc))
                .getOrElseGet((e) -> TwoTrack.of(new ErrorCodeAndParam(e, ErrorCode.FILE_WRTING_FAILED)));
    }
}
