package healthypond017.healthy.weight;

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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import healthypond017.healthy.R;
import healthypond017.healthy.RegisterFragment;

public class Weight_FormFragment extends Fragment {
    FirebaseFirestore _firestore;
    FirebaseAuth _auth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight_item, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        _firestore = FirebaseFirestore.getInstance();
        _auth = FirebaseAuth.getInstance();
        initBack();
        initSave();

    }
    public void initBack(){
        Button _addBtn = (Button) getView().findViewById(R.id.weight_form_backBtn);
        _addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new WeightFragment())
                        .addToBackStack(null)
                        .commit();
                Log.d("System", "[WeightForm] Back to menu");
            }
        });
    }
    public void initSave(){
        Button _saveBtn = (Button) getView().findViewById(R.id.weight_form_saveBtn);
        _saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText _date = (EditText) getView().findViewById(R.id.weight_form_date);
                EditText _weight = (EditText) getView().findViewById(R.id.weight_form_weight);
                String _dateStr = _date.getText().toString();
                String _weightStr = _weight.getText().toString();
                String _uid = _auth.getCurrentUser().getUid();
                Weight weight = new Weight(
                        _dateStr,
                        Integer.valueOf(_weightStr),
                        "up"
                );
                _firestore.collection("myfitness")
                        .document(_uid)
                        .collection("weight")
                        .document(_dateStr)
                        .set(weight).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_view, new WeightFragment())
                                .addToBackStack(null)
                                .commit();
                        Log.d("System", "[WeightForm] Add Success goto weight");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error =  "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
