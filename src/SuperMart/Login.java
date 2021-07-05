package SuperMart;


import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.EventObject;
import java.util.Locale;
import java.util.ResourceBundle;

public class Login implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private Label date;

    @FXML
    private Label time;

    @FXML
    private TextField UserTextField;

    @FXML
    private PasswordField PasswordTextfield;

    @FXML
    private Button Login1;

    @FXML
    private Button GoBack;

    public Login() throws SQLException {
    }

    @FXML
    void GoBackAction(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("HomePage1.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


    @FXML
    void Login1Action(ActionEvent event) throws IOException {

        String uname = UserTextField.getText();
        String pass = PasswordTextfield.getText();


        if (UserTextField.getText().isEmpty() || PasswordTextfield.getText().isEmpty()) {

            JOptionPane.showMessageDialog(null, "UserID or Password cannot be empty!");
        } else if (UserTextField.getText().equals("ADMIN")) {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "");
                String url = "SELECT * FROM login.login1 WHERE UserID='" + uname + "' AND Password='" + pass + "' ";
                Statement ps = con.createStatement();
                ResultSet rs = ps.executeQuery(url);

                if (rs.next()) {

                    root = FXMLLoader.load(getClass().getResource("Semilogin.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } else {

                    JOptionPane.showMessageDialog(null, "Invalid Username or Password");
                }

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        } else {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "");
                String url = "SELECT * FROM login.login2 WHERE UserID='" + uname + "' AND Password='" + pass + "' ";
                Statement ps = con.createStatement();
                ResultSet rs = ps.executeQuery(url);

                if (rs.next()) {

                    root = FXMLLoader.load(getClass().getResource("EmpView.fxml"));

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("EmpView.fxml"));
                    Parent root = (Parent) loader.load();

                    EmpView ev1 = loader.getController();
                    ev1.name(UserTextField.getText());

                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                } else {

                    JOptionPane.showMessageDialog(null, "Invalid Username or Password");
                }

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }

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
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/YYYY" + ", EEEE", Locale.getDefault());
        date.setText(format.format(ld));

    }


    @FXML
    void Login2(KeyEvent event) throws IOException {

        /*String uname = UserTextField.getText();
        String pass = PasswordTextfield.getText();

        /*Parent root = FXMLLoader.load(getClass().getResource("EmpView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        if(UserTextField.getText().isEmpty() || PasswordTextfield.getText().isEmpty()){

            JOptionPane.showMessageDialog(null, "UserID or Password cannot be empty!");
        }

        else if(UserTextField.getText().equals("ADMIN")){

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/login","root","India@321");
                String url = "SELECT * FROM login.login1 WHERE UserID='"+uname+"' AND Password='"+pass+"' ";
                Statement ps=con.createStatement();
                ResultSet rs = ps.executeQuery(url);

                if(rs.next()){

                    root = FXMLLoader.load(getClass().getResource("Semilogin.fxml"));
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }

                else{

                    JOptionPane.showMessageDialog(null,"Invalid Username or Password");
                }

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }

        else {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "India@321");
                String url = "SELECT * FROM login.login2 WHERE UserID='" + uname + "' AND Password='" + pass + "' ";
                Statement ps = con.createStatement();
                ResultSet rs = ps.executeQuery(url);

                if (rs.next()) {

                    root = FXMLLoader.load(getClass().getResource("EmpView.fxml"));

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("EmpView.fxml"));
                    Parent root = (Parent) loader.load();

                    EmpView ev1 = loader.getController();
                    ev1.name(UserTextField.getText());

                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                } else {

                    JOptionPane.showMessageDialog(null, "Invalid Username or Password");
                }

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }

        }*/
    }
}
