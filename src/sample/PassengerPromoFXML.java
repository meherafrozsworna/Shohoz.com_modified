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
import static sample.Main.object;

public class PassengerPromoFXML implements Initializable {
    public AnchorPane passengerPromoPane;
    public TableView<passengerPromo> tableField;
    public TableColumn<passengerPromo,String> nameField;
    public TableColumn<passengerPromo,String> homeAddressField;
    public JFXButton backButton;
    public JFXTextArea textArea;

    public void backButtonAction(ActionEvent actionEvent) {
        try {
            System.out.println("login ");
            AnchorPane pane = FXMLLoader.load(getClass().getResource("query.fxml"));
            passengerPromoPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        object.passenger_using_promo() ;
        textArea.setText("SELECT  Public.\\\"Passenger\\\".name,Public.\\\"Passenger\\\".home_address\\n\" +\n" +
                "        \"FROM Public.\\\"Promo\\\",Public.\\\"Passenger\\\"\\n\" +\n" +
                "        \"where  Public.\\\"Promo\\\".passenger_id=Public.\\\"Passenger\\\".passenger_id ;");
        //table view
        nameField.setCellValueFactory(new PropertyValueFactory<passengerPromo, String>("name"));
        homeAddressField.setCellValueFactory(new PropertyValueFactory<passengerPromo, String>("address"));
        tableField.setItems(getMember());
    }

    public ObservableList<passengerPromo> getMember()
    {
        ObservableList<passengerPromo> ride = FXCollections.observableArrayList();
        for (int i = 0; i < passengerPromoList.size() ; i++) {
            ride.add(passengerPromoList.get(i)) ;
        }
        return ride;
    }
}
