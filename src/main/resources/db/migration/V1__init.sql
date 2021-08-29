create sequence hibernate_sequence start 1 increment 1;
create table category (id serial not null, cover varchar(255), title varchar(255), primary key (id));

insert into category (cover, title) values ('categories/alcoholic.svg', 'Alkoholisch');
insert into category (cover, title) values ('categories/nonAlcoholic.svg', 'Nicht Alkoholisch');
insert into category (cover, title) values ('categories/alcoholic.svg', 'Spirituosen');
insert into category (cover, title) values ('categories/softDrinks.svg', 'Soft Drinks');
insert into category (cover, title) values ('categories/water.svg', 'Wasser');
insert into category (cover, title) values ('categories/wine.svg', 'Wein');
insert into category (cover, title) values ('categories/beer.svg', 'Bier');
insert into category (cover, title) values ('categories/sparklingWine.svg', 'Sekt');
