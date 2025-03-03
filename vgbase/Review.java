package vgbase;

/**
 * Review Model Class
 * This class represents a review entity from the database.
 * It contains properties corresponding to the columns in the Reviewed_By table.
 * 
 * @author Group 9
 * @version 1.0
 */
public class Review {
    private String reviewer;
    private Integer rating;
    private String reviewTitle;
    private String reviewContent;
    private String gameTitle;
    private String gamePlot;
    
    /**
     * Default constructor
     */
    public Review() {
    }
    
    /**
     * Constructor with all properties
     * 
     * @param reviewer The username of the reviewer
     * @param rating The rating given by the reviewer
     * @param reviewTitle The title of the review
     * @param reviewContent The content of the review
     * @param gameTitle The title of the game being reviewed
     * @param gamePlot The plot of the game being reviewed
     */
    public Review(String reviewer, Integer rating, String reviewTitle, String reviewContent,
                  String gameTitle, String gamePlot) {
        this.reviewer = reviewer;
        this.rating = rating;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.gameTitle = gameTitle;
        this.gamePlot = gamePlot;
    }
    
    /**
     * Gets the username of the reviewer
     * 
     * @return The username of the reviewer
     */
    public String getReviewer() {
        return reviewer;
    }
    
    /**
     * Sets the username of the reviewer
     * 
     * @param reviewer The username of the reviewer to set
     */
    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }
    
    /**
     * Gets the rating given by the reviewer
     * 
     * @return The rating
     */
    public Integer getRating() {
        return rating;
    }
    
    /**
     * Sets the rating given by the reviewer
     * 
     * @param rating The rating to set
     */
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    
    /**
     * Gets the title of the review
     * 
     * @return The review title
     */
    public String getReviewTitle() {
        return reviewTitle;
    }
    
    /**
     * Sets the title of the review
     * 
     * @param reviewTitle The review title to set
     */
    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }
    
    /**
     * Gets the content of the review
     * 
     * @return The review content
     */
    public String getReviewContent() {
        return reviewContent;
    }
    
    /**
     * Sets the content of the review
     * 
     * @param reviewContent The review content to set
     */
    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }
    
    /**
     * Gets the title of the game being reviewed
     * 
     * @return The game title
     */
    public String getGameTitle() {
        return gameTitle;
    }
    
    /**
     * Sets the title of the game being reviewed
     * 
     * @param gameTitle The game title to set
     */
    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }
    
    /**
     * Gets the plot of the game being reviewed
     * 
     * @return The game plot
     */
    public String getGamePlot() {
        return gamePlot;
    }
    
    /**
     * Sets the plot of the game being reviewed
     * 
     * @param gamePlot The game plot to set
     */
    public void setGamePlot(String gamePlot) {
        this.gamePlot = gamePlot;
    }
    
    /**
     * Returns a string representation of the review
     * 
     * @return A string representation of the review
     */
    @Override
    public String toString() {
        return reviewTitle != null && !reviewTitle.isEmpty() 
               ? reviewTitle 
               : "Review by " + reviewer;
    }
} 