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
import static connect_postgres.Shohoz_DbImpl.tripTableList;
import static sample.Main.object;

public class TripTrigFXML implements Initializable{

    public TableView<tripTable> tableField;
    public TableColumn<tripTable,Boolean> is_acceptedField;
    public TableColumn<tripTable,String> trip_id_field;
    public TableColumn<tripTable,String> payment_idField;
    public TableColumn<tripTable,Integer> driver_id_field;
    public JFXTextArea text_area;
    public JFXTextArea trigFunctionField;
    public JFXButton backButton;
    public AnchorPane trigAcceptPane;

    public void backButtonAction(ActionEvent actionEvent) {
        try {
            System.out.println("login ");
            AnchorPane pane = FXMLLoader.load(getClass().getResource("currentTrip.fxml"));
            trigAcceptPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        object.Trip_trig_print();
        text_area.setText("select * from public.\\\"Trip\\\"\\n\" +\n" +
                "           \"where trip_id = '\"+ trip_id  ");

        trigFunctionField.setText("DECLARE\n" +
                "ndriverid INTEGER;\n" +
                "   \n" +
                "BEGIN\n" +
                "   \n" +
                "  IF (TG_OP = 'UPDATE') THEN\n" +
                "   ndriverid= NEW.driver_id;\n" +
                "   UPDATE Public.\"Trip\" SET is_accepted='true'\n" +
                "   WHERE driver_id=ndriverid;\n" +
                "    RETURN NEW;\n" +
                "         \n" +
                " END IF;\n" +
                "      RETURN NULL;\n" +
                "END;\n");

        //table view
        is_acceptedField.setCellValueFactory(new PropertyValueFactory<tripTable, Boolean>("is_accepted"));
        trip_id_field.setCellValueFactory(new PropertyValueFactory<tripTable, String>("trip_id"));
        payment_idField.setCellValueFactory(new PropertyValueFactory<tripTable, String>("payment_id"));
        driver_id_field.setCellValueFactory(new PropertyValueFactory<tripTable, Integer>("driver_id"));

        tableField.setItems(getMember());

    }

    public ObservableList<tripTable> getMember()
    {
        ObservableList<tripTable> ride = FXCollections.observableArrayList();
        for (int i = 0; i < tripTableList.size() ; i++) {
            ride.add(tripTableList.get(i)) ;
        }
        return ride;
    }

}
