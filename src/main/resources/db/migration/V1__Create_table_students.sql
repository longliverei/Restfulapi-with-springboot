CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE public.students (
    id bigserial NOT NULL ,
    first_name varchar(30) NOT NULL,
    last_name varchar(50) NOT NULL,
    email varchar(50) NOT NULL,
    course varchar(40) NOT NULL,
    PRIMARY KEY (id)
)