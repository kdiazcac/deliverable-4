package vgbase;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

/**
 * Game Details Viewer
 * This class provides a UI for viewing detailed information about a game,
 * including its properties and reviews.
 * 
 * @author Group 9
 * @version 1.0
 */
public class GameDetailsViewer {
    
    private Game game;
    private ReviewDAO reviewDAO;
    
    /**
     * Constructor
     * 
     * @param game The Game to view
     */
    public GameDetailsViewer(Game game) {
        this.game = game;
        this.reviewDAO = new ReviewDAO();
    }
    
    /**
     * Shows the game details viewer window.
     * 
     * @param stage The primary stage
     */
    public void show(Stage stage) {
        // Create a border pane as the root
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        
        // Create a title
        Label titleLabel = new Label("Game Details");
        titleLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        root.setTop(titleLabel);
        
        // Create a grid pane for the game details
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));
        
        // Add game details to the grid pane
        int row = 0;
        
        // Title
        Label titleHeader = new Label("Title:");
        titleHeader.setStyle("-fx-font-weight: bold;");
        Label titleValue = new Label(game.getTitle());
        gridPane.add(titleHeader, 0, row);
        gridPane.add(titleValue, 1, row);
        row++;
        
        // Genre
        Label genreHeader = new Label("Genre:");
        genreHeader.setStyle("-fx-font-weight: bold;");
        Label genreValue = new Label(game.getGenreName());
        gridPane.add(genreHeader, 0, row);
        gridPane.add(genreValue, 1, row);
        row++;
        
        // Release Date
        Label releaseDateHeader = new Label("Release Date:");
        releaseDateHeader.setStyle("-fx-font-weight: bold;");
        Label releaseDateValue = new Label(game.getReleaseDate() != null ? game.getReleaseDate().toString() : "N/A");
        gridPane.add(releaseDateHeader, 0, row);
        gridPane.add(releaseDateValue, 1, row);
        row++;
        
        // ESRB
        Label esrbHeader = new Label("ESRB Rating:");
        esrbHeader.setStyle("-fx-font-weight: bold;");
        Label esrbValue = new Label(game.getEsrb());
        gridPane.add(esrbHeader, 0, row);
        gridPane.add(esrbValue, 1, row);
        row++;
        
        // Publisher
        Label publisherHeader = new Label("Publisher:");
        publisherHeader.setStyle("-fx-font-weight: bold;");
        Label publisherValue = new Label(game.getPublisherName());
        gridPane.add(publisherHeader, 0, row);
        gridPane.add(publisherValue, 1, row);
        row++;
        
        // Developer
        Label developerHeader = new Label("Developer:");
        developerHeader.setStyle("-fx-font-weight: bold;");
        Label developerValue = new Label(game.getDeveloperName());
        gridPane.add(developerHeader, 0, row);
        gridPane.add(developerValue, 1, row);
        row++;
        
        // Plot
        Label plotHeader = new Label("Plot:");
        plotHeader.setStyle("-fx-font-weight: bold;");
        gridPane.add(plotHeader, 0, row);
        
        TextArea plotTextArea = new TextArea(game.getPlot());
        plotTextArea.setEditable(false);
        plotTextArea.setWrapText(true);
        plotTextArea.setPrefRowCount(5);
        gridPane.add(plotTextArea, 1, row);
        row++;
        
        VBox gameDetailsBox = new VBox(10);
        gameDetailsBox.setPadding(new Insets(10));
        gameDetailsBox.getChildren().addAll(gridPane);
        
        // Reviews section
        Label reviewsLabel = new Label("Reviews");
        reviewsLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
        gameDetailsBox.getChildren().add(reviewsLabel);
        
        // Create a table view for reviews
        TableView<Review> reviewTable = new TableView<>();
        reviewTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        
        // Create table columns
        TableColumn<Review, String> reviewerColumn = new TableColumn<>("Reviewer");
        reviewerColumn.setCellValueFactory(new PropertyValueFactory<>("reviewerName"));
        
        TableColumn<Review, Integer> ratingColumn = new TableColumn<>("Rating");
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        
        TableColumn<Review, String> reviewTextColumn = new TableColumn<>("Review");
        reviewTextColumn.setCellValueFactory(new PropertyValueFactory<>("reviewText"));
        reviewTextColumn.setPrefWidth(400);
        
        // Add columns to the table
        reviewTable.getColumns().add(reviewerColumn);
        reviewTable.getColumns().add(ratingColumn);
        reviewTable.getColumns().add(reviewTextColumn);
        
        // Load reviews for this game
        try {
            List<Review> reviews = reviewDAO.getReviewsByGame(game.getTitle(), game.getPlot());
            reviewTable.setItems(FXCollections.observableArrayList(reviews));
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Error loading reviews: " + e.getMessage());
        }
        
        gameDetailsBox.getChildren().add(reviewTable);
        
        // Add the game details box to the center of the border pane
        root.setCenter(gameDetailsBox);
        
        // Create buttons for the bottom
        Button backBtn = new Button("Back to Games");
        
        // Set button actions
        backBtn.setOnAction(e -> {
            // Go back to the game list
            new GameViewer().show(stage);
        });
        
        // Create an HBox for the buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));
        buttonBox.getChildren().addAll(backBtn);
        
        root.setBottom(buttonBox);
        
        // Create and show the scene
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Game Details - " + game.getTitle());
        stage.show();
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