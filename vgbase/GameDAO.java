package vgbase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Game Data Access Object
 * This class provides methods for accessing and manipulating game data in the database.
 * It implements CRUD operations for games.
 * 
 * @author Group 9
 * @version 1.0
 */
public class GameDAO {
    
    /**
     * Retrieves all games from the database.
     * 
     * @return A list of Game objects
     * @throws SQLException If a database access error occurs
     */
    public List<Game> getAllGames() throws SQLException {
        List<Game> games = new ArrayList<>();
        String query = "SELECT G.title, G.plot, G.releaseDate, G.aggRating, G.ESRB, " +
                       "G.publisherName, G.developerName, G.genreId, GE.name AS genreName " +
                       "FROM GAME G LEFT JOIN GENRE GE ON G.genreId = GE.genreId";
        
        ResultSet rs = null;
        try {
            rs = DBUtil.executeQuery(query);
            while (rs.next()) {
                Game game = mapResultSetToGame(rs);
                games.add(game);
            }
        } finally {
            DBUtil.closeResultSet(rs);
        }
        
        return games;
    }
    
    /**
     * Retrieves a game by its title and plot.
     * 
     * @param title The title of the game
     * @param plot The plot of the game
     * @return The Game object, or null if not found
     * @throws SQLException If a database access error occurs
     */
    public Game getGameByTitleAndPlot(String title, String plot) throws SQLException {
        String query = "SELECT G.title, G.plot, G.releaseDate, G.aggRating, G.ESRB, " +
                       "G.publisherName, G.developerName, G.genreId, GE.name AS genreName " +
                       "FROM GAME G LEFT JOIN GENRE GE ON G.genreId = GE.genreId " +
                       "WHERE G.title = ? AND G.plot = ?";
        
        ResultSet rs = null;
        try {
            rs = DBUtil.executeQuery(query, title, plot);
            if (rs.next()) {
                return mapResultSetToGame(rs);
            }
        } finally {
            DBUtil.closeResultSet(rs);
        }
        
        return null;
    }
    
    /**
     * Searches for games by title.
     * 
     * @param titleSearch The search term for the title
     * @return A list of Game objects matching the search
     * @throws SQLException If a database access error occurs
     */
    public List<Game> searchGamesByTitle(String titleSearch) throws SQLException {
        List<Game> games = new ArrayList<>();
        String query = "SELECT G.title, G.plot, G.releaseDate, G.aggRating, G.ESRB, " +
                       "G.publisherName, G.developerName, G.genreId, GE.name AS genreName " +
                       "FROM GAME G LEFT JOIN GENRE GE ON G.genreId = GE.genreId " +
                       "WHERE G.title LIKE ?";
        
        ResultSet rs = null;
        try {
            rs = DBUtil.executeQuery(query, "%" + titleSearch + "%");
            while (rs.next()) {
                Game game = mapResultSetToGame(rs);
                games.add(game);
            }
        } finally {
            DBUtil.closeResultSet(rs);
        }
        
        return games;
    }
    
    /**
     * Retrieves games by developer name.
     * 
     * @param developerName The name of the developer
     * @return A list of Game objects developed by the specified developer
     * @throws SQLException If a database access error occurs
     */
    public List<Game> getGamesByDeveloper(String developerName) throws SQLException {
        List<Game> games = new ArrayList<>();
        String query = "SELECT G.title, G.plot, G.releaseDate, G.aggRating, G.ESRB, " +
                       "G.publisherName, G.developerName, G.genreId, GE.name AS genreName " +
                       "FROM GAME G LEFT JOIN GENRE GE ON G.genreId = GE.genreId " +
                       "WHERE G.developerName = ?";
        
        ResultSet rs = null;
        try {
            rs = DBUtil.executeQuery(query, developerName);
            while (rs.next()) {
                Game game = mapResultSetToGame(rs);
                games.add(game);
            }
        } finally {
            DBUtil.closeResultSet(rs);
        }
        
        return games;
    }
    
    /**
     * Retrieves games by publisher name.
     * 
     * @param publisherName The name of the publisher
     * @return A list of Game objects published by the specified publisher
     * @throws SQLException If a database access error occurs
     */
    public List<Game> getGamesByPublisher(String publisherName) throws SQLException {
        List<Game> games = new ArrayList<>();
        String query = "SELECT G.title, G.plot, G.releaseDate, G.aggRating, G.ESRB, " +
                       "G.publisherName, G.developerName, G.genreId, GE.name AS genreName " +
                       "FROM GAME G LEFT JOIN GENRE GE ON G.genreId = GE.genreId " +
                       "WHERE G.publisherName = ?";
        
        ResultSet rs = null;
        try {
            rs = DBUtil.executeQuery(query, publisherName);
            while (rs.next()) {
                Game game = mapResultSetToGame(rs);
                games.add(game);
            }
        } finally {
            DBUtil.closeResultSet(rs);
        }
        
        return games;
    }
    
    /**
     * Retrieves games by genre.
     * 
     * @param genreId The genre ID
     * @return A list of Game objects in the specified genre
     * @throws SQLException If a database access error occurs
     */
    public List<Game> getGamesByGenre(int genreId) throws SQLException {
        List<Game> games = new ArrayList<>();
        String query = "SELECT G.title, G.plot, G.releaseDate, G.aggRating, G.ESRB, " +
                       "G.publisherName, G.developerName, G.genreId, GE.name AS genreName " +
                       "FROM GAME G LEFT JOIN GENRE GE ON G.genreId = GE.genreId " +
                       "WHERE G.genreId = ?";
        
        ResultSet rs = null;
        try {
            rs = DBUtil.executeQuery(query, genreId);
            while (rs.next()) {
                Game game = mapResultSetToGame(rs);
                games.add(game);
            }
        } finally {
            DBUtil.closeResultSet(rs);
        }
        
        return games;
    }
    
    /**
     * Inserts a new game into the database.
     * 
     * @param game The Game object to insert
     * @return The number of rows affected
     * @throws SQLException If a database access error occurs
     */
    public int insertGame(Game game) throws SQLException {
        String query = "INSERT INTO GAME (title, plot, releaseDate, aggRating, ESRB, publisherName, developerName, genreId) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        return DBUtil.executeUpdate(query,
                game.getTitle(),
                game.getPlot(),
                game.getReleaseDate(),
                game.getAggRating(),
                game.getEsrb(),
                game.getPublisherName(),
                game.getDeveloperName(),
                game.getGenreId());
    }
    
    /**
     * Updates an existing game in the database.
     * 
     * @param oldTitle The original title of the game
     * @param oldPlot The original plot of the game
     * @param game The updated Game object
     * @return The number of rows affected
     * @throws SQLException If a database access error occurs
     */
    public int updateGame(String oldTitle, String oldPlot, Game game) throws SQLException {
        String query = "UPDATE GAME SET title = ?, plot = ?, releaseDate = ?, aggRating = ?, " +
                       "ESRB = ?, publisherName = ?, developerName = ?, genreId = ? " +
                       "WHERE title = ? AND plot = ?";
        
        return DBUtil.executeUpdate(query,
                game.getTitle(),
                game.getPlot(),
                game.getReleaseDate(),
                game.getAggRating(),
                game.getEsrb(),
                game.getPublisherName(),
                game.getDeveloperName(),
                game.getGenreId(),
                oldTitle,
                oldPlot);
    }
    
    /**
     * Deletes a game from the database.
     * 
     * @param title The title of the game to delete
     * @param plot The plot of the game to delete
     * @return The number of rows affected
     * @throws SQLException If a database access error occurs
     */
    public int deleteGame(String title, String plot) throws SQLException {
        // First delete from the HAS table (foreign key constraint)
        String hasQuery = "DELETE FROM HAS WHERE gameTitle = ? AND gamePlot = ?";
        DBUtil.executeUpdate(hasQuery, title, plot);
        
        // Then delete from the Hosted_By table (foreign key constraint)
        String hostedByQuery = "DELETE FROM Hosted_By WHERE gameTitle = ? AND gamePlot = ?";
        DBUtil.executeUpdate(hostedByQuery, title, plot);
        
        // Then delete from the Reviewed_By table (foreign key constraint)
        String reviewedByQuery = "DELETE FROM Reviewed_By WHERE gameTitle = ? AND gamePlot = ?";
        DBUtil.executeUpdate(reviewedByQuery, title, plot);
        
        // Finally delete from the GAME table
        String gameQuery = "DELETE FROM GAME WHERE title = ? AND plot = ?";
        return DBUtil.executeUpdate(gameQuery, title, plot);
    }
    
    /**
     * Maps a ResultSet row to a Game object.
     * 
     * @param rs The ResultSet containing game data
     * @return A Game object
     * @throws SQLException If a database access error occurs
     */
    private Game mapResultSetToGame(ResultSet rs) throws SQLException {
        Game game = new Game();
        game.setTitle(rs.getString("title"));
        game.setPlot(rs.getString("plot"));
        game.setReleaseDate(rs.getDate("releaseDate"));
        game.setAggRating(rs.getFloat("aggRating"));
        game.setEsrb(rs.getString("ESRB"));
        game.setPublisherName(rs.getString("publisherName"));
        game.setDeveloperName(rs.getString("developerName"));
        game.setGenreId(rs.getInt("genreId"));
        game.setGenreName(rs.getString("genreName"));
        return game;
    }
    
    /**
     * Retrieves all genres from the database.
     * 
     * @return A list of Genre objects
     * @throws SQLException If a database access error occurs
     */
    public List<Genre> getAllGenres() throws SQLException {
        List<Genre> genres = new ArrayList<>();
        String query = "SELECT genreId, name FROM GENRE";
        
        ResultSet rs = null;
        try {
            rs = DBUtil.executeQuery(query);
            while (rs.next()) {
                Genre genre = new Genre();
                genre.setGenreId(rs.getInt("genreId"));
                genre.setName(rs.getString("name"));
                genres.add(genre);
            }
        } finally {
            DBUtil.closeResultSet(rs);
        }
        
        return genres;
    }
    
    /**
     * Retrieves all publishers from the database.
     * 
     * @return A list of publisher names
     * @throws SQLException If a database access error occurs
     */
    public List<String> getAllPublishers() throws SQLException {
        List<String> publishers = new ArrayList<>();
        String query = "SELECT name FROM PUBLISHER";
        
        ResultSet rs = null;
        try {
            rs = DBUtil.executeQuery(query);
            while (rs.next()) {
                publishers.add(rs.getString("name"));
            }
        } finally {
            DBUtil.closeResultSet(rs);
        }
        
        return publishers;
    }
    
    /**
     * Retrieves all developers from the database.
     * 
     * @return A list of developer names
     * @throws SQLException If a database access error occurs
     */
    public List<String> getAllDevelopers() throws SQLException {
        List<String> developers = new ArrayList<>();
        String query = "SELECT name FROM DEVELOPER";
        
        ResultSet rs = null;
        try {
            rs = DBUtil.executeQuery(query);
            while (rs.next()) {
                developers.add(rs.getString("name"));
            }
        } finally {
            DBUtil.closeResultSet(rs);
        }
        
        return developers;
    }
} 