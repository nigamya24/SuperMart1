package SuperMart;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Checkout implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label date;

    @FXML
    private Label time;

    @FXML
    private Button goback;

    @FXML
    private TextField billprdid;

    @FXML
    private TextField billquan;

    @FXML
    private Button addbt;

    @FXML
    private Button removebt;

    @FXML
    private Button pay;

    @FXML
    private TextField totalarea;


    @FXML
    private TableView<CheckoutTable> billtable;

    @FXML
    private TableColumn<CheckoutTable, String> prodidcol;

    @FXML
    private TableColumn<CheckoutTable, String> quancol;

    @FXML
    private TableColumn<CheckoutTable, String> pricecol;

    @FXML
    private TableColumn<CheckoutTable, String> totalcol;

    String Totalarea;
    int total1=0;


    @FXML
    void addtobill(ActionEvent event) throws SQLException, ClassNotFoundException {

        show();

    }

    @FXML
    void goback(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Homepage1.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void paybill(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Checkout2.fxml"));
        Parent root = (Parent) loader.load();

        Checkout2 ct2 = loader.getController();
        ct2.billtext(totalarea.getText());

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        Random r = new Random();
        int n = r.nextInt(100000);
        n += 1;

        ct2.invoicetext(String.valueOf(n));


    }

    @FXML
    void removefrombill(ActionEvent event) throws SQLException, ClassNotFoundException {

        del();
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
            clrdb();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        billquan.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    billquan.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        billprdid.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    billprdid.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });


    }

    ObservableList<CheckoutTable> table = FXCollections.observableArrayList();

    int t4;
    String t3 =null;
    String t1 = null;
    String t2 = null;

    public void show() throws SQLException, ClassNotFoundException {

        t1 = billprdid.getText();
        t2 = billquan.getText();


        Class.forName("com.mysql.jdbc.Driver");
        Connection conn1= DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory","root","");
        PreparedStatement ps4 = conn1.prepareStatement("SELECT * FROM inventory.inventory1 WHERE ProductID=?");
        ps4.setString(1, t1);
        ResultSet rs4 = ps4.executeQuery();
        if(rs4.next()) {
            t3 = rs4.getString("Price");
            t4 = Integer.parseInt(t3) * Integer.parseInt(t2);
            String t5 = String.valueOf(t4);
            CheckoutTable ct = new CheckoutTable(t1, t2, t3, t5);
            billtable.getItems().add(ct);
            total1 = total1+t4;
            Totalarea = ""+total1;
            totalarea.setText(Totalarea);
            clear();

            Class.forName("com.mysql.jdbc.Driver");
            Connection connn1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/bill","root","");
            PreparedStatement ps1 = connn1.prepareStatement("INSERT INTO newbill (ProductID, Quantity, Price, Total) VALUES (?,?,?,?)");

            ps1.setString(1, t1);
            ps1.setString(2, t2);
            ps1.setString(3, t3);
            ps1.setString(4, String.valueOf(t4));

            int stat = ps1.executeUpdate();

            updatetable1();
            updatedb();
            updatebd1();

        }
        else{
            JOptionPane.showMessageDialog(null, "Wrong Product ID");
        }


    }


    String tt= null;
    int tt1;
    public void del() throws ClassNotFoundException, SQLException {

        CheckoutTable ct = billtable.getSelectionModel().getSelectedItem();

        String t1 = billprdid.getText();
        String t2 = billquan.getText();


        Class.forName("com.mysql.jdbc.Driver");
        Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/bill","root","");
        PreparedStatement ps1 = con1.prepareStatement("SELECT * FROM newbill WHERE ProductID=?");
        ps1.setString(1, t1);
        ResultSet rs2 = ps1.executeQuery();
        while(rs2.next()){

            tt = rs2.getString("Total");
            tt1 = Integer.parseInt(tt);

        }

        total1 = total1 - tt1;
        Totalarea = ""+total1;
        totalarea.setText(Totalarea);

        Class.forName("com.mysql.jdbc.Driver");
        Connection con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/bill","root","");
        PreparedStatement ps2 = con1.prepareStatement("DELETE FROM newbill WHERE ProductID=?");
        ps2.setString(1, t1);
        int stat = ps2.executeUpdate();

        updatetable1();
        updatedb();
        updatedb2();

        }

        public void clear(){

        billprdid.clear();
        billquan.clear();

        }

        public void clrdb() throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.jdbc.Driver");
        Connection connn1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/bill","root","");
        PreparedStatement ps1 = connn1.prepareStatement("DELETE FROM newbill");
        ps1.executeUpdate();

    }

    int redquan;
    String redquan1=null;
    String redqaun2=null;
    int s1;

    public void updatedb() throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.jdbc.Driver");
        Connection c1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory","root","");
        PreparedStatement ps2 = c1.prepareStatement("SELECT * FROM inventory.inventory1 WHERE ProductID=?");
        ps2.setString(1, t1);
        ResultSet rs2 = ps2.executeQuery();

        while(rs2.next()){

            redquan1 = rs2.getString("Quantity");
            s1=Integer.parseInt(redquan1);

        }

    }

    public void updatebd1() throws SQLException {

        redquan = s1 - Integer.parseInt(t2);
        redqaun2 = String.valueOf(redquan);

        Connection c2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory","root","");
        PreparedStatement ps3 = c2.prepareStatement("UPDATE inventory.inventory1 SET Quantity=? WHERE ProductID=?");
        ps3.setString(1, redqaun2);
        ps3.setString(2, t1);
        int stat = ps3.executeUpdate();
         /*if(stat==1){
             JOptionPane.showMessageDialog(null,"Inventory Updated");
         }
         else{
             JOptionPane.showMessageDialog(null, "Inventory Update Failed");
         }*/


    }

    public void updatedb2() throws ClassNotFoundException, SQLException {

        String t1 = billprdid.getText();
        String t2 = billquan.getText();

        int s = Integer.parseInt(t2) + redquan;
        String s1 = String.valueOf(s);
        Class.forName("com.mysql.jdbc.Driver");
        Connection con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory","root","");
        PreparedStatement ps2 = con2.prepareStatement("UPDATE inventory1 SET Quantity=? WHERE ProductID=?");
        ps2.setString(1, s1);
        ps2.setString(2, t1);
        int stat1 = ps2.executeUpdate();
        /*if(stat1==1){
            JOptionPane.showMessageDialog(null, "Deletion Successful and quantity added back to inventory");
        }
        else{
            JOptionPane.showMessageDialog(null, "Deletion failed and adding back to inventory failed");
        }
        System.out.println(redquan);
        System.out.println(t1);
        System.out.println(s);
        System.out.println(s1);*/


    }

    public void updatetable1(){

        billtable.getItems().clear();

        prodidcol.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
        quancol.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        pricecol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        totalcol.setCellValueFactory(new PropertyValueFactory<>("Total"));
        //billtable.setItems(table);

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bill","root", "");
            String query = "SELECT * From newbill";
            Statement ps4 = connection.createStatement();
            ResultSet rs5 = ps4.executeQuery(query);
            while(rs5.next()){
                table.add(new CheckoutTable(rs5.getString("ProductID"), rs5.getString("Quantity"), rs5.getString("Price"),
                        rs5.getString("Total")));
                billtable.setItems(table);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }



}

