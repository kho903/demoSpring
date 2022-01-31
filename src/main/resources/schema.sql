
DROP TABLE IF EXISTS QNA;
DROP TABLE IF EXISTS API_ADMIN_USER;

create table QNA
(
    id              BIGINT auto_increment primary key,
    category        VARCHAR(255),
    phone_number    VARCHAR(255),
    email           VARCHAR(255),
    title           VARCHAR(255),
    message         VARCHAR(255),
    checked         BOOLEAN,
    create_date     TIMESTAMP

);

create table API_ADMIN_USER
(
    id              BIGINT auto_increment primary key,
    email           VARCHAR(255),
    password        VARCHAR(255),
    username        VARCHAR(255),
    admin_status    VARCHAR(255),
    create_date     TIMESTAMP,
    update_date     TIMESTAMP
)
