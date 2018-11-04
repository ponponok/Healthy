package healthypond017.healthy.sleep;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import healthypond017.healthy.MenuFragment;
import healthypond017.healthy.R;


public class SleepFragment extends Fragment {
    FirebaseAuth _auth = FirebaseAuth.getInstance();
    ArrayList<Sleep> _sleep = new ArrayList<Sleep>();
    ListView _sleepList;
    SleepAdapter _sleepAdapter;
    String []  st;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sleep, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initAddBtn();
        initBackBtn();
        ShowDatabase();

    }

    void ShowDatabase(){
        _sleep.clear();
        _sleepList = getView().findViewById(R.id.sleep_list);
        _sleepAdapter = new SleepAdapter(getActivity(), R.layout.fragment_sleep, _sleep);
        SQLiteDatabase myDB = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);

        myDB.execSQL("CREATE TABLE IF NOT EXISTS sleep (_id INTEGER PRIMARY KEY AUTOINCREMENT,uid VARCHAR(200), date VARCHAR(200), sleep VARCHAR(200), wake VARCHAR(200))");

        final Cursor myCursor = myDB.rawQuery("select date, sleep, wake, _id from sleep where uid = "+"'"+_auth.getCurrentUser().getUid().toString()+"'", null);

        while (myCursor.moveToNext()){
            String dt = myCursor.getString(0);
            String sp = myCursor.getString(1);
            String we = myCursor.getString(2);
            _sleep.add(new Sleep(dt, sp, we));
        }

        _sleepList.setAdapter(_sleepAdapter);



        _sleepList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                myCursor.moveToPosition(position);
                String ddd = myCursor.getString(0);
                Bundle bd = new Bundle();
                bd.putString("DATE", String.valueOf(myCursor.getInt(3)));
                //Toast.makeText(getActivity(), "your date = ", Toast.LENGTH_SHORT);
                SleepFormFragment spf = new SleepFormFragment();
                spf.setArguments(bd);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, spf)
                        .addToBackStack(null)
                        .commit();
                Log.d("System", ddd);
            }
        });
//        myCursor.close();
//        myDB.close();
    }

    void initAddBtn(){
        Button AddBtn = getView().findViewById(R.id.sleep_addBtn);
        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new SleepFormFragment())
                        .addToBackStack(null)
                        .commit();
                Log.d("System", "[SleepFragment] go to Sleep Form");
            }
        });
    }

    void initBackBtn(){
        Button BackBtn = getView().findViewById(R.id.sleep_backBtn);
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new MenuFragment())
                        .addToBackStack(null)
                        .commit();
                Log.d("System", "[SleepFragment] go to Menu");

            }
        });
    }
}
