package Database;

import Model.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.time.LocalDateTime;

public class DBTimeTest {
    /**
     * The Following Below Gets Everything From Appointments it is basically the reserves if
     * the Other features Dont Work
     *
     * @return
     */
    public static ObservableList<Appointments> getAllAppointments() {
        ObservableList<Appointments> AppointmentData = FXCollections.observableArrayList();

        String sql = "Select * from appointments";
        //PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);

        return AppointmentData;
    }

    /**
     * Below Is an Over lap Checker which is a requirment
     *
     * @param Customer_ID
     * @param Start_Date
     * @param End_Date
     * @return
     */
    public static boolean overlapCheck(int Customer_ID, LocalDateTime Start_Date, LocalDateTime End_Date) {
        AppointmentDaos appointment = new AppointmentImpl();
        ObservableList<Appointments> appointmentList = appointment.getAllAppointments();
        LocalDateTime checkApptStart;
        LocalDateTime checkApptEnd;
        for (Appointments a : appointmentList) {
            checkApptStart = a.getStart_Date();
            checkApptEnd = a.getEnd_Date();
            if (Customer_ID != a.getCustomer_ID()) {
                continue;
            } else if (checkApptStart.isEqual(Start_Date) || checkApptEnd.isEqual(End_Date)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("ERROR: Appointment Must not start or end during exisiting appointment ");
                alert.showAndWait();
                return true;
            } else if (Start_Date.isAfter(checkApptStart) && (End_Date.isBefore(checkApptEnd))) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("ERROR: Appointment Cant Start During Exisitng Appointment ");
                alert.showAndWait();
                return true;
            } else if (Start_Date.isAfter(checkApptStart) && End_Date.isBefore(checkApptEnd)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("ERROR: Appointment cant end during an Exisiting Appointment");
                alert.showAndWait();
                return true;
            }
        }
        return false;
    }
}
