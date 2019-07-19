-- liquibase formatted sql

-- changeset diego.drumond:create_user_table
CREATE TABLE `user`
(
  `id`        BIGINT       NOT NULL AUTO_INCREMENT,
  `email`     VARCHAR(200) NOT NULL,
  `password`  VARCHAR(200) NOT NULL,
  `role`      VARCHAR(200) NOT NULL,
  `create_at` DATETIME     NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `uq_user_email` UNIQUE (`email`)
);
-- rollback DROP TABLE `user`;

-- changeset diego.drumond:add_users context:dev
INSERT INTO `user`(`email`, `password`, `role`, `create_at`)
VALUES ('user.1@hotmart.com', '123456', 'USER', NOW()),
       ('user.2@hotmart.com', '123456', 'USER', NOW()),
       ('user.3@hotmart.com', '123456', 'USER', NOW());
-- rollback DELETE FROM `user` WHERE email IN ('user.1@hotmart.com', 'user.2@hotmart.com', 'user.3@hotmart.com');

-- changeset diego.drumond:create_category_table
CREATE TABLE `category`
(
  `id`          BIGINT        NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(50)   NOT NULL,
  `description` TEXT          NOT NULL,
  `image`       VARCHAR(1024) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `uq_category_name` UNIQUE (`name`)
);
-- rollback DROP TABLE `category`;

-- changeset diego.drumond:create_idea_table
CREATE TABLE `idea`
(
  `id`          BIGINT      NOT NULL AUTO_INCREMENT,
  `id_user`     BIGINT      NOT NULL,
  `id_category` BIGINT      NOT NULL,
  `title`       VARCHAR(50) NOT NULL,
  `description` TEXT        NOT NULL,
  `create_at`   DATETIME    NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_idea_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_idea_category` FOREIGN KEY (`id_category`) REFERENCES `category` (`id`)
);
-- rollback DROP TABLE `idea`;

-- changeset diego.drumond:create_comment_table
CREATE TABLE `comment`
(
  `id`        BIGINT   NOT NULL AUTO_INCREMENT,
  `id_user`   BIGINT   NOT NULL,
  `id_idea`   BIGINT   NOT NULL,
  `message`   TEXT     NOT NULL,
  `create_at` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_comment_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_comment_idea` FOREIGN KEY (`id_idea`) REFERENCES `idea` (`id`)
);
-- rollback DROP TABLE `comment`;

-- changeset diego.drumond:create_idea_stats_table
CREATE TABLE `idea_stats`
(
  `id`       BIGINT NOT NULL AUTO_INCREMENT,
  `id_idea`  BIGINT NOT NULL,
  `likes`    BIGINT NOT NULL,
  `dislikes` BIGINT NOT NULL,
  `joins`    BIGINT NOT NULL,
  `comments` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_idea_stats_idea` FOREIGN KEY (`id_idea`) REFERENCES `idea` (`id`),
  CONSTRAINT `uq_idea_stats_idea` UNIQUE (`id_idea`)
);
-- rollback DROP TABLE `idea_stats`;

-- changeset diego.drumond:create_idea_interaction_table
CREATE TABLE `idea_interaction`
(
  `id`        BIGINT      NOT NULL AUTO_INCREMENT,
  `id_idea`   BIGINT      NOT NULL,
  `id_user`   BIGINT      NOT NULL,
  `type`      VARCHAR(50) NOT NULL,
  `create_at` DATETIME    NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_idea_interaction_idea` FOREIGN KEY (`id_idea`) REFERENCES `idea` (`id`),
  CONSTRAINT `fk_idea_interaction_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`),
  CONSTRAINT `uq_idea_interaction_idea_user_type` UNIQUE (`id_idea`, `id_user`, `type`)
);
-- rollback DROP TABLE `idea_interaction`;
