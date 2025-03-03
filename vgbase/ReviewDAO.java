package vgbase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Review Data Access Object
 * This class provides methods for accessing and manipulating review data in the database.
 * It implements CRUD operations for reviews.
 * 
 * @author Group 9
 * @version 1.0
 */
public class ReviewDAO {
    
    /**
     * Retrieves all reviews from the database.
     * 
     * @return A list of Review objects
     * @throws SQLException If a database access error occurs
     */
    public List<Review> getAllReviews() throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT reviewer, rating, reviewTitle, reviewContent, gameTitle, gamePlot " +
                       "FROM Reviewed_By";
        
        ResultSet rs = null;
        try {
            rs = DBUtil.executeQuery(query);
            while (rs.next()) {
                Review review = mapResultSetToReview(rs);
                reviews.add(review);
            }
        } finally {
            DBUtil.closeResultSet(rs);
        }
        
        return reviews;
    }
    
    /**
     * Retrieves reviews for a specific game.
     * 
     * @param gameTitle The title of the game
     * @param gamePlot The plot of the game
     * @return A list of Review objects for the specified game
     * @throws SQLException If a database access error occurs
     */
    public List<Review> getReviewsByGame(String gameTitle, String gamePlot) throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT reviewer, rating, reviewTitle, reviewContent, gameTitle, gamePlot " +
                       "FROM Reviewed_By WHERE gameTitle = ? AND gamePlot = ?";
        
        ResultSet rs = null;
        try {
            rs = DBUtil.executeQuery(query, gameTitle, gamePlot);
            while (rs.next()) {
                Review review = mapResultSetToReview(rs);
                reviews.add(review);
            }
        } finally {
            DBUtil.closeResultSet(rs);
        }
        
        return reviews;
    }
    
    /**
     * Retrieves reviews by a specific reviewer.
     * 
     * @param reviewer The username of the reviewer
     * @return A list of Review objects by the specified reviewer
     * @throws SQLException If a database access error occurs
     */
    public List<Review> getReviewsByReviewer(String reviewer) throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT reviewer, rating, reviewTitle, reviewContent, gameTitle, gamePlot " +
                       "FROM Reviewed_By WHERE reviewer = ?";
        
        ResultSet rs = null;
        try {
            rs = DBUtil.executeQuery(query, reviewer);
            while (rs.next()) {
                Review review = mapResultSetToReview(rs);
                reviews.add(review);
            }
        } finally {
            DBUtil.closeResultSet(rs);
        }
        
        return reviews;
    }
    
    /**
     * Inserts a new review into the database.
     * 
     * @param review The Review object to insert
     * @return The number of rows affected
     * @throws SQLException If a database access error occurs
     */
    public int insertReview(Review review) throws SQLException {
        String query = "INSERT INTO Reviewed_By (reviewer, rating, reviewTitle, reviewContent, gameTitle, gamePlot) " +
                       "VALUES (?, ?, ?, ?, ?, ?)";
        
        return DBUtil.executeUpdate(query,
                review.getReviewer(),
                review.getRating(),
                review.getReviewTitle(),
                review.getReviewContent(),
                review.getGameTitle(),
                review.getGamePlot());
    }
    
    /**
     * Updates an existing review in the database.
     * 
     * @param review The updated Review object
     * @return The number of rows affected
     * @throws SQLException If a database access error occurs
     */
    public int updateReview(Review review) throws SQLException {
        String query = "UPDATE Reviewed_By SET rating = ?, reviewTitle = ?, reviewContent = ? " +
                       "WHERE reviewer = ? AND gameTitle = ? AND gamePlot = ?";
        
        return DBUtil.executeUpdate(query,
                review.getRating(),
                review.getReviewTitle(),
                review.getReviewContent(),
                review.getReviewer(),
                review.getGameTitle(),
                review.getGamePlot());
    }
    
    /**
     * Deletes a review from the database.
     * 
     * @param reviewer The username of the reviewer
     * @param gameTitle The title of the game
     * @param gamePlot The plot of the game
     * @return The number of rows affected
     * @throws SQLException If a database access error occurs
     */
    public int deleteReview(String reviewer, String gameTitle, String gamePlot) throws SQLException {
        String query = "DELETE FROM Reviewed_By WHERE reviewer = ? AND gameTitle = ? AND gamePlot = ?";
        return DBUtil.executeUpdate(query, reviewer, gameTitle, gamePlot);
    }
    
    /**
     * Maps a ResultSet row to a Review object.
     * 
     * @param rs The ResultSet containing review data
     * @return A Review object
     * @throws SQLException If a database access error occurs
     */
    private Review mapResultSetToReview(ResultSet rs) throws SQLException {
        Review review = new Review();
        review.setReviewer(rs.getString("reviewer"));
        review.setRating(rs.getInt("rating"));
        review.setReviewTitle(rs.getString("reviewTitle"));
        review.setReviewContent(rs.getString("reviewContent"));
        review.setGameTitle(rs.getString("gameTitle"));
        review.setGamePlot(rs.getString("gamePlot"));
        return review;
    }
    
    /**
     * Retrieves all users from the database.
     * 
     * @return A list of user usernames
     * @throws SQLException If a database access error occurs
     */
    public List<String> getAllUsers() throws SQLException {
        List<String> users = new ArrayList<>();
        String query = "SELECT usernm FROM USER";
        
        ResultSet rs = null;
        try {
            rs = DBUtil.executeQuery(query);
            while (rs.next()) {
                users.add(rs.getString("usernm"));
            }
        } finally {
            DBUtil.closeResultSet(rs);
        }
        
        return users;
    }
} 