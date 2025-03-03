package vgbase;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

/**
 * Game Viewer
 * This class provides a UI for viewing all games in the database.
 * It displays games in a table view with columns for various properties.
 * 
 * @author Group 9
 * @version 1.0
 */
public class GameViewer {
    
    private TableView<Game> gameTable;
    private GameDAO gameDAO;
    
    /**
     * Default constructor
     */
    public GameViewer() {
        gameDAO = new GameDAO();
    }
    
    /**
     * Shows the game viewer window.
     * 
     * @param stage The primary stage
     */
    public void show(Stage stage) {
        // Create a border pane as the root
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        
        // Create a title
        Label titleLabel = new Label("Game List");
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
        
        TableColumn<Game, String> ratingColumn = new TableColumn<>("Rating");
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("aggRating"));
        
        TableColumn<Game, String> esrbColumn = new TableColumn<>("ESRB");
        esrbColumn.setCellValueFactory(new PropertyValueFactory<>("esrb"));
        
        TableColumn<Game, String> publisherColumn = new TableColumn<>("Publisher");
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisherName"));
        
        TableColumn<Game, String> developerColumn = new TableColumn<>("Developer");
        developerColumn.setCellValueFactory(new PropertyValueFactory<>("developerName"));
        
        // Add columns to the table
        gameTable.getColumns().add(titleColumn);
        gameTable.getColumns().add(genreColumn);
        gameTable.getColumns().add(releaseDateColumn);
        gameTable.getColumns().add(ratingColumn);
        gameTable.getColumns().add(esrbColumn);
        gameTable.getColumns().add(publisherColumn);
        gameTable.getColumns().add(developerColumn);
        
        // Load data into the table
        loadGames();
        
        // Add the table to the center of the border pane
        root.setCenter(gameTable);
        
        // Create buttons for the bottom
        Button viewDetailsBtn = new Button("View Details");
        Button backBtn = new Button("Back to Menu");
        
        // Set button actions
        viewDetailsBtn.setOnAction(e -> {
            Game selectedGame = gameTable.getSelectionModel().getSelectedItem();
            if (selectedGame != null) {
                new GameDetailsViewer(selectedGame).show(stage);
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
        stage.setTitle("Game List");
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
        Stage errorStage = new Stage();
        VBox errorBox = new VBox(10);
        errorBox.setAlignment(Pos.CENTER);
        errorBox.setPadding(new Insets(10));
        
        Label errorLabel = new Label("Error");
        errorLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        
        Label messageLabel = new Label(message);
        
        Button okButton = new Button("OK");
        okButton.setOnAction(e -> errorStage.close());
        
        errorBox.getChildren().addAll(errorLabel, messageLabel, okButton);
        
        Scene errorScene = new Scene(errorBox, 400, 200);
        errorStage.setScene(errorScene);
        errorStage.setTitle("Error");
        errorStage.show();
    }
} 