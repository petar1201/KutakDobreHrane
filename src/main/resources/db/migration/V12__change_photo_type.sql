alter table "user" drop column "profile_photo";

alter table "user" add column "profile_photo" VARCHAR;

alter table "food" drop column "photo";

alter table "food" add column "photo" VARCHAR;