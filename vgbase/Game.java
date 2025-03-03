package vgbase;

import java.sql.Date;

/**
 * Game Model Class
 * This class represents a game entity from the database.
 * It contains properties corresponding to the columns in the GAME table.
 * 
 * @author Group 9
 * @version 1.0
 */
public class Game {
    private String title;
    private String plot;
    private Date releaseDate;
    private Float aggRating;
    private String esrb;
    private String publisherName;
    private String developerName;
    private Integer genreId;
    private String genreName; // Transient property for displaying genre name
    
    /**
     * Default constructor
     */
    public Game() {
    }
    
    /**
     * Constructor with all properties
     * 
     * @param title The title of the game
     * @param plot The plot/description of the game
     * @param releaseDate The release date of the game
     * @param aggRating The aggregate rating of the game
     * @param esrb The ESRB rating of the game
     * @param publisherName The name of the publisher
     * @param developerName The name of the developer
     * @param genreId The genre ID
     */
    public Game(String title, String plot, Date releaseDate, Float aggRating, String esrb,
                String publisherName, String developerName, Integer genreId) {
        this.title = title;
        this.plot = plot;
        this.releaseDate = releaseDate;
        this.aggRating = aggRating;
        this.esrb = esrb;
        this.publisherName = publisherName;
        this.developerName = developerName;
        this.genreId = genreId;
    }
    
    /**
     * Gets the title of the game
     * 
     * @return The title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Sets the title of the game
     * 
     * @param title The title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * Gets the plot/description of the game
     * 
     * @return The plot
     */
    public String getPlot() {
        return plot;
    }
    
    /**
     * Sets the plot/description of the game
     * 
     * @param plot The plot to set
     */
    public void setPlot(String plot) {
        this.plot = plot;
    }
    
    /**
     * Gets the release date of the game
     * 
     * @return The release date
     */
    public Date getReleaseDate() {
        return releaseDate;
    }
    
    /**
     * Sets the release date of the game
     * 
     * @param releaseDate The release date to set
     */
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
    
    /**
     * Gets the aggregate rating of the game
     * 
     * @return The aggregate rating
     */
    public Float getAggRating() {
        return aggRating;
    }
    
    /**
     * Sets the aggregate rating of the game
     * 
     * @param aggRating The aggregate rating to set
     */
    public void setAggRating(Float aggRating) {
        this.aggRating = aggRating;
    }
    
    /**
     * Gets the ESRB rating of the game
     * 
     * @return The ESRB rating
     */
    public String getEsrb() {
        return esrb;
    }
    
    /**
     * Sets the ESRB rating of the game
     * 
     * @param esrb The ESRB rating to set
     */
    public void setEsrb(String esrb) {
        this.esrb = esrb;
    }
    
    /**
     * Gets the name of the publisher
     * 
     * @return The publisher name
     */
    public String getPublisherName() {
        return publisherName;
    }
    
    /**
     * Sets the name of the publisher
     * 
     * @param publisherName The publisher name to set
     */
    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }
    
    /**
     * Gets the name of the developer
     * 
     * @return The developer name
     */
    public String getDeveloperName() {
        return developerName;
    }
    
    /**
     * Sets the name of the developer
     * 
     * @param developerName The developer name to set
     */
    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }
    
    /**
     * Gets the genre ID
     * 
     * @return The genre ID
     */
    public Integer getGenreId() {
        return genreId;
    }
    
    /**
     * Sets the genre ID
     * 
     * @param genreId The genre ID to set
     */
    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }
    
    /**
     * Gets the genre name
     * 
     * @return The genre name
     */
    public String getGenreName() {
        return genreName;
    }
    
    /**
     * Sets the genre name
     * 
     * @param genreName The genre name to set
     */
    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
    
    /**
     * Returns a string representation of the game
     * 
     * @return A string representation of the game
     */
    @Override
    public String toString() {
        return title;
    }
} 