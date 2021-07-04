package SuperMart;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class SeeFeedback implements Initializable {

    Stage stage;
    Scene scene;

    @FXML
    private Label date;

    @FXML
    private Label time;

    @FXML
    private TableView<FeedbackTable> fdcol;

    @FXML
    private TableColumn<FeedbackTable, String> fcnamecol;

    @FXML
    private TableColumn<FeedbackTable, String> fdmobilecol;

    @FXML
    private TableColumn<FeedbackTable, String> fdemailcol;

    @FXML
    private TableColumn<FeedbackTable, String> fdmessagecol;

    @FXML
    void goback(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("Semilogin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    ObservableList<FeedbackTable> List = FXCollections.observableArrayList();

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

        fcnamecol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        fdmobilecol.setCellValueFactory(new PropertyValueFactory<>("ContactNo"));
        fdemailcol.setCellValueFactory(new PropertyValueFactory<>("Email"));
        fdmessagecol.setCellValueFactory(new PropertyValueFactory<>("Message"));

        Connection c1 = null;
        try {
            c1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/feedback","root","India@321");
            PreparedStatement p1 = c1.prepareStatement("SELECT * FROM feedback1");
            ResultSet r1 = p1.executeQuery();
            while(r1.next()){
                List.add(new FeedbackTable(r1.getString("Name"),
                        r1.getString("ContactNo"),
                        r1.getString("Email"),
                        r1.getString("Message")));
                fdcol.setItems(List);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}
