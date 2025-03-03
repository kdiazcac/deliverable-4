CREATE TABLE ADDRESS (
    id INT AUTO_INCREMENT NOT NULL,
    unitNum VARCHAR(10),
    number VARCHAR(10) NOT NULL,
    str_nm VARCHAR(50) NOT NULL,
    city VARCHAR(50) NOT NULL,
    state VARCHAR(50) NOT NULL,
    zip VARCHAR(20) NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE PUBLISHER (
    name VARCHAR(50) NOT NULL,
    phone VARCHAR(20),
    aggRating INT,
    addressId INT NOT NULL,
    PRIMARY KEY (name),
    UNIQUE (phone, addressId),
    FOREIGN KEY (addressId) REFERENCES ADDRESS (id)
);
CREATE TABLE DEVELOPER (
    name VARCHAR(50) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    aggRating INT,
    addressId INT NOT NULL,
    PRIMARY KEY (name),
    UNIQUE (addressID),
    FOREIGN KEY (addressId) REFERENCES ADDRESS (id)
);
CREATE TABLE GENRE (
    name VARCHAR(20),
    genreId INT AUTO_INCREMENT NOT NULL,
    PRIMARY KEY (genreId)
);
CREATE TABLE GAME (
    title VARCHAR(50) NOT NULL,
    plot VARCHAR(255) NOT NULL,
    releaseDate DATE NOT NULL,
    aggRating INT,
    ESRB VARCHAR(1) NOT NULL,
    publisherName VARCHAR(50) NOT NULL,
    developerName VARCHAR(50) NOT NULL,
    genreId INT NOT NULL,
    PRIMARY KEY (title, plot),
    FOREIGN KEY (publisherName) REFERENCES PUBLISHER (name),
    FOREIGN KEY (developerName) REFERENCES DEVELOPER (name),
    FOREIGN KEY (genreId) REFERENCES GENRE (genreId)
);
CREATE TABLE HAS (
    genreId INT NOT NULL,
    gameTitle VARCHAR(50) NOT NULL,
    gamePlot VARCHAR(255) NOT NULL,
    PRIMARY KEY (genreId, gameTitle, gamePlot),
    FOREIGN KEY (genreId) REFERENCES GENRE (genreId),
    FOREIGN KEY (gameTitle, gamePlot) REFERENCES GAME (title, plot)
);
CREATE TABLE PLATFORM (
    brandName VARCHAR(50) NOT NULL,
    aggRating INT,
    manufacturer VARCHAR(50) NOT NULL,
    PRIMARY KEY (brandName, manufacturer)
);
CREATE TABLE Hosted_By (
    gameTitle VARCHAR(50) NOT NULL,
    gamePlot VARCHAR(255) NOT NULL,
    platformName VARCHAR(50) NOT NULL,
    manufacturerName VARCHAR(50) NOT NULL,
    PRIMARY KEY (
        gameTitle,
        gamePlot,
        platformName,
        manufacturerName
    ),
    FOREIGN KEY (gameTitle, gamePlot) REFERENCES GAME (title, plot),
    FOREIGN KEY (platformName, manufacturerName) REFERENCES PLATFORM (brandName, manufacturer)
);
CREATE TABLE USER (
    usernm VARCHAR(50) NOT NULL,
    pw VARCHAR(50) NOT NULL,
    age INT,
    phone VARCHAR(20),
    addressId INT,
    PRIMARY KEY (usernm),
    UNIQUE (addressID),
    FOREIGN KEY (addressId) REFERENCES ADDRESS (id)
);
CREATE TABLE Reviewed_By (
    reviewer VARCHAR(50) NOT NULL,
    rating INT,
    reviewTitle VARCHAR(50),
    reviewContent VARCHAR(255) NOT NULL,
    gameTitle VARCHAR(50) NOT NULL,
    gamePlot VARCHAR(255) NOT NULL,
    PRIMARY KEY (reviewer, gameTitle, gamePlot),
    FOREIGN KEY (reviewer) REFERENCES USER (usernm),
    FOREIGN KEY (gameTitle, gamePlot) REFERENCES GAME (title, plot)
);