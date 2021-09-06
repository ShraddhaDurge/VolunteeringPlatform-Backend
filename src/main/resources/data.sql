--INSERT INTO event (event_id, event_type, name, description, venue, start_time, end_time) VALUES (1, 'Weekend event','Animal Shelter', 'Come to our fifth animal pet adoption day at mumbai , take a look our long time friends at The Animal Shelter, and help them to find a forever home. ADOPT, DO NOT SHOP', 'Mumbai','2021-08-28T12:30:00.000+05:30', '2021-08-28T14:30:00.000+05:30');
--INSERT INTO event (event_id, event_type, name, description, venue, start_time, end_time) VALUES (2, 'Weekend event','Children Home', 'Lend a contributing hand to help us support 200 orphan children in our orphanage by donating us money, clothes, or food. Your contribution is going to make a big difference in the lives of the children at our orphanage.', 'Bangalore', '2021-08-28T13:00:00.000+05:30', '2021-08-28T14:30:00.000+05:30');
INSERT INTO event (event_id, event_type, name, description, venue, start_time, end_time) VALUES (1, 'Weekend event','Old Age Home', 'Old age home is the event for volunteers to interact with old people and spend time with them', 'Delhi', '2021-07-20T11:30:00.000+05:30', '2021-07-20T13:30:00.000+05:30');
INSERT INTO event (event_id, event_type, name, description, venue, start_time, end_time) VALUES (2, 'Weekend event','Green Earth', 'Green Earth Foundation is organizing a event to spread the awareness about climate change crisis.', 'Chennai',  '2021-08-12T17:00:00.000+05:30', '2021-07-20T19:00:00.000+05:30');
INSERT INTO event (event_id, event_type, name, description, venue, start_time, end_time) VALUES (3, 'Weekend event','Empowering Women', 'Empowering Women', 'Pune',  '2021-08-20T17:30:00.000+00', '2021-08-20T20:30:00.000+00');
INSERT INTO event (event_id, event_type, name, description, venue, start_time, end_time) VALUES (4, 'Weekend event','Feeding India', 'Feeding India', 'Kolkata',  '2021-08-20T17:30:00.000+00', '2021-08-20T20:30:00.000+00');
INSERT INTO event (event_id, event_type, name, description, venue, start_time, end_time) VALUES (5, 'Weekend event','Creating history', 'Creating history', 'Gujarat',  '2021-08-20T17:30:00.000+00', '2021-08-20T20:30:00.000+00');
--INSERT INTO event (event_id, event_type, name, description, venue, start_time, end_time) VALUES (8, 'NGO Webinars', 'Teach India', 'Teach India','Pune',  '2021-07-20T17:30:00.000+00', '2021-07-20T20:30:00.000+00');
--INSERT INTO event (event_id, event_type, name, description, venue, start_time, end_time) VALUES (9, 'Food For Thought','Food Shelter', 'Food Shelter', 'Kolkata',  '2021-07-20T17:30:00.000+00', '2021-07-20T20:30:00.000+00');
--INSERT INTO event (event_id, event_type, name, description, venue, start_time, end_time) VALUES (10, 'Art And Craft','Draw and Improve', 'Draw and Improve', 'Gujarat',  '2021-07-20T17:30:00.000+00', '2021-07-20T20:30:00.000+00');

--admin1
INSERT INTO user (user_id, email, first_name, last_name, password, active, role, hours) VALUES (1,'spdurge99@gmail.com','Shraddha','Durge','$2a$10$j9amWymVTsGRxLSo8aLhkuN9HaWHGpsrK9sNxXOlb3u0LSPP/goMq',1, 'ADMIN',3);
--admin2
INSERT INTO user (user_id, email, first_name, last_name, password, active, role, hours) VALUES (2,'aryams11042000@gmail.com','Arya','M S','$2a$10$Av39pS3b1uNlQnzz6lWrKOcl1CJzL9oA41IsEqJ96GJy1sUt9o49S', 1, 'ADMIN',2);
--admin3
INSERT INTO user (user_id, email, first_name, last_name, password, active, role, hours) VALUES (3,'kausalyar2000@gmail.com','Kausalya','Rajeshwari','$2a$10$LFdJypoqpnwI.izirZQow.1mMxL6grjT0Km/YgDVM7//axsS39vMW', 1, 'ADMIN',4);
--leader1
INSERT INTO user (user_id, email, first_name, last_name, password, active, role, hours) VALUES (4,'mspletters@gmail.com','Maanasa','S. Prasad','$2a$10$SBehPJvm5jWQGmmfTRDtiurFizPhZ2qzIo.hUd19DTkz0kL4IoPku', 1,'LEADER',3);
--leader2
INSERT INTO user (user_id, email, first_name, last_name, password, active, role, hours) VALUES (5,'shreya.b.patil.05@gmail.com','Shreya','Patil','$2a$10$qsYFGexi12e3pWFM/maa/.n9DLOFWoHV2mL3iU7TclIiv.VAcR6Qq', 1,'LEADER', 2);
--leader3
INSERT INTO user (user_id, email, first_name, last_name, password, active, role, hours) VALUES (6,'odetomusic1347@gmail.com','Geethanjali','Mathu','	$2a$10$yzTYh9bdKe0ErdOIFsMuQe0oPi2ulFsX7QE98oMRiPk8a1XY3d1gO', 1,'LEADER', 4);


INSERT INTO user_events (user_user_id, events_event_id) VALUES (1,1);
INSERT INTO user_events (user_user_id, events_event_id) VALUES (2,2);
INSERT INTO user_events (user_user_id, events_event_id) VALUES (3,3);
INSERT INTO user_events (user_user_id, events_event_id) VALUES (4,4);
INSERT INTO user_events (user_user_id, events_event_id) VALUES (5,5);
INSERT INTO user_events (user_user_id, events_event_id) VALUES (6,1);

INSERT INTO user_events (user_user_id, events_event_id) VALUES (1,2);
INSERT INTO user_events (user_user_id, events_event_id) VALUES (2,3);
INSERT INTO user_events (user_user_id, events_event_id) VALUES (3,4);
INSERT INTO user_events (user_user_id, events_event_id) VALUES (4,5);
INSERT INTO user_events (user_user_id, events_event_id) VALUES (5,1);
INSERT INTO user_events (user_user_id, events_event_id) VALUES (6,2);