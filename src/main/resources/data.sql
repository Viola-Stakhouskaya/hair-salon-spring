INSERT INTO gender
VALUES (1, 'male'),
       (2, 'female'),
       (3, 'unset');

-- CREATING ADMIN
-- LOGIN: admin
-- PASSWORD: admin
INSERT INTO customer(login,
                     customer_email,
                     customer_password,
                     customer_name,
                     customer_surname,
                     phone_number,
                     active)
VALUES ('admin',
        'admin@gmail.com',
        '$2y$08$TyLias0VEu0hBs9obVm/uO2hi3xcI7D0va.GHp9Cx5WH7NPcCwVQC',
        'ADMIN',
        'ADMIN',
        NULL,
        TRUE);

-- CREATING CUSTOMER
-- LOGIN: maxim
-- PASSWORD: maxim12345

-- `login
-- pass: viola
INSERT INTO customer(login,
                     customer_email,
                     customer_password,
                     customer_name,
                     customer_surname,
                     phone_number,
                     active)
VALUES ('viola',
        'viola.staki@gmail.com',
        '$2y$08$e0RCtSRpE3dSaUIcjI/gueRF85fwQq9TCBbYlNAqAAzxLYw4bvkEC',
        'viola',
        'staki',
        '',
        TRUE);

INSERT INTO user_role(user_id, roles)
VALUES (1, 'ADMIN'),
       (2, 'USER');

INSERT INTO employee(id, name, surname, payrate, gender_id)
VALUES (1, 'Мария', 'Кравчук', 1500, 2),
       (2, 'Эвелина', 'Захарьева', 2100, 2),
       (3, 'Максим', 'Ефимов', 1900, 1);

INSERT INTO salon_procedure(id, cost, title, duration, description)
VALUES (1, 60, 'Женская стрижка', 90, 'Стрижка женская. Очень'),
       (2, 25, 'Мужская стрижка', 90, 'Стрижка мужская. Не очень'),
       (3, 50, 'Окраска волос', 120, '');

INSERT INTO employee_procedure(employee_id, procedure_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 1),
       (2, 2),
       (2, 3),
       (3, 1),
       (3, 2),
       (3, 3);

INSERT INTO procedure_appointment(id, appointment_date, appointment_time, customer_id, procedure_id,
                                  performing_employee_id)
VALUES (1, '2020-12-13', '13:00:00', 2, 1, 1);

INSERT INTO procedure_appointment(id, appointment_date, appointment_time, customer_id, procedure_id,
                                  performing_employee_id)
VALUES (2, '2020-12-18', '20:00:00', 2, 3, 2);
