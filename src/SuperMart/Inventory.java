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
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class Inventory implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private TextField prdid;

    @FXML
    private TextField quantity;

    @FXML
    private TextField price;


    @FXML
    private TextField costprice;

    @FXML
    private TextField category;

    @FXML
    private Button GoBack;

    @FXML
    private Button Add;

    @FXML
    private Button Remove;

    @FXML
    private Button Update;

    @FXML
    private Button Clear;

    @FXML
    private TableView<InventoryTable> table1;

    @FXML
    private TableColumn<InventoryTable, Integer> Prdidcol;

    @FXML
    private TableColumn<InventoryTable, Integer> quancol;

    @FXML
    private TableColumn<InventoryTable, Integer> pricecol;

    @FXML
    private TableColumn<InventoryTable, Integer> costpricecol;

    @FXML
    private TableColumn<InventoryTable, String> categorycol;

    @FXML
    private Label date;

    @FXML
    private Label time;


    @FXML
    void InventoryGoback(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("Semilogin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void Addproduct(ActionEvent event) throws ClassNotFoundException, SQLException {

        String pr1 = prdid.getText();
        String q1 = quantity.getText();
        String pr2 = price.getText();
        String cp1 = costprice.getText();
        String ct1 = category.getText();

        Class.forName("com.mysql.jdbc.Driver");
        Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory","root","");

        PreparedStatement ps1 = con1.prepareStatement("INSERT INTO inventory1 (ProductID, Quantity, Price, CostPrice, Category) VALUES (?,?,?,?,?)");

        ps1.setString(1, pr1);
        ps1.setString(2,q1);
        ps1.setString(3,pr2);
        ps1.setString(4, cp1);
        ps1.setString(5, ct1);

        int stat = ps1.executeUpdate();

        if(stat == 1){
            JOptionPane.showMessageDialog(null, "Adding successful");
            table1.getItems().clear();
            updatetable();
        }
        else{
            JOptionPane.showMessageDialog(null, "Adding Failed");
        }

        prdid.clear();
        quantity.clear();
        price.clear();
        costprice.clear();



    }

    @FXML
    void Clearfields(ActionEvent event) {

        prdid.clear();
        quantity.clear();
        price.clear();
        costprice.clear();
        category.clear();

    }

    @FXML
    void Removeproduct(ActionEvent event) throws ClassNotFoundException, SQLException {

        String pr1 = prdid.getText();

        Class.forName("com.mysql.jdbc.Driver");
        Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory","root","");
        PreparedStatement ps1 = con1.prepareStatement("DELETE FROM inventory1 WHERE ProductID=?");
        ps1.setString(1, pr1);
        int stat = ps1.executeUpdate();

        if(stat == 1){
            JOptionPane.showMessageDialog(null, "DELETE Successful");
            table1.getItems().clear();
            updatetable();
        }
        else{
            JOptionPane.showMessageDialog(null, "DELETE Failed");
        }

    }

    @FXML
    void Updateproduct(ActionEvent event) throws ClassNotFoundException, SQLException {

        rmprd();
        addprd();
        table1.getItems().clear();
        updatetable();

    }

        String query = null;
        Connection connection = null;
        Statement ps4 = null;
        ResultSet rs4 = null;

    ObservableList<InventoryTable> List = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        updatetable();

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

        prdid.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    prdid.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        quantity.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    quantity.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        costprice.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    costprice.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        price.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    price.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

    }

    void updatetable(){

        Prdidcol.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
        quancol.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        pricecol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        costpricecol.setCellValueFactory(new PropertyValueFactory<>("CostPrice"));
        categorycol.setCellValueFactory(new PropertyValueFactory<>("Category"));

        try{

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory","root","");
            query = "SELECT * FROM inventory1;";
            ps4 = connection.createStatement();
            rs4 = ps4.executeQuery(query);
            while(rs4.next()){
                List.add(new InventoryTable(rs4.getString("Quantity"),
                        rs4.getString("Price"),
                        rs4.getString("ProductID"),
                        rs4.getString("CostPrice"),
                        rs4.getString("Category")));

                table1.setItems(List);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    void rmprd() throws ClassNotFoundException, SQLException {

        String pr1 = prdid.getText();

        Class.forName("com.mysql.jdbc.Driver");
        Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory","root","");

        PreparedStatement ps1 = con1.prepareStatement("DELETE FROM inventory1 WHERE ProductID=?");

        ps1.setString(1, pr1);

        int stat = ps1.executeUpdate();

        if(stat == 1){
            table1.getItems().clear();
            updatetable();
            addprd();
        }
        else{
            JOptionPane.showMessageDialog(null, "Can not update as the product is not in the Inventory");
        }

    }

    void addprd() throws ClassNotFoundException, SQLException {

        String pr1 = prdid.getText();
        String q1 = quantity.getText();
        String pr2 = price.getText();
        String cp1 = costprice.getText();

        Class.forName("com.mysql.jdbc.Driver");
        Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory","root","");

        PreparedStatement ps1 = con1.prepareStatement("INSERT INTO inventory1 (ProductID, Quantity, Price, CostPrice, Category) VALUES (?,?,?,?,?)");

        ps1.setString(1, pr1);
        ps1.setString(2,q1);
        ps1.setString(3,pr2);
        ps1.setString(4, cp1);

        int stat = ps1.executeUpdate();

        if(stat == 1){
            JOptionPane.showMessageDialog(null, "Update successful");
            table1.getItems().clear();
            updatetable();
        }
        else{
            //JOptionPane.showMessageDialog(null, "Adding Failed");
        }

    }

    @FXML
    private Button refreshinventory;

    @FXML
    void Refreshtable(ActionEvent event) {

        table1.getItems().clear();
        updatetable();
        JOptionPane.showMessageDialog(null, "List Refreshed!");

    }

}



