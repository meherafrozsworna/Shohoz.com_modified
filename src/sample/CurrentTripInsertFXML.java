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
import java.sql.Timestamp;
import java.util.ResourceBundle;

import static connect_postgres.Shohoz_DbImpl.currentTripInsertList;
import static connect_postgres.Shohoz_DbImpl.nearByDriverList;
import static sample.Main.object;

public class CurrentTripInsertFXML implements Initializable{
    public TableView<current_trip_insert> tableField;
    public TableColumn<current_trip_insert,String> trip_idField;
    public TableColumn<current_trip_insert,Timestamp> start_timeField;
    public TableColumn<current_trip_insert,String> elapsedTimeField;
    public TableColumn<current_trip_insert,Integer> distanceField;
    public TableColumn<current_trip_insert,String> locationField;
    public JFXTextArea textArea;
    public JFXTextArea trigFunctionArea;
    public JFXButton backButton;
    public AnchorPane currentTrigPane;

    public void backButtonAction(ActionEvent actionEvent) {
        try {
            System.out.println("login ");
            AnchorPane pane = FXMLLoader.load(getClass().getResource("currentTrip.fxml"));
            currentTrigPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        object.Current_insert_trip_print() ;
        textArea.setText("\"select * from public.\\\"Current_trip\\\"\\n\" +\n" +
                "         \"where trip_id = \"+ trip_id ");

        trigFunctionArea.setText("DECLARE\n" +
                " ndriverid INTEGER;\n" +
                " ntripid character varying;\n" +
                " nloc character varying;\n" +
                "BEGIN\n" +
                "  IF (TG_OP = 'UPDATE') THEN\n" +
                "   ndriverid= NEW.driver_id;\n" +
                "  --select trip_id  into ntripid\n" +
                "  --from Public.\"Trip\"\n" +
                " -- WHERE driver_id=ndriverid;\n" +
                " ntripid=OLD.trip_id;\n" +
                "  select source into nloc\n" +
                "  from Public.\"Request\"\n" +
                "  WHERE trip_id=ntripid;\n" +
                "  \n" +
                "   INSERT INTO Public.\"Current_trip\"(elapsed_time,trip_id,start_time,elapsed_distance,current_location)  VALUES(0,ntripid,CURRENT_TIMESTAMP,0,nloc);\n" +
                "   RETURN NEW;\n" +
                "END IF;\n" +
                "   RETURN NULL;\n" +
                "END;\n");
        //table view
        trip_idField.setCellValueFactory(new PropertyValueFactory<current_trip_insert, String>("trip_id"));
        start_timeField.setCellValueFactory(new PropertyValueFactory<current_trip_insert, Timestamp>("start_time"));
        elapsedTimeField.setCellValueFactory(new PropertyValueFactory<current_trip_insert, String>("elapsed_time"));
        distanceField.setCellValueFactory(new PropertyValueFactory<current_trip_insert, Integer>("elapsed_distance"));
        locationField.setCellValueFactory(new PropertyValueFactory<current_trip_insert, String>("current_location"));

        tableField.setItems(getMember());
    }

    public ObservableList<current_trip_insert> getMember()
    {
        ObservableList<current_trip_insert> driver = FXCollections.observableArrayList();
        for (int i = 0; i < currentTripInsertList.size() ; i++) {
            driver.add(currentTripInsertList.get(i)) ;
        }
        return driver;

    }
}
