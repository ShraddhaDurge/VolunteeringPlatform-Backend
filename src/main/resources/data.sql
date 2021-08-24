INSERT INTO event (event_id, event_type, name, description, venue, start_time, end_time) VALUES (1, 'Weekend event','Animal Shelter', 'Animal Shelter', 'Mumbai','2021-08-28T12:30:00.000+05:30', '2021-08-28T14:30:00.000+05:30');
INSERT INTO event (event_id, event_type, name, description, venue, start_time, end_time) VALUES (2, 'Weekend event','Children Home', 'Children Home', 'Bangalore', '2021-08-28T13:00:00.000+05:30', '2021-08-28T14:30:00.000+05:30');
INSERT INTO event (event_id, event_type, name, description, venue, start_time, end_time) VALUES (3, 'Weekend event','Old Age Home', 'Old Age Home', 'Delhi', '2021-07-20T11:30:00.000+00', '2021-07-20T13:30:00.000+00');
INSERT INTO event (event_id, event_type, name, description, venue, start_time, end_time) VALUES (4, 'Weekend event','Green Earth', 'Green Earth', 'Chennai',  '2021-07-20T12:30:00.000+00', '2021-07-20T14:30:00.000+00');
INSERT INTO event (event_id, event_type, name, description, venue, start_time, end_time) VALUES (5, 'NGO Webinars','Empowering Women', 'Empowering Women', 'Pune',  '2021-08-20T17:30:00.000+00', '2021-08-20T20:30:00.000+00');
INSERT INTO event (event_id, event_type, name, description, venue, start_time, end_time) VALUES (6, 'Food For Thought','Feeding India', 'Feeding India', 'Kolkata',  '2021-08-20T17:30:00.000+00', '2021-08-20T20:30:00.000+00');
INSERT INTO event (event_id, event_type, name, description, venue, start_time, end_time) VALUES (7, 'Art And Craft','Creating history', 'Creating history', 'Gujarat',  '2021-08-20T17:30:00.000+00', '2021-08-20T20:30:00.000+00');
INSERT INTO event (event_id, event_type, name, description, venue, start_time, end_time) VALUES (8, 'NGO Webinars', 'Teach India', 'Teach India','Pune',  '2021-07-20T17:30:00.000+00', '2021-07-20T20:30:00.000+00');
INSERT INTO event (event_id, event_type, name, description, venue, start_time, end_time) VALUES (9, 'Food For Thought','Food Shelter', 'Food Shelter', 'Kolkata',  '2021-07-20T17:30:00.000+00', '2021-07-20T20:30:00.000+00');
INSERT INTO event (event_id, event_type, name, description, venue, start_time, end_time) VALUES (10, 'Art And Craft','Draw and Improve', 'Draw and Improve', 'Gujarat',  '2021-07-20T17:30:00.000+00', '2021-07-20T20:30:00.000+00');

--admin1
INSERT INTO user (user_id, email, first_name, last_name, password, active, role, hours) VALUES (1,'spdurge99@gmail.com','Nikita','Deore','$2a$10$j9amWymVTsGRxLSo8aLhkuN9HaWHGpsrK9sNxXOlb3u0LSPP/goMq',1, 'ADMIN',3);
--admin2
INSERT INTO user (user_id, email, first_name, last_name, password, active, role, hours) VALUES (2,'aryams11042000@gmail.com','Ajit','Kale','$2a$10$Av39pS3b1uNlQnzz6lWrKOcl1CJzL9oA41IsEqJ96GJy1sUt9o49S', 1, 'ADMIN',2);
--leader1
INSERT INTO user (user_id, email, first_name, last_name, password, active, role, hours) VALUES (3,'mspletters@gmail.com','Priya','Kate','$2a$10$SBehPJvm5jWQGmmfTRDtiurFizPhZ2qzIo.hUd19DTkz0kL4IoPku', 1,'LEADER',3);
--leader2
INSERT INTO user (user_id, email, first_name, last_name, password, active, role, hours) VALUES (4,'shreya.b.patil.05@gmail.com','Akid','Khan','$2a$10$qsYFGexi12e3pWFM/maa/.n9DLOFWoHV2mL3iU7TclIiv.VAcR6Qq', 1,'LEADER', 2);

INSERT INTO user_events (user_user_id, events_event_id) VALUES (1,1);
INSERT INTO user_events (user_user_id, events_event_id) VALUES (2,2);
--INSERT INTO user_events (user_user_id, events_event_id) VALUES (3,1);
INSERT INTO user_events (user_user_id, events_event_id) VALUES (4,2);
