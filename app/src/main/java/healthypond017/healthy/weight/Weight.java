package healthypond017.healthy.weight;

import android.support.annotation.NonNull;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;



public class Weight implements Comparable<Weight> {

        private String date;
        private int weight;
        private String status;

        public Weight(){}

        public Weight(String date, int weight, String status){
            this.setDate(date);
            this.setWeight(weight);
            this.setStatus(status);
        }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int compareTo(@NonNull Weight o) {
            int compareWeight = ((Weight) o ).getWeight();
        return this.weight-compareWeight;
    }

    public static Comparator<Weight> DateComparator = new Comparator<Weight>() {
        @Override
        public int compare(Weight o1, Weight o2) {
            String d1 = o1.getDate();
            String d2 = o2.getDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy");
            LocalDate dateTime = LocalDate.parse(d1, formatter);
            LocalDate dateTime2 = LocalDate.parse(d2, formatter);
            System.out.println(dateTime);

            return dateTime2.compareTo(dateTime);
        }
    };

}
