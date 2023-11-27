--changeset relucky:1
CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(255),
    email    VARCHAR(255),
    age      INT,
    password VARCHAR(255),
    role     VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS task
(
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(255),
    description VARCHAR(255),
    user_id     BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS file
(
    id        SERIAL PRIMARY KEY,
    filename  VARCHAR(255),
    miniopath VARCHAR(255),
    filetype  VARCHAR(255),
    task_id   BIGINT NOT NULL,
    FOREIGN KEY (task_id) REFERENCES task (id)
);
