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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import healthypond017.healthy.MenuFragment;
import healthypond017.healthy.R;


public class SleepFormFragment extends Fragment {
    FirebaseAuth _auth = FirebaseAuth.getInstance();
    Bundle bundle;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sleep_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bundle = this.getArguments();
        if(bundle != null){
            Toast.makeText(getActivity(), "DATE", Toast.LENGTH_SHORT);
            EditText edt = getView().findViewById(R.id.sleep_form_date);
            EditText edt2 = getView().findViewById(R.id.sleep_form_bed);
            EditText edt3 = getView().findViewById(R.id.sleep_form_wakeup);
            //edt.setText(bundle.getString("DATE"));
            SQLiteDatabase myDB = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);
            final Cursor myCursor = myDB.rawQuery("select date, sleep, wake, _id from sleep where _id = "+Integer.parseInt(bundle.getString("DATE")), null);
            while (myCursor.moveToNext()){
                String dt = myCursor.getString(0);
                String sp = myCursor.getString(1);
                String we = myCursor.getString(2);
                edt.setText(dt);
                edt2.setText(sp);
                edt3.setText(we);
                myDB.close();


            }
            myCursor.close();
        }
        BackBtn();
        SaveBtn();

    }


    private void BackBtn(){
        Button _backBtn = getView().findViewById(R.id.sleep_form_backBtn);
        _backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new SleepFragment())
                        .addToBackStack(null)
                        .commit();
                Log.d("System", "[SleepFormFragment] go to Sleep ");
            }
        });
    }
    private void SaveBtn(){
        Button _saveBtn = getView().findViewById(R.id.sleep_form_saveBtn);
        _saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText _date, _sleep, _wake;
                _date = getView().findViewById(R.id.sleep_form_date);
                _sleep = getView().findViewById(R.id.sleep_form_bed);
                _wake = getView().findViewById(R.id.sleep_form_wakeup);
                SQLiteDatabase myDB = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);
                    myDB.execSQL("CREATE TABLE IF NOT EXISTS sleep (_id INTEGER PRIMARY KEY AUTOINCREMENT,uid VARCHAR(200), date VARCHAR(200), sleep VARCHAR(200), wake VARCHAR(200))");
                    ContentValues row1 = new ContentValues();
                    row1.put("uid", _auth.getCurrentUser().getUid().toString());
                    row1.put("date", _date.getText().toString());
                    row1.put("sleep", _sleep.getText().toString());
                    row1.put("wake", _wake.getText().toString());
                    if(bundle == null){
                        myDB.insert("sleep", null, row1);
                    }else{
                        myDB.update("sleep", row1, "_id="+Integer.parseInt(bundle.getString("DATE")), null);
                    }

                    myDB.close();



                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new SleepFragment())
                        .addToBackStack(null)
                        .commit();
                Log.d("System", "[SleepForm] Back to sleep");
            }
        });
    }
}
