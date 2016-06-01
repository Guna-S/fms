package com.fms.core.facade;

import com.fms.core.converter.DocumentConverter;
import com.fms.core.dto.DocumentInfo;
import com.fms.core.model.Document;
import com.fms.core.repository.DocumentRepository;
import com.fms.core.util.*;
import javaslang.Tuple;
import javaslang.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;

import static com.fms.core.util.FunctionUtils.asTwoTrack;

@Component
public class DocumentFacade {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private CategoryDocTypeFacade facade;

    @Value("${file.rootFolder}")
    private String rootFolder;


    public Function<Function<Document, TwoTrack<Document>>, Promise<TwoTrack<DocumentInfo>>> save(final
    DocumentInfo documentInfo) {
        return (fileProcessor) -> React.of(documentInfo)
            .thenWithCF(info -> facade.findCategoryDocType
                (info.getDocumentTypeId()).get())
            .then(cdt -> Tuple.of(DocumentUtil.getDocumentWithFileLocation(documentInfo).apply(Tuple.of(rootFolder,
                cdt)), cdt))
            .then(tuple -> DocumentConverter.convert(tuple._1).apply(tuple._2))
            .then(document -> fileProcessor.apply(document))
            .then(asTwoTrack(documentRepository::save))
            .then(asTwoTrack(DocumentConverter::convertTo))
            .getPromise();
    }

    public static Function<Document, TwoTrack<Document>> fileWritter(final com.fms.core.dto.Document document) {
        return (doc) -> Try.of(() -> document.getFile().getInputStream())
            .mapTry(is -> Files.copy(is, Paths.get(doc.getFileLocation())))
            .mapTry(l -> TwoTrack.of(doc))
            .getOrElseGet((e) -> TwoTrack.of(ErrorCode.FILE_WRTING_FAILED));
    }

    public Promise<List<DocumentInfo>> documents(final String uploaderId) {
        return React.of(() -> documentRepository.findByDocumentUploaderId(uploaderId))
            .then(FunctionUtils.asList(DocumentConverter::convertTo)).getPromise();
    }

    public Promise<Long> removeFile(final Long id) {
        return React.of(id).thenWithVoid(documentRepository::delete).getPromise();
    }
}
