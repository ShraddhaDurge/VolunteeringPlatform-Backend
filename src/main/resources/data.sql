INSERT INTO event (event_id, event_type, name, description, venue, start_time, end_time) VALUES (11, 'Weekend event','Animal Shelter', 'Animal Shelter', 'Mumbai','2021-08-20T17:30:00.000+00','2021-08-20T20:30:00.000+00');
INSERT INTO event (event_id, event_type, name, description, venue, start_time, end_time) VALUES (12, 'Weekend event','Children Home', 'Children Home', 'Bangalore', '2021-08-23T18:30:00.000+00','2021-08-20T20:30:00.000+00');
INSERT INTO event (event_id, event_type, name, description, venue, start_time, end_time) VALUES (13, 'Weekend event','Old Age Home', 'Old Age Home', 'Delhi', '2021-07-20T17:30:00.000+00','2021-07-20T20:30:00.000+00');
INSERT INTO event (event_id, event_type, name, description, venue, start_time, end_time) VALUES (14, 'Weekend event','Green Earth', 'Green Earth', 'Chennai',  '2021-07-20T17:30:00.000+00','2021-07-20T20:30:00.000+00');
INSERT INTO event (event_id, event_type, name, description, venue, start_time, end_time) VALUES (15, 'NGO Webinars','Empowering Women', 'Empowering Women', 'Pune',  '2021-08-20T17:30:00.000+00','2021-08-20T20:30:00.000+00');
INSERT INTO event (event_id, event_type, name, description, venue, start_time, end_time) VALUES (16, 'Food For Thought','Feeding India', 'Feeding India', 'Kolkata',  '2021-08-20T17:30:00.000+00','2021-08-20T20:30:00.000+00');
INSERT INTO event (event_id, event_type, name, description, venue, start_time, end_time) VALUES (17, 'Art And Craft','Creating history', 'Creating history', 'Gujarat',  '2021-08-20T17:30:00.000+00','2021-08-20T20:30:00.000+00');
INSERT INTO event (event_id, event_type, name, description, venue, start_time, end_time) VALUES (18, 'NGO Webinars', 'Teach India', 'Teach India','Pune',  '2021-07-20T17:30:00.000+00','2021-07-20T20:30:00.000+00');
INSERT INTO event (event_id, event_type, name, description, venue, start_time, end_time) VALUES (19, 'Food For Thought','Food Shelter', 'Food Shelter', 'Kolkata',  '2021-07-20T17:30:00.000+00','2021-07-20T20:30:00.000+00');
INSERT INTO event (event_id, event_type, name, description, venue, start_time, end_time) VALUES (20, 'Art And Craft','Draw and Improve', 'Draw and Improve', 'Gujarat',  '2021-07-20T17:30:00.000+00','2021-07-20T20:30:00.000+00');

INSERT INTO user (user_id, email, first_name, last_name, password, active, role) VALUES (11,'spdurge99@gmail.com','Nikita','Deore','$2a$10$j9amWymVTsGRxLSo8aLhkuN9HaWHGpsrK9sNxXOlb3u0LSPP/goMq',1,'ADMIN');
INSERT INTO user (user_id, email, first_name, last_name, password, active, role) VALUES (12,'spdurge99@gmail.com','Ajit','Kale','$2a$10$Av39pS3b1uNlQnzz6lWrKOcl1CJzL9oA41IsEqJ96GJy1sUt9o49S',1,'ADMIN');
INSERT INTO user (user_id, email, first_name, last_name, password, active, role) VALUES (13,'spdurge99@gmail.com','Priya','Kate','$2a$10$SBehPJvm5jWQGmmfTRDtiurFizPhZ2qzIo.hUd19DTkz0kL4IoPku',1,'LEADER');
INSERT INTO user (user_id, email, first_name, last_name, password, active, role) VALUES (14,'spdurge99@gmail.com','Akid','Khan','$2a$10$qsYFGexi12e3pWFM/maa/.n9DLOFWoHV2mL3iU7TclIiv.VAcR6Qq',1,'LEADER');

INSERT INTO user_events (user_user_id, events_event_id) VALUES (11,11);
INSERT INTO user_events (user_user_id, events_event_id) VALUES (12,12);
INSERT INTO user_events (user_user_id, events_event_id) VALUES (13,11);
INSERT INTO user_events (user_user_id, events_event_id) VALUES (14,12);
