-- Insert data into the Faculty table
INSERT INTO faculty (id, name, description)
VALUES (1,'Faculty of Science', 'Science department description'),
       (2,'Faculty of Arts', 'Arts department description'),
       (3,'Faculty of Engineering', 'Engineering department description');

-- Insert data into the Discipline table
INSERT INTO discipline (name, description, faculty_id)
VALUES ('Physics', 'Physics discipline description', 1),
       ('Chemistry', 'Chemistry discipline description', 1),
       ('History', 'History discipline description', 2),
       ('Mathematics', 'Mathematics discipline description', 1),
       ('Biology', 'Biology discipline description', 1),
       ('English Literature', 'English Literature discipline description', 2),
       ('Computer Science', 'Computer Science discipline description', 3),
       ('Mechanical Engineering', 'Mechanical Engineering discipline description', 3);