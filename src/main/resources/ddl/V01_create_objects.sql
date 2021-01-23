create table if not exists place_article (
    id int auto_increment primary key,
    title varchar(255) not null,
    thumbnail_id int,
    short_description varchar(255),
    created_at date not null,
    updated_at date not null,
    address varchar(511),
    prefecture varchar(63) not null,
    category_id int,
    raw_content text not null,
    content text not null,
    status int not null
) character set utf8mb4;

create table if not exists category (
    id int auto_increment primary key,
    name varchar(128) not null
) character set utf8mb4;

create table if not exists tag (
    id int auto_increment primary key,
    name varchar(128) not null
) character set utf8mb4;

create table if not exists place_article_tag (
    tag_id int,
    place_article_id int,
    constraint primary key (tag_id, place_article_id)
);

create table if not exists article_stats (
    article_id int not null,
    article_title varchar(255) not null,
    accumulated_access_count int
);

create table if not exists image_info (
    id int auto_increment primary key,
    thumbnail_url varchar(255) not null,
    original_image_url varchar(255) not null,
    credit_display varchar(255),
    credit_url varchar(255)
);