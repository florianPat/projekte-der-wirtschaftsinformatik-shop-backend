alter table orders add column user_id int4;
alter table orders add constraint FK32ql8ubntj5uh44ph9659tiih foreign key (user_id) references users;
alter table users drop constraint UKob8kqyqqgmefl0aco34akdtpe;
