CREATE TABLE "admin"
(
    id       BIGINT    NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    constraint pk_admin PRIMARY KEY (id)
);

CREATE SEQUENCE IF NOT EXISTS admin_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE "user" (
                        id BIGINT NOT NULL,
                        security_question VARCHAR(255) NOT NULL,
                        security_answer VARCHAR(255) NOT NULL,
                        username VARCHAR(255) UNIQUE NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        name VARCHAR(255) NOT NULL,
                        last_name VARCHAR(255) NOT NULL,
                        gender CHAR(1) CHECK(gender IN ('M', 'F')) NOT NULL,
                        address VARCHAR(255) NOT NULL,
                        phone_number VARCHAR(20) NOT NULL,
                        email VARCHAR(255) UNIQUE NOT NULL,
                        profile_photo BYTEA NULL,
                        card_number varchar(255) NOT NULL,
                        status VARCHAR(50) NOT NULL,
                        type varchar(50) NOT NULL ,
                        constraint pk_user PRIMARY KEY (id)
);

CREATE SEQUENCE IF NOT EXISTS user_seq START WITH 1 INCREMENT BY 1;