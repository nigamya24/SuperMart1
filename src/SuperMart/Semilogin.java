package SuperMart;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import java.util.ResourceBundle;

public class Semilogin implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private Label date;

    @FXML
    private Label time;

    @FXML
    private Button manageinventory;

    @FXML
    private Button manageempdetails;

    @FXML
    private Button goback;

    @FXML
    private TextField totalsale;

    @FXML
    private DatePicker attendate;

    @FXML
    void goback(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

    @FXML
    void manageempdetails(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("EmpDetails.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void manageinventory(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("Inventory.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
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

        try {
            tsale();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    String s;

        public void tsale() throws SQLException {

        LocalDate ld = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/YYYY", Locale.getDefault());
        String s1 = format.format(ld);

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice","root","India@321");
        PreparedStatement ps4 = connection.prepareStatement("SELECT SUM(Amount) FROM invoice1 WHERE Billdate=?");
        ps4.setString(1, s1);

        ResultSet rs4 = ps4.executeQuery();

        while (rs4.next()){
            s = rs4.getString("SUM(Amount)");
            totalsale.setText(s);
        }

    }

    @FXML
    void seevendors(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("Vendor.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    String a= null;
    public void alert() throws ClassNotFoundException, SQLException {

            Class.forName("com.mysql.jdbc.Driver");
            Connection c1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory","root","India@321");
            PreparedStatement p1 = c1.prepareStatement("SELECT * FROM inventory1");
            ResultSet r1 = p1.executeQuery();
            while(r1.next()){
                a = r1.getString("Quantity");
                int a1 = Integer.parseInt(a);
                if(a1<10){
                    JOptionPane.showMessageDialog(null, "Low stock of "+r1.getString("ProductID"));
                }
                else{
                    JOptionPane.showMessageDialog(null, "No shortage for stock of "+r1.getString("ProductID"));
                }

        }
    }

    @FXML
    void checkinventory(ActionEvent event) throws SQLException, ClassNotFoundException {

        alert();

    }


    @FXML
    void getattendance(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("AttendanceCheck.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void seefeedback(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("SeeFeedback.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
