DELETE
FROM meals;
DELETE
FROM user_roles;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100000, '2020-01-30 10:00', 'User', 500),
       (100000, '2020-01-30 13:00', 'User', 1000),
       (100000, '2020-01-30 20:00', 'User', 500),
       (100000, '2020-01-31 00:00', 'User', 100),
       (100000, '2020-01-31 10:00', 'User', 1000),
       (100000, '2020-01-31 13:00', 'User', 500),
       (100000, '2020-01-31 20:00', 'User', 410),


       (100001, '2020-01-30 10:00', 'Admin', 500),
       (100001, '2020-01-30 13:00', 'Admin', 1000),
       (100001, '2020-01-30 20:00', 'Admin', 500),
       (100001, '2020-01-31 00:00', 'Admin', 100),
       (100001, '2020-01-31 10:00', 'Admin', 1000),
       (100001, '2020-01-31 13:00', 'Admin', 500),
       (100001, '2020-01-31 20:00', 'Admin', 410)
