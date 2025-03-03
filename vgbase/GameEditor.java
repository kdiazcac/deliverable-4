package vgbase;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Game Editor
 * This class provides a UI for adding or editing games in the database.
 * 
 * @author Group 9
 * @version 1.0
 */
public class GameEditor {
    
    private Game game;
    private GameDAO gameDAO;
    private String originalTitle;
    private String originalPlot;
    
    /**
     * Constructor
     * 
     * @param game The Game to edit, or null for a new game
     */
    public GameEditor(Game game) {
        this.gameDAO = new GameDAO();
        if (game != null) {
            this.game = game;
            this.originalTitle = game.getTitle();
            this.originalPlot = game.getPlot();
        } else {
            this.game = new Game();
        }
    }
    
    /**
     * Shows the game editor window.
     * 
     * @param stage The primary stage
     */
    public void show(Stage stage) {
        boolean isNewGame = (originalTitle == null);
        
        // Create a border pane as the root
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        
        // Create a title
        Label titleLabel = new Label(isNewGame ? "Add New Game" : "Edit Game");
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
        
        formGrid.add(new Label("Title:"), 0, row);
        TextField titleField = new TextField(game.getTitle());
        formGrid.add(titleField, 1, row++);
        
        formGrid.add(new Label("Plot:"), 0, row);
        TextArea plotArea = new TextArea(game.getPlot());
        plotArea.setWrapText(true);
        plotArea.setPrefHeight(100);
        formGrid.add(plotArea, 1, row++);
        
        formGrid.add(new Label("Release Date:"), 0, row);
        DatePicker releaseDatePicker = new DatePicker();
        if (game.getReleaseDate() != null) {
            releaseDatePicker.setValue(game.getReleaseDate().toLocalDate());
        }
        formGrid.add(releaseDatePicker, 1, row++);
        
        formGrid.add(new Label("Rating:"), 0, row);
        TextField ratingField = new TextField();
        if (game.getAggRating() != null) {
            ratingField.setText(game.getAggRating().toString());
        }
        formGrid.add(ratingField, 1, row++);
        
        formGrid.add(new Label("ESRB:"), 0, row);
        ComboBox<String> esrbComboBox = new ComboBox<>(
                FXCollections.observableArrayList("E", "T", "M", "A")
        );
        if (game.getEsrb() != null) {
            esrbComboBox.setValue(game.getEsrb());
        }
        formGrid.add(esrbComboBox, 1, row++);
        
        formGrid.add(new Label("Publisher:"), 0, row);
        ComboBox<String> publisherComboBox = new ComboBox<>();
        try {
            List<String> publishers = gameDAO.getAllPublishers();
            publisherComboBox.setItems(FXCollections.observableArrayList(publishers));
            if (game.getPublisherName() != null) {
                publisherComboBox.setValue(game.getPublisherName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Error loading publishers: " + e.getMessage());
        }
        formGrid.add(publisherComboBox, 1, row++);
        
        formGrid.add(new Label("Developer:"), 0, row);
        ComboBox<String> developerComboBox = new ComboBox<>();
        try {
            List<String> developers = gameDAO.getAllDevelopers();
            developerComboBox.setItems(FXCollections.observableArrayList(developers));
            if (game.getDeveloperName() != null) {
                developerComboBox.setValue(game.getDeveloperName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Error loading developers: " + e.getMessage());
        }
        formGrid.add(developerComboBox, 1, row++);
        
        formGrid.add(new Label("Genre:"), 0, row);
        ComboBox<Genre> genreComboBox = new ComboBox<>();
        try {
            List<Genre> genres = gameDAO.getAllGenres();
            genreComboBox.setItems(FXCollections.observableArrayList(genres));
            if (game.getGenreId() != null) {
                // Find the genre with matching ID
                for (Genre genre : genres) {
                    if (genre.getGenreId().equals(game.getGenreId())) {
                        genreComboBox.setValue(genre);
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Error loading genres: " + e.getMessage());
        }
        formGrid.add(genreComboBox, 1, row++);
        
        // Add the form to the center of the border pane
        root.setCenter(formGrid);
        
        // Create buttons for the bottom
        Button saveBtn = new Button(isNewGame ? "Add Game" : "Save Changes");
        Button cancelBtn = new Button("Cancel");
        
        // Set button actions
        saveBtn.setOnAction(e -> {
            try {
                // Validate required fields
                if (titleField.getText().trim().isEmpty() ||
                    plotArea.getText().trim().isEmpty() ||
                    releaseDatePicker.getValue() == null ||
                    esrbComboBox.getValue() == null ||
                    publisherComboBox.getValue() == null ||
                    developerComboBox.getValue() == null ||
                    genreComboBox.getValue() == null) {
                    
                    showAlert("Validation Error", "All fields are required.");
                    return;
                }
                
                // Parse rating
                Float rating = null;
                if (!ratingField.getText().trim().isEmpty()) {
                    try {
                        rating = Float.parseFloat(ratingField.getText());
                        if (rating < 0 || rating > 10) {
                            showAlert("Validation Error", "Rating must be between 0 and 10.");
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        showAlert("Validation Error", "Rating must be a valid number.");
                        return;
                    }
                }
                
                // Update game object
                game.setTitle(titleField.getText());
                game.setPlot(plotArea.getText());
                game.setReleaseDate(Date.valueOf(releaseDatePicker.getValue()));
                game.setAggRating(rating);
                game.setEsrb(esrbComboBox.getValue());
                game.setPublisherName(publisherComboBox.getValue());
                game.setDeveloperName(developerComboBox.getValue());
                game.setGenreId(genreComboBox.getValue().getGenreId());
                
                // Save to database
                if (isNewGame) {
                    gameDAO.insertGame(game);
                    showAlert("Success", "Game added successfully!");
                } else {
                    gameDAO.updateGame(originalTitle, originalPlot, game);
                    showAlert("Success", "Game updated successfully!");
                }
                
                // Go back to game list
                new GameViewer().show(stage);
                
            } catch (SQLException ex) {
                ex.printStackTrace();
                showError("Error saving game: " + ex.getMessage());
            }
        });
        
        cancelBtn.setOnAction(e -> {
            if (isNewGame) {
                // Go back to main menu
                try {
                    new GameMenu().start(stage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    showError("Error returning to main menu: " + ex.getMessage());
                }
            } else {
                // Go back to game details
                new GameDetailsViewer(game).show(stage);
            }
        });
        
        // Create an HBox for the buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));
        buttonBox.getChildren().addAll(saveBtn, cancelBtn);
        
        root.setBottom(buttonBox);
        
        // Create and show the scene
        Scene scene = new Scene(root, 600, 600);
        stage.setScene(scene);
        stage.setTitle(isNewGame ? "Add New Game" : "Edit Game");
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