package vgbase;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

/**
 * Game Selector
 * This class provides a UI for selecting games to update or delete.
 * 
 * @author Group 9
 * @version 1.0
 */
public class GameSelector {
    
    /**
     * Enum representing the mode of the selector
     */
    public enum Mode {
        UPDATE,
        DELETE
    }
    
    private TableView<Game> gameTable;
    private GameDAO gameDAO;
    private Mode mode;
    
    /**
     * Constructor that takes a mode
     * 
     * @param mode The mode of the selector (UPDATE or DELETE)
     */
    public GameSelector(Mode mode) {
        this.gameDAO = new GameDAO();
        this.mode = mode;
    }
    
    /**
     * Shows the game selector window.
     * 
     * @param stage The primary stage
     */
    public void show(Stage stage) {
        // Create a border pane as the root
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        
        // Create a title
        String titleText = "Select a Game to " + (mode == Mode.UPDATE ? "Update" : "Delete");
        Label titleLabel = new Label(titleText);
        titleLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        root.setTop(titleLabel);
        
        // Create a table view
        gameTable = new TableView<>();
        gameTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        
        // Create table columns
        TableColumn<Game, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        
        TableColumn<Game, String> genreColumn = new TableColumn<>("Genre");
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genreName"));
        
        TableColumn<Game, String> releaseDateColumn = new TableColumn<>("Release Date");
        releaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        
        TableColumn<Game, String> publisherColumn = new TableColumn<>("Publisher");
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisherName"));
        
        TableColumn<Game, String> developerColumn = new TableColumn<>("Developer");
        developerColumn.setCellValueFactory(new PropertyValueFactory<>("developerName"));
        
        // Add columns to the table
        gameTable.getColumns().add(titleColumn);
        gameTable.getColumns().add(genreColumn);
        gameTable.getColumns().add(releaseDateColumn);
        gameTable.getColumns().add(publisherColumn);
        gameTable.getColumns().add(developerColumn);
        
        // Load data into the table
        loadGames();
        
        // Add the table to the center of the border pane
        root.setCenter(gameTable);
        
        // Create buttons for the bottom
        Button selectBtn = new Button(mode == Mode.UPDATE ? "Update Selected Game" : "Delete Selected Game");
        Button backBtn = new Button("Back to Menu");
        
        // Set button actions
        selectBtn.setOnAction(e -> {
            Game selectedGame = gameTable.getSelectionModel().getSelectedItem();
            if (selectedGame != null) {
                if (mode == Mode.UPDATE) {
                    // Go to the game editor
                    new GameEditor(selectedGame).show(stage);
                } else {
                    // Delete the game
                    try {
                        gameDAO.deleteGame(selectedGame.getTitle(), selectedGame.getPlot());
                        showAlert("Success", "Game deleted successfully!");
                        // Go back to the main menu
                        new GameMenu().start(stage);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        showError("Error deleting game: " + ex.getMessage());
                    }
                }
            } else {
                showAlert("Selection Required", "Please select a game.");
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
        buttonBox.getChildren().addAll(selectBtn, backBtn);
        
        root.setBottom(buttonBox);
        
        // Create and show the scene
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle(titleText);
        stage.show();
    }
    
    /**
     * Loads games from the database into the table view.
     */
    private void loadGames() {
        try {
            List<Game> games = gameDAO.getAllGames();
            gameTable.setItems(FXCollections.observableArrayList(games));
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Error loading games: " + e.getMessage());
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