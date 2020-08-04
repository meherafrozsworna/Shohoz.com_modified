package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static sample.Main.object;

public class Query implements Initializable{
    public AnchorPane queryPane;
    public Label carTripfield;
    public Label motortripField;
    public JFXButton carTripButton;
    public JFXButton tripPromoCodeButton;
    public JFXButton driverPromoCodeButton;
    public JFXButton userPromoCodeButton;
    public JFXButton motorTripButton;
    public JFXButton backButton;
    public JFXTextArea textArea;

    public void carTripButtonAction(ActionEvent actionEvent) {
        int trips = object.Count_carTrips() ;
        carTripfield.setText("Total car Trips : "+ trips );
        textArea.setText("\"\" +\n" +
                "          \"SELECT COUNT(*) as cartrips\\n\" +\n" +
                "          \"FROM Public.\\\"Trip\\\"\\n\" +\n" +
                "          \"where Public.\\\"Trip\\\".trip_id LIKE'c%'\"");
    }

    public void tripPromoCodeButtonAction(ActionEvent actionEvent) {
        try {
            System.out.println("login ");
            AnchorPane pane = FXMLLoader.load(getClass().getResource("tripPromoFXML.fxml"));
            queryPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void driverPromoCodeButtonAction(ActionEvent actionEvent) {
        try {
            System.out.println("login ");
            AnchorPane pane = FXMLLoader.load(getClass().getResource("DriverPromoFXML.fxml"));
            queryPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void userPromoCodeButtonAction(ActionEvent actionEvent) {
        try {
            System.out.println("login ");
            AnchorPane pane = FXMLLoader.load(getClass().getResource("passengerPromoFXML.fxml"));
            queryPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void motorTripButtonAction(ActionEvent actionEvent) {
        int trips = object.Count_motorTrips() ;
        motortripField.setText("Total car Trips : "+ trips );
        textArea.setText("\"SELECT COUNT(*) as motortrips\\n\" +\n" +
                "         \"FROM Public.\\\"Trip\\\"\\n\" +\n" +
                "         \"where Public.\\\"Trip\\\".trip_id LIKE'm%'\"");
    }

    public void backButtonAction(ActionEvent actionEvent) {
        try {
            System.out.println("login ");
            AnchorPane pane = FXMLLoader.load(getClass().getResource("afterLogin.fxml"));
            queryPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
