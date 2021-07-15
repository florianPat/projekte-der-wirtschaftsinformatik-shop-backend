create table allergen (id serial not null, name varchar(255), primary key (id));
create table allergen_products (allergen_id int4 not null, product_id int4 not null);
create table producer (id serial not null, name varchar(255), primary key (id));
create table product (id serial not null, name varchar(255), category_id int4, producer_id int4, primary key (id));
create table product_variant (id serial not null, number_of_bottles int4 not null, price float4 not null, quantity float4 not null, product_id int4, primary key (id));
alter table allergen_products add constraint FKj66tyloxyuqyg57p9oslpgpfj foreign key (product_id) references product;
alter table allergen_products add constraint FKqr2pr3jmtf6aoiagd164r5d2n foreign key (allergen_id) references allergen;
alter table product add constraint FK1mtsbur82frn64de7balymq9s foreign key (category_id) references category;
alter table product add constraint FKaxeo9fj1sfah36yd9bujs8qft foreign key (producer_id) references producer;
alter table product_variant add constraint FKgrbbs9t374m9gg43l6tq1xwdj foreign key (product_id) references product;
