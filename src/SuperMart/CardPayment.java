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
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class CardPayment implements Initializable {

    Parent root;
    Stage stage;
    Scene scene;

    @FXML
    private Label BillLabel;

    @FXML
    private TextField uniquecode;

    @FXML
    private TextField billtotal;

    @FXML
    private TextField billinvoice;

    @FXML
    private Button confirmpaymentbt;

    @FXML
    private TextArea billarea;

    @FXML
    private Button printrecieptbt;

    @FXML
    private Label date;

    @FXML
    private Label time;

    @FXML
    private Button goback;

    @FXML
    private TextField memnum1;

    String a1 = null;

    public void text(String t, String t1){
        billtotal.setText(t);
        billinvoice.setText(t1);
        a1 = t;
    }

    String a=null;

    public void confirmpayment(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {

        String t1 = uniquecode.getText();

        Class.forName("com.mysql.jdbc.Driver");
        Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/cardauthenticate","root","");
        PreparedStatement ps1 = con1.prepareStatement("SELECT * FROM card1 WHERE UniqueCode=?");
        ps1.setString(1, t1);
        ResultSet rs1 = ps1.executeQuery();
        if(rs1.next()){
            a = rs1.getString("Amount");
            int a2 = Integer.parseInt(a);
            int b1 = Integer.parseInt(a1);
            if(a2==b1){
                JOptionPane.showMessageDialog(null, "Payment Received for Unique Code "+uniquecode.getText());
                printrecieptbt.setDisable(false);
            }

            else{
                JOptionPane.showMessageDialog(null, "Total Amount does not match the Recieved Amount");
            }

        }
        else {
            JOptionPane.showMessageDialog(null, "Payment not recieved yet!");
        }
        System.out.println(a);

    }

    public void printreciept(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {

        String t1 = billinvoice.getText();
        String t2 = billtotal.getText();
        String t3 = uniquecode.getText();

        java.util.Date d1 = new java.util.Date();
        java.sql.Timestamp t5 = new java.sql.Timestamp(d1.getTime());

        LocalDate ld = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/YYYY",Locale.getDefault());
        String s1 = format.format(ld);

        Class.forName("com.mysql.jdbc.Driver");
        Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice","root","");

        PreparedStatement p1 = con1.prepareStatement("INSERT INTO invoice.invoice2 (InvoiceNo, Amount, UniqueCode, Billdateandtime,BillDate) VALUES (?,?,?,?,?)");

        p1.setString(1, t1);
        p1.setString(2, t2);
        p1.setString(3, t3);
        p1.setString(4, String.valueOf(t5));
        p1.setString(5, s1);

        p1.executeUpdate();

        JOptionPane.showMessageDialog(null, "Printing Reciept");

        Parent root = FXMLLoader.load(getClass().getResource("SelfCheckout.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void goback(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("SelfCheckout.fxml"));
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

        printrecieptbt.setDisable(true);
        try {
            showtable();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        uniquecode.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    uniquecode.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

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

    int a4;

    @FXML
    void checkmemdet(ActionEvent event) throws ClassNotFoundException, SQLException {

        String t1 = memnum1.getText();

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

