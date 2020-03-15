drop database if exists free_vst;

create database free_vst;

use free_vst;

CREATE TABLE brand (
    brand_id INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    url VARCHAR(225) NOT NULL
);

CREATE TABLE `user` (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(25) NOT NULL,
    `password` VARCHAR(100) NOT NULL,
    `role` VARCHAR(10) NOT NULL
);

CREATE TABLE category (
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(25)
);

CREATE TABLE `type` (
    type_id INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    category_id INT NOT NULL,
    CONSTRAINT fk_type_category FOREIGN KEY (category_id)
        REFERENCES category (category_id)
);

CREATE TABLE `plugin` (
    plugin_id INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    brand_id INT NOT NULL,
    `description` VARCHAR(256) NULL,
    download_link VARCHAR(225) NOT NULL,
    image_url VARCHAR(225) NULL,
    type_id INT NOT NULL,
    CONSTRAINT fk_plugin_brand FOREIGN KEY (brand_id)
        REFERENCES brand (brand_id),
    CONSTRAINT fk_plugin_type FOREIGN KEY (type_id)
        REFERENCES `type` (type_id)
);

CREATE TABLE video (
    video_id INT PRIMARY KEY AUTO_INCREMENT,
    plugin_id INT NOT NULL,
    link VARCHAR(225) NOT NULL,
    CONSTRAINT fk_video_plugin FOREIGN KEY (plugin_id)
        REFERENCES `plugin` (plugin_id)
);

CREATE TABLE rating (
    plugin_id INT NOT NULL,
    user_id INT NOT NULL,
    rating INT NOT NULL,
    note varchar(256) null,
    PRIMARY KEY (plugin_id , user_id),
    CONSTRAINT fk_rating_plugin FOREIGN KEY (plugin_id)
        REFERENCES `plugin` (plugin_id),
    CONSTRAINT fk_rating_user FOREIGN KEY (user_id)
        REFERENCES `user` (user_id)
);

insert into brand (`name`, url)
values
('Valhalla', 'https://valhalladsp.com/'),
('Tal', 'https://tal-software.com/');

insert into `user` (username, `password`, `role`)
values
('ryan', 'pass', 'ROLE_USER'),
('monica', 'pass', 'ROLE_USER'),
('admin', 'pass', 'ROLE_ADMIN');

insert into category (`name`)
values 
('Instrument'),
('Effect');

insert into `type` (`name`, category_id)
values 
('Synth', 1),
('Delay', 2);

insert into `plugin` (`name`, brand_id, type_id, `description`, download_link, image_url)
values
('Valhalla Frequency Echo', 1, 2, 'Create the weirdest sounds you can imagine', 'https://valhalladsp.com/shop/delay/valhalla-freq-echo/', 'https://valhalladsp.com/wp-content/uploads/2016/06/valhalla-gui_0007_FreqEcho-525x329.png'),
('TAL-NoiseMaker', 2, 1, 'An incredibly verstaile and wonderful sounding plugin', 'https://tal-software.com/products/tal-noisemaker', 'https://tal-software.com/images/products/tal-noisemaker.png');

insert into video (plugin_id, link)
values
(1, 'https://www.youtube.com/watch?v=mMq2aIuiYcw'),
(1, 'https://www.youtube.com/watch?v=aC87mptOlrI'),
(2, 'https://www.youtube.com/watch?v=ZSD3TXksLkM');

insert into rating (plugin_id, user_id, rating)
values 
(1, 1, 8),
(2, 1, 6),
(2, 2, 4);


-- updating the users with the new encoded passwords 


UPDATE `user` 
SET 
    `password` = '$2a$10$DwVBtDqFMP7Dj2yckspWp.h0NSYn56qLcWLS.xuP/8KKwNb3cyfQm'
WHERE
    user_id = 1;
UPDATE `user` 
SET 
    `password` = '$2a$10$DwVBtDqFMP7Dj2yckspWp.h0NSYn56qLcWLS.xuP/8KKwNb3cyfQm'
WHERE
    user_id = 2;
UPDATE `user` 
SET 
    `password` = '$2a$10$DwVBtDqFMP7Dj2yckspWp.h0NSYn56qLcWLS.xuP/8KKwNb3cyfQm'
WHERE
    user_id = 3;
















