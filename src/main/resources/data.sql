INSERT INTO event (event_id, eventType, name, description, venue, startTime, endTime) VALUES (1, 'Weekend event','Animal Shelter', 'Animal Shelter', 'Mumbai',PARSEDATETIME('Tue, 20 Jul 2021 11:00:00 GMT',   'EEE, d MMM yyyy HH:mm:ss z', 'en', 'GMT'),PARSEDATETIME('Tue, 20 Jul 2021 12:00:00 GMT',   'EEE, d MMM yyyy HH:mm:ss z', 'en', 'GMT'));
INSERT INTO event (event_id, eventType, name, description, venue, startTime, endTime) VALUES (2, 'Weekend event','Children Home', 'Children Home', 'Bangalore', '2021-08-20T17:30:00.000+00','2021-08-20T20:30:00.000+00');
INSERT INTO event (event_id, eventType, name, description, venue, startTime, endTime) VALUES (3, 'Weekend event','Old Age Home', 'Old Age Home', 'Delhi', '2021-07-19 14:00:00','2021-07-19 16:00:00');
INSERT INTO event (event_id, eventType, name, description, venue, startTime, endTime) VALUES (4, 'Weekend event','Green Earth', 'Green Earth', 'Chennai',  {ts '2021-07-02 16:00:00.69'}, {ts '2021-07-02 18:00:00.69'});

INSERT INTO user (user_id, email, first_name, last_name, password, active, role, event_id) VALUES (1,'Nikita.Deore@gmail.com','Nikita','Deore','$2a$10$j9amWymVTsGRxLSo8aLhkuN9HaWHGpsrK9sNxXOlb3u0LSPP/goMq',1,'ADMIN',1);
INSERT INTO user (user_id, email, first_name, last_name, password, active, role, event_id) VALUES (2,'Ajit.Kale@gmail.com','Ajit','Kale','$2a$10$Av39pS3b1uNlQnzz6lWrKOcl1CJzL9oA41IsEqJ96GJy1sUt9o49S',1,'ADMIN',2);
INSERT INTO user (user_id, email, first_name, last_name, password, active, role, event_id) VALUES (3,'Priya.Kate@gmail.com','Priya','Kate','$2a$10$SBehPJvm5jWQGmmfTRDtiurFizPhZ2qzIo.hUd19DTkz0kL4IoPku',1,'LEADER',1);
INSERT INTO user (user_id, email, first_name, last_name, password, active, role, event_id) VALUES (4,'Akid.Khan@gmail.com','Akid','Khan','$2a$10$qsYFGexi12e3pWFM/maa/.n9DLOFWoHV2mL3iU7TclIiv.VAcR6Qq',1,'LEADER',2);

--INSERT INTO user_events (user_id, event_id) VALUES (1,1);
--INSERT INTO user_events (user_id, event_id) VALUES (2,2);
--INSERT INTO user_events (user_id, event_id) VALUES (3,1);
--INSERT INTO user_events (user_id, event_id) VALUES (4,2);
