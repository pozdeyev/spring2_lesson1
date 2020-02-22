drop table if exists categories cascade;
create table categories (id bigserial, title varchar(255), primary key(id));
insert into categories
(title) values
('FOOD'), ('DEVICES');

drop table if exists products cascade;
create table products (id bigserial, title varchar(255), category_id bigint, description varchar(5000), price numeric(8, 2), primary key(id), constraint fk_cat_id foreign key (category_id) references categories (id));
insert into products
(title, category_id, description, price) values
('banana', 1, 'big yellow', 75),
('apple', 1, 'fresh red', 95),
('grapefruit', 1, 'good tasty', 90),
('lemon', 1, 'sour', 115),
('NoteBook ASUS X1000', 2, 'Model: ASUS X1000, CPU: Xeon N700, RAM: 128 Gb, SSD: 1Tb', 25000),
('Samsung S10', 2, 'CPU: Quallcom, RAM: 6GB', 80000),
('Iphone 11PRO', 2, 'CPU: A13, RAM: 3GB', 85000),
('Mi 10', 2, 'CPU: Quallcom, RAM: 8GB', 35000),
('P30 Pro', 2, 'CPU: Mediatek, RAM: 6GB', 45000);

DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id                    bigserial,
  phone                 VARCHAR(30) NOT NULL UNIQUE,
  password              VARCHAR(80),
  email                 VARCHAR(50),
  first_name            VARCHAR(50),
  last_name             VARCHAR(50),
  email_token           VARCHAR(50),
  email_approve         boolean,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS roles;
CREATE TABLE roles (
  id                    serial,
  name                  VARCHAR(50) NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS users_roles;
CREATE TABLE users_roles (
  user_id               INT NOT NULL,
  role_id               INT NOT NULL,
  PRIMARY KEY (user_id, role_id),
  FOREIGN KEY (user_id)
  REFERENCES users (id),
  FOREIGN KEY (role_id)
  REFERENCES roles (id)
);

INSERT INTO roles (name)
VALUES
('ROLE_CUSTOMER'), ('ROLE_MANAGER'), ('ROLE_ADMIN');


insert into users (phone, password, first_name, last_name, email, email_token, email_approve)
values
('111','$2y$12$tLpdcz.qMqWm094VFzGmWu2veXxkrDZTuq68EBJtN.sIJOA6noVZm','Admin','Student','pozdeyev@gmail.com','rrrr',true);
-- login: 111, password: admin



INSERT INTO users_roles (user_id, role_id)
VALUES
(1, 1),
(1, 2),
(1, 3);

drop table if exists orders cascade;
create table orders (id bigserial, user_id bigint, price numeric(10, 2), address varchar(5000),contact_phone varchar(128),
primary key(id), constraint fk_user_id foreign key (user_id) references users (id));

drop table if exists orders_items cascade;
create table orders_items (id bigserial, order_id bigint, product_id bigint, quantity int, price numeric(8, 2),
primary key(id), constraint fk_prod_id foreign key (product_id) references products (id),
constraint fk_order_id foreign key (order_id) references orders (id));

drop table if exists comments cascade;
create table comments (id bigserial, product_id bigint, user_id bigint, comment varchar(10000), mark int,
primary key(id), constraint fk_product_id foreign key (product_id) references products (id),
constraint fk_user_id foreign key (user_id) references users (id));

drop table if exists products_images;
create table products_images (id bigserial PRIMARY KEY, product_id bigint, path varchar(255), FOREIGN KEY (product_id) REFERENCES products(id));
insert into products_images (product_id, path) VALUES
(1, 'image.jpg'),
(2, 'image.jpg'),
(3, 'image.jpg'),
(4, 'image.jpg'),
(5, 'image.jpg'),
(6, 'image.jpg'),
(7, 'image.jpg'),
(8, 'image.jpg'),
(9, 'image.jpg');

--insert into comments
--(product_id, user_id, comment, mark) values
--(1, 1, 'Отличные свежие бананы!', 5);

