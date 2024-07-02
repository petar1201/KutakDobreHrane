CREATE TABLE "restaurant" (
                        id BIGINT NOT NULL,
                        name VARCHAR(255) NOT NULL,
                        address VARCHAR(255) NOT NULL,
                        contact_person VARCHAR(255) UNIQUE NOT NULL,
                        description varchar(255) NOT NULL,
                        type varchar(50) NOT NULL,
                        working_hours varchar(255) NULL,
                        restaurant_layout varchar(1024) NOT NULL,
                        constraint pk_res PRIMARY KEY (id)
);

CREATE SEQUENCE IF NOT EXISTS res_seq START WITH 1 INCREMENT BY 1;