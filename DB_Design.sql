/*
The Naming Convention of Tables is: CamelCase.
The Naming Convention of column is: snake_case.
*/
CREATE TABLE Users
(
    user_id       INT AUTO_INCREMENT PRIMARY KEY,
    phone_number  VARCHAR(20) UNIQUE  NOT NULL,
    display_name  VARCHAR(50)         NOT NULL,
    email         VARCHAR(100) UNIQUE NOT NULL,
    picture       mediumblob,
    password      VARCHAR(100)        NOT NULL,
    gender        ENUM('Male', 'Female') NOT NULL,
    country       VARCHAR(50)         NOT NULL,
    date_of_birth DATE                NOT NULL,
    bio           TEXT,
    status        ENUM('Available', 'Busy', 'Away') DEFAULT 'Away',
    mode          ENUM('Online', 'Offline') DEFAULT 'Offline',
    last_log      timestamp default NULL
);

CREATE TABLE Connections
(
    first_user_id   int not null,
    second_user_id  int not null,
    connected_since timestamp,
    FOREIGN KEY (first_user_id) REFERENCES Users (user_id),
    FOREIGN KEY (second_user_id) REFERENCES Users (user_id),
    PRIMARY KEY (first_user_id, second_user_id)
);

CREATE TABLE Invitations
(
    invitation_id INT AUTO_INCREMENT PRIMARY KEY,
    sender_id     INT NOT NULL,
    receiver_id   INT NOT NULL,
    sent_at       timestamp,
    FOREIGN KEY (sender_id) REFERENCES Users (user_id),
    FOREIGN KEY (receiver_id) REFERENCES Users (user_id)
);

CREATE TABLE Chats
(
    chat_id    INT AUTO_INCREMENT PRIMARY KEY,
    chat_image mediumblob,
    chat_name  VARCHAR(50),
    created_at timestamp,
    admin_id   INT,
    FOREIGN KEY (admin_id) REFERENCES Users (user_id)
);

CREATE TABLE ChatParticipants
(
    chat_id        INT NOT NULL,
    participant_id INT NOT NULL,
    member_since   timestamp,
    FOREIGN KEY (chat_id) REFERENCES Chats (chat_id),
    FOREIGN KEY (participant_id) REFERENCES Users (user_id),
    PRIMARY KEY (chat_id, participant_id)
);

CREATE TABLE Messages
(
    message_id      INT AUTO_INCREMENT PRIMARY KEY,
    sender_id       INT  NOT NULL,
    chat_id         INT  NOT NULL,
    contains_file   bool,
    message_content TEXT Not NUll,
    sent_at         timestamp DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sender_id) REFERENCES Users (user_id),
    FOREIGN KEY (chat_id) REFERENCES Chats (chat_id)
);

CREATE TABLE Media
(
    file_id      int auto_increment primary key,
    message_id   INT      NOT NULL,
    file_content LONGBLOB not null,
    FOREIGN KEY (message_id) REFERENCES Messages (message_id)
);