package SuperMart;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class Feedback implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField FeedbackName;

    @FXML
    private TextField FeedbackNumber;

    @FXML
    private TextField FeedbackEmail;

    @FXML
    private TextArea FeedbackArea;

    @FXML
    private Button Submit;

    @FXML
    private Button GoBack;

    @FXML
    private Button clearbt;

    @FXML
    private Label date;

    @FXML
    private Label time;

    @FXML
    void GoBack(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("SelfCheckout.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void Submit(ActionEvent event) throws SQLException, ClassNotFoundException {

        String fdn=FeedbackName.getText();
        String fdn1=FeedbackNumber.getText();
        String fdn2=FeedbackEmail.getText();
        String fdn3=FeedbackArea.getText();

        Class.forName("com.mysql.jdbc.Driver");
        Connection conn2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/feedback","root","");

        PreparedStatement ps5 = conn2.prepareStatement("INSERT INTO feedback1 (Name,ContactNo, Email, Message) VALUES (?,?,?,?)");

        ps5.setString(1,fdn);
        ps5.setString(2,fdn1);
        ps5.setString(3,fdn2);
        ps5.setString(4,fdn3);

        int stat1 = ps5.executeUpdate();

        if(stat1 == 1){
            JOptionPane.showMessageDialog(null, "Adding successful");

        }
        else{
            JOptionPane.showMessageDialog(null, "Adding Failed");
        }

    }

    @FXML
    void clearbt(ActionEvent event) {

        FeedbackName.setText("");
        FeedbackNumber.setText("");
        FeedbackEmail.setText("");
        FeedbackArea.setText("");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Timeline currenttime = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime ct = LocalTime.now();
            time.setText(ct.getHour() + ":" + ct.getMinute() + ":" + ct.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        currenttime.setCycleCount(Animation.INDEFINITE);
        currenttime.play();

        LocalDate ld = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/YYYY"+", EEEE", Locale.getDefault());
        date.setText(format.format(ld));

        FeedbackNumber.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    FeedbackNumber.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }
}
