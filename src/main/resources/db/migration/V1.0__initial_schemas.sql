CREATE TABLE email_password_user
(
    id               BIGSERIAL PRIMARY KEY,
    email            VARCHAR(255) NOT NULL,
    password         VARCHAR(255) NOT NULL,
    platform_user_id BIGINT
);

CREATE TABLE githuboauth_user
(
    id               BIGINT NOT NULL,
    name             VARCHAR(255),
    email            VARCHAR(255),
    avatar_url       VARCHAR(255),
    platform_user_id BIGINT,
    CONSTRAINT pk_githuboauthuser PRIMARY KEY (id)
);

CREATE TABLE platform_user
(
    id           BIGSERIAL PRIMARY KEY,
    display_name VARCHAR(255) NOT NULL,
    email        VARCHAR(255),
    avatar_url   VARCHAR(255),
    description  VARCHAR(255)
);

ALTER TABLE email_password_user
    ADD CONSTRAINT uc_emailpassworduser_email UNIQUE (email);

ALTER TABLE email_password_user
    ADD CONSTRAINT FK_EMAILPASSWORDUSER_ON_PLATFORMUSER FOREIGN KEY (platform_user_id) REFERENCES platform_user (id);

ALTER TABLE githuboauth_user
    ADD CONSTRAINT FK_GITHUBOAUTHUSER_ON_PLATFORMUSER FOREIGN KEY (platform_user_id) REFERENCES platform_user (id);