package SuperMart;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.Property;
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

public class AttendanceCheck implements Initializable {

    Parent root;
    Stage stage;
    Scene scene;

    @FXML
    private TableView<AttendanceTable> attentable;

    @FXML
    private TableColumn<AttendanceTable,String> tablename;

    @FXML
    private TableColumn<AttendanceTable,String> tablestatus;

    @FXML
    private TableColumn<AttendanceTable, String> tabledatentime;

    @FXML
    private DatePicker dateselector;

    @FXML
    private Button getattendancebt;

    @FXML
    private Label date;

    @FXML
    private Label time;

    ObservableList<AttendanceTable> List = FXCollections.observableArrayList();

    @FXML
    void getattendance(ActionEvent event) throws ClassNotFoundException, SQLException {

        String a = dateselector.getValue().toString();

        Class.forName("com.mysql.jdbc.Driver");
        Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance","root","");
        PreparedStatement p1 = con1.prepareStatement("SELECT * FROM attandance1 WHERE Date=?");
        p1.setString(1, a);
        ResultSet rs = p1.executeQuery();
        while (rs.next()){
            List.add(new AttendanceTable(rs.getString("Name"), rs.getString("Status"), rs.getString("DatenTime")));
            attentable.setItems(List);

        }

    }

    @FXML
    void goback(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("Semilogin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tablename.setCellValueFactory(new PropertyValueFactory<>("Name"));
        tablestatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        tabledatentime.setCellValueFactory(new PropertyValueFactory<>("DatenTime"));

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



    }
}
