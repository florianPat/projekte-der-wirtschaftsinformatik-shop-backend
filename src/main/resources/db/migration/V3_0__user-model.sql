create table role (id serial not null, name varchar(255), primary key (id));
create table users (id serial not null, birthday varchar(255), city varchar(255), email varchar(255), firstname varchar(255), lastname varchar(255), password varchar(255), password_repeat varchar(255), privacy_statement boolean not null, street varchar(255), zip varchar(255), primary key (id));
create table users_roles (users_id int4 not null, role_id int4 not null, primary key (users_id, role_id));
alter table users add constraint UKob8kqyqqgmefl0aco34akdtpe unique (email);
alter table users_roles add constraint FKrhfovtciq1l558cw6udg0h0d3 foreign key (role_id) references role;
alter table users_roles add constraint FK55itppkw3i07do3h7qoclqd4k foreign key (users_id) references users;
