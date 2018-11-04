package healthypond017.healthy.sleep;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

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
        _sleep.setText(row.getSleep());
        _wake.setText(row.getWake());
        return v;
    }
}
