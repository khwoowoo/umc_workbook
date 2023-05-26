CREATE DATABASE IF NOT EXISTS umc;
USE umc;

CREATE TABLE Users (
                       UserID VARCHAR(50) PRIMARY KEY,
                       Username VARCHAR(50),
                       Password VARCHAR(50),
                       BirthDate TIMESTAMP
);

CREATE TABLE Posts (
                       PostID INT AUTO_INCREMENT PRIMARY KEY,
                       UserID VARCHAR(50),
                       Title VARCHAR(100),
                       Content TEXT,
                       PostDate DATE,
                       FOREIGN KEY (UserID) REFERENCES Users(UserID)
);


CREATE TABLE Comments (
                          CommentID INT PRIMARY KEY,
                          PostID INT,
                          UserID VARCHAR(50),
                          CommentContent TEXT,
                          CommentDate DATE,
                          FOREIGN KEY (PostID) REFERENCES Posts(PostID),
                          FOREIGN KEY (UserID) REFERENCES Users(UserID)
);