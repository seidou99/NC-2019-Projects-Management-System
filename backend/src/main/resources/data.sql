insert into user_role (name)
values ('Admin'),
       ('Project Manager'),
       ('Developer'),
       ('Tester');

insert into user (surname, name, role_id, email, password_hash)
value ('surname', 'name', 1, 'example@gmail.com', '123');