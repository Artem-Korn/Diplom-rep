delete from user_studclass_table;
delete from studclass_table;

insert into studclass_table(id, name, user_id) values
(1, 'test_class1', 1),
(2, 'test_class2', 1),
(3, 'test_class3', 1);

insert into user_studclass_table(user_id, studclass_id) values
(1, 1), (1, 2), (1,3), (2,1);

alter sequence studclass_table_seq restart with 10;