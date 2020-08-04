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

import static connect_postgres.Shohoz_DbImpl.*;
import static sample.Main.object;
import static sample.RequestSent.destination;

public class CurrentTrip implements Initializable{
    public AnchorPane currentTripPane;
    public Label locationField;
    //public Label elapsedTimeField;
    public Label elapsedDistancreField;
    public JFXButton backButton;
    public JFXButton UpdateButton;

    public static int count_trip = 0 ;
    public JFXTextArea textfield;
    public JFXButton currentTripTrigButton;
    public JFXButton acceptTrigButton;

    public void backButtonAction(ActionEvent actionEvent) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("afterrequest.fxml"));
            currentTripPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void UpdateButtonAction(ActionEvent actionEvent) {
        count_trip++ ;
        object.Update_current_trip();
        locationField.setText(path_list.get(count_trip));
        String str = (int) distance[path_id_list.get(count_trip)] + "" ;
        elapsedDistancreField.setText(str);

        rideList.clear();
        object.ShowRideList() ;

        if(destination.equals(locationField.getText()))
        {
            try {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("RideFinish.fxml"));
                currentTripPane.getChildren().setAll(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textfield.setText("DECLARE\n" +
                "elptime INTEGER;\n" +
                "t INTEGER;\n" +
                "ndest character varying;\n" +
                "ncr character varying;\n" +
                "nsrc character varying;\n" +
                "nstart timestamp without time zone;\n" +
                "npay character varying;\n" +
                "npas INTEGER;\n" +
                "ndriver INTEGER;\n" +
                "\n" +
                "BEGIN\n" +
                " t=1;\n" +
                " elptime=(elapseddistance*500)/60;\n" +
                "  UPDATE Public.\"Current_trip\" SET elapsed_time = elptime\n" +
                "  WHERE trip_id = tripid;\n" +
                "  UPDATE Public.\"Current_trip\" SET elapsed_distance= elapseddistance\n" +
                "  WHERE trip_id = tripid;\n" +
                "   UPDATE Public.\"Current_trip\" SET current_location= currentlocation\n" +
                "  WHERE trip_id = tripid;\n" +
                "    select passenger_id into npas\n" +
                "    from Public.\"Request\"\n" +
                "    WHERE trip_id=tripid;\n" +
                " UPDATE Public.\"Passenger\" SET current_location= currentlocation\n" +
                "  WHERE passenger_id = npas;\n" +
                "    select driver_id into ndriver\n" +
                "    from Public.\"Trip\"\n" +
                "    WHERE trip_id=tripid;\n" +
                " UPDATE Public.\"Driver\" SET current_location= currentlocation\n" +
                "  WHERE driver_id = ndriver;\n" +
                "  \n" +
                "  ncr=currentlocation;\n" +
                "  select destination into ndest\n" +
                "  from Public.\"Request\"\n" +
                "  WHERE trip_id=tripid;\n" +
                "  if(ncr=ndest) then\n" +
                "     select source into nsrc\n" +
                "     from Public.\"Request\"\n" +
                "     WHERE trip_id=tripid;\n" +
                "\t select start_time into nstart\n" +
                "     from Public.\"Current_trip\"\n" +
                "     WHERE trip_id=tripid;\n" +
                "    INSERT INTO Public.\"Ride_record\" VALUES(nsrc,ndest,tripid,elapseddistance,nstart,CURRENT_TIMESTAMP);\n" +
                "\tUPDATE Public.\"Passenger\" SET total_ride= total_ride+1\n" +
                "    WHERE passenger_id = npas;\n" +
                "\tselect  payment_id into npay\n" +
                "    from Public.\"Trip\"\n" +
                "    WHERE trip_id=tripid;\n" +
                "\tUPDATE Public.\"Payment\" SET given = 'true'\n" +
                "    WHERE payment_id = npay;\n" +
                "\t\n" +
                " END IF;\n" +
                "   \n" +
                "     \n" +
                "      \n" +
                " return t;     \n" +
                " \n" +
                "END;\n");
        locationField.setText(path_list.get(0));
        String str = (int) distance[path_id_list.get(0)] + "" ;
        elapsedDistancreField.setText(str);
    }

    public void currentTripTrigButtonAction(ActionEvent actionEvent) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("currentTripInsertFXML.fxml"));
            currentTripPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void acceptTrigButtonAction(ActionEvent actionEvent) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("TripTrigFXML.fxml"));
            currentTripPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
