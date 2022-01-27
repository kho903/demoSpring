
DROP TABLE IF EXISTS QNA;

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

)
