INSERT INTO ADDRESS (unitNum, number, str_nm, city, state, zip)
VALUES
('4800', '185', 'Berry Street', 'San Francisco', 'CA', '94107'),     -- ID 1: Capcom
('4600', '150', '150th Avenue', 'Redmond', 'WA', '98052'),          -- ID 2: Nintendo
('100', '1', 'One Microsoft Way', 'Redmond', 'WA', '98052'),        -- ID 3: Mojang
('1230', '123', 'Second Avenue', 'Palo Alto', 'CA', '98344'),       -- ID 4: Grezzo
('1223', '11', 'Washington Avenue', 'Redding', 'CA', '95544'),      -- ID 5: jSmith1090
('278', '23', 'Sunbird Street', 'Tempe', 'AZ', '76546'),           -- ID 6: j12Doe
('322', '88', 'Willowhood Drive', 'Berkeley', 'CA', '98567'),       -- ID 7: mWheeler45
('501', '42', 'Innovation Drive', 'Osaka', 'JP', '123456'),         -- ID 8: Capcom Dev
('700', '88', 'Gaming Way', 'Kyoto', 'JP', '234567'),               -- ID 9: Nintendo Dev
('200', '15', 'Tech Row', 'Stockholm', 'SE', '345678'),             -- ID 10: Mojang Dev
('305', '77', 'Game Street', 'Tokyo', 'JP', '456789');              -- ID 11: Grezzo Dev

INSERT INTO PUBLISHER (name, phone, aggRating, addressId)
VALUES
('Capcom', '6503506500', 8, 1),
('Nintendo', '18002553700', 9, 2),
('Mojang Studios', '4258828080', 7, 3),
('Grezzo', '987254670', 6, 4);

INSERT INTO DEVELOPER (name, phone, aggRating, addressId)
VALUES
('Capcom', '6503506500', 8, 8),
('Nintendo', '18002553700', 9, 9),
('Mojang Studios', '4258828080', 7, 10),
('Grezzo', '987254670', 6, 11);

INSERT INTO GENRE (name)
VALUES
('action'),('adventure'),('puzzle'),('RPG'),('platformer'),('strategy'),('fantasy'),('simulation'),
('Survival horror'),('Combat'),('Racing'),('Sandbox');

INSERT INTO GAME (title, plot, releaseDate, aggRating, ESRB, publisherName, developerName, genreId)
VALUES
('Devil May Cry', 'The game centers on the demon hunter Dante and his efforts to thwart various demon invasions of Earth', '2001-08-23', 8, 'M', 'Capcom', 'Capcom', 1),
('The Legend of Zelda', 'Link, a courageous Hylian warrior, and Princess Zelda must protect the kingdom of Hyrule from Ganon, an evil warlord turned demon king. Together they battle through dungeons and overcome trials to prevent Ganon from obtaining absolute power', '1986-02-21', 10, 'E', 'Nintendo', 'Nintendo', 2),
('Resident Evil', 'The game follows a group of survivors battling bioweapons and uncovering sinister corporate conspiracies after a deadly virus outbreak', '1996-03-22', 7, 'M', 'Capcom', 'Capcom', 9),
('Super Mario', 'The game focuses on main character Mario who embarks on a quest through the Mushroom kingdom to rescue Princess Toadstool from the evil king Bowser', '1996-06-23', 6, 'E', 'Nintendo', 'Nintendo', 5),
('Minecraft', 'Open sandbox to explore a limitless blocky world, mine resources, craft tools, and build to survive against monsters', '2011-11-18', 8, 'E', 'Mojang Studios', 'Mojang Studios', 12);

INSERT INTO HAS (genreId, gameTitle, gamePlot)
VALUES
(1,'Devil May Cry','The game centers on the demon hunter Dante and his efforts to thwart various demon invasions of Earth' ),
(2,'Devil May Cry','The game centers on the demon hunter Dante and his efforts to thwart various demon invasions of Earth' ),
(1 ,'The Legend of Zelda', 'Link, a courageous Hylian warrior, and Princess Zelda must protect the kingdom of Hyrule from Ganon, an evil warlord turned demon king. Together they battle through dungeons and overcome trials to prevent Ganon from obtaining absolute power'),
(2 ,'The Legend of Zelda', 'Link, a courageous Hylian warrior, and Princess Zelda must protect the kingdom of Hyrule from Ganon, an evil warlord turned demon king. Together they battle through dungeons and overcome trials to prevent Ganon from obtaining absolute power'),
(3 ,'The Legend of Zelda', 'Link, a courageous Hylian warrior, and Princess Zelda must protect the kingdom of Hyrule from Ganon, an evil warlord turned demon king. Together they battle through dungeons and overcome trials to prevent Ganon from obtaining absolute power'),
(1,'Resident Evil', 'The game follows a group of survivors battling bioweapons and uncovering sinister corporate conspiracies after a deadly virus outbreak'),
(2,'Resident Evil', 'The game follows a group of survivors battling bioweapons and uncovering sinister corporate conspiracies after a deadly virus outbreak'),
(9,'Resident Evil', 'The game follows a group of survivors battling bioweapons and uncovering sinister corporate conspiracies after a deadly virus outbreak'),
(5, 'Super Mario','The game focuses on main character Mario who embarks on a quest through the Mushroom kingdom to rescue Princess Toadstool from the evil king Bowser'),
(12, 'Minecraft', 'Open sandbox to explore a limitless blocky world, mine resources, craft tools, and build to survive against monsters');

INSERT INTO PLATFORM (brandName, aggRating, manufacturer)
VALUES
('PlayStation', 9, 'Sony'),
('Nintendo Consoles', 8, 'Nintendo'),
('Xbox', 9, 'Microsoft'),
('PS3', 8, 'Sony');

INSERT INTO HOSTED_BY (gameTitle, gamePlot, platformName, manufacturerName)
VALUES
('Devil May Cry','The game centers on the demon hunter Dante and his efforts to thwart various demon invasions of Earth' , 'PlayStation', 'Sony'),
('The Legend of Zelda', 'Link, a courageous Hylian warrior, and Princess Zelda must protect the kingdom of Hyrule from Ganon, an evil warlord turned demon king. Together they battle through dungeons and overcome trials to prevent Ganon from obtaining absolute power','Nintendo Consoles', 'Nintendo'),
('Resident Evil', 'The game follows a group of survivors battling bioweapons and uncovering sinister corporate conspiracies after a deadly virus outbreak', 'PlayStation', 'Sony'),
('Super Mario','The game focuses on main character Mario who embarks on a quest through the Mushroom kingdom to rescue Princess Toadstool from the evil king Bowser', 'Nintendo Consoles', 'Nintendo'),
('Minecraft', 'Open sandbox to explore a limitless blocky world, mine resources, craft tools, and build to survive against monsters', 'PS3',  'Sony');

INSERT INTO USER (usernm, pw, age, phone, addressId)
VALUES
('jSmith1090', 'th1sIsMyPa$$word', 19, '987877666', 5),
('j12Doe', '1AmJ@neD@E', 25, '453628891', 6),
('mWheeler45', 'wh@t1sMYn@mE99', 34, '7893452345', 7);

INSERT INTO REVIEWED_BY (reviewer, rating, reviewTitle, reviewContent, gameTitle, gamePlot)
VALUES
('jSmith1090', 8, 'Good', '', 'Devil May Cry', 'The game centers on the demon hunter Dante and his efforts to thwart various demon invasions of Earth'),
('jSmith1090', 9, 'Great', 'This is a great game to play.', 'The Legend of Zelda', 'Link, a courageous Hylian warrior, and Princess Zelda must protect the kingdom of Hyrule from Ganon, an evil warlord turned demon king. Together they battle through dungeons and overcome trials to prevent Ganon from obtaining absolute power'),
('j12Doe', 9, 'Awesome', 'Great video game! Loved playing it', 'Resident Evil', 'The game follows a group of survivors battling bioweapons and uncovering sinister corporate conspiracies after a deadly virus outbreak'),
('j12Doe', 9, 'Super fun', 'Super fun game', 'Super Mario', 'The game focuses on main character Mario who embarks on a quest through the Mushroom kingdom to rescue Princess Toadstool from the evil king Bowser'),
('jSmith1090', 8, 'Good', '', 'Minecraft', 'Open sandbox to explore a limitless blocky world, mine resources, craft tools, and build to survive against monsters');
