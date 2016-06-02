package com.fms.core.document;

import com.fms.core.categorydoctype.CategoryDocTypeFacade;
import com.fms.core.repository.DocumentRepository;
import com.fms.core.common.*;
import javaslang.Tuple;

import java.util.List;

import static com.fms.core.common.FunctionUtils.asTwoTrack;

public class DocumentFacade {



    public static Reader<DocumentConfig, Promise<TwoTrack<DocumentInfo>>> save(final
    UploadInfo uploadInfo) {
        return Reader.of(config -> React.of(() -> uploadInfo)
            .thenP(info -> CategoryDocTypeFacade.findCategoryDocType
                (info.getDocumentTypeId()).with(config.getCategoryDocTypeRepository()))
            .then(cdt -> Tuple.of(DocumentUtil.getDocumentWithFileLocation(uploadInfo).apply(Tuple.of(config.getRootFolder(),
                cdt)), cdt))
            .then(tuple -> DocumentConverter.convert(tuple._1).apply(tuple._2))
            .then(document -> config.getFileWriter().apply(document))
            .then(asTwoTrack(s-> config.getDocumentRepository().save(s)))
            .then(asTwoTrack(DocumentConverter::convertTo))
            .getPromise());
    }

    public static Reader<DocumentRepository, Promise<List<DocumentInfo>>> documents(final String uploaderId) {
        return Reader.of(repo -> React
                .of(() ->  repo.findByDocumentUploaderId(uploaderId))
                .then(FunctionUtils.asList(DocumentConverter::convertTo))
                .getPromise());
    }

    public static Reader<DocumentRepository, Promise<Long>> removeFile(final Long theId) {
        return Reader.of(repo -> React
                .of(() -> theId)
                .thenV(repo::delete)
                .getPromise());
    }
}
