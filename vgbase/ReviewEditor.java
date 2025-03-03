package vgbase;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

/**
 * Review Editor
 * This class provides a UI for adding or editing reviews in the database.
 * 
 * @author Group 9
 * @version 1.0
 */
public class ReviewEditor {
    
    private Review review;
    private ReviewDAO reviewDAO;
    private GameDAO gameDAO;
    private boolean isNewReview;
    
    /**
     * Default constructor for adding a new review
     */
    public ReviewEditor() {
        this.reviewDAO = new ReviewDAO();
        this.gameDAO = new GameDAO();
        this.review = new Review();
        this.isNewReview = true;
    }
    
    /**
     * Constructor for adding a review for a specific game
     * 
     * @param review The Review object with game title and plot already set
     */
    public ReviewEditor(Review review) {
        this.reviewDAO = new ReviewDAO();
        this.gameDAO = new GameDAO();
        this.review = review;
        this.isNewReview = true;
    }
    
    /**
     * Constructor for editing an existing review
     * 
     * @param review The Review object to edit
     * @param isNewReview Flag indicating if this is a new review
     */
    public ReviewEditor(Review review, boolean isNewReview) {
        this.reviewDAO = new ReviewDAO();
        this.gameDAO = new GameDAO();
        this.review = review;
        this.isNewReview = isNewReview;
    }
    
    /**
     * Shows the review editor window.
     * 
     * @param stage The primary stage
     */
    public void show(Stage stage) {
        // Create a border pane as the root
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        
        // Create a title
        Label titleLabel = new Label(isNewReview ? "Add New Review" : "Edit Review");
        titleLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        root.setTop(titleLabel);
        
        // Create a grid pane for form fields
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(20, 10, 10, 10));
        
        // Add form fields
        int row = 0;
        
        formGrid.add(new Label("Game:"), 0, row);
        ComboBox<Game> gameComboBox = new ComboBox<>();
        try {
            List<Game> games = gameDAO.getAllGames();
            gameComboBox.setItems(FXCollections.observableArrayList(games));
            
            if (review.getGameTitle() != null && review.getGamePlot() != null) {
                // Find the game with matching title and plot
                for (Game game : games) {
                    if (game.getTitle().equals(review.getGameTitle()) && 
                        game.getPlot().equals(review.getGamePlot())) {
                        gameComboBox.setValue(game);
                        break;
                    }
                }
            }
            
            // Disable game selection if editing or if game is already selected
            if (!isNewReview || (review.getGameTitle() != null && review.getGamePlot() != null)) {
                gameComboBox.setDisable(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Error loading games: " + e.getMessage());
        }
        formGrid.add(gameComboBox, 1, row++);
        
        formGrid.add(new Label("Reviewer:"), 0, row);
        ComboBox<String> reviewerComboBox = new ComboBox<>();
        try {
            List<String> users = reviewDAO.getAllUsers();
            reviewerComboBox.setItems(FXCollections.observableArrayList(users));
            
            if (review.getReviewer() != null) {
                reviewerComboBox.setValue(review.getReviewer());
            }
            
            // Disable reviewer selection if editing
            if (!isNewReview) {
                reviewerComboBox.setDisable(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Error loading users: " + e.getMessage());
        }
        formGrid.add(reviewerComboBox, 1, row++);
        
        formGrid.add(new Label("Rating (1-10):"), 0, row);
        ComboBox<Integer> ratingComboBox = new ComboBox<>(
                FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        );
        if (review.getRating() != null) {
            ratingComboBox.setValue(review.getRating());
        }
        formGrid.add(ratingComboBox, 1, row++);
        
        formGrid.add(new Label("Review Title:"), 0, row);
        TextField reviewTitleField = new TextField(review.getReviewTitle());
        formGrid.add(reviewTitleField, 1, row++);
        
        formGrid.add(new Label("Review Content:"), 0, row);
        TextArea reviewContentArea = new TextArea(review.getReviewContent());
        reviewContentArea.setWrapText(true);
        reviewContentArea.setPrefHeight(100);
        formGrid.add(reviewContentArea, 1, row++);
        
        // Add the form to the center of the border pane
        root.setCenter(formGrid);
        
        // Create buttons for the bottom
        Button saveBtn = new Button(isNewReview ? "Add Review" : "Save Changes");
        Button cancelBtn = new Button("Cancel");
        
        // Set button actions
        saveBtn.setOnAction(e -> {
            try {
                // Validate required fields
                if (gameComboBox.getValue() == null ||
                    reviewerComboBox.getValue() == null ||
                    ratingComboBox.getValue() == null ||
                    reviewContentArea.getText().trim().isEmpty()) {
                    
                    showAlert("Validation Error", "Game, Reviewer, Rating, and Review Content are required.");
                    return;
                }
                
                // Update review object
                Game selectedGame = gameComboBox.getValue();
                review.setGameTitle(selectedGame.getTitle());
                review.setGamePlot(selectedGame.getPlot());
                review.setReviewer(reviewerComboBox.getValue());
                review.setRating(ratingComboBox.getValue());
                review.setReviewTitle(reviewTitleField.getText());
                review.setReviewContent(reviewContentArea.getText());
                
                // Save to database
                if (isNewReview) {
                    reviewDAO.insertReview(review);
                    showAlert("Success", "Review added successfully!");
                } else {
                    reviewDAO.updateReview(review);
                    showAlert("Success", "Review updated successfully!");
                }
                
                new GameDetailsViewer(selectedGame).show(stage);
                return;
                
            } catch (SQLException ex) {
                ex.printStackTrace();
                showError("Error saving review: " + ex.getMessage());
            }
        });
        
        cancelBtn.setOnAction(e -> {
            // Go back to game details or main menu
            if (review.getGameTitle() != null && review.getGamePlot() != null) {
                try {
                    Game game = gameDAO.getGameByTitleAndPlot(review.getGameTitle(), review.getGamePlot());
                    if (game != null) {
                        new GameDetailsViewer(game).show(stage);
                        return;
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            
            // If we can't go back to game details, go to review list
            new ReviewViewer().show(stage);
        });
        
        // Create an HBox for the buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));
        buttonBox.getChildren().addAll(saveBtn, cancelBtn);
        
        root.setBottom(buttonBox);
        
        // Create and show the scene
        Scene scene = new Scene(root, 600, 500);
        stage.setScene(scene);
        stage.setTitle(isNewReview ? "Add New Review" : "Edit Review");
        stage.show();
    }
    
    /**
     * Shows an error message.
     * 
     * @param message The error message
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Shows an alert message.
     * 
     * @param title The alert title
     * @param message The alert message
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
} 