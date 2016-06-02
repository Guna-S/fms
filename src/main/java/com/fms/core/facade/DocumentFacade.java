package com.fms.core.facade;

import com.fms.core.config.FmsConfig;
import com.fms.core.converter.DocumentConverter;
import com.fms.core.dto.DocumentInfo;
import com.fms.core.model.Document;
import com.fms.core.repository.DocumentRepository;
import com.fms.core.util.*;
import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;

import static com.fms.core.util.FunctionUtils.asTwoTrack;

public class DocumentFacade {



    public static Function<Tuple2<FmsConfig, Function<Document, TwoTrack<Document>>>, Promise<TwoTrack<DocumentInfo>>> save(final
    DocumentInfo documentInfo) {
        return (arg) -> React.of(() -> documentInfo)
            .thenP(info -> CategoryDocTypeFacade.findCategoryDocType
                (info.getDocumentTypeId()).apply(arg._1.getCategoryDocTypeRepository()))
            .then(cdt -> Tuple.of(DocumentUtil.getDocumentWithFileLocation(documentInfo).apply(Tuple.of(arg._1.getRootFolder(),
                cdt)), cdt))
            .then(tuple -> DocumentConverter.convert(tuple._1).apply(tuple._2))
            .then(document -> arg._2.apply(document))
            .then(asTwoTrack(s-> arg._1.getDocumentRepository().save(s)))
            .then(asTwoTrack(DocumentConverter::convertTo))
            .getPromise();
    }

    public static Function<Document, TwoTrack<Document>> fileWritter(final com.fms.core.dto.Document document) {
        return (doc) -> Try.of(() -> document.getFile().getInputStream())
            .mapTry(is -> Files.copy(is, Paths.get(doc.getFileLocation())))
            .mapTry(l -> TwoTrack.of(doc))
            .getOrElseGet((e) -> TwoTrack.of(ErrorCode.FILE_WRTING_FAILED));
    }

    public static Function<FmsConfig, Promise<List<DocumentInfo>>> documents(final String uploaderId) {
        return (config) -> React.of(() -> config.getDocumentRepository()
                .findByDocumentUploaderId(uploaderId))
                .then(FunctionUtils.asList(DocumentConverter::convertTo))
                .getPromise();
    }

    public static Function<FmsConfig, Promise<Long>> removeFile(final Long theId) {
        return config -> React.of(() -> theId).thenV(id -> config.getDocumentRepository().delete(id)).getPromise();
    }
}
