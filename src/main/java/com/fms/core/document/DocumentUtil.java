package com.fms.core.document;

import com.fms.core.common.Builder;
import com.fms.core.common.ErrorCode;
import com.fms.core.common.ErrorCodeAndParam;
import com.fms.core.common.TwoTrack;
import com.fms.core.model.CategoryDocType;
import com.fms.core.model.DocumentModel;
import javaslang.Tuple2;
import javaslang.control.Try;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Ganesan on 02/06/16.
 * <p></p>
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

    public static Function<DocumentModel, TwoTrack<DocumentModel>> fileWritter(final MultipartFile file) {
        return doc -> Try.of(() -> {
                    Path path = Paths.get(doc.getFileLocation());
                    if(!Files.exists(path.getParent())) {
                        Files.createDirectories(path.getParent());
                    }
                    return path;
                }).mapTry(path -> Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING))
                .mapTry(l -> TwoTrack.of(doc))
                .getOrElseGet(e-> TwoTrack.of(new ErrorCodeAndParam(ErrorCode.FILE_WRITING_FAILED)));
    }

    public static Function<CategoryDocType,TwoTrack<CategoryDocType>> validateFileSequence(final UploadInfo uploadInfo){
        return categoryDocType -> {
            TwoTrack<CategoryDocType> categoryDocTypeTwoTrack = TwoTrack.of(categoryDocType);
            if(!categoryDocType.isMultiple() && uploadInfo.getFileSequence().intValue() != 0) {
                categoryDocTypeTwoTrack = TwoTrack.of(new ErrorCodeAndParam(ErrorCode.DOC_FILE_SEQUENCE));
            }
            return categoryDocTypeTwoTrack;
        };
    }



}
