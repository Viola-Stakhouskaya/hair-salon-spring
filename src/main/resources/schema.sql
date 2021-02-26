DROP TABLE IF EXISTS procedure_appointment;
DROP TABLE IF EXISTS employee_procedure;
DROP TABLE IF EXISTS salon_procedure;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS customer;
-- DROP TABLE IF EXISTS manager;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS gender;
DROP TABLE IF EXISTS role;


CREATE TABLE IF NOT EXISTS gender
(
    id    BIGINT      NOT NULL PRIMARY KEY,
    value VARCHAR(15) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS role
(
    id   BIGINT      NOT NULL PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS employee
(
    id        BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name      VARCHAR(30) NOT NULL,
    surname   VARCHAR(30) NOT NULL,
    payrate   DOUBLE      NOT NULL,

    gender_id BIGINT      NOT NULL,
    --    role_id   BIGINT      NOT NULL,

    FOREIGN KEY (gender_id) REFERENCES gender (id) ON DELETE CASCADE
    --   FOREIGN KEY (role_id) REFERENCES role (id)
);

/*
CREATE TABLE IF NOT EXISTS manager
(
    id       BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    login    VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(30) NOT NULL,
    name     VARCHAR(30) NOT NULL,

    role_id  BIGINT      NOT NULL,

    FOREIGN KEY (role_id) REFERENCES role (id)
);
*/

CREATE TABLE IF NOT EXISTS customer
(
    id                BIGINT       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    login             VARCHAR(20)  NOT NULL UNIQUE,
    customer_email    VARCHAR(50)  NOT NULL UNIQUE,
    customer_password VARCHAR(100) NOT NULL,
    customer_name     VARCHAR(50)  NOT NULL,
    customer_surname  VARCHAR(50)  NOT NULL,
    phone_number      VARCHAR(20),
    active            BOOLEAN      NOT NULL

    -- gender_id         BIGINT      NOT NULL,
    -- role_id           BIGINT      NOT NULL,

    -- FOREIGN KEY (gender_id) REFERENCES gender (id)
    -- FOREIGN KEY (role_id) REFERENCES role (id)
);

CREATE TABLE IF NOT EXISTS salon_procedure
(
    id          BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    cost        DOUBLE      NOT NULL,
    title       VARCHAR(50) NOT NULL,
    duration    INTEGER     NOT NULL,
    description VARCHAR(300)
);

CREATE TABLE IF NOT EXISTS procedure_appointment
(
    id                     BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    appointment_date       DATE   NOT NULL,
    appointment_time       TIME   NOT NULL,

    customer_id            BIGINT NOT NULL,
    procedure_id           BIGINT NOT NULL,
    performing_employee_id BIGINT NOT NULL,

    FOREIGN KEY (customer_id) REFERENCES customer (id) ON DELETE CASCADE,
    FOREIGN KEY (procedure_id) REFERENCES salon_procedure (id) ON DELETE CASCADE,
    FOREIGN KEY (performing_employee_id) REFERENCES employee (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS employee_procedure
(
    employee_id  BIGINT NOT NULL,
    procedure_id BIGINT NOT NULL,

    FOREIGN KEY (employee_id) REFERENCES employee (id) ON DELETE CASCADE,
    FOREIGN KEY (procedure_id) REFERENCES salon_procedure (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS user_role
(
    user_id BIGINT NOT NULL,
    roles   VARCHAR(20),

    FOREIGN KEY (user_id) REFERENCES customer (id) ON DELETE CASCADE
);
