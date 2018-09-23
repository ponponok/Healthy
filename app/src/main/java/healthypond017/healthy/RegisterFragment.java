package healthypond017.healthy;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterFragment extends Fragment {
    private FirebaseAuth fbAuth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fbAuth = FirebaseAuth.getInstance();
        initRegisBtn();
    }

    void initRegisBtn(){
        Button _regisBtn = (Button) getView().findViewById(R.id.register_regisBtn);
        _regisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();

            }
        });
    }

    void registerNewUser(){
        EditText _mail = (EditText) getView().findViewById(R.id.register_email);
        EditText _password = (EditText) getView().findViewById(R.id.register_password);
        EditText _repassword = (EditText) getView().findViewById(R.id.register_rePassword);

        String _mailStr = _mail.getText().toString();
        String _passwordStr = _password.getText().toString();
        String _repasswordStr = _repassword.getText().toString();

        if(_passwordStr.length() < 6 || _repasswordStr.length() < 6){
            Toast.makeText(getActivity(), "Password must be atlest 6 character", Toast.LENGTH_SHORT).show();

        }else if(!_passwordStr.equals(_repasswordStr)){
            Toast.makeText(getActivity(), _passwordStr+" "+ _repasswordStr, Toast.LENGTH_SHORT).show();

        }else {
            fbAuth.createUserWithEmailAndPassword(_mailStr, _passwordStr).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    sendVerifiedEmail(authResult.getUser());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "Error = + "+ e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    private void sendVerifiedEmail(FirebaseUser _user){
        _user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new LoginFragment())
                        .commit();
                Toast.makeText(getActivity(), "Register complete", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Error =  "+ e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}