package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

import static sample.Main.object;

public class Afterrequest {
    public AnchorPane afterReqPane;
    public Label paymentField;
    public JFXButton paymentAmountButton;
    public JFXButton currentTripButton;
    public JFXButton DriverButton;
    public JFXButton RidePathButton;
    public JFXButton backButton;
    public JFXTextArea textArea;

    public void paymentAmountButton(ActionEvent actionEvent) {
        textArea.setText("istance INTEGER;\n" +
                "   npromo character varying;\n" +
                "   pr INTEGER;\n" +
                "   pas_id INTEGER;\n" +
                "   t INTEGER;\n" +
                "   base INTEGER;\n" +
                "\n" +
                "BEGIN\n" +
                "     t=1;\n" +
                "      SELECT payment_id into  npayment_id \n" +
                "\t  from Public.\"Trip\"\n" +
                "\t  where trip_id=ntrip;\n" +
                "\t  if(npayment_id like 'M%') then\n" +
                "\t      base=70;\n" +
                "\t   else\n" +
                "\t      base=100;\n" +
                "\t   END IF;\n" +
                "\t  total_pay = 0 ;\n" +
                "\t  total_pay=base+(ndist*10);\n" +
                "\t  select passenger_id into pas_id from Public.\"Request\" where trip_id=ntrip;\n" +
                "      select promo_code into npromo from Public.\"Promo\" where passenger_id=pas_id;\n" +
                "     if(npromo IS NULL) THEN\n" +
                "       pr=0;\n" +
                "     else\n" +
                "       select parcentage_of into pr from Public.\"Promo\" WHERE promo_code=npromo;\n" +
                "    END IF;\n" +
                "      total_pay=total_pay-(total_pay*(pr/10));\n" +
                "   \n" +
                " INSERT INTO Public.\"Payment\"\n" +
                " VALUES(npayment_id,base,'cash',2,'false',4,total_pay,npromo);\n" +
                "     \n" +
                " \n" +
                " \n" +
                "    RETURN total_pay;\n" +
                "END;");
        double amount = object.paymentAmount() ;
        paymentField.setText("Payment Amount : " + amount);
    }

    public void currentTripButtonAction(ActionEvent actionEvent) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("currentTrip.fxml"));
            afterReqPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void DriverButtonActiion(ActionEvent actionEvent) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("nearbyDriver.fxml"));
            afterReqPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void RidePathButtonAction(ActionEvent actionEvent) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("tripPath.fxml"));
            afterReqPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void backButtonAction(ActionEvent actionEvent) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("RequestSent.fxml"));
            afterReqPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
