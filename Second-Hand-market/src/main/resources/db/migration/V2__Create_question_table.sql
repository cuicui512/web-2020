create table question
(
    id BIGINT auto_increment,
    title VARCHAR(100),
    description TEXT,
    gmt_create BIGINT,
    gmt_modified BIGINT,
    creator BIGINT,
    comment_count int DEFAULT 0 NOT NULL,
    view_count int DEFAULT 0 NOT NULL,
    like_count int DEFAULT 0 NOT NULL,
    tag VARCHAR(100),
    constraint question_pk
        primary key (id)
);

comment on table question is '问题表';