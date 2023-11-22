-- Create the course_student join table for many-to-many relationship
CREATE TABLE IF NOT EXISTS enrollment
(
    id         SERIAL PRIMARY KEY,
    course_id  BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    CONSTRAINT unique_course_and_student UNIQUE (course_id, student_id)
);
