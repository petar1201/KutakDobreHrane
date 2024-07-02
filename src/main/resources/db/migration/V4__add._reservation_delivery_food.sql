CREATE TABLE "reservation" (
                              id BIGINT NOT NULL,
                              id_guest BIGINT NOT NULL,
                              id_waiter BIGINT NULL,
                              id_restaurant BIGINT NOT NULL,
                              date_time TIMESTAMP NOT NULL,
                              description varchar(255) NULL,
                              status varchar(255) NOT NULL,
                              CONSTRAINT fk_reservation_guest FOREIGN KEY (id_guest) REFERENCES "user" (id),
                              CONSTRAINT fk_reservation_waiter FOREIGN KEY (id_waiter) REFERENCES "user" (id),
                              CONSTRAINT fk_reservation_restaurant FOREIGN KEY (id_restaurant) REFERENCES "restaurant" (id),
                              constraint pk_reservation PRIMARY KEY (id)
);
CREATE SEQUENCE IF NOT EXISTS reservation_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE "table_usage" (
                               id BIGINT NOT NULL,
                               id_reservation BIGINT NOT NULL,
                               id_restaurant BIGINT NOT NULL,
                               table_num BIGINT NOT NULL,
                               status varchar(255) NOT NULL,
                               CONSTRAINT fk_table_usage_reservation FOREIGN KEY (id_reservation) REFERENCES "reservation" (id),
                               CONSTRAINT fk_table_usage_restaurant FOREIGN KEY (id_restaurant) REFERENCES "restaurant" (id),
                               constraint pk_table_usage PRIMARY KEY (id)
);
CREATE SEQUENCE IF NOT EXISTS table_usage_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE "food" (
                               id BIGINT NOT NULL,
                               id_restaurant BIGINT NOT NULL,
                               name varchar(255) NOT NULL,
                               photo BYTEA NULL,
                               price BIGINT NOT NULL,
                               ingredients VARCHAR(255),
                               CONSTRAINT fk_food_restaurant FOREIGN KEY (id_restaurant) REFERENCES "restaurant" (id),
                               constraint pk_food PRIMARY KEY (id)
);
CREATE SEQUENCE IF NOT EXISTS food_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE "delivery" (
                               id BIGINT NOT NULL,
                               id_guest BIGINT NOT NULL,
                               id_restaurant BIGINT NOT NULL,
                               delivery_time varchar(255) NOT NULL,
                               date_time TIMESTAMP NOT NULL,
                               status varchar(255) NOT NULL,
                               CONSTRAINT fk_delivery_guest FOREIGN KEY (id_guest) REFERENCES "user" (id),
                               CONSTRAINT fk_delivery_restaurant FOREIGN KEY (id_restaurant) REFERENCES "restaurant" (id),
                               constraint pk_delivery PRIMARY KEY (id)
);
CREATE SEQUENCE IF NOT EXISTS delivery_seq START WITH 1 INCREMENT BY 1;
