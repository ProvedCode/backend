insert into authority (id, authority)
VALUES (1, 'ROLE_TALENT');
-- FOR USER AUTHORITY
-- SELECT USER_INFO.ID , LOGIN , PASSWORD, USER_ID , AUTHORITY FROM
--     USER_INFO
--         JOIN USER_AUTHORITY ON USER_ID = USER_INFO.ID
--         JOIN AUTHORITY ON AUTHORITY.ID = AUTHORITY_ID


insert into talent (first_name, last_name, specialization, image)
values ('Serhii', 'Soloviov', 'Java-Developer', 'https://i.pinimg.com/564x/e1/08/49/e10849923a8b2e85a7adf494ebd063e6.jpg');
insert into talent_description (talent_id, BIO, addition_info)
values((select id from talent order by id desc limit 1), 'Serhii Soloviov bio', 'Serhii Soloviov addition info');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://first_link');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://second_link');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://third_link');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'Java Core');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'Spring Core');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'Spring boot');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'H2 Database');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'first_contact');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'second_contact');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'third_contact');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'first_file');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'second_file');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'third_file');

insert into user_info (id, login, password)
values ((select id from talent order by id desc limit 1), 'SerhiiSoloviov', 'password');
insert into user_authority (id, user_id, authority_id)
values ((select id from user_info order by id desc limit 1),
        (select id from user_info order by id desc limit 1),
        (select authority.id from authority order by id desc limit 1));

insert into talent (first_name, last_name, specialization, image)
values ('Mykhailo', 'Ordyntsev', 'Java-Developer', 'https://i.pinimg.com/564x/c2/41/31/c24131fe00218467721ba5bacdf0a256.jpg');
insert into talent_description (talent_id, BIO, addition_info)
values((select id from talent order by id desc limit 1), 'Mykhailo Ordyntsev bio', 'Mykhailo Ordyntsev addition info');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://MykhailoOrdyntsev_first_link');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://MykhailoOrdyntsev_second_link');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://MykhailoOrdyntsev_third_link');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'Java Core');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'Hibernate');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'Spring Boot');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'Git');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'MykhailoOrdyntsev_first_contact');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'MykhailoOrdyntsev_second_contact');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'MykhailoOrdyntsev_third_contact');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'MykhailoOrdyntsev_first_file');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'MykhailoOrdyntsev_second_file');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'MykhailoOrdyntsev_third_file');

insert into user_info (id, login, password)
values ((select id from talent order by id desc limit 1), 'MykhailoOrdyntsev', 'password');
insert into user_authority (id, user_id, authority_id)
values ((select id from user_info order by id desc limit 1),
        (select id from user_info order by id desc limit 1),
        (select authority.id from authority order by id desc limit 1));

insert into talent (first_name, last_name, specialization, image)
values ('Denis', 'Boyko', 'Java-Developer', 'https://i.pinimg.com/564x/2a/0c/08/2a0c08c421e253ca895c3fdc8c9e08d9.jpg');
insert into talent_description (talent_id, BIO, addition_info)
values((select id from talent order by id desc limit 1), 'Denis Boyko bio', 'Denis Boyko addition info');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://DenisBoyko_first_link');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://DenisBoyko_second_link');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://DenisBoyko_third_link');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'Java Core');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'Spring Security');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'Spring Core');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'DenisBoyko_first_contact');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'DenisBoyko_second_contact');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'DenisBoyko_third_contact');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'DenisBoyko_first_file');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'DenisBoyko_second_file');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'DenisBoyko_third_file');

insert into user_info (id, login, password)
values ((select id from talent order by id desc limit 1), 'DenisBoyko', 'password');
insert into user_authority (id, user_id, authority_id)
values ((select id from user_info order by id desc limit 1),
        (select id from user_info order by id desc limit 1),
        (select authority.id from authority order by id desc limit 1));

insert into talent (first_name, last_name, specialization, image)
values ('Ihor', 'Schurenko', 'Java-Developer', 'https://i.pinimg.com/564x/e1/11/2f/e1112f0b7b63644dc3e313084936dedb.jpg');
insert into talent_description (talent_id, BIO, addition_info)
values((select id from talent order by id desc limit 1), 'Ihor Shchurenko bio', 'Ihor Shchurenko addition info');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://IhorShchurenko_first_link');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://IhorShchurenko_second_link');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://IhorShchurenko_third_link');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'Java Core');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'REST API');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'IhorShchurenko_first_contact');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'IhorShchurenko_second_contact');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'IhorShchurenko_third_contact');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'IhorShchurenko_first_file');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'IhorShchurenko_second_file');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'IhorShchurenko_third_file');
insert into talent (first_name, last_name, specialization, image)
values ('Dmytro', 'Uzun', 'Dev-Ops', 'https://i.pinimg.com/564x/1c/af/87/1caf8771ef3edf351f6f2bf6f1c0a276.jpg');
insert into talent_description (talent_id, BIO, addition_info)
values((select id from talent order by id desc limit 1), 'Dmytro Uzun bio', 'Dmytro Uzun addition info');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://DmytroUzun_first_link');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://DmytroUzun_second_link');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://DmytroUzun_third_link');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'Git');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'Docker');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'Mentor');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'DmytroUzun_first_contact');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'DmytroUzun_second_contact');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'DmytroUzun_third_contact');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'DmytroUzun_first_file');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'DmytroUzun_second_file');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'DmytroUzun_third_file');

insert into user_info (id, login, password)
values ((select id from talent order by id desc limit 1), 'DmytroUzun', 'password');
insert into user_authority (id, user_id, authority_id)
values ((select id from user_info order by id desc limit 1),
        (select id from user_info order by id desc limit 1),
        (select authority.id from authority order by id desc limit 1));

insert into talent (first_name, last_name, specialization, image)
values ('Viktor', 'Voloshko', 'Dev-Ops', 'https://i.pinimg.com/564x/a9/51/ab/a951ab682413b89617235e65564c1e5e.jpg');
insert into talent_description (talent_id, BIO, addition_info)
values((select id from talent order by id desc limit 1), 'Viktor Voloshko bio', 'Viktor Voloshko addition info');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://ViktorVoloshko_first_link');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://ViktorVoloshko_second_link');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://ViktorVoloshko_third_link');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'Git');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'Docker');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'ViktorVoloshko_first_contact');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'ViktorVoloshko_second_contact');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'ViktorVoloshko_third_contact');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'ViktorVoloshko_first_file');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'ViktorVoloshko_second_file');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'ViktorVoloshko_third_file');

insert into user_info (id, login, password)
values ((select id from talent order by id desc limit 1), 'ViktorVoloshko', 'password');
insert into user_authority (id, user_id, authority_id)
values ((select id from user_info order by id desc limit 1),
        (select id from user_info order by id desc limit 1),
        (select authority.id from authority order by id desc limit 1));

insert into talent (first_name, last_name, specialization, image)
values ('Olha', 'Moiseienko', 'QA', 'https://i.pinimg.com/564x/6d/9d/43/6d9d437baf4db114c047d927307beb84.jpg');
insert into talent_description (talent_id, BIO, addition_info)
values((select id from talent order by id desc limit 1), 'Olha Moiseienko bio', 'Olha Moiseienko addition info');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://OlhaMoiseienko_first_link');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://OlhaMoiseienko_second_link');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://OlhaMoiseienko_third_link');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'Git');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'Jira');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'QA');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'OlhaMoiseienko_first_contact');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'OlhaMoiseienko_second_contact');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'OlhaMoiseienko_third_contact');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'OlhaMoiseienko_first_file');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'OlhaMoiseienko_second_file');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'OlhaMoiseienko _third_file');

insert into user_info (id, login, password)
values ((select id from talent order by id desc limit 1), 'OlhaMoiseienko', 'password');
insert into user_authority (id, user_id, authority_id)
values ((select id from user_info order by id desc limit 1),
        (select id from user_info order by id desc limit 1),
        (select authority.id from authority order by id desc limit 1));

insert into talent (first_name, last_name, specialization, image)
values ('Maxim', 'Kiyashko', 'QA', 'https://i.pinimg.com/564x/80/2d/58/802d58b0302985f9486893d499d3634d.jpg');
insert into talent_description (talent_id, BIO, addition_info)
values((select id from talent order by id desc limit 1), 'Maxim Kiyashko', 'Ihor Shchurenko addition info');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://MaximKiyashko_first_link');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://MaximKiyashko_second_link');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://MaximKiyashko_third_link');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'Git');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'QA');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'MaximKiyashko_first_contact');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'MaximKiyashko_second_contact');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'MaximKiyashko_third_contact');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'MaximKiyashko_first_file');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'MaximKiyashko_second_file');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'MaximKiyashko_third_file');

insert into user_info (id, login, password)
values ((select id from talent order by id desc limit 1), 'MaximKiyashko', 'password');
insert into user_authority (id, user_id, authority_id)
values ((select id from user_info order by id desc limit 1),
        (select id from user_info order by id desc limit 1),
        (select authority.id from authority order by id desc limit 1));

insert into talent (first_name, last_name, specialization, image)
values ('Nikolaiev', 'Oleksii', 'QA', 'https://i.pinimg.com/564x/54/d1/0d/54d10dfce64afefabc9fbbce5de82c87.jpg');
insert into talent_description (talent_id, BIO, addition_info)
values((select id from talent order by id desc limit 1), 'Nikolaiev Oleksii bio', 'Nikolaiev Oleksii addition info');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://NikolaievOleksii_first_link');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://NikolaievOleksii_second_link');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://NikolaievOleksii_third_link');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'QA');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'Git');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'NikolaievOleksii_third_skill');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'NikolaievOleksii_first_contact');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'NikolaievOleksii_second_contact');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'NikolaievOleksii_third_contact');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'NikolaievOleksii_first_file');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'NikolaievOleksii_second_file');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'NikolaievOleksiio_third_file');

insert into user_info (id, login, password)
values ((select id from talent order by id desc limit 1), 'NikolaievOleksiio', 'password');
insert into user_authority (id, user_id, authority_id)
values ((select id from user_info order by id desc limit 1),
        (select id from user_info order by id desc limit 1),
        (select authority.id from authority order by id desc limit 1));

insert into talent (first_name, last_name, specialization, image)
values ('Artem', 'Lytvynenko', 'QA', 'https://i.pinimg.com/564x/87/63/55/87635509c5fa7ee496ec351fa7e67eaa.jpg');
insert into talent_description (talent_id, BIO, addition_info)
values((select id from talent order by id desc limit 1), 'Artem Lytvynenko bio', 'Artem Lytvynenko addition info');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://ArtemLytvynenko_first_link');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://ArtemLytvynenko_second_link');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://ArtemLytvynenko_third_link');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'QA');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'Git');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'ArtemLytvynenko_first_contact');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'ArtemLytvynenko_second_contact');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'ArtemLytvynenko_third_contact');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'ArtemLytvynenko_first_file');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'ArtemLytvynenko_second_file');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'ArtemLytvynenko_third_file');

insert into user_info (id, login, password)
values ((select id from talent order by id desc limit 1), 'ArtemLytvynenko', 'password');
insert into user_authority (id, user_id, authority_id)
values ((select id from user_info order by id desc limit 1),
        (select id from user_info order by id desc limit 1),
        (select authority.id from authority order by id desc limit 1));

insert into talent (first_name, last_name, specialization, image)
values ('Daniil', 'Yevtukhov', 'Java-Script-Developer', 'https://i.pinimg.com/564x/fe/b1/37/feb137d88a3d1c8fb28796db6cbc576f.jpg');
insert into talent_description (talent_id, BIO, addition_info)
values((select id from talent order by id desc limit 1), 'Daniil Yevtukhov bio', 'Daniil Yevtukhov addition info');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://DaniilYevtukhov_first_link');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://DaniilYevtukhov_second_link');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://DaniilYevtukhov_third_link');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'JavaScript Core');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'React');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'DaniilYevtukhov_first_contact');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'DaniilYevtukhov_second_contact');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'DaniilYevtukhov_third_contact');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'DaniilYevtukhov_first_file');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'DaniilYevtukhov_second_file');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'DaniilYevtukhov_third_file');

insert into user_info (id, login, password)
values ((select id from talent order by id desc limit 1), 'DaniilYevtukhov', 'password');
insert into user_authority (id, user_id, authority_id)
values ((select id from user_info order by id desc limit 1),
        (select id from user_info order by id desc limit 1),
        (select authority.id from authority order by id desc limit 1));

insert into talent (first_name, last_name, specialization, image)
values ('Ruslan', 'Morozov', 'Java-Script-Developer', 'https://i.pinimg.com/736x/36/ae/0e/36ae0ea4aad656f7c3d3175bc33b8399.jpg');
insert into talent_description (talent_id, BIO, addition_info)
values((select id from talent order by id desc limit 1), 'Ruslan Morozov bio', 'Ruslan Morozov addition info');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://RuslanMorozov_first_link');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://RuslanMorozov_second_link');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://RuslanMorozov_third_link');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'JavaScript Core');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'React');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'Node.js');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'RuslanMorozov_first_contact');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'RuslanMorozov_second_contact');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'RuslanMorozov_third_contact');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'RuslanMorozov_first_file');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'RuslanMorozov_second_file');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'RuslanMorozov_third_file');

insert into user_info (id, login, password)
values ((select id from talent order by id desc limit 1), 'RuslanMorozov', 'password');
insert into user_authority (id, user_id, authority_id)
values ((select id from user_info order by id desc limit 1),
        (select id from user_info order by id desc limit 1),
        (select authority.id from authority order by id desc limit 1));

insert into talent (first_name, last_name, specialization, image)
values ('Ihor', 'Kopieichykov', 'Java-Script-Developer', 'https://i.pinimg.com/564x/0d/f0/83/0df083121bac75f64e3d93c7c5682d04.jpg');
insert into talent_description (talent_id, BIO, addition_info)
values((select id from talent order by id desc limit 1), 'Ihor Kopieichykov bio', 'Ihor Kopieichykov addition info');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://IhorKopieichykov_first_link');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://IhorKopieichykov_second_link');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://IhorKopieichykov_third_link');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'JavaScript Core');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'React');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'Angular');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'IhorKopieichykov_first_contact');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'IhorKopieichykov_second_contact');
insert into talent_contact (talent_id, contact)
values ((select id from talent order by id desc limit 1), 'IhorKopieichykov_third_contact');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'IhorKopieichykov_first_file');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'IhorKopieichykov_second_file');
insert into talent_attached_file (talent_id, attached_file)
values ((select id from talent order by id desc limit 1), 'IhorKopieichykov_third_file');

insert into user_info (id, login, password)
values ((select id from talent order by id desc limit 1), 'IhorKopieichykov', 'password');
insert into user_authority (id, user_id, authority_id)
values ((select id from user_info order by id desc limit 1),
        (select id from user_info order by id desc limit 1),
        (select authority.id from authority order by id desc limit 1));
