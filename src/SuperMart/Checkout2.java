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
import javafx.geometry.Pos;
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
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class Checkout2 implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label date;

    @FXML
    private Label time;

    @FXML
    private TextArea billarea;

    @FXML
    private TextField billtotal;

    @FXML
    private Label BillLabel;

    @FXML
    private TextField billcashpaid;

    @FXML
    private TextField billchange;

    @FXML
    private TextField billinvoice;

    @FXML
    private Button paymentdone;

    @FXML
    private Button goback;

    @FXML
    private TextField memship1;


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
            showtable();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        billcashpaid.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    billcashpaid.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

    }

    String a1=null;

    public void billtext(String t) {
        billtotal.setText(t);
        a1 = t;
    }

    public void invoicetext(String t1){
        billinvoice.setText(t1);
    }

    @FXML
    void goback(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("Checkout.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void paymentdone(ActionEvent event) throws SQLException, ClassNotFoundException {
        pay();
        addinvoice();
    }


    @FXML
    void paymentconfirm(ActionEvent event) throws IOException {

        int input = JOptionPane.showOptionDialog(null, "Payment Confirmed","Payment" , JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
        if(input == JOptionPane.OK_OPTION){

            Parent root = FXMLLoader.load(getClass().getResource("Checkout.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else if(input == JOptionPane.CANCEL_OPTION){
            JOptionPane.showMessageDialog(null, "Correct the Payment Information");
        }
    }

    public void pay(){


        int t = Integer.parseInt(billtotal.getText());
        int t1 = Integer.parseInt(billcashpaid.getText());
        int t2 = t1 - t;

        billchange.setText(String.valueOf(t2));
    }

    public void addinvoice() throws ClassNotFoundException, SQLException {

        String t1 = billinvoice.getText();
        String t2 = billtotal.getText();
        String t3 = billcashpaid.getText();
        String t4 = billchange.getText();

        java.util.Date d1 = new java.util.Date();
        java.sql.Timestamp t5 = new java.sql.Timestamp(d1.getTime());

        LocalDate ld = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/YYYY", Locale.getDefault());
        String s1 = format.format(ld);

        Class.forName("com.mysql.jdbc.Driver");
        Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice","root","");

        PreparedStatement p1 = con1.prepareStatement("INSERT INTO invoice.invoice1 (InvoiceNo, Amount, Cash, Remaining, Billdateandtime,BillDate) VALUES (?,?,?,?,?,?)");

        p1.setString(1, t1);
        p1.setString(2, t2);
        p1.setString(3, t3);
        p1.setString(4, t4);
        p1.setString(5, String.valueOf(t5));
        p1.setString(6, s1);

        int s = p1.executeUpdate();

    }

    public void showtable() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/bill","root","");
        Statement s1 = con1.createStatement();
        ResultSet rs = s1.executeQuery("SELECT * FROM newbill");


        while (rs.next()){
            billarea.appendText("\n\n"+rs.getString(1)+"\t\t\t   "+rs.getString(2)+"\t\t\t "+rs.getString(3)+"       \t\t"+rs.getString(4));
        }
    }

    @FXML
    void checkmembership(ActionEvent event) throws ClassNotFoundException, SQLException {

        String t1 = memship1.getText();

        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/member","root","");
        PreparedStatement p1 = con.prepareStatement("SELECT * FROM membership WHERE MembershipNumber=?");
        p1.setString(1, t1);
        ResultSet rs = p1.executeQuery();
        if(rs.next()){
            String a2 = rs.getString("Status");
            if(a2.equals("Active")){
                double a3 = Integer.parseInt(a1)-(Integer.parseInt(a1)*0.05);
                int a4 = (int) Math.round(a3);
                billtotal.setText(String.valueOf(a4));
                JOptionPane.showMessageDialog(null, "You get a Discount of 5%");
            }
            else{
                JOptionPane.showMessageDialog(null, "Your Membership is Inactive.\n Contact a Cashier");
            }

        }
        else{
            JOptionPane.showMessageDialog(null, "Invalid Membership Number\nOR\nYou are not a Member");
        }

    }

}
