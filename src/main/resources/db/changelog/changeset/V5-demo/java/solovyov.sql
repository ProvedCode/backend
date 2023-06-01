--liquibase formatted sql
--changeset Maslyna:2

insert into talents (first_name, last_name, specialization, image)
values (
           'Serhii',
           'Soloviov',
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
               ), 'I am a Java backend developer. ' ||
                  'I started my career in programming by learning Pascal at school, ' ||
                  'then dabbled in Python for a while. I also have some knowledge of ' ||
                  'java script and php. At the moment, I''m studying java, specifically spring boot. ' ||
                  'I am also familiar with database management systems such as msql, postgres, and mysql.',
        'GLORY TO UKRAINE'
       );
insert into links (talent_id, link)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
               ), 'https://github.com/LordRenDS'
       );
insert into links (talent_id, link)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
               ), 'https://www.instagram.com/good_boy_is_dead_boy'
       );
insert into contacts (talent_id, contact)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
               ), '+380636823945'
       );
insert into contacts (talent_id, contact)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
               ), 'sergeysolovyov2016@gmail.com'
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
               ), 'https://github.com/ProvedCode/backend', 'At this link to github you can see the project I participated in. You are using it right now)','PUBLISHED', '2023-05-26 18:00:19'
       );
insert into proofs (talent_id, link, text, status, created)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
               ), 'https://github.com/LordRenDS/Python-and-Data-Scince', 'Here are the tasks I completed during the course on data science using Python.', 'PUBLISHED', '2023-05-26 18:10:56'
       );
insert into users_info (talent_id, login, password)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
               ), 'serhiisoloviov@gmail.com', '$2a$10$EzYxG1DEUek/veK.HzP7B.ynSKE42VbLb4pvFd/v4OwGPNol6buEC'
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

