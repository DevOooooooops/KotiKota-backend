create extension if not exists "uuid-ossp";

create table if not exists "user" (
    id varchar constraint user_pk primary key default uuid_generate_v4(),
    username varchar unique,
    firebase_id varchar unique,
    birthdate date,
    first_name varchar,
    last_name varchar,
    email varchar unique
);