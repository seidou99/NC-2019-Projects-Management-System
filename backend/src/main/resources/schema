drop table if exists task;
drop table if exists user;
drop table if exists user_role;
drop table if exists project;
drop table if exists task_status;
drop table if exists task_priority;

create table project
(
  id      int auto_increment,
  code    nvarchar(6)   not null,
  summary nvarchar(255) not null,
  unique (code),
  constraint project_pk
    primary key (id)
);

create table task_priority
(
  id   int auto_increment,
  name nvarchar(20) not null,
  unique (name),
  constraint task_priority_pk
    primary key (id)
);

create table task_status
(
  id   int auto_increment,
  name nvarchar(20) not null,
  unique (name),
  constraint task_status_pk
    primary key (id)
);

create table user_role
(
  id   int auto_increment,
  name nvarchar(20) not null,
  unique (name),
  constraint user_role_pk
    primary key (id)
);

create table user
(
  id            int auto_increment,
  surname       nvarchar(20) not null,
  name          nvarchar(20) not null,
  role_id       int          not null,
  email         varchar(30)  not null,
  password_hash varchar(255) not null,
  constraint user_role_id_fk
    foreign key (role_id) references user_role (id),
  constraint user_pk
    primary key (id)
);

create table task
(
  id          int auto_increment,
  project_id  int           not null,
  description nvarchar(255) not null,
  priority_id int           not null,
  status_id   int           not null,
  due_date    date          not null,
  estimation  date          not null,
  assignee_id int           not null,
  constraint task_pk
    primary key (id),
  constraint task_project_id_fk
    foreign key (project_id) references project (id),
  constraint task_priority_id_fk
    foreign key (priority_id) references task_priority (id),
  constraint task_status_id_fk
    foreign key (status_id) references task_status (id),
  constraint task_assignee_id_fk
    foreign key (assignee_id) references user (id)
);