package healthypond017.healthy.sleep;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import healthypond017.healthy.R;

public class SleepAdapter extends ArrayAdapter<Sleep> {
    ArrayList<Sleep> _sleep = new ArrayList<Sleep>();
    Context context;
    public SleepAdapter(@NonNull Context context, int resource, ArrayList<Sleep> objects) {
        super(context, resource, objects);
        this._sleep = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.fragment_sleep_item, parent, false);
        TextView _date, _sleep, _wake;
        _date = v.findViewById(R.id.sleep_item_date);
        _sleep = v.findViewById(R.id.sleep_item_sleep);
        _wake = v.findViewById(R.id.sleep_item_wake);
        Sleep row = this._sleep.get(position);
        _date.setText(row.getDate());
        _sleep.setText(row.getSleep()+" - "+row.getWake());
        _wake.setText(timeCompare(row.getSleep(), row.getWake()));
        return v;
    }

    String timeCompare(String t1, String t2){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String dateStart = "14 "+t1;
        String dateStop = "15 "+t2;
        SimpleDateFormat format2 = new SimpleDateFormat("dd HH:mm");
        try {
            if(format.parse(t2).getTime() > format.parse(t1).getTime()){
                dateStop ="14 "+t2;
            }
            Date d1 = format2.parse(dateStart);
            Date d2 = format2.parse(dateStop);
            long diff = d2.getTime() - d1.getTime();
            long hours = TimeUnit.MILLISECONDS.toHours(diff);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diff) % 60;
            return String.format("%02d:%02d", hours, minutes);
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }

    }
}
