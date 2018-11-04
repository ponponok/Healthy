package healthypond017.healthy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;


import healthypond017.healthy.sleep.SleepFragment;
import healthypond017.healthy.weight.WeightFragment;

public class MenuFragment extends Fragment {
    FirebaseAuth _auth;
    String[] list = {"BMI", "WEIGHT", "SLEEP", "LOGOUT"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        _auth = FirebaseAuth.getInstance();
        final ArrayAdapter<String> _menuAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,list);
        ListView _menuList = (ListView) getView().findViewById(R.id.menu_list);
        _menuList.setAdapter(_menuAdapter);
        _menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (_menuAdapter.getItem(position).equals("BMI")){
                    Log.d("System", "[MenuFragment] Go to BMI");
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new BMIFragment())
                            .addToBackStack(null)
                            .commit();
                }else if(_menuAdapter.getItem(position).equals("WEIGHT")){
                    Log.d("System", "[MenuFragment] Go to WEIGHT");
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new WeightFragment())
                            .addToBackStack(null)
                            .commit();
                }else if(_menuAdapter.getItem(position).equals("LOGOUT")){
                    _auth.signOut();
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new LoginFragment())
                            .commit();
                }else if(_menuAdapter.getItem(position).equals("SLEEP")){
                    Log.d("System", "[MenuFragment] Go to SLEEP");
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new SleepFragment())
                            .addToBackStack(null)
                            .commit();
                }


            }
        });
    }
}
