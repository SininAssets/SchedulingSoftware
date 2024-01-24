package Controller;

import Database.UtilityDao;
import Database.UtilityDaoImpl;
import LampadaStuff.LampadaStuff;
import Model.Appointments;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Utilities;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import static main.LoginScreenFunctions.*;

public class LoginScreen1Controller implements Initializable {

    Stage stage;
    //This Logs The User In Once Button Is pressed
    boolean Logged_In = false;
    /**
     * There is Lampada Impression Above
     */
    //This sets Current Time
    LocalDateTime Currently = LocalDateTime.now();
    //This Sets Current Time + 15 Minutes
    LocalDateTime Currently15Min = LocalDateTime.now().plusMinutes(15);
    //This Sets up Language Bundles
    ResourceBundle Language_Bundle = ResourceBundle.getBundle("Language/lang");
    //This Converts Local Time
    ZonedDateTime LocalTimeConvert = Currently.atZone(ZoneId.systemDefault());
    //Converts Local To UTC
    ZonedDateTime UTC = LocalTimeConvert.withZoneSameInstant(ZoneId.of("Etc/UTC"));
    /**
     * This Is Part of the Requirments getting Login Activiities.txt
     * <p>
     * This is also a Lampada Expression to get file names for all login activities
     */
    LogActivities logActivities = () -> {
        return "login_activity.txt";
    };
    @FXML
    private Button Login_Btn;
    @FXML
    private TextField UserName_Text_Field;
    @FXML
    private PasswordField Password_Field;
    @FXML
    private Label Time_Zone;
    @FXML
    private Label Username;
    @FXML
    private Label Password;
    @FXML
    private Button Exit_Btn;

    @FXML
    void Login_Btn_Click(ActionEvent event) throws IOException, SQLException {
        //This Collects User Name of the Person logging in
        String User_Name = UserName_Text_Field.getText();
        //This part Collects the Password Field
        String Password = Password_Field.getText();
        //This just shows all Appointmnets
        ObservableList<Appointments> User_Appointments;
        //This makes it that if the USername is empty it will send an Error Message
        //It also translate it into french in case it zone is set in france.
        if (User_Name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(Language_Bundle.getString("EmptyUser"));
            alert.setContentText(Language_Bundle.getString("EmptyUser2"));
            Optional<ButtonType> result = alert.showAndWait();
        } else if
            //If password is empty then it sends an Error message
            //Translates it into french based on zone ID
        (Password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(Language_Bundle.getString("EmptyPassword"));
            alert.setContentText(Language_Bundle.getString("EmptyPassword2"));
            Optional<ButtonType> result = alert.showAndWait();

            //If Password and UserName are incorrect it sends an Error Message
            //It also translates
        } else if (!Password_Check(Password) && !User_Name_Check((User_Name))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(Language_Bundle.getString("WrongUserPass"));
            alert.setContentText(Language_Bundle.getString("EnterCorUserPass"));
            Optional<ButtonType> result = alert.showAndWait();

            Logged_In = false;
            LoginActivities();
            //If Username is incorrect it will send a Message
            //It also translates based on zone ID
        } else if (!User_Name_Check(User_Name)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(Language_Bundle.getString("WrongUserName"));
            alert.setContentText(Language_Bundle.getString("EnterCorUser"));
            Optional<ButtonType> result = alert.showAndWait();

            Logged_In = false;
            LoginActivities();
            //If password is wrong it sends an Error Message
            //Also Translates it
        } else if (!Password_Check(Password)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(Language_Bundle.getString("WrongPassword"));
            alert.setContentText(Language_Bundle.getString("EnterCorPassword"));
            Optional<ButtonType> result = alert.showAndWait();

            Logged_In = false;
            LoginActivities();

            //This basically makes sure Password and Username is correct
            //If so it sends user to dashboard
        } else if (Password_Check(Password) && User_Name_Check(User_Name)) {

            Parent addParts = FXMLLoader.load(getClass().getResource("../View/DashboardScreen2.fxml"));
            Scene scene = new Scene(addParts);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();

            Logged_In = true;
            LoginActivities();
        }
        //This Will Check for Appointments after log-in and then send User the Notification
        boolean valid = false;
        //Gets all the Utility stuff
        UtilityDao utility = new UtilityDaoImpl();
        //Defines User ID
        int User_ID = Get_User_ID(User_Name);
        //This Gets Appointments by User ID
        ObservableList<Appointments> user_App = Utilities.Get_Appointments_User_ID(User_ID);
        //
        for (Appointments appointment : user_App) {
            //Defines Start
            LocalDateTime Start = appointment.getStart();
            //This makes sure that Appointment is upcoming
            if ((Start.isAfter(Currently) || Start.isEqual(Currently15Min)) &&
                    //Once it Finds An Appointment Comming in
                    (Start.isBefore(Currently15Min) || Start.isEqual(Currently))) {
                //This Will Send Out Information
                Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
                //Translates Alert into French
                confirmation.setTitle(Language_Bundle.getString("Alert"));
                //Translate Appointment into French if within Zone ID
                confirmation.setContentText(Language_Bundle.getString("Appointment"));
                //This Sends Out A message
                confirmation.setContentText(Language_Bundle.getString("Appointment") + " " + appointment.getAppointment_ID() + " " + Language_Bundle.getString("BeginningAt") + appointment.getStart());
                confirmation.getButtonTypes().clear();
                confirmation.getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
                confirmation.showAndWait();
                //This Confirms Login Worked
                valid = true;

            }
            //It is used Here makes sure the Login was sucsessful so it can send out the
            //You have No Appointments
        }
        if (!valid && Logged_In) {

            Alert info = new Alert(Alert.AlertType.INFORMATION);
            //Sends out the Notice in French or English based on Location
            info.setTitle(Language_Bundle.getString("Appointment"));
            //This Sends out Message you have No Appointments
            info.setContentText(Language_Bundle.getString("NoApt"));
            Optional<ButtonType> result = info.showAndWait();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        //This Sets the Time Zone to Computer Default
        String TimeZone = ZoneId.systemDefault().toString();
        Time_Zone.setText(TimeZone);
        //Turns Login into French or english
        Login_Btn.setText(Language_Bundle.getString("Login"));
        //turns Exit into french or english
        Exit_Btn.setText(Language_Bundle.getString("Cancel"));
        //Turns username into french or english
        Username.setText(Language_Bundle.getString("UserName"));
        //Turns password into French Or english
        Password.setText(Language_Bundle.getString("Password"));

        LampadaStuff.LampadaExpress LoginScreenEnter = s -> "You're in the " + s;
        System.out.println(LoginScreenEnter.LoginScreenEnter("Login Screen"));
    }

    //Exits
    public void Exit_Btn_Click(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }

    //This Clears out UserName
    public void Username_Clear_Btn_Click(ActionEvent event) {
        UserName_Text_Field.setText("");
    }

    public void Password_Clear_Btn_Click(ActionEvent event) {
        Password_Field.setText("");
    }

    /**
     * These are the Method to record login attempts and activity to login_activity.txt
     * and uses Lampada on lines 219-221 to get File name
     *
     * @throws IOException
     */
    public void LoginActivities() throws IOException {
        FileWriter file = new FileWriter(logActivities.Get_File_Name(), true);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm:ss");
        ZoneId local = ZoneId.systemDefault();
        if (Logged_In) {
            file.write(UserName_Text_Field.getText() + " Has Logged on " + format.format(Currently));
        } else if (!Logged_In) {
            file.write(UserName_Text_Field.getText() + " Has Failed to log in " + format.format(Currently));
        }
        file.write("\n");
        file.close();
    }

    //This Gets The File Name
    interface LogActivities {
        String Get_File_Name();
    }
    //Final
}
