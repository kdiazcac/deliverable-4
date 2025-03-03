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
 * Review Viewer
 * This class provides a UI for viewing all reviews in the database.
 * It displays reviews in a table view with columns for various properties.
 * 
 * @author Group 9
 * @version 1.0
 */
public class ReviewViewer {
    
    private TableView<Review> reviewTable;
    private ReviewDAO reviewDAO;
    private GameDAO gameDAO;
    
    /**
     * Default constructor
     */
    public ReviewViewer() {
        this.reviewDAO = new ReviewDAO();
        this.gameDAO = new GameDAO();
    }
    
    /**
     * Shows the review viewer window.
     * 
     * @param stage The primary stage
     */
    public void show(Stage stage) {
        // Create a border pane as the root
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        
        // Create a title
        Label titleLabel = new Label("Review List");
        titleLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        root.setTop(titleLabel);
        
        // Create a table view
        reviewTable = new TableView<>();
        reviewTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        
        // Create table columns
        TableColumn<Review, String> gameColumn = new TableColumn<>("Game");
        gameColumn.setCellValueFactory(new PropertyValueFactory<>("gameTitle"));
        
        TableColumn<Review, String> reviewerColumn = new TableColumn<>("Reviewer");
        reviewerColumn.setCellValueFactory(new PropertyValueFactory<>("reviewer"));
        
        TableColumn<Review, Integer> ratingColumn = new TableColumn<>("Rating");
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        
        TableColumn<Review, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("reviewTitle"));
        
        TableColumn<Review, String> contentColumn = new TableColumn<>("Content");
        contentColumn.setCellValueFactory(new PropertyValueFactory<>("reviewContent"));
        contentColumn.setPrefWidth(300);
        
        // Add columns to the table
        reviewTable.getColumns().add(gameColumn);
        reviewTable.getColumns().add(reviewerColumn);
        reviewTable.getColumns().add(ratingColumn);
        reviewTable.getColumns().add(titleColumn);
        reviewTable.getColumns().add(contentColumn);
        
        // Load data into the table
        loadReviews();
        
        // Add the table to the center of the border pane
        root.setCenter(reviewTable);
        
        // Create buttons for the bottom
        Button viewGameBtn = new Button("View Game");
        Button addReviewBtn = new Button("Add Review");
        Button editReviewBtn = new Button("Edit Review");
        Button deleteReviewBtn = new Button("Delete Review");
        Button backBtn = new Button("Back to Menu");
        
        // Set button actions
        viewGameBtn.setOnAction(e -> {
            Review selectedReview = reviewTable.getSelectionModel().getSelectedItem();
            if (selectedReview != null) {
                try {
                    Game game = gameDAO.getGameByTitleAndPlot(
                        selectedReview.getGameTitle(), 
                        selectedReview.getGamePlot()
                    );
                    if (game != null) {
                        new GameDetailsViewer(game).show(stage);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    showError("Error loading game: " + ex.getMessage());
                }
            }
        });
        
        addReviewBtn.setOnAction(e -> {
            new ReviewEditor().show(stage);
        });
        
        editReviewBtn.setOnAction(e -> {
            Review selectedReview = reviewTable.getSelectionModel().getSelectedItem();
            if (selectedReview != null) {
                new ReviewEditor(selectedReview, false).show(stage);
            }
        });
        
        deleteReviewBtn.setOnAction(e -> {
            Review selectedReview = reviewTable.getSelectionModel().getSelectedItem();
            if (selectedReview != null) {
                try {
                    reviewDAO.deleteReview(
                        selectedReview.getReviewer(),
                        selectedReview.getGameTitle(),
                        selectedReview.getGamePlot()
                    );
                    loadReviews(); // Refresh the table
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    showError("Error deleting review: " + ex.getMessage());
                }
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
        buttonBox.getChildren().addAll(
                viewGameBtn,
                addReviewBtn,
                editReviewBtn,
                deleteReviewBtn,
                backBtn
        );
        
        root.setBottom(buttonBox);
        
        // Create and show the scene
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Review List");
        stage.show();
    }
    
    /**
     * Loads reviews from the database into the table view.
     */
    private void loadReviews() {
        try {
            List<Review> reviews = reviewDAO.getAllReviews();
            reviewTable.setItems(FXCollections.observableArrayList(reviews));
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Error loading reviews: " + e.getMessage());
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