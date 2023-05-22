INSERT INTO files (id, file_name, type, extension, size, location, upload_date)
VALUES (1, 'document', 'document', 'docx', 1024.5, 'documents/', '2023-05-18');

INSERT INTO files (id, file_name, type, extension, size, location, upload_date)
VALUES (2, 'image', 'image', 'png', 2048.0, 'images/', '2023-05-18');

INSERT INTO files (id, file_name, type, extension, size, location, upload_date)
VALUES (3, 'presentation', 'document', 'pptx', 5120.75, 'presentations/', '2023-05-18');

INSERT INTO files (id, file_name, type, extension, size, location, upload_date)
VALUES (4, 'video', 'video', 'mp4', 8192.0, 'videos/', '2023-05-18');

INSERT INTO files (id, file_name, type, extension, size, location, upload_date)
VALUES (5, 'spreadsheet', 'document', 'xlsx', 3072.25, 'spreadsheets/', '2023-05-18');

INSERT INTO files (id, file_name, type, extension, size, location, upload_date)
VALUES (6, 'audio', 'audio', 'mp3', 512.75, 'audio/', '2023-05-18');

INSERT INTO files (id, file_name, type, extension, size, location, upload_date)
VALUES (7, 'code', 'text', 'java', 204.5, 'code/', '2023-05-18');

INSERT INTO files (id, file_name, type, extension, size, location, upload_date)
VALUES (8, 'archive', 'archive', 'zip', 4096.0, 'archives/', '2023-05-18');

INSERT INTO files (id, file_name, type, extension, size, location, upload_date)
VALUES (9, 'document2', 'document', 'pdf', 512.0, 'documents/', '2023-05-18');

INSERT INTO files (id, file_name, type, extension, size, location, upload_date)
VALUES (10, 'image2', 'image', 'jpg', 1024.0, 'images/', '2023-05-18');

INSERT INTO events (id, name, date, start_time, end_time, description, color)
VALUES (1, 'Event A', '2023-05-22', '2023-05-22T09:00:00', '2023-05-22T11:00:00', 'First event', 'red');

INSERT INTO events (id, name, date, start_time, end_time, description, color)
VALUES (2, 'Event B', '2023-05-23', '2023-05-23T14:30:00', '2023-05-23T16:30:00', 'Second event', 'blue');

INSERT INTO events (id, name, date, start_time, end_time, description, color)
VALUES (3, 'Event C', '2023-05-24', '2023-05-24T18:00:00', '2023-05-24T20:00:00', 'Third event', 'green');

INSERT INTO events (id, name, date, start_time, end_time, description, color)
VALUES (4, 'Event D', '2023-05-25', '2023-05-25T10:00:00', '2023-05-25T12:00:00', 'Fourth event', 'yellow');

INSERT INTO events (id, name, date, start_time, end_time, description, color)
VALUES (5, 'Event E', '2023-05-26', '2023-05-26T16:00:00', '2023-05-26T18:00:00', 'Fifth event', 'purple');

INSERT INTO events (id, name, date, start_time, end_time, description, color)
VALUES (6, 'Event F', '2023-05-27', '2023-05-27T09:30:00', '2023-05-27T11:30:00', 'Sixth event', 'orange');

INSERT INTO events (id, name, date, start_time, end_time, description, color)
VALUES (7, 'Event G', '2023-05-28', '2023-05-28T13:00:00', '2023-05-28T15:00:00', 'Seventh event', 'pink');

INSERT INTO events (id, name, date, start_time, end_time, description, color)
VALUES (8, 'Event H', '2023-05-29', '2023-05-29T17:30:00', '2023-05-29T19:30:00', 'Eighth event', 'brown');

INSERT INTO events (id, name, date, start_time, end_time, description, color)
VALUES (9, 'Event I', '2023-05-30', '2023-05-30T12:00:00', '2023-05-30T14:00:00', 'Ninth event', 'gray');

INSERT INTO events (id, name, date, start_time, end_time, description, color)
VALUES (10, 'Event J', '2023-05-31', '2023-05-31T11:00:00', '2023-05-31T13:00:00', 'Tenth event', 'cyan');

INSERT INTO tasks (id, name_task, deadline, description, accepted, completed)
VALUES (101, 'Task A', '2023-06-01', 'Task A description', true, false);

INSERT INTO tasks (id, name_task, deadline, description, accepted, completed)
VALUES (102, 'Task B', '2023-06-02', 'Task B description', false, true);

INSERT INTO tasks (id, name_task, deadline, description, accepted, completed)
VALUES (103, 'Task C', '2023-06-03', 'Task C description', true, true);

INSERT INTO tasks (id, name_task, deadline, description, accepted, completed)
VALUES (104, 'Task D', '2023-06-04', 'Task D description', false, false);

INSERT INTO tasks (id, name_task, deadline, description, accepted, completed)
VALUES (105, 'Task E', '2023-06-05', 'Task E description', true, false);

INSERT INTO tasks (id, name_task, deadline, description, accepted, completed)
VALUES (106, 'Task F', '2023-06-06', 'Task F description', false, true);

INSERT INTO tasks (id, name_task, deadline, description, accepted, completed)
VALUES (107, 'Task G', '2023-06-07', 'Task G description', true, true);

INSERT INTO tasks (id, name_task, deadline, description, accepted, completed)
VALUES (108, 'Task H', '2023-06-08', 'Task H description', false, false);

INSERT INTO tasks (id, name_task, deadline, description, accepted, completed)
VALUES (109, 'Task I', '2023-06-09', 'Task I description', true, false);

INSERT INTO tasks (id, name_task, deadline, description, accepted, completed)
VALUES (110, 'Task J', '2023-06-10', 'Task J description', false, true);
