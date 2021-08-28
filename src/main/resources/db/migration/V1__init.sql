create sequence hibernate_sequence start 1 increment 1;
create table category (id serial not null, cover varchar(255), title varchar(255), primary key (id));

insert into category (cover, title) values ('categories/alcoholic.jpg', 'Alkoholisch');
insert into category (cover, title) values ('categories/nonAlcoholic.jpg', 'Nicht Alkoholisch');
insert into category (cover, title) values ('categories/spirits.jpg', 'Spirituosen');
insert into category (cover, title) values ('categories/softDrinks.jpg', 'Soft Drinks');
insert into category (cover, title) values ('categories/water.jpg', 'Wasser');
insert into category (cover, title) values ('categories/wine.jpg', 'Wein');
insert into category (cover, title) values ('categories/beer.jpg', 'Bier');
insert into category (cover, title) values ('categories/sparklingWine.jpg', 'Sekt');
