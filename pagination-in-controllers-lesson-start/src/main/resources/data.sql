INSERT INTO Project(id, code, name, description) VALUES (1, 'P1', 'Project 1', 'About Project 1');
INSERT INTO Project(id, code, name, description) VALUES (2, 'P2', 'Project 2', 'About Project 2');
INSERT INTO Project(id, code, name, description) VALUES (3, 'P3', 'Project 3', 'About Project 3');

INSERT INTO Worker(id, email, first_name, last_name) VALUES(1, 'john@test.com', 'John', 'Doe');

INSERT INTO Task(id, uuid, name, due_date, description, project_id, status) VALUES (1, uuid(), 'Task 1', '2025-01-12', 'Task 1 Description', 1, 0);
INSERT INTO Task(id, uuid, name, due_date, description, project_id, status) VALUES (2, uuid(), 'Task 2', '2025-02-10', 'Task 2 Description', 1, 0);
INSERT INTO Task(id, uuid, name, due_date, description, project_id, status) VALUES (3, uuid(), 'Task 3', '2025-03-16', 'Task 3 Description', 1, 0);
INSERT INTO Task(id, uuid, name, due_date, description, project_id, status) VALUES (4, uuid(), 'Task 4', '2025-06-25', 'Task 4 Description', 2, 0);
INSERT INTO Task(id, uuid, name, due_date, description, project_id, status) VALUES (5, uuid(), 'Task 5', '2025-07-12', 'Task 5 Description', 1, 0);
INSERT INTO Task(id, uuid, name, due_date, description, project_id, status) VALUES (6, uuid(), 'Task 6', '2025-08-10', 'Task 6 Description', 1, 0);
INSERT INTO Task(id, uuid, name, due_date, description, project_id, status) VALUES (7, uuid(), 'Task 7', '2025-09-16', 'Task 7 Description', 1, 0);
INSERT INTO Task(id, uuid, name, due_date, description, project_id, status) VALUES (8, uuid(), 'Task 8', '2025-02-12', 'Task 8 Description', 1, 0);
INSERT INTO Task(id, uuid, name, due_date, description, project_id, status) VALUES (9, uuid(), 'Task 9', '2025-10-10', 'Task 9 Description', 1, 0);
INSERT INTO Task(id, uuid, name, due_date, description, project_id, status) VALUES (10, uuid(), 'Task 10', '2025-11-16', 'Task 10 Description', 1, 0);