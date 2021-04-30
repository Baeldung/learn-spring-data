INSERT INTO Project(id, code, name, date_created) VALUES (1, 'P1', 'Project 1', '2020-10-13');
INSERT INTO Project(id, code, name, date_created) VALUES (2, 'P2', 'Project 2', '2020-10-14');
INSERT INTO Project(id, code, name, date_created) VALUES (3, 'P3', 'Project 3', '2020-10-15');

INSERT INTO User(id, email, first_name, last_name) VALUES(1, 'john@test.com', 'John', 'Doe');

INSERT INTO Task(id, name, date_created, due_date, description, project_id, status) VALUES (1, 'Task 1', '2020-10-13', '2021-01-12', 'Task 1 Description', 1, 0);
INSERT INTO Task(id, name, date_created, due_date, description, project_id, status) VALUES (2, 'Task 2', '2020-10-13', '2021-02-10', 'Task 2 Description', 1, 0);
INSERT INTO Task(id, name, date_created, due_date, description, project_id, status) VALUES (3, 'Task 3', '2020-10-13', '2021-03-16', 'Task 3 Description', 1, 0);
INSERT INTO Task(id, name, date_created, due_date, description, project_id, status, assignee_id) VALUES (4, 'Task 4', '2020-11-30', '2021-06-25', 'Task 4 Description', 2, 0, 1); 