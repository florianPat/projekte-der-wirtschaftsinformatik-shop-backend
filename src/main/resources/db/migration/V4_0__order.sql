create table orders (id serial not null, primary key (id));
create table order_item (id serial not null, quantity int4 not null, order_id int4, product_variant_id int4, primary key (id));
alter table order_item add constraint FKt6wv8m7eshksp5kp8w4b2d1dm foreign key (order_id) references orders;
alter table order_item add constraint FKasbjwtdare2wb3anogb1oai26 foreign key (product_variant_id) references product_variant;
