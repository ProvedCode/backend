insert into talent (first_name, last_name, specialization, image)
values ('Serhii', 'Soloviov', 'Java-Developer', 'http://image');
insert into talent_description (talent_id, BIO, addition_info)
values((select id from talent order by id desc limit 1), 'Default bio', 'Default addition info');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://first_link');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://second_link');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://third_link');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'first_skill');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'second_skill');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'third_skill');
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

insert into talent (first_name, last_name, specialization, image)
values ('Mykhailo', 'Ordyntsev', 'Java-Developer', 'http://MykhailoOrdyntsevImage');
insert into talent_description (talent_id, BIO, addition_info)
values((select id from talent order by id desc limit 1), 'Mykhailo Ordyntsev bio', 'Mykhailo Ordyntsev addition info');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://MykhailoOrdyntsev_first_link');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://MykhailoOrdyntsev_second_link');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://MykhailoOrdyntsev_third_link');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'MykhailoOrdyntsev_first_skill');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'MykhailoOrdyntsev_second_skill');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'MykhailoOrdyntsev_third_skill');
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

insert into talent (first_name, last_name, specialization, image)
values ('Denis', 'Boyko', 'Java-Developer', 'http://DenisBoykoImage');
insert into talent_description (talent_id, BIO, addition_info)
values((select id from talent order by id desc limit 1), 'Denis Boyko bio', 'Denis Boyko addition info');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://DenisBoyko_first_link');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://DenisBoyko_second_link');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://DenisBoyko_third_link');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'DenisBoyko_first_skill');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'DenisBoyko_second_skill');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'DenisBoyko_third_skill');
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

insert into talent (first_name, last_name, specialization, image)
values ('Ihor', 'Schurenko', 'Java-Developer', 'http://IhorShchurenkoImage');
insert into talent_description (talent_id, BIO, addition_info)
values((select id from talent order by id desc limit 1), 'Ihor Shchurenko bio', 'Ihor Shchurenko addition info');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://IhorShchurenko_first_link');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://IhorShchurenko_second_link');
insert into talent_link (talent_id, link)
values ((select id from talent order by id desc limit 1), 'http://IhorShchurenko_third_link');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'IhorShchurenko_first_skill');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'IhorShchurenko_second_skill');
insert into talent_skill (talent_id, skill)
values ((select id from talent order by id desc limit 1), 'IhorShchurenko_third_skill');
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
