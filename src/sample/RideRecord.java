package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static connect_postgres.Shohoz_DbImpl.rideList;


public class RideRecord implements Initializable {
    public AnchorPane RideRecordPane;
    public TableView<Rideinfo> recordTable;
    public TableColumn<Rideinfo,String> sourceField;
    public TableColumn<Rideinfo,String> destinationField;
    public TableColumn<Rideinfo,Double> distanceField;
    public TableColumn<Rideinfo,String> StartTimeField;
    public TableColumn<Rideinfo,String> endtimeField;
    public JFXButton backButton;
    public JFXTextArea textArea;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        textArea.setText("\" \n" +
                "           \"SELECT Public.\\\"Ride_record\\\".source, Public.\\\"Ride_record\\\".destination,\\n\" +\n" +
                "           \" Public.\\\"Ride_record\\\".total_distance,Public.\\\"Ride_record\\\".start_time,Public.\\\"Ride_record\\\".end_time\\n\" +\n" +
                "           \"FROM Public.\\\"Ride_record\\\",Public.\\\"Passenger\\\",Public.\\\"Request\\\"\\n\" +\n" +
                "           \"WHERE (Public.\\\"Ride_record\\\".trip_id=Public.\\\"Request\\\".trip_id) AND (Public.\\\"Request\\\".passenger_id=Public.\\\"Passenger\\\".passenger_id) AND\\n\" +\n" +
                "           \"   (Public.\\\"Passenger\\\".passenger_id=\"+ passenger_id +\")\"");
        //table view
        sourceField.setCellValueFactory(new PropertyValueFactory<Rideinfo, String>("source"));
        destinationField.setCellValueFactory(new PropertyValueFactory<Rideinfo, String>("destination"));
        distanceField.setCellValueFactory(new PropertyValueFactory<Rideinfo, Double>("distance"));
        StartTimeField.setCellValueFactory(new PropertyValueFactory<Rideinfo, String>("start_time"));
        endtimeField.setCellValueFactory(new PropertyValueFactory<Rideinfo, String>("end_time"));

        recordTable.setItems(getMember());

    }

    public ObservableList<Rideinfo> getMember()
    {
        ObservableList<Rideinfo> ride = FXCollections.observableArrayList();
        for (int i = 0; i < rideList.size() ; i++) {
            ride.add(rideList.get(i)) ;
        }
        return ride;
    }
    public void backButtonAction(ActionEvent actionEvent) {
        try {
            System.out.println("login ");
            AnchorPane pane = FXMLLoader.load(getClass().getResource("afterLogin.fxml"));
            RideRecordPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
