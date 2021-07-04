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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class Membership implements Initializable {

    Parent root;
    Stage stage;
    Scene scene;

    @FXML
    private Label date;

    @FXML
    private Label time;

    @FXML
    private TextField memnum;

    @FXML
    private Label membershipname;

    @FXML
    private Label membershipstatus;

    @FXML
    private Button getdetbt;

    @FXML
    void getdetails(ActionEvent event) throws ClassNotFoundException, SQLException {

        String t1 = memnum.getText();

        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/member","root","India@321");
        PreparedStatement p1 = con.prepareStatement("SELECT * FROM membership WHERE MembershipNumber=?");
        p1.setString(1, t1);
        ResultSet rs = p1.executeQuery();
        if(rs.next()){
            String a = rs.getString("MemberName");
            String a1 = rs.getString("Status");
            membershipname.setText(a);
            membershipstatus.setText(a1);
        }
        else{
            JOptionPane.showMessageDialog(null, "Membership Details do not exist!");
        }

    }

    @FXML
    void goback(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("SelfCheckout.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Self-Checkout");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

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

        membershipname.setText("");
        membershipstatus.setText("");

        memnum.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    memnum.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }


}
