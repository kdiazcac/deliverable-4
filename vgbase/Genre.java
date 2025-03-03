package vgbase;

/**
 * Genre Model Class
 * This class represents a genre entity from the database.
 * It contains properties corresponding to the columns in the GENRE table.
 * 
 * @author Group 9
 * @version 1.0
 */
public class Genre {
    private Integer genreId;
    private String name;
    
    /**
     * Default constructor
     */
    public Genre() {
    }
    
    /**
     * Constructor with all properties
     * 
     * @param genreId The genre ID
     * @param name The genre name
     */
    public Genre(Integer genreId, String name) {
        this.genreId = genreId;
        this.name = name;
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
    public String getName() {
        return name;
    }
    
    /**
     * Sets the genre name
     * 
     * @param name The genre name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Returns a string representation of the genre
     * 
     * @return A string representation of the genre
     */
    @Override
    public String toString() {
        return name;
    }
} 