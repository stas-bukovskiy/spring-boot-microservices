-- Create the course table
CREATE TABLE IF NOT EXISTS course
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255)     NOT NULL,
    credits     DOUBLE PRECISION NOT NULL,
    workload    INT              NOT NULL,
    description TEXT,
    faculty_id  BIGINT,
    CONSTRAINT fk_faculty FOREIGN KEY (faculty_id) REFERENCES faculty (id)
);

-- Create the student table
CREATE TABLE IF NOT EXISTS student
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Create the course_student join table for many-to-many relationship
CREATE TABLE IF NOT EXISTS course_student
(
    course_id  BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    PRIMARY KEY (course_id, student_id),
    CONSTRAINT fk_course FOREIGN KEY (course_id) REFERENCES course (id),
    CONSTRAINT fk_student FOREIGN KEY (student_id) REFERENCES student (id)
);
