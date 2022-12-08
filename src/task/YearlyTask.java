package task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.InvalidPropertiesFormatException;

public class YearlyTask extends Task {

    public YearlyTask(String title, String description, Type type, LocalDateTime taskDateTime) throws InvalidPropertiesFormatException {
        super(title, description, type, taskDateTime);
    }

    @Override
    public boolean isAvailable(LocalDate inputDate) {
        var startDate = getDateTime().toLocalDate();
        while (!startDate.isAfter(inputDate)) {
            if (startDate.equals(inputDate)) {
                return true;
            }
            startDate = startDate.plusYears(1);

        }
        return false;
    }
}
