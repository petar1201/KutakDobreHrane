CREATE TABLE waiter_restaurant (
                                   user_id BIGINT NOT NULL,
                                   restaurant_id BIGINT NOT NULL,
                                   PRIMARY KEY (user_id, restaurant_id),
                                   CONSTRAINT fk_waiter_user FOREIGN KEY (user_id) REFERENCES "user" (id),
                                   CONSTRAINT fk_waiter_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurant (id)
);
