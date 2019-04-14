insert into user_role (name)
values ('Admin'),
       ('Project manager'),
       ('Developer'),
       ('QA');

insert into task_priority (name)
values ('Blocker'),
       ('Critical'),
       ('Major'),
       ('Normal'),
       ('Minor');

insert into task_status (name)
values ('Open'),
       ('In progress'),
       ('Resolved'),
       ('Ready for test'),
       ('Closed'),
       ('Reopen');