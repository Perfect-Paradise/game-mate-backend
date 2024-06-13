INSERT INTO platform_user (id, description, display_name, avatar_url, email)
VALUES (1, 'test description', 'test name', 'http://google.com', 'test@gmail.com');

ALTER SEQUENCE platform_user_id_seq RESTART WITH 2;

INSERT INTO email_password_user (id, email, password, platform_user_id)
VALUES (1, 'test@gmail.com', '{bcrypt}$2a$10$EB7SmyrWx.Nlrg0o/sGsqeBmQrOE6Yh133xdU0ERaF6hLJWSfzaOe', 1);

ALTER SEQUENCE email_password_user_id_seq RESTART WITH 2;
