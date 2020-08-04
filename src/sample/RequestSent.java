package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static sample.Main.object;

public class RequestSent implements Initializable{
    public AnchorPane requestPane;
    public JFXTextField sourcefield;
    public JFXTextField typeField;
    public JFXTextField destinationField;
    public JFXButton backButton;
    public JFXButton sentreqButton;

    public String source ;
    public static String destination ;
    public String type ;
    public JFXTextArea textArea;

    public void backButtonAction(ActionEvent actionEvent) {
        try {
            System.out.println("login ");
            AnchorPane pane = FXMLLoader.load(getClass().getResource("afterLogin.fxml"));
            requestPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sentreqButtonAction(ActionEvent actionEvent) {
        source = sourcefield.getText() ;
        destination = destinationField.getText() ;
        type = typeField.getText() ;
        object.Load_Graph_AND_Request(source,destination,type);
        try {
            System.out.println("login ");
            AnchorPane pane = FXMLLoader.load(getClass().getResource("afterrequest.fxml"));
            requestPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textArea.setText("Declare\n" +
                "\ttotal character varying;\n" +
                "\tcnt integer ;\n" +
                "\trowCount integer ;\n" +
                "\tnewRowCount integer ;\n" +
                "\ttrip_id character varying ;\n" +
                "BEGIN\n" +
                "   SELECT count(*) into rowCount FROM public.\"Request\";\n" +
                "   cnt = rowCount + 1 ;\n" +
                "   if type = 'car' then\n" +
                "   \t\ttrip_id = 'c' || cnt ;\n" +
                "\telse\n" +
                "\t\ttrip_id = 'm'|| cnt ;\n" +
                "\tend if ;\n" +
                "   \n" +
                "   Insert into public.\"Request\"\n" +
                "   values(source,destination,type,trip_id,passenger_id);\n" +
                "   \n" +
                "   SELECT count(*) into newRowCount FROM public.\"Request\";\n" +
                "   \n" +
                "   total = 0 ;\n" +
                " if newRowCount = rowCount + 1 then\n" +
                "   \t total = trip_id ;\n" +
                " end if ; \n" +
                "   \n" +
                "   RETURN total ;\n" +
                "END;\n");
    }
}
