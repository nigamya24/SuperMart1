package SuperMart;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class Placeorder implements Initializable {

    Parent root;
    Stage stage;
    Scene scene;

    @FXML
    private Label date;

    @FXML
    private Label time;

    @FXML
    private Button gobackbt;

    @FXML
    private Button plaveorderbt;

    @FXML
    private TextField vendorname;

    @FXML
    private TextField vendorcategory;

    @FXML
    private TextField vendorcontact;

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
            clrdb();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void setname(String t, String t1, String t2){

        vendorname.setText(t);
        vendorcategory.setText(t1);
        vendorcontact.setText(t2);
    }

    public void goback(javafx.event.ActionEvent actionEvent) throws IOException {

        root = FXMLLoader.load(getClass().getResource("Vendor.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void place(javafx.event.ActionEvent actionEvent) {

        JOptionPane.showMessageDialog(null, "Order Placed with Vendor, Confirm with the Vendor");
    }

    @FXML
    private Button addprdbt;

    @FXML
    private Button removeprdbt;

    @FXML
    private TextField prdname;

    @FXML
    private TextField prdquan;

    @FXML
    private TableView<Placeordertable> prdtable;

    @FXML
    private TableColumn<Placeordertable, String> prddetailtable;

    @FXML
    private TableColumn<Placeordertable, String> prdquantable;

    ObservableList<Placeordertable> List1 = FXCollections.observableArrayList();

    public void updatetable() throws ClassNotFoundException, SQLException {

        prdtable.getItems().clear();

        prddetailtable.setCellValueFactory(new PropertyValueFactory<>("ProductDetail"));
        prdquantable.setCellValueFactory(new PropertyValueFactory<>("Quantity"));

        Class.forName("com.mysql.jdbc.Driver");
        Connection c1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/vendor","root","");
        PreparedStatement ps1 = c1.prepareStatement("SELECT * FROM placeorder1");
        ResultSet rs1 = ps1.executeQuery();
        while (rs1.next()){
            List1.add(new Placeordertable(rs1.getString("ProductDetail")
            , rs1.getString("Quantity")));
            prdtable.setItems(List1);
        }

    }

    public void addprd(javafx.event.ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String s1 = prdname.getText();
        String s2 = prdquan.getText();

        Class.forName("com.mysql.jdbc.Driver");
        Connection c1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/vendor","root","");
        PreparedStatement p1 = c1.prepareStatement("INSERT INTO placeorder1 (ProductDetail, Quantity) VALUES (?,?)");
        p1.setString(1, s1);
        p1.setString(2, s2);

        int s = p1.executeUpdate();
        if(s==1){
            System.out.println("Success");
        }
        else{
            System.out.println("Failed");
        }
        updatetable();
    }

    public void removeprd(javafx.event.ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String s1 = prdname.getText();

        Class.forName("com.mysql.jdbc.Driver");
        Connection c1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/vendor","root","");
        PreparedStatement p1 = c1.prepareStatement("DELETE FROM placeorder1 where ProductDetail=?");
        p1.setString(1, s1);
        p1.executeUpdate();
        updatetable();
    }

    public void clrdb() throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.jdbc.Driver");
        Connection connn1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/vendor","root","");
        PreparedStatement ps1 = connn1.prepareStatement("DELETE FROM placeorder1");
        ps1.executeUpdate();

    }
}
