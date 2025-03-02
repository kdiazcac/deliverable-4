package hellofx;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** This program inserts/modifies/deletes/queries and accesses entries in the Game database.
 *
 * @author manjobankaurhundal, et al.
 * @version 1.0
 */
public class GameMenu extends Application {
    private String url;
    private String user;
    private String password;
    private Connection connection;

    @Override
    public void start(Stage primaryStage) {

        /*
        NOTE: The expectation is to develop an application with 4-5 entities and corresponding relationships.
        The final application must implement the insert, update, delete, and querying functionalities designed in the first 3 deliverables. So,
        if your application models a "movies" database, then the application MUST allow you to add movies, edit movies,
        list movies, search movies, and delete movies.

         */

        url = "jdbc:mysql://localhost:3306/mysql";
        user = "root";
        //my password is different!
        password = "GRT5e43s";


        // Create a label
        Label mainLabel = new Label("What would you like to do?"); //for main screen
        mainLabel.setStyle("-fx-font-size: 24px;");
        //-----------------------------add game button logic below here------------------------------//
        Button insertBtn = new Button();
        Label insertLabel = new Label("Give the following column values.");  //for Add a Game screen
        insertLabel.setStyle("-fx-font-size: 24px;");
        insertBtn.setText("Add a Game");
        StackPane insertPane = new StackPane();
        insertPane.getChildren().add(insertLabel);

        //--------------------Insert page fields---------------------------//
        final TextField titleField = new TextField();
        titleField.setPrefWidth(60);
        Label titleLabel = new Label("Title");
        HBox titleBox = new HBox(20);
        titleBox.getChildren().addAll(titleLabel, titleField);

        final TextField plotField = new TextField();
        plotField.setPrefWidth(60);
        Label plotLabel = new Label("Plot");
        HBox plotBox = new HBox(20);
        plotBox.getChildren().addAll(plotLabel, plotField);

        final TextField releaseDateField = new TextField();
        releaseDateField.setPrefWidth(60);
        Label releaseDateLabel = new Label("Release Date(yyyy-mm-dd)");
        HBox relDateBox = new HBox(20);
        relDateBox.getChildren().addAll(releaseDateLabel, releaseDateField);

        final TextField aggRatingField = new TextField();
        aggRatingField.setPrefWidth(60);
        Label aggRatingLabel = new Label("Agg Rating");
        HBox aggRatingBox = new HBox(20);
        aggRatingBox.getChildren().addAll(aggRatingLabel, aggRatingField);

        final TextField esrbField = new TextField();
        esrbField.setPrefWidth(60);
        Label esrbLabel = new Label("ESRB");
        HBox esrbBox = new HBox(20);
        esrbBox.getChildren().addAll(esrbLabel, esrbField);

        final TextField pubNameField = new TextField();
        pubNameField.setPrefWidth(60);
        Label pubNameLabel = new Label("Publisher Name");
        HBox pubNameBox = new HBox(20);
        pubNameBox.getChildren().addAll(pubNameLabel, pubNameField);

        final TextField devNameField = new TextField();
        pubNameField.setPrefWidth(60);
        Label devNameLabel = new Label("Developer Name");
        HBox devNameBox = new HBox(20);
        devNameBox.getChildren().addAll(devNameLabel, devNameField);

        final TextField genreField = new TextField();
        genreField.setPrefWidth(60);
        Label genreLabel = new Label("Genre");
        HBox genreBox = new HBox(20);
        genreBox.getChildren().addAll(genreLabel, genreField);

        Button backBtn = new Button();
        backBtn.setText("Back");
        Button enterBtn = new Button();
        enterBtn.setText("Enter");
        HBox btnBox = new HBox(20);
        btnBox.getChildren().addAll(enterBtn, backBtn);

        enterBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                insertGame(titleField, plotField, releaseDateField, aggRatingField,
                        esrbField, pubNameField, devNameField, genreField);

            }
        });

        VBox insertTextVBox = new VBox(20);
        insertTextVBox.getChildren().addAll(titleBox, plotBox, relDateBox, aggRatingBox, esrbBox, pubNameBox, devNameBox, genreBox, btnBox);

        StackPane insertTextPane = new StackPane();
        insertTextPane.getChildren().add(insertTextVBox);

        //-------------------insert game GUI logic ---------------------------//
        BorderPane insertBorderPane = new BorderPane();
        BorderPane.setMargin(insertPane, new Insets(20, 20, 20, 20));
        insertBorderPane.setTop(insertPane);
        insertBorderPane.setCenter(insertTextPane);
        //insertBorderPane.setBottom(insertVBox);

        Scene insertScene = new Scene(insertBorderPane, 600, 600);
        insertBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(insertScene);
            }
        });
        //------------------end insert game GUI logic----------------------//

        //----------------- Remove a Game ----------- added 3/1/25 JZ----------------//

        Button removeBtn = new Button();
        Label deleteLabel = new Label("Enter the game title to delete.");
        deleteLabel.setStyle("-fx-font-size: 24px;");
        removeBtn.setText("Remove a Game");
        StackPane deletePane = new StackPane();
        deletePane.getChildren().add(deleteLabel);
        //---------------------delete page field---------------------------//
        final TextField titleField2 = new TextField();
        titleField2.setPrefWidth(60);
        Label titleLabel2 = new Label("Title");
        HBox titleBox2 = new HBox(20);
        titleBox2.getChildren().addAll(titleLabel2, titleField2);

        HBox btnBox2 = new HBox(20);
        btnBox2.getChildren().addAll(enterBtn, backBtn);
        enterBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                deleteGame(titleField2);
            }
        });

        VBox deleteTextVBox = new VBox(20);
        deleteTextVBox.getChildren().addAll(titleBox2, btnBox2);
        StackPane deleteTextPane = new StackPane();
        deleteTextPane.getChildren().add(deleteTextVBox);

        BorderPane deleteBorderPane = new BorderPane();
        BorderPane.setMargin(deleteBorderPane, new Insets( 20,20,20,20));
        deleteBorderPane.setTop(deletePane);
        deleteBorderPane.setCenter(deleteTextPane);

        Scene deleteScene = new Scene(deleteBorderPane, 600, 200); //made it narrower JZ 3/1/25
        removeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.setScene(deleteScene);
            }
        });
        //----------------------end delete button GUI logic---------------------//

        ///////////////****************////////////////////////
        //////**Edit Game**////////////////////




        Button editBtn = new Button();
        editBtn.setText("Edit a Game");
        BorderPane editBorderPane = new BorderPane();

        Button editBackBtn = new Button();
        editBackBtn.setText("Back");
        Button editEnterBtn = new Button();
        editEnterBtn.setText("Enter");
        HBox editBtnBox = new HBox(20);
        editBtnBox.getChildren().addAll(editEnterBtn, editBackBtn);

        final TextField gameNameField = new TextField();
        gameNameField.setPrefWidth(60);
        Label editTitleLabel = new Label("Enter the title of the game you want to modify");
        HBox editTitleBox = new HBox(20);
        editTitleBox.getChildren().addAll(editTitleLabel, gameNameField);

        StackPane editPane = new StackPane();
        //Label editLabel = new Label("Enter the title of the game you want to modify.");
        //editPane.getChildren().add(editLabel);
        ///new/////
        final TextField fieldModField = new TextField();
        fieldModField.setPrefWidth(60);
        Label editModLabel = new Label("Which field do you want to modify?");
        HBox editModBox = new HBox(20);
        editModBox.getChildren().addAll(editModLabel, fieldModField);

        final TextField fieldModNewField = new TextField();
        fieldModNewField.setPrefWidth(60);
        Label editModNewLabel = new Label("Enter the new value");
        HBox editModNewBox = new HBox(20);
        editModNewBox.getChildren().addAll(editModNewLabel, fieldModNewField);

        BorderPane.setMargin(editPane, new Insets(20, 20, 20, 20));
        editBorderPane.setTop(editPane);
        StackPane editTextPane = new StackPane();
        VBox editTextVBox = new VBox(20);
        editTextVBox.getChildren().addAll(editTitleBox, editModBox, editModNewBox, editBtnBox);


        editTextPane.getChildren().add(editTextVBox);
        editBorderPane.setCenter(editTextPane);
        Scene editScene  =  new Scene(editBorderPane,400, 400);
        editBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(editScene);
            }
        });



        Button editLocalBackBtn = new Button();
        editLocalBackBtn.setText("Back");
        Label editUpdatedLabel = new Label("Record updated successfully!");
        VBox editUpdatedVBox = new VBox(20);
        editUpdatedVBox.getChildren().addAll(editUpdatedLabel, editLocalBackBtn);
        Scene recUpdatedScene  =  new Scene(editUpdatedVBox, 400, 400);
        editEnterBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                editGame(gameNameField, fieldModField, fieldModNewField);
                primaryStage.setScene(recUpdatedScene);

            }

        });


        /////////////--end Edit game logic/////////////////////



        Button listBtn = new Button();
        listBtn.setText("List Games");

        /////////List Games///////////////////
        Label listLabel = new Label("The list of games in the database:");
        listLabel.setStyle("-fx-font-size: 20px;");
        Label gamesLabel = new Label("");
        gamesLabel.setStyle("-fx-font-size: 15px;");

        Iterator<String> iterator = listGames().iterator();

        // Step 3: Iterate through the List
        while (iterator.hasNext()) {
            String name = iterator.next();
            String currentText = gamesLabel.getText();
            gamesLabel.setText(currentText + "\n" + name);
        }


        StackPane listPane = new StackPane();
        listPane.getChildren().add(listLabel);
        StackPane listGamesPane = new StackPane();
        listGamesPane.getChildren().add(gamesLabel);
        BorderPane listBorderPane = new BorderPane();
        Button listBackButton = new Button("Back");

        BorderPane.setMargin(listPane, new Insets(20, 20, 20, 20));
        listBorderPane.setTop(listPane);
        listBorderPane.setCenter(listGamesPane);
        listBorderPane.setBottom(listBackButton);

        Scene listGamesScene = new Scene(listBorderPane, 400, 400);
        listBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(listGamesScene);
            }
        });




        ////////////////*****end list games logic*****//////////////////////

        Button searchBtn = new Button();
        searchBtn.setText("Search Games");

        // Create a StackPane to center the label
        StackPane topPane = new StackPane();
        topPane.getChildren().add(mainLabel);

        // Create a BorderPane layout
        BorderPane borderPane = new BorderPane();
        // Create a VBox to stack the components vertically
        VBox vbox = new VBox(20);
        vbox.getChildren().addAll(insertBtn, removeBtn, editBtn, listBtn, searchBtn);
        vbox.setAlignment(Pos.CENTER);
        // Set the top section of the BorderPane to the StackPane (which centers the label)
        borderPane.setTop(topPane);
        borderPane.setCenter(vbox);

        BorderPane.setMargin(topPane, new Insets(20, 20, 20, 20));
        // Create a scene with the BorderPane and set its size
        Scene mainScene = new Scene(borderPane, 600, 400);

        backBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(mainScene);
            }
        });

        editBackBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(mainScene);
            }
        });
        editLocalBackBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(mainScene);
            }
        });
        // Set up the stage
        primaryStage.setTitle("Gaming Database");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    /** Main launch point for application.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /** This method inserts a Game into the database
     *
     *
     * @param t1 title (String).
     * @param t2 plot (String).
     * @param t3 release date (Date).
     * @param t4 aggregate rating (float).
     * @param t5 ESRB Rating (String).
     * @param t6 publisher name (String).
     * @param t7 developer name (String).
     * @param t8 genre (String).
     *
     * @author manjobankaurhundal, et al.
     * */

    public void insertGame(TextField t1, TextField t2, TextField t3, TextField t4,
                           TextField t5, TextField t6, TextField t7, TextField t8) {

        String title = t1.getText();
        String plot = t2.getText();
        String releaseDate = t3.getText();
        String aggRating = t4.getText();
        String esrb = t5.getText();
        String publisherName = t6.getText();
        String developerName = t7.getText();
        String genre = t8.getText();
        java.sql.Date relDate = null;
        //java.sql.Date relDate = java.sql.Date.valueOf(releaseDate.trim());
        //float agRating =
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = dateFormat.parse(releaseDate);
            // Step 2: Convert java.util.Date to java.sql.Date
            relDate = new java.sql.Date(parsedDate.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }

        float agRating = Float.parseFloat(aggRating);


        String insertQuery = "INSERT INTO GAME(title, plot, releaseDate, aggRating, ESRB, publisherName, developerName, genreID) values (?,?,?,?,?,?,?,?)";
        try {

            connection = DriverManager.getConnection(url, user, password);

            String selectSQL = "SELECT genreId FROM genre where name = ?";
            PreparedStatement selectPreparedStatement = connection.prepareStatement(selectSQL);
            selectPreparedStatement.setString(1, genre);

            ResultSet resultSet = selectPreparedStatement.executeQuery();
            int genreid =  0;
            while (resultSet.next()) {

                genreid = resultSet.getInt("genreId");
                //System.out.println("ID is...." + id);

            }
            // Create a PreparedStatement to prevent SQL injection
            PreparedStatement insertPreparedStatement = connection.prepareStatement(insertQuery);

            // Set the parameters for the insert query
            insertPreparedStatement.setString(1, title);
            insertPreparedStatement.setString(2, plot);
            insertPreparedStatement.setDate(3, relDate);
            insertPreparedStatement.setFloat(4, agRating);
            insertPreparedStatement.setString(5, esrb);  // Set age
            insertPreparedStatement.setString(6, publisherName);
            insertPreparedStatement.setString(7, developerName);
            insertPreparedStatement.setInt(8, genreid);

            // Execute the insert query
            int rowsAffected = insertPreparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Record inserted successfully.");
                //JZ added 3/1/25
                connection.commit();
            } else {
                System.out.println("Failed to insert record.");
                //JZ added 3/1/25
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            //TODO - close resources  3/1/25 JZ
        }
    }//end insertGame method

    /** This method deletes a Game from the database by title.
     *
     * @param t1 text field for title.
     *
     * @author Justin Zimmerman, et al.
     * @version 1.0
     *
     */
    public void deleteGame(TextField t1) {

        PreparedStatement deletePreparedStatement = null;
        String title = t1.getText();
        String conditionValue = "";

        try {
            connection.setAutoCommit(false);
            connection = DriverManager.getConnection(url, user, password);
            String deleteSQL = "Delete from GAME where title = " + title;

            deletePreparedStatement = connection.prepareStatement(deleteSQL);
            deletePreparedStatement.setObject(1, conditionValue);

            int rowsAffected = deletePreparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Game deleted successfully");
                connection.commit();

            } else {
                System.out.println("Deletion unsuccessful");
                connection.rollback();
            }
            deletePreparedStatement.close();
        } catch (SQLException e) {
            try {
                if(connection !=null) {
                    connection.rollback();
                }
            } catch (SQLException s) {
                s.printStackTrace();
            }
            e.printStackTrace();
        }
        finally {
            try{
                
                if (deletePreparedStatement != null){
                    deletePreparedStatement.close();
                }
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }//end finally block
       
    }//end deleteGame method

    public  void editGame(TextField t1, TextField t2, TextField t3) {



        String title = t1.getText();
        String fieldName = t2.getText();
        String newFieldValue = t3.getText();
        try{
            Connection connection = DriverManager.getConnection(url, user, password);
            //java.util.Date dateField;
            //String otherField;
            //Float floatField;
            String updateQuery;
            if(fieldName.equals("releaseDate")){

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date newDate = dateFormat.parse(newFieldValue);
                java.sql.Date newReldate  = new java.sql.Date(newDate.getTime());

                updateQuery = "UPDATE GAME SET releaseDate = ? where title = ?";


                // Create a PreparedStatement to prevent SQL injection
                PreparedStatement updatePreparedStatement = connection.prepareStatement(updateQuery);

                // Set the parameters for the insert query
                updatePreparedStatement.setDate(1, newReldate);
                updatePreparedStatement.setString(2, title);

                // Execute the insert query
                int rowsAffected = updatePreparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Record updated successfully.");
                } else {
                    System.out.println("Failed to update record.");
                }


            }
            else if (fieldName.equals("aggRating")){
                Float newFloatField = Float.parseFloat(newFieldValue);
                updateQuery = "UPDATE GAME SET aggRating = ? where title = ?";


                PreparedStatement updatePreparedStatement = connection.prepareStatement(updateQuery);

                // Set the parameters for the insert query
                updatePreparedStatement.setFloat(1, newFloatField);
                updatePreparedStatement.setString(2,title);

                // Execute the insert query
                int rowsAffected = updatePreparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Record updated successfully.");
                } else {
                    System.out.println("Failed to update record.");
                }
            }
            else {

                updateQuery = "UPDATE GAME SET " + fieldName  + " = ? where title = ?";
                PreparedStatement updatePreparedStatement = connection.prepareStatement(updateQuery);

                // Set the parameters for the insert query
                updatePreparedStatement.setString(1, newFieldValue);
                updatePreparedStatement.setString(2,title);

                // Execute the insert query
                int rowsAffected = updatePreparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Record updated successfully.");
                } else {
                    System.out.println("Failed to update record.");
                }

            }



        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public  List<String> listGames() {

        List<String> listOfTitles = new ArrayList<>();
        try{
            Connection connection = DriverManager.getConnection(url, user, password);
            //java.util.Date dateField;
            //String otherField;
            //Float floatField;
            String listQuery = "Select title from GAME";
            PreparedStatement listPreparedStatement = connection.prepareStatement(listQuery);

            ResultSet resultSet = listPreparedStatement.executeQuery();


            while (resultSet.next()) {

                listOfTitles.add(resultSet.getString("title"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listOfTitles;
    }
}