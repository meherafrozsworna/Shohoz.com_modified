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

import static connect_postgres.Shohoz_DbImpl.RidePath_from_s_to_d;

public class TripPath implements Initializable {
    public AnchorPane pathPane;
    public Label pathField;
    public JFXButton backButton;
    public JFXTextArea textArea;


    public void backButtonAction(ActionEvent actionEvent) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("afterrequest.fxml"));
            pathPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textArea.setText("DECLARE\n" +
                "t INTEGER;\n" +
                "BEGIN\n" +
                "   t=1;\n" +
                "   INSERT INTO Public.\"Ride_path\" values(tripid,vpath);  \n" +
                "   RETURN t;\n" +
                "END;\n");
        pathField.setText(RidePath_from_s_to_d);
    }
}
