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

public class EmpDetails implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private TableView<EmpTable> emptable;

    @FXML
    private TableColumn<EmpTable,String> tableempid;

    @FXML
    private TableColumn<EmpTable,String> tableempname;

    @FXML
    private TableColumn<EmpTable,String> tableempmob;

    @FXML
    private TableColumn<EmpTable,String> tableempsal;

    @FXML
    private TableColumn<EmpTable,String> tablemepgender;


    @FXML
    private TableColumn<EmpTable,String> tabledob;

    @FXML
    private Button goback;

    @FXML
    private TextField empname;

    @FXML
    private TextField empID;

    @FXML
    private TextField empsal;

    @FXML
    private TextField empmobile;

    @FXML
    private Button addemp;

    @FXML
    private Button removeemp;

    @FXML
    private Button clearbtn;

    @FXML
    private Label date;

    @FXML
    private Label time;

    @FXML
    private DatePicker dob;

    @FXML
    private ComboBox<String> empgender;

    ObservableList<String> List1 = FXCollections.observableArrayList("Male","Female");


    @FXML
    void clear(ActionEvent event) {

        clear();
    }

    @FXML
    void addemp(ActionEvent event) throws SQLException, ClassNotFoundException {

        String t1 = empID.getText();
        String t2 = empname.getText();
        String t3 = empmobile.getText();
        String t4 = empsal.getText();
        String t5 = empgender.getValue();
        String t6 = String.valueOf(dob.getValue());

        Class.forName("com.mysql.jdbc.Driver");
        Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee","root","");
        PreparedStatement ps1 = con1.prepareStatement("INSERT INTO employee1 (EmpID, Name, Mobile, Salary, Gender, DOB) VALUES (?,?,?,?,?,?)");

        ps1.setString(1,t1);
        ps1.setString(2,t2);
        ps1.setString(3,t3);
        ps1.setString(4,t4);
        ps1.setString(5,t5);
        ps1.setString(6, t6);

        int stat = ps1.executeUpdate();

        if(stat==1){
            JOptionPane.showMessageDialog(null, "Employee Added");
            emptable.getItems().clear();
            updatetable();
        }
        else{
            JOptionPane.showMessageDialog(null, "Error");
        }

        clear();

    }

    @FXML
    void goback(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("Semilogin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

    @FXML
    void removeemp(ActionEvent event) throws SQLException, ClassNotFoundException {

        rmemp();
        emptable.getItems().clear();
        updatetable();

    }

    String query = null;
    Connection connection = null;
    Statement ps4 = null;
    ResultSet rs4 = null;

    ObservableList<EmpTable> List = FXCollections.observableArrayList();

    void updatetable(){

        tableempid.setCellValueFactory(new PropertyValueFactory<>("EmpID"));
        tableempname.setCellValueFactory(new PropertyValueFactory<>("Name"));
        tableempmob.setCellValueFactory(new PropertyValueFactory<>("Mobile"));
        tableempsal.setCellValueFactory(new PropertyValueFactory<>("Salary"));
        tablemepgender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        tabledob.setCellValueFactory(new PropertyValueFactory<>("DOB"));

        try{

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee","root","");
            query = "SELECT * FROM employee1;";
            ps4 = connection.createStatement();
            rs4 = ps4.executeQuery(query);
            while(rs4.next()){
                List.add(new EmpTable(rs4.getString("Name"),
                        rs4.getString("Mobile"),
                        rs4.getString("EmpID"),
                        rs4.getString("Salary"),
                        rs4.getString("Gender"),
                        rs4.getString("DOB")));

                emptable.setItems(List);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void rmemp() throws SQLException, ClassNotFoundException {

        String t1 = empID.getText();

        Class.forName("com.mysql.jdbc.Driver");
        Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee","root","");

        PreparedStatement ps1 = con1.prepareStatement("DELETE FROM employee1 WHERE EmpID=?");

        ps1.setString(1, t1);

        int stat = ps1.executeUpdate();

        System.out.println(stat);

        if(stat == 1){
            JOptionPane.showMessageDialog(null, "Deletion Successful");
            updatetable();
            clear();

        }
        else{
            JOptionPane.showMessageDialog(null, "Deletion Failed");
            clear();
        }

    }

    void clear(){

        empname.clear();
        empID.clear();
        empmobile.clear();
        empsal.clear();
        empgender.getSelectionModel().clearSelection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        empgender.setItems(List1);
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

        empsal.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    empsal.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        empmobile.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    empmobile.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        empID.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    empID.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }
}
