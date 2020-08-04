package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import connect_postgres.Shohoz_DbImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static sample.Main.object;

public class Login implements Initializable {

    //public static Shohoz_DbImpl object ;

    public AnchorPane loginPane;
    public JFXTextField phone_id;
    public JFXPasswordField password;
    public JFXButton loginButton;
    public JFXButton back_button;
    public String phone ;
    public String pass ;
    public TextArea loginCode;

    public void loginAction(ActionEvent actionEvent) {
        phone = phone_id.getText() ;
        pass = password.getText() ;
        System.out.println(phone+ "  " + pass);
        //object = new Shohoz_DbImpl() ;
        //object.connect();
        int passengerId = object.login_db(phone,pass) ;
        if( passengerId != 0)
        {
            object.ShowRideList() ;

            try {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("afterLogin.fxml"));
                loginPane.getChildren().setAll(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public void BackAction(ActionEvent actionEvent) {
        try {
            System.out.println("login ");
            AnchorPane pane = FXMLLoader.load(getClass().getResource("login_or_signup.fxml"));
            loginPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginCode.setText("SELECT password into passd\n" +
                "   FROM Public.\"Passenger\"\n" +
                "   where phone_number=phoneno;\n" +
                "   checkp=0;\n" +
                "   SELECT passenger_id into npassid\n" +
                "   FROM Public.\"Passenger\"\n" +
                "   where phone_number=phoneno;\n" +
                "   ");
    }
}
