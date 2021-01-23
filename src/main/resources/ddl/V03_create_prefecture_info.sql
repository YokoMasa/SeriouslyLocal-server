create table if not exists prefecture_info (
    name varchar(32) primary key,
    description varchar(512) not null,
    image_info_id int
) character set utf8mb4;