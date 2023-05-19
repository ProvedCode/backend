--liquibase formatted sql
--changeset dennis:5
insert into proof_skill (skill_id, proof_id)
values (1, 1);
insert into proof_skill (skill_id, proof_id)
values (2, 1);
insert into proof_skill (skill_id, proof_id)
values (2, 2);
insert into proof_skill (skill_id, proof_id)
values (3, 3);

insert into talent_skill(talent_id, skill_id)
values (1, 1);
insert into talent_skill(talent_id, skill_id)
values (1, 2);
insert into talent_skill(talent_id, skill_id)
values (1, 3);
insert into talent_skill(talent_id, skill_id)
values (2, 1);
insert into talent_skill(talent_id, skill_id)
values (2, 2);
insert into talent_skill(talent_id, skill_id)
values (2, 6);
insert into talent_skill(talent_id, skill_id)
values (3, 1);