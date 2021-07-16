DROP TABLE IF EXISTS users_roles;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS user;

CREATE TABLE role (
  role_id int(20) NOT NULL,
  role varchar(255) DEFAULT NULL,
  PRIMARY KEY (role_id)
);

CREATE TABLE user (
  user_id bigint(20) NOT NULL,
  email varchar(255) DEFAULT NULL,
  first_name varchar(255) DEFAULT NULL,
  last_name varchar(255) DEFAULT NULL,
  password varchar(255) DEFAULT NULL,
  active int(10) DEFAULT NULL,
  PRIMARY KEY (user_id),
  UNIQUE KEY (email)
);

CREATE TABLE users_roles (
  user_id bigint(20) NOT NULL,
  role_id bigint(20) NOT NULL,
  FOREIGN KEY (user_id) REFERENCES user(user_id),
  FOREIGN KEY (role_id) REFERENCES role(role_id)
);