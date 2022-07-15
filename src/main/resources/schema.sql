-- we don't know how to generate root <with-no-name> (class Root) :(
grant binlog monitor, process, select on *.* to exporter;

grant alter, alter routine, binlog admin, binlog monitor, binlog replay, connection admin, create, create routine, create tablespace, create temporary tables, create user, create view, delete, delete history, drop, event, execute, federated admin, file, index, insert, lock tables, process, read_only admin, references, reload, replication master admin, replication slave, replication slave admin, select, set user, show databases, show view, shutdown, slave monitor, super, trigger, update, grant option on *.* to root;

grant alter, alter routine, binlog admin, binlog monitor, binlog replay, connection admin, create, create routine, create tablespace, create temporary tables, create user, create view, delete, delete history, drop, event, execute, federated admin, file, index, insert, lock tables, process, read_only admin, references, reload, replication master admin, replication slave, replication slave admin, select, set user, show databases, show view, shutdown, slave monitor, super, trigger, update, grant option on *.* to root@localhost;

create table content
(
    content_id         bigint auto_increment
        primary key,
    created_date       datetime(6) null,
    last_modified_date datetime(6) null,
    content            text        null
);

create table topic
(
    topic_id           bigint auto_increment
        primary key,
    created_date       datetime(6) null,
    last_modified_date datetime(6) null
);

create table post
(
    post_id            bigint auto_increment
        primary key,
    created_date       datetime(6)  null,
    last_modified_date datetime(6)  null,
    title              varchar(255) not null,
    user_id            bigint       null,
    content_id         bigint       not null,
    topic_id           bigint       not null,
    constraint FK79iwu5iebbm8oh39jgml3vp48
        foreign key (content_id) references content (content_id),
    constraint FKg8ln3nj8tjclai0nuvpw5s5uw
        foreign key (topic_id) references topic (topic_id)
);

create index paging_idx
    on post (topic_id desc, created_date asc);

create table user
(
    user_id            bigint      not null
        primary key,
    created_date       datetime(6) null,
    last_modified_date datetime(6) null
);


create
definer = root@`%` procedure SMART_DATA(IN AMOUNT int)
BEGIN

    DECLARE COUNTER INT DEFAULT 1;
    DECLARE RANDOM_TOPIC_ID INT;
    DECLARE USER_ID INT;
    DECLARE CONTENT_ID INT;
    DECLARE TOPIC_ID INT;

    WHILE COUNTER <= AMOUNT
        DO
            #         content
            insert into post.content (created_date, last_modified_date, content)
            values (curdate(), curdate(), MD5(RAND()));

            SET CONTENT_ID = (SELECT LAST_INSERT_ID());

            #topic
SET RANDOM_TOPIC_ID = (SELECT FLOOR(100 + RAND() * (229000 - 100 + 1)));
            SET USER_ID = (SELECT ELT(FLOOR(RAND() * 8) + 1, 1, 8, 9, 10, 11, 12, 13, 14));

            # post
            insert delayed into post.post (created_date, last_modified_date, title, content_id, topic_id, user_id)
            values (curdate(), curdate(), MD5(RAND()), CONTENT_ID, RANDOM_TOPIC_ID, USER_ID);

            SET COUNTER = COUNTER + 1;

END WHILE;

END;

create
definer = root@`%` procedure makeContent()
BEGIN
insert into post.content (created_date, last_modified_date, content)
values (curdate(), curdate(), MD5(RAND()))
    returning content_id;
END;

