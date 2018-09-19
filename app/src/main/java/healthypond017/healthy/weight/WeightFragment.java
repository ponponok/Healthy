package healthypond017.healthy.weight;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

import healthypond017.healthy.R;

public class WeightFragment extends Fragment {
    FirebaseFirestore mdb = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    ArrayList<Weight> weights = new ArrayList<Weight>();
    ListView _weightList;
    WeightAdapter _weightAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight_list, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        weights.clear();
        _weightList = (ListView) getView().findViewById(R.id.weight_list);
        _weightAdapter = new WeightAdapter(getActivity(), R.layout.fragment_weight_list, weights);



        mdb.collection("myfitness").document(auth.getCurrentUser().getUid()).collection("weight").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(
                    @javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots,
                    @javax.annotation.Nullable FirebaseFirestoreException e) {
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots){
                    if(doc.get("date") != null){
                        String dt = doc.get("date").toString();
                        String wi = doc.get("weight").toString();
                        weights.add(new Weight(dt, Integer.parseInt(wi), "UP"));
                    }
                }
                Collections.sort(weights, Weight.DateComparator);
                updateStatus();
                _weightList.setAdapter(_weightAdapter);
            }
        });
        initAdd();
    }
    public void initAdd(){
        Button _addBtn = (Button) getView().findViewById(R.id.weight_addBtn);
        _addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new Weight_FormFragment())
                        .addToBackStack(null)
                        .commit();
                Log.d("System", "[WeightFragment] go to Form Weight");
            }
        });
    }

    public void updateStatus() {
        int num = weights.size();
        if(num >= 1){
            weights.get(num-1).setStatus("");
        }
        if (num >= 2) {
            for (int i = num-1; i > 0 ; i--) {
                if(weights.get(i).getWeight() < weights.get(i-1).getWeight()){
                    weights.get(i-1).setStatus("Up");
                }else if(weights.get(i).getWeight() > weights.get(i-1).getWeight()){
                    weights.get(i-1).setStatus("Down");
                } else{
                    weights.get(i-1).setStatus("");
                }
            }
        }
    }
}
