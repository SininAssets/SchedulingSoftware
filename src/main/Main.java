package main;

import Database.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    /**
     * This makes sure the program Actually Launches
     *
     * @param args
     */
    //public ObservableList<Appointments>
    public static void main(String[] args) {
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();

    }

    /**
     * This is where it all starts it sends User to Beginning Screen the Login Screen
     *
     * @param stage
     * @throws IOException
     */
    @Override
    //This Is here so The user can see the main screen
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("../View/LoginScreen1.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 520);
        stage.setTitle("Inventory Management System!");
        stage.setScene(scene);
        stage.show();
    }

}
