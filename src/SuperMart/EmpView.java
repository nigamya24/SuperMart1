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
import javafx.scene.control.*;
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

public class EmpView implements Initializable {

    Stage stage;
    Scene scene;
    Parent root;

    @FXML
    private RadioButton present;

    @FXML
    private RadioButton onleave;

    @FXML
    private Label date;

    @FXML
    private Label time;

    @FXML
    private TextField leavereason;

    @FXML
    private Button markattancebt;

    @FXML
    private Label person;

    @FXML
    private Button gtcheckout;

    @FXML
    void goback(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void gotocheckout(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("Checkout.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    String a=null;
    String t1 = null;
    @FXML
    void markattendance(ActionEvent event) throws ClassNotFoundException, SQLException {

        if(present.isSelected()){
            t1 = "Present";
        }
        else{
            t1 = "On Leave";
        }


        empid();

        java.util.Date d1 = new java.util.Date();
        java.sql.Timestamp t5 = new java.sql.Timestamp(d1.getTime());

        LocalDate ld = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("YYYY-MM-dd", Locale.getDefault());
        String s1 = format.format(ld);

        Class.forName("com.mysql.jdbc.Driver");
        Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance","root","India@321");
        PreparedStatement ps1 = con1.prepareStatement("INSERT INTO attandance1 (EmpID, Name, Status,DatenTime,Reason,Date) VALUES (?,?,?,?,?,?)");

        ps1.setString(1,String.valueOf(a) );
        ps1.setString(2,person.getText());
        ps1.setString(3,t1);
        ps1.setString(4,String.valueOf(t5));
        ps1.setString(5,leavereason.getText());
        ps1.setString(6, s1);

        int rs = ps1.executeUpdate();
        JOptionPane.showMessageDialog(null, "Attendance Submitted");
        gtcheckout.setDisable(false);
        markattancebt.setDisable(true);

    }



    public void empid() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        Connection conn1= DriverManager.getConnection("jdbc:mysql://localhost:3306/employee","root","India@321");
        PreparedStatement ps4 = conn1.prepareStatement("SELECT * FROM employee1 WHERE Name=?");
        ps4.setString(1, person.getText());
        ResultSet rs4 = ps4.executeQuery();
        while (rs4.next()){
            a = rs4.getString("EmpID");
        }


    }
    public void name(String t) throws ClassNotFoundException, SQLException {
        person.setText(t);
    }


    @FXML
    void mkattendance(ActionEvent event) {

        if(onleave.isSelected()) {

            leavereason.setDisable(false);
            markattancebt.setDisable(false);
            JOptionPane.showMessageDialog(null, "Give the reason for leave");
        }
        else{
            markattancebt.setDisable(false);
            leavereason.setDisable(true);
        }

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

        final ToggleGroup g1 = new ToggleGroup();
        present.setToggleGroup(g1);
        onleave.setToggleGroup(g1);

        leavereason.setDisable(true);
        gtcheckout.setDisable(true);
        markattancebt.setDisable(true);

       }


}
