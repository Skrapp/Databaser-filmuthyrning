import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ErrorCheck {
    private String dateFormat;

    public ErrorCheck(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public ErrorCheck() {
    }

    public boolean isDate(String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        simpleDateFormat.setLenient(false);
        try {
            simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public boolean isInteger(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public boolean isDouble(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
