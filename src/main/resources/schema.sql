DROP TABLE IF EXISTS user;
CREATE TABLE user (
  user_id bigint(20) NOT NULL,
  email varchar(255) DEFAULT NULL,
  first_name varchar(255) DEFAULT NULL,
  last_name varchar(255) DEFAULT NULL,
  password varchar(255) DEFAULT NULL,
  active int(10) DEFAULT NULL,
  role varchar(255) DEFAULT NULL,
  PRIMARY KEY (user_id),
  UNIQUE KEY (email)
);