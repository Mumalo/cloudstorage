CREATE TABLE IF NOT EXISTS USERS (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(20),
  salt VARCHAR(900),
  password VARCHAR(700),
  firstname VARCHAR(200),
  lastname VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS NOTES (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(20),
    description VARCHAR (1000),
    userid INT,
    foreign key (userid) references USERS(id)
);

CREATE TABLE IF NOT EXISTS FILES (
    id INT AUTO_INCREMENT PRIMARY KEY,
    filename VARCHAR(500),
    contenttype VARCHAR(300),
    filesize VARCHAR(200),
    userid INT,
    filedata LONGBLOB,
    foreign key (userid) references USERS(id)
);

CREATE TABLE IF NOT EXISTS CREDENTIALS (
    id INT AUTO_INCREMENT PRIMARY KEY,
    url VARCHAR(100),
    username VARCHAR (30),
    `key` VARCHAR(1000),
    password VARCHAR(700),
    userid INT,
    foreign key (userid) references USERS(id)
);