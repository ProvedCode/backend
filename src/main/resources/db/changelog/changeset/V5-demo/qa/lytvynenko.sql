--liquibase formatted sql
--changeset Maslyna:9

insert into talents (first_name, last_name, specialization, image)
values (
           'Daniil',
           'Lytvynenko',
           'QA',
           'https://i.ibb.co/N394jRq/IMG-20230330-162433.jpg'
       );
insert into descriptions (talent_id, bio, addition_info)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
               ), 'I am Ukrainian student from Poltava😎. Manual tester'
       );
insert into links (talent_id, link)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
               ), 'https://github.com/Artem-Toyvo'
       );
insert into links (talent_id, link)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
               ), 'https://www.linkedin.com/in/artem-lytvynenko-ba7a94244/'
       );

insert into contacts (talent_id, contact)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
               ), '+380500739119'
       );
insert into contacts (talent_id, contact)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
               ), 'metalurg1889@gmail.com'
       );

insert into proofs (talent_id, link, text, status, created)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
               ), 'https://olhammm.atlassian.net/jira/software/projects/PC/boards/1', 'Our team project, organization of work, and our plans','PUBLISHED', '2023-06-01 11:42:20'
       );
insert into proofs (talent_id, link, text, status, created)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
               ), 'https://github.com/orgs/ProvedCode/projects/1', 'Github projects, here QA team and me track a front\back end progres, and start testing then ready', 'DRAFT', '2023-05-01 12:42:20 '
       );
insert into users_info (talent_id, login, password)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
               ), 'superpuper5432@gmail.com', '$2a$10$EzYxG1DEUek/veK.HzP7B.ynSKE42VbLb4pvFd/v4OwGPNol6buEC'
       );
insert into users_authorities (user_id, authority_id)
values (
           (
               select id
               from users_info
               order by id desc
               limit 1
               ), (
               select id
               from authorities
               where id = 1
               )
       );