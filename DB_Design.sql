/*

The Naming Convention of Tables is: CamelCase.
The Naming Convention of column is: snake_case.
*/
CREATE DATABASE chat_app;
CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    phone_number VARCHAR(20) UNIQUE NOT NULL,
    display_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    picture VARCHAR(255),
    password VARCHAR(100) NOT NULL,
    gender ENUM('Male', 'Female') NOT NULL,
    country VARCHAR(50) NOT NULL,
    date_of_birth DATE NOT NULL,
    bio TEXT,
	status ENUM('Online', 'Offline') DEFAULT 'Offline',
	mode ENUM('Busy', 'Away', 'Available') DEFAULT 'Available',
	last_log timestamp
);

CREATE TABLE Connections (
    contact_id int auto_increment primary key,
    user_id int not null,
    contact_status ENUM ('Pending', 'Accepted', 'Blocked') default 'Pending',
    category ENUM ('Friends', 'Family', 'Work', 'others'),
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (contact_id) REFERENCES Users(user_id)
);

CREATE TABLE Media (
    file_id int auto_increment primary key,
    sender_id INT NOT NULL,
    receiver_id INT NOT NULL,
    type VARCHAR(20) not null,
    content MEDIUMBLOB not null ,
    sent_at timestamp DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sender_id) REFERENCES Users(user_id),
    FOREIGN KEY (receiver_id) REFERENCES Users(user_id)

);

CREATE TABLE Invitations (
    sender_id INT NOT NULL,
    receiver_id INT NOT NULL,
    FOREIGN KEY (sender_id) REFERENCES Users(user_id),
    FOREIGN KEY (receiver_id) REFERENCES Users(user_id)
);

CREATE TABLE Messages (
    message_id INT AUTO_INCREMENT PRIMARY KEY,
    sender_id INT NOT NULL,
    receiver_id INT NOT NULL,
    message TEXT Not NUll,
    font_style VARCHAR(20),
    font_color VARCHAR(20),
    text_background VARCHAR(20),
    font_size INT,
    is_bold BOOLEAN,
    is_italic BOOLEAN,
    is_underline BOOLEAN,
    sent_at timestamp DEFAULT CURRENT_TIMESTAMP,
    emoji VARCHAR(20),
    FOREIGN KEY (sender_id) REFERENCES Users(user_id),
    FOREIGN KEY (receiver_id) REFERENCES Users(user_id)
);

CREATE TABLE Notifications (
    notification_id int auto_increment primary key,
    user_id int,
    content text,
    sent_at timestamp DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

CREATE TABLE ChatGroups (
    group_id int auto_increment primary key,
    group_name varchar(255) NOT NULL,
    created_by int not null,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES Users(user_id)
);

CREATE TABLE GroupParticipants (
    group_id int not null ,
    participant_id INT not null ,
    primary key (group_id, participant_id),
    FOREIGN KEY (group_id) REFERENCES ChatGroups(group_id),
    FOREIGN KEY (participant_id) REFERENCES Users(user_id)
);

CREATE TABLE GroupMessages (
	g_message_id int auto_increment primary key,
    group_id int not null,
    sender_id int not null,
    content text not null,
    sent_at timestamp DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (group_id) REFERENCES ChatGroups(group_id),
    FOREIGN KEY (sender_id) REFERENCES Users(user_id)
);

CREATE TABLE ServerAnnouncements (
	announcement_id int auto_increment primary key,
    content text not null,
    sent_at timestamp DEFAULT CURRENT_TIMESTAMP,
    announcement_format enum('Text', 'HTML') default 'TEXT'
);