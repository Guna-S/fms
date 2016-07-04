create table fms_ma_category_doctype (cd_id int8 not null, cd_desc varchar(255), cd_multiple boolean, cd_type varchar(255), cd_uc_id int8, primary key (cd_id));
create table fms_ma_upload_category (uc_id int8 not null, uc_desc varchar(255), uc_name varchar(255), primary key (uc_id));
create table fms_tr_uploaded_docs (ud_id int8 not null, ud_doc_id varchar(255), ud_file_info varchar(255), ud_file_location varchar(255), ud_file_name varchar(255), ud_file_sequence int8, ud_cd_id int8, primary key (ud_id));
alter table fms_tr_uploaded_docs add constraint UK_UD_001 unique (ud_doc_id, ud_cd_id, ud_file_sequence);
alter table fms_ma_category_doctype add constraint FK_CD_001 foreign key (cd_uc_id) references fms_ma_upload_category;
alter table fms_tr_uploaded_docs add constraint FK_UD_001 foreign key (ud_cd_id) references fms_ma_category_doctype;
create sequence fms_sq_cd INCREMENT by 1 start with 1;
create sequence fms_sq_uc INCREMENT by 1 start with 1;
create sequence fms_sq_ud INCREMENT by 1 start with 1;