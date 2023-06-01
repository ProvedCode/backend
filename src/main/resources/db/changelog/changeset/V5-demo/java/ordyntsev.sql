--liquibase formatted sql
--changeset Maslyna:0

insert into talents (first_name, last_name, specialization, image)
values (
           'Mykhailo',
           'Ordyntsev',
           'Java back-end developer',
           'https://i.pinimg.com/564x/e1/08/49/e10849923a8b2e85a7adf494ebd063e6.jpg'
       );
insert into descriptions (talent_id, bio, addition_info)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
               ), 'Hello! I was born in Ukraine. My profession is Java Back-end developer. I started my journey by learning python, after which I became interested in C++ and now I am actively studying Java, Docker, ABC services, PostgreSQL.',
           'GLORY TO UKRAINE'
       );
insert into links (talent_id, link)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
               ), 'https://github.com/Maslyna'
       );
insert into links (talent_id, link)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
               ), 'https://www.linkedin.com/in/ординцев-михайло-a49403259/?locale=en_US'
       );
insert into contacts (talent_id, contact)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
               ), '+380685152038'
       );
insert into contacts (talent_id, contact)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
               ), 'sanci324@gmail.com'
       );
insert into contacts (talent_id, contact)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
               ), 'mykhailo.ordyntsev@nure.ua'
       );
insert into attached_files (talent_id, attached_file)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
               ), 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'
       );
insert into attached_files (talent_id, attached_file)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
               ), 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'
       );
insert into attached_files (talent_id, attached_file)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
               ), 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'
       );
insert into proofs (talent_id, link, text, status, created)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
               ), 'https://github.com/ProvedCode/backend', 'At this link to github you can see the project I participated in. You are using it right now)','PUBLISHED', '2023-05-29 18:00:19'
       );
insert into proofs (talent_id, link, text, status, created)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
               ), 'https://github.com/Maslyna/SpringEmailService', 'At this link yoy can see my demo project, where you can check for implement mail service in spring boot application. Also, here you can see a simple implements of blocking and activating accounts by email.', 'PUBLISHED', '2023-05-29 18:10:56'
       );
insert into users_info (talent_id, login, password)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
               ), 'MykhailoOrdyntsev@gmail.com', '$2a$10$EzYxG1DEUek/veK.HzP7B.ynSKE42VbLb4pvFd/v4OwGPNol6buEC'
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