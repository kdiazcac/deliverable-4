# VideoGame Database Management System

A Java application with JavaFX interface for managing video game data. This application provides a user-friendly interface to interact with a MySQL database of video games, allowing users to view, add, update, and delete games and reviews.

## Table of Contents
- [Database Setup](#database-setup)
- [Environment Requirements](#environment-requirements)
- [Running the Application](#running-the-application)
- [Video Demonstration](#video-demonstration)
- [Features](#features)
- [Project Structure](#project-structure)
- [Team Contribution](#team-contribution)

## Database Setup

### Creating the Database

1. Install MySQL if you haven't already. You can download it from [MySQL Download Page](https://dev.mysql.com/downloads/).

2. Open MySQL Command Line Client.

3. Create a new database named `vgbase` (video game data base):
   ```sql
   CREATE DATABASE IF NOT EXISTS vgbase;
   USE vgbase;
   ```

4. Execute the SQL scripts in the following order to create the database schema and populate it with sample data:
   
   a. Execute `sql/group_9_create.sql` to create the tables:
   ```sql
   SOURCE path/to/sql/group_9_create.sql;
   ```
   
   b. Execute `sql/group_9_insert.sql` to insert sample data:
   ```sql
   SOURCE path/to/sql/group_9_insert.sql;
   ```

### Database Configuration

The application is configured to connect to a MySQL database with the following settings:
- Database URL: `jdbc:mysql://localhost:3306/vgbase`
- Username: `root`
- Password: `` (empty password)

If your MySQL configuration is different, please update the connection settings in the `DBUtil.java` file:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/vgbase";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "";
```

## Environment Requirements

To run this application, you need:

1. **Java Development Kit (JDK) 17 or higher**

2. **JavaFX SDK 21.0.6**

3. **MySQL Connector/J** (already included in the project)
   - The MySQL Connector/J JAR file is already included in the `lib/` directory for convenience
   - No separate download is required

## Running the Application

### Running from Command Line

1. Compile the Java files:
   ```bash
   javac --module-path /path/to/javafx-sdk-21.0.6/lib --add-modules javafx.controls,javafx.fxml -cp lib/mysql-connector-j-9.2.0.jar vgbase/*.java
   ```

2. Run the application:
   ```bash
   java --module-path /path/to/javafx-sdk-21.0.6/lib --add-modules javafx.controls,javafx.fxml -cp .:lib/mysql-connector-j-9.2.0.jar vgbase.GameMenu
   ```

Replace `/path/to/javafx-sdk-21.0.6/lib` with the actual path to your JavaFX lib directory.

## Video Demonstration

[Link to video demonstration](#) <!-- Add your video URL here -->

## Features

- **View Games**: View all games in a table with details
- **Add/Edit Games**: Add new games or edit existing ones
- **Delete Games**: Remove games from the database
- **Search Games**: Search games by title, publisher, developer, or genre
- **Manage Reviews**: View, add, edit, and delete reviews for games
- **User-Friendly Interface**: Intuitive UI with navigation between screens

## Project Structure

- `vgbase/GameMenu.java`: Main application entry point and menu
- `vgbase/DBUtil.java`: Database utility for connection management
- `vgbase/Game.java`: Game model class
- `vgbase/Genre.java`: Genre model class
- `vgbase/Review.java`: Review model class
- `vgbase/GameDAO.java`: Data access object for games
- `vgbase/ReviewDAO.java`: Data access object for reviews
- `vgbase/GameViewer.java`: UI for viewing games
- `vgbase/GameDetailsViewer.java`: UI for viewing game details
- `vgbase/GameEditor.java`: UI for adding/editing games
- `vgbase/GameSelector.java`: UI for selecting games
- `vgbase/GameSearch.java`: UI for searching games
- `vgbase/ReviewViewer.java`: UI for viewing reviews
- `vgbase/ReviewEditor.java`: UI for adding/editing reviews
- `sql/group_9_create.sql`: SQL script to create database tables
- `sql/group_9_insert.sql`: SQL script to insert sample data
- `lib/mysql-connector-j.jar`: MySQL JDBC driver for database connectivity

## Team Members

- **Kevin Diaz**

