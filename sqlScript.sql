CREATE DATABASE studentSystem WITH OWNER = postgres ENCODING = 'UTF8' CONNECTION LIMIT = -1;
CREATE SCHEMA students;
CREATE TABLE students.students
(
    id           SERIAL PRIMARY KEY,
    first_name   VARCHAR(100) NOT NULL,
    last_name    VARCHAR(100) NOT NULL,
    year_entered INT          NOT NULL
);
CREATE TABLE students.courses
(
    id                  SERIAL PRIMARY KEY,
    course_name         VARCHAR(100) NOT NULL,
    max_num_of_students INT          NOT NULL,
    num_of_students     INT          NOT NULL
);
CREATE TABLE students.lectures
(
    id                  SERIAL PRIMARY KEY,
    lecture_name        VARCHAR(100) NOT NULL,
    max_num_of_students INT          NOT NULL,
    num_of_students     INT          NOT NULL,
    course_id           INT          NOT NULL
);
CREATE TABLE students.students_courses
(
    id_students INT NOT NULL,
    id_courses  INT NOT NULL,
    grade       INT NOT NULL
);
CREATE TABLE students.students_lectures
(
    id_students INT NOT NULL,
    id_lectures INT NOT NULL
);
CREATE TABLE students.students_averageGrades
(
    id_students   INT   NOT NULL,
    average_grade FLOAT NOT NULL
);

CREATE SCHEMA crypto;
CREATE TABLE crypto.coins
(
    id          SERIAL PRIMARY KEY,
    coin_id     VARCHAR(100) NOT NULL,
    coin_name   VARCHAR(100) NOT NULL,
    coin_symbol VARCHAR(100) NOT NULL
);
CREATE TABLE crypto.market_history
(
    id      SERIAL PRIMARY KEY,
    coin_id VARCHAR(100) NOT NULL,
    coin_value FLOAT NOT NULL ,
    date DATE NOT NULL
);
