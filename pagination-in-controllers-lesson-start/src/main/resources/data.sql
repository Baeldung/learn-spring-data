INSERT INTO Project(id, code, name, description) VALUES (default, 'P1', 'Project 1', 'About Project 1');
INSERT INTO Project(id, code, name, description) VALUES (default, 'P2', 'Project 2', 'About Project 2');
INSERT INTO Project(id, code, name, description) VALUES (default, 'P3', 'Project 3', 'About Project 3');

INSERT INTO Worker(id, email, first_name, last_name) VALUES (default, 'john@test.com', 'John', 'Doe');

INSERT INTO Task(id, uuid, name, due_date, description, project_id, status) VALUES (default, uuid(), 'Task 1', '2025-01-12', 'Task 1 Description', 1, 0);
INSERT INTO Task(id, uuid, name, due_date, description, project_id, status) VALUES (default, uuid(), 'Task 2', '2025-02-10', 'Task 2 Description', 1, 0);
INSERT INTO Task(id, uuid, name, due_date, description, project_id, status) VALUES (default, uuid(), 'Task 3', '2025-03-16', 'Task 3 Description', 1, 0);
INSERT INTO Task(id, uuid, name, due_date, description, project_id, status) VALUES (default, uuid(), 'Task 4', '2025-06-25', 'Task 4 Description', 2, 0);
INSERT INTO Task(id, uuid, name, due_date, description, project_id, status) VALUES (default, uuid(), 'Task 5', '2025-07-12', 'Task 5 Description', 1, 0);
INSERT INTO Task(id, uuid, name, due_date, description, project_id, status) VALUES (default, uuid(), 'Task 6', '2025-08-10', 'Task 6 Description', 1, 0);
INSERT INTO Task(id, uuid, name, due_date, description, project_id, status) VALUES (default, uuid(), 'Task 7', '2025-09-16', 'Task 7 Description', 1, 0);
INSERT INTO Task(id, uuid, name, due_date, description, project_id, status) VALUES (default, uuid(), 'Task 8', '2025-02-12', 'Task 8 Description', 1, 0);
INSERT INTO Task(id, uuid, name, due_date, description, project_id, status) VALUES (default, uuid(), 'Task 9', '2025-10-10', 'Task 9 Description', 1, 0);
INSERT INTO Task(id, uuid, name, due_date, description, project_id, status) VALUES (default, uuid(), 'Task 10', '2025-11-16', 'Task 10 Description', 1, 0);