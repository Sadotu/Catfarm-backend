INSERT INTO files (id, file_name, extension, size, location, upload_date)
VALUES (101, 'document', 'docx', 1024.5, 'documents/', '2023-05-18');

INSERT INTO files (id, file_name, extension, size, location, upload_date)
VALUES (102, 'image', 'png', 2048.0, 'images/', '2023-05-18');

INSERT INTO files (id, file_name, extension, size, location, upload_date)
VALUES (103, 'presentation', 'pptx', 5120.75, 'presentations/', '2023-05-18');

INSERT INTO files (id, file_name, extension, size, location, upload_date)
VALUES (104, 'video', 'mp4', 8192.0, 'videos/', '2023-05-18');

INSERT INTO files (id, file_name, extension, size, location, upload_date)
VALUES (105, 'spreadsheet', 'xlsx', 3072.25, 'spreadsheets/', '2023-05-18');

INSERT INTO files (id, file_name, extension, size, location, upload_date)
VALUES (106, 'audio', 'mp3', 512.75, 'audio/', '2023-05-18');

INSERT INTO files (id, file_name, extension, size, location, upload_date)
VALUES (107, 'code', 'java', 204.5, 'code/', '2023-05-18');

INSERT INTO files (id, file_name, extension, size, location, upload_date)
VALUES (108, 'archive', 'zip', 4096.0, 'archives/', '2023-05-18');

INSERT INTO files (id, file_name, extension, size, location, upload_date)
VALUES (109, 'document2', 'pdf', 512.0, 'documents/', '2023-05-18');

INSERT INTO files (id, file_name, extension, size, location, upload_date)
VALUES (110, 'image2', 'jpg', 1024.0, 'images/', '2023-05-18');

INSERT INTO events (id, name, start_time, end_time, description, color)
VALUES (101, 'Event A', '2023-05-22T09:00:00', '2023-05-22T11:00:00', 'First event', 'red');

INSERT INTO events (id, name, start_time, end_time, description, color)
VALUES (102, 'Event B', '2023-05-23T14:30:00', '2023-05-23T16:30:00', 'Second event', 'blue');

INSERT INTO events (id, name, start_time, end_time, description, color)
VALUES (103, 'Event C', '2023-05-24T18:00:00', '2023-05-24T20:00:00', 'Third event', 'green');

INSERT INTO events (id, name, start_time, end_time, description, color)
VALUES (104, 'Event D', '2023-05-25T10:00:00', '2023-05-25T12:00:00', 'Fourth event', 'yellow');

INSERT INTO events (id, name, start_time, end_time, description, color)
VALUES (105, 'Event E', '2023-05-26T16:00:00', '2023-05-26T18:00:00', 'Fifth event', 'purple');

INSERT INTO events (id, name, start_time, end_time, description, color)
VALUES (106, 'Event F', '2023-05-27T09:30:00', '2023-05-27T11:30:00', 'Sixth event', 'orange');

INSERT INTO events (id, name, start_time, end_time, description, color)
VALUES (107, 'Event G', '2023-05-28T13:00:00', '2023-05-28T15:00:00', 'Seventh event', 'pink');

INSERT INTO events (id, name, start_time, end_time, description, color)
VALUES (108, 'Event H', '2023-05-29T17:30:00', '2023-05-29T19:30:00', 'Eighth event', 'brown');

INSERT INTO events (id, name, start_time, end_time, description, color)
VALUES (109, 'Event I', '2023-05-30T12:00:00', '2023-05-30T14:00:00', 'Ninth event', 'gray');

INSERT INTO events (id, name, start_time, end_time, description, color)
VALUES (110, 'Event J', '2023-05-31T11:00:00', '2023-05-31T13:00:00', 'Tenth event', 'cyan');

INSERT INTO tasks (id, name_task, deadline, description, completed)
VALUES (101, 'Task A', '2023-06-01', 'Task A description', false);

INSERT INTO tasks (id, name_task, deadline, description, completed)
VALUES (102, 'Task B', '2023-06-02', 'Task B description', true);

INSERT INTO tasks (id, name_task, deadline, description, completed)
VALUES (103, 'Task C', '2023-06-03', 'Task C description', true);

INSERT INTO tasks (id, name_task, deadline, description, completed)
VALUES (104, 'Task D', '2023-06-04', 'Task D description', false);

INSERT INTO tasks (id, name_task, deadline, description, completed)
VALUES (105, 'Task E', '2023-06-05', 'Task E description', false);

INSERT INTO tasks (id, name_task, deadline, description, completed)
VALUES (106, 'Task F', '2023-06-06', 'Task F description', true);

INSERT INTO tasks (id, name_task, deadline, description, completed)
VALUES (107, 'Task G', '2023-06-07', 'Task G description', true);

INSERT INTO tasks (id, name_task, deadline, description, completed)
VALUES (108, 'Task H', '2023-06-08', 'Task H description', false);

INSERT INTO tasks (id, name_task, deadline, description, completed)
VALUES (109, 'Task I', '2023-06-09', 'Task I description', false);

INSERT INTO tasks (id, name_task, deadline, description, completed)
VALUES (110, 'Task J', '2023-06-10', 'Task J description', true);

INSERT INTO tasks (id, name_task, deadline, description, completed)
VALUES (111, 'Task K', '2023-09-10', 'Task K description', false);

--password1
INSERT INTO users (enabled, email, full_name, pronouns, age, phone_number, bio, password, newsletter, creation_date)
VALUES (true, 'john.doe@example.com', 'John Doe', 'he/him', 25, '1234567890', 'I am a software engineer', '$2a$12$DQsOkCGWcxRxN6uVvI3ztupqDYtXvAHbGYXfj7hoWxfhTj1XvzEwy', true, '2023-08-08 14:32:18.836');

--password2
INSERT INTO users (enabled, email, full_name, pronouns, age, phone_number, bio, password, newsletter, creation_date)
VALUES (true, 'super@user.com', 'Super User', 'she/her', 30, '9876543210', 'I am a Super User', '$2a$12$6TlNu8.162x5EWUny9rdKePV3EsH85FrxBfnpo8NgGZ3FXKPAjPXK', true, '2023-08-08 14:32:18.836');

--password3
INSERT INTO users (enabled, email, full_name, pronouns, age, phone_number, bio, password, newsletter, creation_date)
VALUES (true, 'michael.johnson@example.com', 'Michael Johnson', 'he/him', 35, '5555555555', 'I am a teacher', '$2a$12$bCo4rM70ylmXuDk9awMmFexWAf1ntxQ/5THBmJA21rfU7UvANldCK', true, '2023-08-08 14:32:18.836');

--password4
INSERT INTO users (enabled, email, full_name, pronouns, age, phone_number, bio, password, newsletter, creation_date)
VALUES (true, 'emily.davis@example.com', 'Emily Davis', 'she/her', 28, '1111111111', 'I am a writer', '$2a$12$O7gq/tqhjQf2LPtL.FJCmedh8GWv12D0AerTLDttgBFyCglhV6zk.', true, '2023-08-08 14:32:18.836');

--password5
INSERT INTO users (enabled, email, full_name, pronouns, age, phone_number, bio, password, newsletter, creation_date)
VALUES (true, 'david.wilson@example.com', 'David Wilson', 'he/him', 32, '9999999999', 'I am an accountant', '$2a$12$i.mCOyyQwYFmNf.8WZ91T.gQ8SntiSrPep3PREXEO/dvoFZvNHe46', true, '2023-08-08 14:32:18.836');

UPDATE files SET task_id = 101 WHERE id = 101;
UPDATE files SET task_id = 102 WHERE id = 102;
UPDATE files SET task_id = 103 WHERE id = 103;
UPDATE files SET task_id = 104 WHERE id = 104;
UPDATE files SET task_id = 105 WHERE id = 105;
UPDATE files SET task_id = 106 WHERE id = 106;
UPDATE files SET task_id = 107 WHERE id = 107;
UPDATE files SET task_id = 108 WHERE id = 108;
UPDATE files SET task_id = 109 WHERE id = 109;
UPDATE files SET task_id = 110 WHERE id = 110;

INSERT INTO authorities (username, authority) VALUES ('super@user.com', 'ROLE_LION');
INSERT INTO authorities (username, authority) VALUES ('super@user.com', 'ROLE_CAT');
INSERT INTO authorities (username, authority) VALUES ('super@user.com', 'ROLE_KITTEN');
INSERT INTO authorities (username, authority) VALUES ('michael.johnson@example.com', 'ROLE_KITTEN');
INSERT INTO authorities (username, authority) VALUES ('emily.davis@example.com', 'ROLE_CAT');
INSERT INTO authorities (username, authority) VALUES ('david.wilson@example.com', 'ROLE_LION');

INSERT INTO task_user (task_id, user_email) VALUES (101, 'emily.davis@example.com');
INSERT INTO task_user (task_id, user_email) VALUES (102, 'emily.davis@example.com');
INSERT INTO task_user (task_id, user_email) VALUES (103, 'emily.davis@example.com');
INSERT INTO task_user (task_id, user_email) VALUES (104, 'emily.davis@example.com');
INSERT INTO task_user (task_id, user_email) VALUES (105, 'emily.davis@example.com');
INSERT INTO task_user (task_id, user_email) VALUES (106, 'emily.davis@example.com');
INSERT INTO task_user (task_id, user_email) VALUES (107, 'emily.davis@example.com');
INSERT INTO task_user (task_id, user_email) VALUES (108, 'emily.davis@example.com');
INSERT INTO task_user (task_id, user_email) VALUES (109, 'emily.davis@example.com');
INSERT INTO task_user (task_id, user_email) VALUES (110, 'emily.davis@example.com');
INSERT INTO task_user (task_id, user_email) VALUES (111, 'emily.davis@example.com');
INSERT INTO task_user (task_id, user_email) VALUES (101, 'michael.johnson@example.com');
INSERT INTO task_user (task_id, user_email) VALUES (102, 'michael.johnson@example.com');
INSERT INTO task_user (task_id, user_email) VALUES (103, 'michael.johnson@example.com');
INSERT INTO task_user (task_id, user_email) VALUES (104, 'michael.johnson@example.com');
INSERT INTO task_user (task_id, user_email) VALUES (105, 'michael.johnson@example.com');
INSERT INTO task_user (task_id, user_email) VALUES (106, 'david.wilson@example.com');
INSERT INTO task_user (task_id, user_email) VALUES (107, 'david.wilson@example.com');
INSERT INTO task_user (task_id, user_email) VALUES (108, 'david.wilson@example.com');
INSERT INTO task_user (task_id, user_email) VALUES (109, 'david.wilson@example.com');
INSERT INTO task_user (task_id, user_email) VALUES (110, 'david.wilson@example.com');

-- Entries for task_id: 101
INSERT INTO todo (id, completed, todo, task_id) VALUES (101, TRUE, 'Get groceries', 101);
INSERT INTO todo (id, completed, todo, task_id) VALUES (102, TRUE, 'Pet dog', 101);
INSERT INTO todo (id, completed, todo, task_id) VALUES (103, FALSE, 'Invite Mike to meeting', 101);
INSERT INTO todo (id, completed, todo, task_id) VALUES (104, FALSE, 'Follow up on Chris', 101);

-- Entry for task_id: 102
INSERT INTO todo (id, completed, todo, task_id) VALUES (105, TRUE, 'Add event to calendar', 102);
INSERT INTO todo (id, completed, todo, task_id) VALUES (106, TRUE, 'Write report for the meeting', 102);
INSERT INTO todo (id, completed, todo, task_id) VALUES (107, TRUE, 'Review project milestones', 102);
INSERT INTO todo (id, completed, todo, task_id) VALUES (108, TRUE, 'Send out invitations for the workshop', 102);
INSERT INTO todo (id, completed, todo, task_id) VALUES (109, TRUE, 'Complete budget analysis', 102);
INSERT INTO todo (id, completed, todo, task_id) VALUES (110, TRUE, 'Order new office supplies', 102);
INSERT INTO todo (id, completed, todo, task_id) VALUES (111, TRUE, 'Book conference room for team meeting', 102);

-- For Task 103
INSERT INTO todo (id, completed, todo, task_id) VALUES (112, TRUE, 'Schedule team catch-up', 103);
INSERT INTO todo (id, completed, todo, task_id) VALUES (113, TRUE, 'Draft proposal for client', 103);

-- For Task 104
INSERT INTO todo (id, completed, todo, task_id) VALUES (114, FALSE, 'Proofread the document', 104);
INSERT INTO todo (id, completed, todo, task_id) VALUES (115, FALSE, 'Submit final draft for approval', 104);
INSERT INTO todo (id, completed, todo, task_id) VALUES (116, FALSE, 'Follow up with the graphics team', 104);

-- For Task 105
INSERT INTO todo (id, completed, todo, task_id) VALUES (117, TRUE, 'Organize team outing', 105);
INSERT INTO todo (id, completed, todo, task_id) VALUES (118, FALSE, 'Collect feedback on the last project', 105);
INSERT INTO todo (id, completed, todo, task_id) VALUES (119, FALSE, 'Order new office supplies', 105);
INSERT INTO todo (id, completed, todo, task_id) VALUES (120, TRUE, 'Book conference room for team meeting', 105);

-- For Task 106
INSERT INTO todo (id, completed, todo, task_id) VALUES (121, TRUE, 'Update software to latest version', 106);
INSERT INTO todo (id, completed, todo, task_id) VALUES (122, TRUE, 'Order new office supplies', 106);
INSERT INTO todo (id, completed, todo, task_id) VALUES (123, TRUE, 'Book conference room for team meeting', 106);
INSERT INTO todo (id, completed, todo, task_id) VALUES (124, TRUE, 'Order new office supplies', 106);
INSERT INTO todo (id, completed, todo, task_id) VALUES (125, TRUE, 'Book conference room for team meeting', 106);

-- For Task 107
INSERT INTO todo (id, completed, todo, task_id) VALUES (126, TRUE, 'Check inventory status', 107);
INSERT INTO todo (id, completed, todo, task_id) VALUES (127, TRUE, 'Place order for new stock', 107);
INSERT INTO todo (id, completed, todo, task_id) VALUES (128, TRUE, 'Confirm delivery date with the supplier', 107);
INSERT INTO todo (id, completed, todo, task_id) VALUES (129, TRUE, 'Order new office supplies', 107);
INSERT INTO todo (id, completed, todo, task_id) VALUES (130, TRUE, 'Book conference room for team meeting', 107);
INSERT INTO todo (id, completed, todo, task_id) VALUES (131, TRUE, 'Order new office supplies', 107);
INSERT INTO todo (id, completed, todo, task_id) VALUES (132, TRUE, 'Book conference room for team meeting', 107);

-- For Task 108
INSERT INTO todo (id, completed, todo, task_id) VALUES (133, TRUE, 'Train new interns on protocol', 108);
INSERT INTO todo (id, completed, todo, task_id) VALUES (134, FALSE, 'Provide them access to required software', 108);

-- For Task 109
INSERT INTO todo (id, completed, todo, task_id) VALUES (135, TRUE, 'Update client database', 109);
INSERT INTO todo (id, completed, todo, task_id) VALUES (136, TRUE, 'Send thank you notes to recent clients', 109);
INSERT INTO todo (id, completed, todo, task_id) VALUES (137, TRUE, 'Prepare presentation for next week', 109);
INSERT INTO todo (id, completed, todo, task_id) VALUES (138, TRUE, 'Order new office supplies', 109);
INSERT INTO todo (id, completed, todo, task_id) VALUES (139, FALSE, 'Book conference room for team meeting', 109);

-- For Task 110
INSERT INTO todo (id, completed, todo, task_id) VALUES (140, TRUE, 'Clean and organize workspace', 110);
INSERT INTO todo (id, completed, todo, task_id) VALUES (141, TRUE, 'Order new office supplies', 110);

-- For Task 111
INSERT INTO todo (id, completed, todo, task_id) VALUES (142, FALSE, 'Review submitted tasks', 111);
INSERT INTO todo (id, completed, todo, task_id) VALUES (143, TRUE, 'Provide feedback to team members', 111);
INSERT INTO todo (id, completed, todo, task_id) VALUES (144, FALSE, 'Set up a feedback session', 111);
