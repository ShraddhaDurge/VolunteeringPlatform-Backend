CREATE TABLE IF NOT EXISTS event (
  event_id int NOT NULL AUTO_INCREMENT,
  eventType varchar(256),
  name  varchar(256),
  description  varchar(2048),
  venue varchar(256),
  startTime TIMESTAMP WITH TIMEZONE,
  endTime TIMESTAMP WITH TIMEZONE,
  PRIMARY KEY (event_id)
);

CREATE TABLE IF NOT EXISTS user (
  user_id bigint(20) NOT NULL,
  email varchar(255) DEFAULT NULL,
  first_name varchar(255) DEFAULT NULL,
  last_name varchar(255) DEFAULT NULL,
  password varchar(255) DEFAULT NULL,
  active int DEFAULT NULL,
  role varchar(255) DEFAULT NULL,
  event_id int NOT NULL,
  FOREIGN KEY (event_id) REFERENCES event(event_id),
  PRIMARY KEY (user_id),
  UNIQUE KEY (email)
);

--CREATE TABLE IF NOT EXISTS user_events (
--  user_id  int NOT NULL,
--  event_id int NOT NULL,
--  FOREIGN KEY (event_id) REFERENCES event(event_id),
--  FOREIGN KEY (user_id) REFERENCES user(user_id),
--);






