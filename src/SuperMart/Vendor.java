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

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class Vendor implements Initializable {

    Parent root;
    Stage stage;
    Scene scene;


    @FXML
    private TextField vname;

    @FXML
    private TextField vcategory;

    @FXML
    private TextField vcontact;

    @FXML
    private Label date;

    @FXML
    private Label time;

    @FXML
    private Button gobackbt;

    @FXML
    private Button addvendorbt;

    @FXML
    private Button removevendorbt;

    @FXML
    private Button placeorderbt;

    @FXML
    void addvendor(ActionEvent event) throws SQLException, ClassNotFoundException {

        newvendor();
        updatetable();
        clr();

    }

    @FXML
    void goback(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("Semilogin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void placeorder(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("Placeorder.fxml"));

        Vendortable vt = vendortable.getSelectionModel().getSelectedItem();
        String s1 = vt.getName();
        String s2 = vt.getCategory();
        String s3 = vt.getContact();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Placeorder.fxml"));
        Parent root = (Parent) loader.load();

        Placeorder pl1 = loader.getController();
        pl1.setname(s1, s2, s3);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();



    }

    @FXML
    void removevendor(ActionEvent event) throws SQLException, ClassNotFoundException {

        delvendor();
        updatetable();
        clr();
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
            updatetable();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        vcontact.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    vcontact.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

    }


    @FXML
    private TableView<Vendortable> vendortable;

    @FXML
    private TableColumn<Vendortable, String> tabelename;

    @FXML
    private TableColumn<Vendortable, String> tablecategory;

    @FXML
    private TableColumn<Vendortable, String> tablecontact;

    ObservableList<Vendortable> List1 = FXCollections.observableArrayList();

    public void updatetable() throws ClassNotFoundException, SQLException {

        vendortable.getItems().clear();

        tabelename.setCellValueFactory(new PropertyValueFactory<>("Name"));
        tablecategory.setCellValueFactory(new PropertyValueFactory<>("Category"));
        tablecontact.setCellValueFactory(new PropertyValueFactory<>("Contact"));

        Class.forName("com.mysql.jdbc.Driver");
        Connection c1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/vendor","root","");
        PreparedStatement p1 = c1.prepareStatement("SELECT * FROM vendor1");
        ResultSet rs1 = p1.executeQuery();
        while (rs1.next()){

            List1.add(new Vendortable(rs1.getString("Name"),
                    rs1.getString("Category"),
                    rs1.getString("Contact")));
            vendortable.setItems(List1);
        }
    }

    public void newvendor() throws SQLException, ClassNotFoundException {

        String t1 = vname.getText();
        String t2 = vcategory.getText();
        String t3 = vcontact.getText();

        Class.forName("com.mysql.jdbc.Driver");
        Connection c1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/vendor","root","");
        PreparedStatement p1 = c1.prepareStatement("INSERT INTO vendor1 (Name, Category, Contact) VALUES (?,?,?)");
        p1.setString(1, t1);
        p1.setString(2, t2);
        p1.setString(3, t3);

        int s = p1.executeUpdate();

    }

    public void delvendor() throws  SQLException, ClassNotFoundException{

        String t1 = vname.getText();

        Class.forName("com.mysql.jdbc.Driver");
        Connection c1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/vendor","root","");
        PreparedStatement p1 = c1.prepareStatement("DELETE FROM vendor1 where Name=?");
        p1.setString(1, t1);
        p1.executeUpdate();
    }

    public void clr(){
        vname.clear();
        vcategory.clear();
        vcontact.clear();
    }

    public void send() throws IOException {



    }
}
