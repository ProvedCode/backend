--liquibase formatted sql
--changeset dennis:5
insert into kudos(amount, sponsor_id, proof_skill_id)
values (10, 1, 1);
insert into kudos(amount, sponsor_id, proof_skill_id)
values (20, 2, 1);
