CREATE database crm;
USE crm;

CREATE TABLE IF NOT EXISTS role (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(100),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user (
    id INT NOT NULL AUTO_INCREMENT,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    fullname VARCHAR(100) NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS status (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS job (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS task (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    user_id INT NOT NULL,
    job_id INT NOT NULL,
    status_id INT NOT NULL DEFAULT 1,
    PRIMARY KEY (id)
);

ALTER TABLE user ADD FOREIGN KEY (role_id) REFERENCES role (id)  ON DELETE CASCADE;
ALTER TABLE job ADD FOREIGN KEY (user_id) REFERENCES user (id)  ON DELETE CASCADE;
ALTER TABLE task ADD FOREIGN KEY (user_id) REFERENCES user (id)  ON DELETE CASCADE;
ALTER TABLE task ADD FOREIGN KEY (job_id) REFERENCES job (id)  ON DELETE CASCADE;
ALTER TABLE task ADD FOREIGN KEY (status_id) REFERENCES status (id)  ON DELETE CASCADE;

INSERT INTO role( name, description ) VALUES ("ROLE_ADMIN", "Quản trị hệ thống");
INSERT INTO role( name, description ) VALUES ("ROLE_LEADER", "Quản lý dự án");
INSERT INTO role( name, description ) VALUES ("ROLE_MEMBER", "Nhân viên");

INSERT INTO status( name ) VALUES ("Chưa thực hiện");
INSERT INTO status( name ) VALUES ("Đang thực hiện");
INSERT INTO status( name ) VALUES ("Đã hoàn thành");