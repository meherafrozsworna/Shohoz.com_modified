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

import static connect_postgres.Shohoz_DbImpl.passengerPromoList;
import static connect_postgres.Shohoz_DbImpl.tripPromoList;
import static sample.Main.object;

public class TripPromoFXML implements Initializable {
    public AnchorPane tripPromoPane;
    public TableView<tripPromo> tableField;
    public TableColumn<tripPromo,String> trip_idField;
    public JFXButton backButton;
    public JFXTextArea textArea;

    public void backButtonAction(ActionEvent actionEvent) {
        try {
            System.out.println("login ");
            AnchorPane pane = FXMLLoader.load(getClass().getResource("query.fxml"));
            tripPromoPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        object.trip_using_promo() ;
        textArea.setText("SELECT DISTINCT Public.\\\"Trip\\\".trip_id\\n\" +\n" +
                "         \"FROM Public.\\\"Promo\\\",Public.\\\"Payment\\\",Public.\\\"Trip\\\"\\n\" +\n" +
                "         \"WHERE (Public.\\\"Trip\\\".payment_id=Public.\\\"Payment\\\".payment_id) AND \\n\" +\n" +
                "         \"      (Public.\\\"Payment\\\".promo_code IS NOT NULL) ;");
        //table view
        trip_idField.setCellValueFactory(new PropertyValueFactory<tripPromo, String>("trip_id"));
        tableField.setItems(getMember());
    }

    public ObservableList<tripPromo> getMember()
    {
        ObservableList<tripPromo> ride = FXCollections.observableArrayList();
        for (int i = 0; i < tripPromoList.size() ; i++) {
            ride.add(tripPromoList.get(i)) ;
        }
        return ride;
    }
}
