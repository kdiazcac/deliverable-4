package vgbase;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

/**
 * Game Search
 * This class provides a UI for searching games by various criteria.
 * 
 * @author Group 9
 * @version 1.0
 */
public class GameSearch {
    
    private TableView<Game> gameTable;
    private GameDAO gameDAO;
    private TextField titleField;
    private ComboBox<String> publisherComboBox;
    private ComboBox<String> developerComboBox;
    private ComboBox<Genre> genreComboBox;
    
    /**
     * Default constructor
     */
    public GameSearch() {
        this.gameDAO = new GameDAO();
    }
    
    /**
     * Shows the game search window.
     * 
     * @param stage The primary stage
     */
    public void show(Stage stage) {
        // Create a border pane as the root
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        
        // Create a title
        Label titleLabel = new Label("Search Games");
        titleLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        root.setTop(titleLabel);
        
        // Create search form
        GridPane searchForm = new GridPane();
        searchForm.setHgap(10);
        searchForm.setVgap(10);
        searchForm.setPadding(new Insets(20, 10, 20, 10));
        
        // Add search fields
        int row = 0;
        
        searchForm.add(new Label("Title:"), 0, row);
        titleField = new TextField();
        titleField.setPromptText("Enter game title");
        searchForm.add(titleField, 1, row++);
        
        searchForm.add(new Label("Publisher:"), 0, row);
        publisherComboBox = new ComboBox<>();
        publisherComboBox.setPromptText("Select publisher");
        publisherComboBox.setPrefWidth(200);
        try {
            List<String> publishers = gameDAO.getAllPublishers();
            publishers.add(0, "Any");
            publisherComboBox.setItems(FXCollections.observableArrayList(publishers));
            publisherComboBox.setValue("Any");
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Error loading publishers: " + e.getMessage());
        }
        searchForm.add(publisherComboBox, 1, row++);
        
        searchForm.add(new Label("Developer:"), 0, row);
        developerComboBox = new ComboBox<>();
        developerComboBox.setPromptText("Select developer");
        developerComboBox.setPrefWidth(200);
        try {
            List<String> developers = gameDAO.getAllDevelopers();
            developers.add(0, "Any");
            developerComboBox.setItems(FXCollections.observableArrayList(developers));
            developerComboBox.setValue("Any");
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Error loading developers: " + e.getMessage());
        }
        searchForm.add(developerComboBox, 1, row++);
        
        searchForm.add(new Label("Genre:"), 0, row);
        genreComboBox = new ComboBox<>();
        genreComboBox.setPromptText("Select genre");
        genreComboBox.setPrefWidth(200);
        try {
            List<Genre> genres = gameDAO.getAllGenres();
            Genre anyGenre = new Genre();
            anyGenre.setGenreId(0);
            anyGenre.setName("Any");
            genres.add(0, anyGenre);
            genreComboBox.setItems(FXCollections.observableArrayList(genres));
            genreComboBox.setValue(anyGenre);
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Error loading genres: " + e.getMessage());
        }
        searchForm.add(genreComboBox, 1, row++);
        
        Button searchBtn = new Button("Search");
        HBox searchBtnBox = new HBox();
        searchBtnBox.setAlignment(Pos.CENTER);
        searchBtnBox.getChildren().add(searchBtn);
        searchForm.add(searchBtnBox, 0, row++, 2, 1);
        
        // Create a table view for results
        gameTable = new TableView<>();
        gameTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        gameTable.setPrefHeight(400);
        
        // Create table columns
        TableColumn<Game, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        
        TableColumn<Game, String> genreColumn = new TableColumn<>("Genre");
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genreName"));
        
        TableColumn<Game, String> releaseDateColumn = new TableColumn<>("Release Date");
        releaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        
        TableColumn<Game, String> ratingColumn = new TableColumn<>("Rating");
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("aggRating"));
        
        TableColumn<Game, String> publisherColumn = new TableColumn<>("Publisher");
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisherName"));
        
        TableColumn<Game, String> developerColumn = new TableColumn<>("Developer");
        developerColumn.setCellValueFactory(new PropertyValueFactory<>("developerName"));
        
        // Add columns to the table
        gameTable.getColumns().add(titleColumn);
        gameTable.getColumns().add(genreColumn);
        gameTable.getColumns().add(releaseDateColumn);
        gameTable.getColumns().add(ratingColumn);
        gameTable.getColumns().add(publisherColumn);
        gameTable.getColumns().add(developerColumn);
        
        // Add search form and results table to VBox
        VBox centerBox = new VBox(10);
        centerBox.getChildren().addAll(searchForm, new Label("Search Results:"), gameTable);
        root.setCenter(centerBox);
        
        // Set search button action
        searchBtn.setOnAction(e -> performSearch());
        
        // Create buttons for the bottom
        Button viewDetailsBtn = new Button("View Details");
        Button backBtn = new Button("Back to Menu");
        
        // Set button actions
        viewDetailsBtn.setOnAction(e -> {
            Game selectedGame = gameTable.getSelectionModel().getSelectedItem();
            if (selectedGame != null) {
                new GameDetailsViewer(selectedGame).show(stage);
            } else {
                showAlert("Selection Required", "Please select a game from the results.");
            }
        });
        
        backBtn.setOnAction(e -> {
            // Go back to the main menu
            try {
                new GameMenu().start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
                showError("Error returning to main menu: " + ex.getMessage());
            }
        });
        
        // Create an HBox for the buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));
        buttonBox.getChildren().addAll(viewDetailsBtn, backBtn);
        
        root.setBottom(buttonBox);
        
        // Create and show the scene
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Search Games");
        stage.show();
    }
    
    /**
     * Performs the search based on the selected criteria.
     */
    private void performSearch() {
        try {
            List<Game> games;
            
            // Get search criteria
            String title = titleField.getText().trim();
            String publisher = publisherComboBox.getValue();
            String developer = developerComboBox.getValue();
            Genre genre = genreComboBox.getValue();
            
            // Determine which search to perform based on criteria
            if (!title.isEmpty()) {
                // Search by title
                games = gameDAO.searchGamesByTitle(title);
            } else if (!"Any".equals(publisher)) {
                // Search by publisher
                games = gameDAO.getGamesByPublisher(publisher);
            } else if (!"Any".equals(developer)) {
                // Search by developer
                games = gameDAO.getGamesByDeveloper(developer);
            } else if (!"Any".equals(genre.getName())) {
                // Search by genre
                games = gameDAO.getGamesByGenre(genre.getGenreId());
            } else {
                // No specific criteria, get all games
                games = gameDAO.getAllGames();
            }
            
            // Update the table
            gameTable.setItems(FXCollections.observableArrayList(games));
            
            // Show message if no results
            if (games.isEmpty()) {
                showAlert("No Results", "No games found matching the search criteria.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Error performing search: " + e.getMessage());
        }
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