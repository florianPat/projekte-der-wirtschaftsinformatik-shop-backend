alter table orders add column created_at timestamp;
alter table product add column min_age int4 not null default 0;
alter table users add column has_verified_age boolean not null default false;
