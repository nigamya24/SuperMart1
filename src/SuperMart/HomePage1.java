package SuperMart;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class HomePage1 extends Application implements Initializable {

    Stage stage;
    Scene scene;

    @FXML
    private Label date;

    @FXML
    private Label time;

    @FXML
    private Button loginbt;

    @FXML
    private Button selfcheckoutbt;

    @FXML
    private Button feedbackbt;

    @FXML
    void feedback(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("Feedback.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Feedback");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void login(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Login");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

    @FXML
    void selfcheckout(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("SelfCheckout.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Self-Checkout");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String args) {

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("HomePage1.fxml"));
            stage.setTitle("HomePage");
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
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


    }

    public void close(MouseEvent mouseEvent) {
    }

    /*@FXML
    void close(MouseEvent event) {

        System.exit(0);

    }*/
}
