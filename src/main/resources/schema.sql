drop table IF EXISTS talent CASCADE ;
drop table IF EXISTS talent_description CASCADE ;
drop table IF EXISTS talent_link CASCADE ;
drop table IF EXISTS talent_contact CASCADE ;
drop table IF EXISTS talent_attached_file CASCADE ;
drop table IF EXISTS talent_talents CASCADE ;
drop table IF EXISTS user_info CASCADE ;
drop table IF EXISTS user_authorities CASCADE ;
drop table IF EXISTS talent_talents CASCADE ;

create TABLE talent (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   first_name VARCHAR(20) NOT NULL,
   last_name VARCHAR(20) NOT NULL,
   specialization VARCHAR(30) NOT NULL,
   image VARCHAR(100),
   CONSTRAINT pk_talent PRIMARY KEY (id)
);

create TABLE talent_description (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   talent_id BIGINT NOT NULL,
   BIO VARCHAR(255) NOT NULL,
   addition_info VARCHAR(255) NOT NULL,
   CONSTRAINT pk_talent_description PRIMARY KEY (id)
);

alter table talent_description add CONSTRAINT FK_TALENT_DESCRIPTION_ON_TALENT FOREIGN KEY (talent_id) REFERENCES talent (id);

create TABLE talent_link (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   talent_id BIGINT NOT NULL,
   link VARCHAR(255) NOT NULL,
   CONSTRAINT pk_talent_link PRIMARY KEY (id)
);

alter table talent_link add CONSTRAINT FK_TALENT_LINK_ON_TALENT FOREIGN KEY (talent_id) REFERENCES talent (id);

CREATE TABLE talent_talents
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    talent_id   BIGINT                                  NOT NULL,
    talent_name VARCHAR(255),
    CONSTRAINT pk_talent_talents PRIMARY KEY (id)
);

ALTER TABLE talent_talents
    ADD CONSTRAINT FK_TALENT_TALENTS_ON_TALENT FOREIGN KEY (talent_id) REFERENCES talent (id);

create TABLE talent_contact (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   talent_id BIGINT NOT NULL,
   contact VARCHAR(255) NOT NULL,
   CONSTRAINT pk_talent_contact PRIMARY KEY (id)
);

alter table talent_contact add CONSTRAINT FK_TALENT_CONTACT_ON_TALENT FOREIGN KEY (talent_id) REFERENCES talent (id);

create TABLE talent_attached_file (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   talent_id BIGINT NOT NULL,
   attached_file VARCHAR(100) NOT NULL,
   CONSTRAINT pk_talent_attached_file PRIMARY KEY (id)
);

alter table talent_attached_file add CONSTRAINT FK_TALENT_ATTACHED_FILE_ON_TALENT FOREIGN KEY (talent_id) REFERENCES talent (id);

--user tables--
CREATE TABLE authority
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    authority VARCHAR(20)                             NOT NULL,
    CONSTRAINT pk_authority PRIMARY KEY (id)
);

CREATE TABLE user_info
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    talent_id BIGINT                                  NOT NULL,
    login     VARCHAR(100)                            NOT NULL,
    password  VARCHAR(255)                            NOT NULL,
    CONSTRAINT pk_user_info PRIMARY KEY (id)
);

CREATE TABLE user_authorities
(
    user_id      BIGINT NOT NULL,
    authority_id BIGINT NOT NULL,
    CONSTRAINT pk_user_authorities PRIMARY KEY (user_id, authority_id)
);

ALTER TABLE user_info ADD CONSTRAINT FK_USER_INFO_ON_USER_ID FOREIGN KEY (talent_id) REFERENCES talent (id);

ALTER TABLE user_authorities ADD CONSTRAINT fk_useaut_on_authority FOREIGN KEY (authority_id) REFERENCES authority (id);

ALTER TABLE user_authorities ADD CONSTRAINT fk_useaut_on_user_info FOREIGN KEY (user_id) REFERENCES user_info (id);
