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

import static connect_postgres.Shohoz_DbImpl.nearByDriverList;

public class NearbyDriver implements Initializable {
    public AnchorPane driverPane;
    public TableView<DriverInfo> tableField;
    public TableColumn<DriverInfo,String> nameField;
    public TableColumn<DriverInfo,String> currentLocationField;
    public JFXButton backButton;
    public JFXTextArea textArea;

    public void backButtonAction(ActionEvent actionEvent) {
        try {
            System.out.println("login ");
            AnchorPane pane = FXMLLoader.load(getClass().getResource("afterrequest.fxml"));
            driverPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        textArea.setText("\"select public.\\\"Driver\\\".name ,public.\\\"Driver\\\".current_location , public.\\\"Driver\\\".driver_id\\n\" +\n" +
                "         \"from public.\\\"Driver\\\",public.\\\"Vehicle\\\"\\n\" +\n" +
                "         \"where\\t \" + \" ( \" + str +\" ) \" +\n" +
                "         \"\\tAND (public.\\\"Driver\\\".driver_id=public.\\\"Vehicle\\\".driver_id)\\n\" +\n" +
                          "\\tAND (public.\\\"Vehicle\\\".type='car')\n" +
                        " \""+
                "         \n\n\n" +
                " Driver Accept Function : \n" +
                "         DECLARE\n" +
                " t INTEGER;\n" +
                " ntr character varying;\n" +
                " nac boolean;\n" +
                "npay character varying;\n" +
                "BEGIN\n" +
                "  t=1;\n" +
                " SELECT trip_id into ntr\n" +
                " FROM Public.\"Trip\"\n" +
                " WHERE trip_id=ntripid;\n" +
                " \n" +
                "  UPDATE Public.\"Trip\" SET driver_id=driverid\n" +
                "  WHERE trip_id = ntr;\n" +
                "  return t;\n" +
                "END;");
        //table view
        nameField.setCellValueFactory(new PropertyValueFactory<DriverInfo, String>("name"));
        currentLocationField.setCellValueFactory(new PropertyValueFactory<DriverInfo, String>("location"));

        tableField.setItems(getMember());
    }

    public ObservableList<DriverInfo> getMember()
    {
        ObservableList<DriverInfo> driver = FXCollections.observableArrayList();
        for (int i = 0; i < nearByDriverList.size() ; i++) {
            driver.add(nearByDriverList.get(i)) ;
        }
        return driver;

    }
}
