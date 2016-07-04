CREATE TABLE FMS_MA_UPLOAD_CATEGORY (
              UC_ID BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
              UC_DESC VARCHAR(255),
              UC_NAME VARCHAR(255),
              PRIMARY KEY (UC_ID)
              );


CREATE TABLE FMS_MA_CATEGORY_DOCTYPE (
             CD_ID BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
             CD_DESC VARCHAR(255),
             CD_MULTIPLE BOOLEAN,
             CD_TYPE VARCHAR(255),
             CD_UC_ID BIGINT,
             PRIMARY KEY (CD_ID),
             CONSTRAINT FK_CD_UC_ID_0001 FOREIGN KEY (CD_UC_ID) REFERENCES FMS_MA_UPLOAD_CATEGORY(UC_ID)
             );

CREATE TABLE FMS_TR_UPLOADED_DOCS (
             UD_ID BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
             UD_DOC_ID VARCHAR(255),
             UD_FILE_INFO VARCHAR(255),
             UD_FILE_LOCATION VARCHAR(255),
             UD_FILE_NAME VARCHAR(255),
             UD_FILE_SEQUENCE BIGINT,
             UD_CD_ID BIGINT,
             PRIMARY KEY (UD_ID),
             CONSTRAINT FK_UD_UC_CD_ID_0001 FOREIGN KEY (UD_CD_ID) REFERENCES FMS_MA_CATEGORY_DOCTYPE(CD_ID));

ALTER TABLE FMS_TR_UPLOADED_DOCS ADD CONSTRAINT UK_UD_ UNIQUE (UD_DOC_ID, UD_CD_ID, UD_FILE_SEQUENCE);