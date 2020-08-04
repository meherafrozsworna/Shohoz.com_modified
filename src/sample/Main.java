package sample;

import connect_postgres.Shohoz_DbImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Shohoz_DbImpl object ;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("login_or_signup.fxml"));
        primaryStage.setTitle("Shohoz.com");
        primaryStage.setScene(new Scene(root, 700, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        object = new Shohoz_DbImpl() ;
        object.connect();

        launch(args);
    }
}
