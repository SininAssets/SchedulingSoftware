package main;

import javafx.scene.control.Alert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

public class TimeStuff {
    /**
     * This Defines the Buissness Hours and All the Time Stuff required for this project
     *
     * @param Start
     * @param End
     * @return
     */
    public static boolean businessHours(LocalDateTime Start, LocalDateTime End) {
        //This Defines the Local Zones
        ZoneId localZone = ZoneId.systemDefault();
        //It states that Zone is New York
        ZoneId estZone = ZoneId.of("America/New_York");

        //This Defines when it should start in New York Time Zones or EST
        LocalDateTime appStartEST = Start.atZone(localZone).withZoneSameInstant(estZone).toLocalDateTime();
        //This Defines where it should Start in the New York Time Zone
        LocalDateTime appEndEST = End.atZone(localZone).withZoneSameInstant(estZone).toLocalDateTime();
        //This is buiisnesss start it makes sure the Hour Selection has options
        LocalDateTime BizStart = appStartEST.withHour(8).withMinute(0);
        LocalDateTime BizEnd = appEndEST.withHour(22).withMinute(0);
        //This Is to makes sure Apppointment cant start before buissness hours
        if (appStartEST.isBefore(BizStart) || appEndEST.isAfter(BizEnd)) {
            LocalTime localStart = TimeStuff.localStart();
            LocalTime localEnd = TimeStuff.localEnd();
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("");
            alert1.setHeaderText("Outside of Business Hours");
            alert1.setContentText("Appointment is outside of business hours: 8:00AM to 10:00PM EST\n");


            alert1.showAndWait();
            return true;
        } else {
            return false;
        }
    }


    public static LocalTime localEnd() {
        //This Defines when and where the Appointment can End
        LocalTime closingBusinessTime = LocalTime.of(22, 0);
        ZoneId easternZone = ZoneId.of("America/New_York");
        ZoneId localZone = ZoneId.systemDefault();
        //Sets the buissness Closing TImes
        LocalDateTime businessEndDT = LocalDateTime.of(LocalDate.now(), closingBusinessTime);
        LocalDateTime businessLocalDT = businessEndDT.atZone(easternZone).withZoneSameInstant(localZone).toLocalDateTime();

        LocalTime businessEndLocal = businessLocalDT.toLocalTime();

        return businessEndLocal;
    }

    public static LocalTime localStart() {
        LocalTime openingBusinessTime = LocalTime.of(8, 0);
        ZoneId easternZone = ZoneId.of("America/New_York");
        ZoneId localZone = ZoneId.systemDefault();

        LocalDateTime businessEastern = LocalDateTime.of(LocalDate.now(), openingBusinessTime);
        LocalDateTime businessLocal = businessEastern.atZone(easternZone).withZoneSameInstant(localZone).toLocalDateTime();

        LocalTime businessStartLocal = businessLocal.toLocalTime();

        return businessStartLocal;


    }

}
