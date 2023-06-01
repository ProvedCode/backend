--liquibase formatted sql
--changeset dennis:5
-- Authority
insert into authorities (id, authority)
values (1, 'TALENT');
insert into authorities (id, authority)
values (2, 'SPONSOR');

-- Talent
-- Serhii Soloviov
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
          'I am also familiar with database management systems such as msql, postgres, and mysql.'
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

-- Denis Boyko
insert into talents (first_name, last_name, specialization, image)
values (
           'Denis',
           'Boyko',
           'Java-Developer',
           'https://i.pinimg.com/564x/4f/1f/30/4f1f3065c6f3cf409a627982f43af365.jpg'
       );
insert into descriptions (talent_id, bio, addition_info)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'Motivated and dedicated Junior Java Developer with a passion for coding and problem-solving. Strong foundation in Java programming and object-oriented concepts, with a keen interest in backend development. Eager to contribute to a dynamic development team and further enhance my skills in software development.'
    );
insert into links (talent_id, link)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'https://www.instagram.com/boyko_denisss/'
    );
insert into links (talent_id, link)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'https://github.com/Denis973'
    );
insert into links (talent_id, link)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'https://www.linkedin.com/in/den-boyko-8846b81a2/'
    );
insert into contacts (talent_id, contact)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), '+380974996162'
    );
insert into contacts (talent_id, contact)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'denisboyko@gmail.com'
    );
insert into proofs (talent_id, link, text, status, created)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'https://github.com/ProvedCode/backend', 'Project aims to develop a Java-based professional networking platform that shares. The platform will provide a robust and user-friendly environment for professionals to connect, network, and showcase their skills and proofs', 'PUBLISHED', '2023-04-04 12:11:19'
    );
insert into proofs (talent_id, link, text, status, created)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'https://www.youtube.com/watch?v=Geq60OVyBPg&ab_channel=Amigoscode', 'Software Testing Tutorial - Learn Unit and Integration Testing', 'DRAFT', '2023-04-024 19:11:19'
    );
insert into proofs (talent_id, link, text, status, created)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'https://www.youtube.com/watch?v=f5j1TaJlc0w&ab_channel=Amigoscode', 'In this video you will learn functional programming with Java Streams. I will introduce you the differences between imperative vs declarative programming using Java Streams API', 'HIDDEN', '2023-05-024 19:11:19'
    );
insert into users_info (talent_id, login, password)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'denisboyko@gmail.com', '$2a$10$tLm27FGH8Sabz57eNkTwm.bSnhmJHINcqt7dNfZI0NfOwD2o/Drse'
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

-- Olha Moiseienko
insert into talents (first_name, last_name, specialization, image)
values (
           'Olha',
           'Moiseienko',
           'QA',
           'https://i.pinimg.com/564x/8c/6a/9c/8c6a9c334e1da809282f544f12d18829.jpg'
       );
insert into descriptions (talent_id, bio, addition_info)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'I am from Ukraine. I live in Sumy. I graduated from college and have a diploma, now I am continuing my studies at the university.I can write test plans, checklists, test cases and bug-reports.'
    );
insert into contacts (talent_id, contact)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), '+38066333553'
    );
insert into contacts (talent_id, contact)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'olhamoiseienko@mail.org'
    );
insert into proofs (talent_id, link, text, status, created)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'https://olhammm.atlassian.net/jira/software/projects/PC/boards/1/roadmap', 'This link reflects the planning and organization of our team''s work in Jira. ', 'PUBLISHED', '2023-03-10 12:00:19'
    );
insert into proofs (talent_id, link, text, status, created)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'http://dev.provedcode.pepega.cloud/talents?page=0&size=5', 'This site was made by our team and mostly tested by me.', 'DRAFT', '2023-05-28 18:00:00'
    );

insert into users_info (talent_id, login, password)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'OlhaMoiseienko@gmail.com', '$2a$10$lvvX7DZOwCS/Q7zSo.k.oeayTcKHh8rO1yBBkgIbU4VAC7abPfIa2'
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

-- Maxim Kiyashko
insert into talents (first_name, last_name, specialization, image)
values (
           'Maxim',
           'Kiyashko',
           'Quality assurance engineer',
           'https://i.pinimg.com/564x/80/2d/58/802d58b0302985f9486893d499d3634d.jpg'
       );
insert into descriptions (talent_id, bio, addition_info)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'My name is Max, I am from Dnipro. I have been studying in Nure for 3 years. My specialization is computer engineering.'
    );

insert into contacts (talent_id, contact)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), '+380967802165'
    );
insert into contacts (talent_id, contact)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'maqime8@gmail.com'
    );

insert into proofs (talent_id, link, text, status, created)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'https://olhammm.atlassian.net/jira/software/projects/PC/boards/1', 'This is my first Jira project with my team.', 'PUBLISHED', '2023-05-28 12:28:20'
    );
insert into proofs (talent_id, link, text, status, created)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'http://dev.provedcode.pepega.cloud/talents?page=0&size=5', 'This is the site we made with my team', 'PUBLISHED', '2023-03-19 20:45:27'
    );
insert into proofs (talent_id, link, text, status, created)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'https://www.canva.com/design/DAFeaYPjj E/lMskbFVL8SwNLicffDE81w/edit?ut m_content=DAFeaYPjj E&utm_campaign=designshare&utm_medium=link2&utm _source=sharebutton', 'This is a presentation of the ProvedCode project with my team', 'Draft', '2023-05-29 16:23:27'
    );
insert into users_info (talent_id, login, password)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'maqime8@gmail.com', '$2a$10$y.g9qHYUOPEkIL8xDc2h1.EdVAG5DYh6OKxf9CRb6s16oHHbr8Bny'
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

-- Nikolaiev Oleksii
insert into talents (first_name, last_name, specialization, image)
values (
           'Nikitin',
           'Oleksii',
           'Quality assurance engineer',
           'https://i.pinimg.com/564x/54/d1/0d/54d10dfce64afefabc9fbbce5de82c87.jpg'
       );
insert into descriptions (talent_id, bio, addition_info)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'In my first year at university, I started learning C++, but I stumbled upon QA courses and realized that this profession was more attractive to me. Now I am actively studying water supply.'
    );

insert into contacts (talent_id, contact)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), '+380673457856'
    );
insert into contacts (talent_id, contact)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'oleksn@mail.ua'
    );
insert into proofs (talent_id, link, text, status, created)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'https://olhammm.atlassian.net/jira/software/projects/PC/boards/1', 'This is my first Jira project with my team.', 'PUBLISHED', '2023-05-28 12:28:20'
    );
insert into proofs (talent_id, link, text, status, created)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'http://dev.provedcode.pepega.cloud/talents?page=0&size=5', 'This is the site we made with my team', 'Hidden', '2023-03-19 20:45:27'
    );
insert into proofs (talent_id, link, text, status, created)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'https://www.canva.com/design/DAFeaYPjj- E/lMskbFVL8SwNLicffDE81w/edit?utm_content=DAFeaYPjj- E&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton', 'This is a presentation of the ProvedCode project with my team', 'Draft', '2023-05-29 16:23:27'
    );
insert into users_info (talent_id, login, password)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'oleksn@mail.ua', '$2a$10$nDObO3nDlhWev29qCnzNuOszdg/.ANaMlTirDVWVyLMapYmtSSqza'
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

-- Olexander Vyganiailo
insert into talents (first_name, last_name, specialization, image)
values (
           'Olexander',
           'Vyganiailo',
           'front-end developer',
           'https://i.pinimg.com/564x/54/d1/0d/54d10dfce64afefabc9fbbce5de82c87.jpg'
       );
insert into descriptions (talent_id, bio, addition_info)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'Hi, I am Olexander , student of 4-th grade in Sumy State University . Last 3 years I work with programming languages such: HTML, CSS, JS, C++ . Hard working and responsible student, who is ready to work and to absorb information '
    );
insert into links (talent_id, link)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'https://github.com/whoisalex1W'
    );
insert into links (talent_id, link)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'https://www.linkedin.com/in/%D0%B0%D0%BB%D0%B5%D0%BA%D1%81%D0%B0%D0%BD%D0%B4%D1%80-%D0%B2%D1%8B%D0%B3%D0%B0%D0%BD%D1%8F%D0%B9%D0%BB%D0%BE-b78421221/'
    );
insert into contacts (talent_id, contact)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), '+380956506901'
    );
insert into contacts (talent_id, contact)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'olexander.vyganiailo@gmail.com'
    );
insert into proofs (talent_id, link, text, status, created)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'https://www.youtube.com/', 'Such tools as NodeJS, MongoDB, Express were used to create the server part of the web-oriented information system. Unlike relational databases, MongoDB uses an approach without SQL queries and tables, instead, a document-oriented data model is used, which allows you to make working with the database scalable and simple.', 'PUBLISHED', '2023-05-04 11:11:11'
    );
insert into users_info (talent_id, login, password)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'olexander.vyganiailo@gmail.com', '$2a$10$nDObO3nDlhWev29qCnzNuOszdg/.ANaMlTirDVWVyLMapYmtSSqza'
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


-- Ruslan Morozov
insert into talents (first_name, last_name, specialization, image)
values (
           'Ruslan',
           'Morozov',
           'JS front-end developer',
           'https://i.pinimg.com/564x/54/d1/0d/54d10dfce64afefabc9fbbce5de82c87.jpg'
       );
insert into descriptions (talent_id, bio, addition_info)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'Student of the Karazin Kharkiv National University. Confident and ambitious Junior React developer. I like to solve problems and look for new and non-standard approaches to them. Always developing and striving for knowledge'
    );
insert into links (talent_id, link)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'https://www.instagram.com/ruslan1903_/'
    );
insert into links (talent_id, link)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'https://github.com/Ruslanchik01'
    );
insert into links (talent_id, link)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'https://www.linkedin.com/in/ruslan-morozov-185b17238/'
    );
insert into contacts (talent_id, contact)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), '+380667538060'
    );
insert into contacts (talent_id, contact)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'morozov.ruslan2003@gmail.com'
    );
insert into proofs (talent_id, link, text, status, created)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'https://github.com/ProvedCode/frontend', 'Our project aims to develop a JS-based professional networking platform that shares. The platform will provide a robust and user-friendly environment for professionals to connect, network, and showcase their skills and proofs.	', 'PUBLISHED', '2023-05-04 11:11:11'
    );
insert into users_info (talent_id, login, password)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'morozov.ruslan2003@gmail.com', '$2a$10$nDObO3nDlhWev29qCnzNuOszdg/.ANaMlTirDVWVyLMapYmtSSqza'
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

-- Daniil Yevtukhov
insert into talents (first_name, last_name, specialization, image)
values (
           'Daniil',
           'Yevtukhov',
           'JS front-end developer',
           'https://i.pinimg.com/564x/0f/2b/4c/0f2b4c6358ced7a70566c05171eb1e9d.jpg'
       );
insert into descriptions (talent_id, bio, addition_info)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'I am a JS backend developer. ' ||
          'I started my career in programming by learning Pascal at school, ' ||
          'then dabbled in Python for a while. I also have some knowledge of ' ||
          'java script and php. At the moment, I''m studying java, specifically spring boot. ' ||
          'I am also familiar with database management systems such as msql, postgres, and mysql.'
    );
insert into links (talent_id, link)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'https://www.instagram.com/ievtukhovofficial/'
    );
insert into links (talent_id, link)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'https://github.com/daniilievtukhov'
    );
insert into links (talent_id, link)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'https://www.linkedin.com/in/daniil-yevtukhov-b9811220b/'
    );
insert into contacts (talent_id, contact)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), '+380505168717'
    );
insert into contacts (talent_id, contact)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'daniil.ievtukhov@nure.ua'
    );

insert into proofs (talent_id, link, text, status, created)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'https://github.com/ProvedCode/fontend', 'Our project aims to develop a JS-based professional networking platform that shares. The platform will provide a robust and user-friendly environment for professionals to connect, network, and showcase their skills and proofs.	','PUBLISHED', '2023-05-04 13:11:19'
    );
insert into proofs (talent_id, link, text, status, created)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'https://www.youtube.com/watch?v=CxgOKJh4zWE&ab_channel=BogdanStashchuk', 'JavaScript courses', 'DRAFT', '2023-05-26 18:10:56'
    );
insert into users_info (talent_id, login, password)
values (
           (
               select id
               from talents
               order by id desc
               limit 1
       ), 'daniil.ievtukhov@nure.ua', '$2a$10$EzYxG1DEUek/veK.HzP7B.ynSKE42VbLb4pvFd/v4OwGPNol6buEC'
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

-- Artem Lytvynenko
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