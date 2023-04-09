create table authority
(
    id        bigserial   not null,
    authority varchar(20) not null,
    primary key (id)
);
create table talent
(
    id             bigserial not null,
    first_name     varchar(20),
    image          varchar(100),
    last_name      varchar(20),
    specialization varchar(30),
    primary key (id)
);
create table talent_attached_file
(
    id            bigserial not null,
    attached_file varchar(100),
    talent_id     bigint    not null,
    primary key (id)
);
create table talent_contact
(
    id        bigserial not null,
    contact   varchar(255),
    talent_id bigint    not null,
    primary key (id)
);
create table talent_description
(
    id            bigserial not null,
    addition_info varchar(255),
    bio           varchar(255),
    talent_id     bigint    not null,
    primary key (id)
);
create table talent_link
(
    id        bigserial not null,
    link      varchar(255),
    talent_id bigint    not null,
    primary key (id)
);
create table talent_proofs
(
    id        bigserial   not null,
    created   timestamp(6),
    link      varchar(100),
    status    varchar(20) not null,
    talent_id bigint      not null,
    text      varchar(255),
    primary key (id)
);
create table talent_talents
(
    id          bigserial not null,
    talent_id   bigint    not null,
    talent_name varchar(255),
    primary key (id)
);
create table user_authorities
(
    user_id      bigint not null,
    authority_id bigint not null,
    primary key (user_id, authority_id)
);
create table user_info
(
    id        bigserial    not null,
    login     varchar(100) not null,
    password  varchar(255) not null,
    talent_id bigint       not null,
    primary key (id)
);
alter table if exists talent_attached_file
    add constraint FKdtjomr27q99ufe065trf8jr7b foreign key (talent_id) references talent;
alter table if exists talent_contact
    add constraint FKkftncuyutiv4ac4s16ru8tg8x foreign key (talent_id) references talent;
alter table if exists talent_description
    add constraint FKcswnowkosgq1rnj1s1b3gnk8q foreign key (talent_id) references talent;
alter table if exists talent_link
    add constraint FKp64n3c44eqoremntcxemdiy1c foreign key (talent_id) references talent;
alter table if exists talent_proofs
    add constraint FKotnc7wmkq7f6g758vkr29lyi5 foreign key (talent_id) references talent;
alter table if exists talent_talents
    add constraint FK1q9cywkek4g3o4femq0n6ecl1 foreign key (talent_id) references talent;
alter table if exists user_authorities
    add constraint FK2n9bab2v62l3y2jgu3qup4etw foreign key (authority_id) references authority;
alter table if exists user_authorities
    add constraint FKhrxn11h0wl1txiaukxjp01uji foreign key (user_id) references user_info;
alter table if exists user_info
    add constraint FKng34qd4ikmdcwg4f8bcpghar9 foreign key (talent_id) references talent;
