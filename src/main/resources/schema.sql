--CREATE TABLE IF NOT EXISTS event (
--  event_id int NOT NULL AUTO_INCREMENT,
--  event_type varchar(256),
--  name  varchar(256),
--  description  varchar(2048),
--  venue varchar(256),
--  start_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--  end_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
----  image BLOB,
--  PRIMARY KEY (event_id)
--);

CREATE TABLE IF NOT EXISTS user (
  user_id bigint(20) NOT NULL,
  email varchar(255) DEFAULT NULL,
  first_name varchar(255) DEFAULT NULL,
  last_name varchar(255) DEFAULT NULL,
  password varchar(255) DEFAULT NULL,
  active int DEFAULT NULL,
  role varchar(255) DEFAULT NULL,
  PRIMARY KEY (user_id)
--  UNIQUE KEY (email)
);

--CREATE TABLE IF NOT EXISTS user_events (
--  user_user_id  int NOT NULL,
--  events_event_id int NOT NULL,
--  FOREIGN KEY (user_user_id) REFERENCES user(user_id),
--  FOREIGN KEY (events_event_id) REFERENCES event(event_id)
--
--);

CREATE TABLE profile (
    profile_id bigint(20) NOT NULL,
    mobile_number varchar(10) DEFAULT NULL,
    dob DATE ,
    about varchar(255) DEFAULT NULL,
    gender varchar(10) DEFAULT NULL,
    location varchar(255) DEFAULT NULL,
    address varchar(255) DEFAULT NULL,
    PRIMARY KEY(profile_id)
);

CREATE TABLE IF NOT EXISTS user_profile (
  user_user_id  int NOT NULL,
  profiles_profile_id int NOT NULL,
  FOREIGN KEY (user_user_id) REFERENCES user(user_id),
  FOREIGN KEY (profiles_profile_id) REFERENCES profile(profile_id)
);
