package healthypond017.healthy.sleep;

public class Sleep {
    private String date;
    private String sleep;
    private String wake;

    public Sleep(String date, String sleep, String wake) {
        this.setDate(date);
        this.setSleep(sleep);
        this.setWake(wake);
    }

    public Sleep(){}


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSleep() {
        return sleep;
    }

    public void setSleep(String sleep) {
        this.sleep = sleep;
    }

    public String getWake() {
        return wake;
    }

    public void setWake(String wake) {
        this.wake = wake;
    }
}
