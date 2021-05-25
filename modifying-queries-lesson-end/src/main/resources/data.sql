INSERT INTO Project(id, code, name, description) VALUES (1, 'P1', 'Project 1', 'Description of Project 1');
INSERT INTO Project(id, code, name, description) VALUES (2, 'P2', 'Project 2', 'About Project 2');
INSERT INTO Project(id, code, name, description) VALUES (3, 'P3', 'Project 3', 'About Project 3');

INSERT INTO User(id, email, first_name, last_name) VALUES(1, 'john@test.com', 'John', 'Doe');

INSERT INTO Task(id, uuid, name, due_date, description, project_id, status) VALUES (1, uuid(), 'Task 1', '2025-01-12', 'Task 1 Description', 1, 0);
INSERT INTO Task(id, uuid, name, due_date, description, project_id, status) VALUES (2, uuid(), 'Task 2', '2025-02-10', 'Task 2 Description', 1, 0);
INSERT INTO Task(id, uuid, name, due_date, description, project_id, status) VALUES (3, uuid(), 'Task 3', '2025-03-16', 'Task 3 Description', 1, 0);
INSERT INTO Task(id, uuid, name, due_date, description, project_id, status, assignee_id) VALUES (4, uuid(), 'Task 4', '2025-06-25', 'Task 4 Description', 2, 0, 1);
INSERT INTO Task(id, uuid, name, due_date, description, project_id, status) VALUES (5, uuid(), 'Task 5', '2025-05-17', 'Task 5 Description', 1, 3);