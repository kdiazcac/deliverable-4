package vgbase;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * VideoGame Database Management System - Main Application
 * This class serves as the entry point for the JavaFX application and displays the main menu.
 * It provides navigation to different operations such as adding, updating, deleting games, and querying the database.
 * 
 * @author Group 9
 * @version 1.0
 */
public class GameMenu extends Application {
    
    private Stage primaryStage;
    
    /**
     * The main entry point for the JavaFX application.
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Initializes and starts the JavaFX application.
     * 
     * @param primaryStage The primary stage for the application
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Video Game Database Management System");
        
        showMainMenu();
    }
    
    /**
     * Displays the main menu with buttons for different operations.
     */
    private void showMainMenu() {
        // Create a label for the title
        Label titleLabel = new Label("Video Game Database Management System");
        titleLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        
        // Create buttons for each operation
        Button viewGamesBtn = new Button("View All Games");
        Button addGameBtn = new Button("Add New Game");
        Button updateGameBtn = new Button("Update Game");
        Button deleteGameBtn = new Button("Delete Game");
        Button searchGamesBtn = new Button("Search Games");
        Button viewReviewsBtn = new Button("View Reviews");
        Button addReviewBtn = new Button("Add Review");
        Button exitBtn = new Button("Exit");
        
        // Set button widths
        viewGamesBtn.setPrefWidth(200);
        addGameBtn.setPrefWidth(200);
        updateGameBtn.setPrefWidth(200);
        deleteGameBtn.setPrefWidth(200);
        searchGamesBtn.setPrefWidth(200);
        viewReviewsBtn.setPrefWidth(200);
        addReviewBtn.setPrefWidth(200);
        exitBtn.setPrefWidth(200);
        
        // Set button actions
        viewGamesBtn.setOnAction(e -> new GameViewer().show(primaryStage));
        addGameBtn.setOnAction(e -> new GameEditor(null).show(primaryStage));
        updateGameBtn.setOnAction(e -> new GameSelector(GameSelector.Mode.UPDATE).show(primaryStage));
        deleteGameBtn.setOnAction(e -> new GameSelector(GameSelector.Mode.DELETE).show(primaryStage));
        searchGamesBtn.setOnAction(e -> new GameSearch().show(primaryStage));
        viewReviewsBtn.setOnAction(e -> new ReviewViewer().show(primaryStage));
        addReviewBtn.setOnAction(e -> new ReviewEditor().show(primaryStage));
        exitBtn.setOnAction(e -> primaryStage.close());
        
        // Create layout and add elements
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(
            titleLabel,
            viewGamesBtn,
            addGameBtn,
            updateGameBtn, 
            deleteGameBtn,
            searchGamesBtn,
            viewReviewsBtn,
            addReviewBtn,
            exitBtn
        );
        
        // Create scene and set it to the stage
        Scene scene = new Scene(vbox, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}