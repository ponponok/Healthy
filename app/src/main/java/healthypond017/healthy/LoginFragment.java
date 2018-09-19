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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {
    FirebaseAuth _auth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        _auth = FirebaseAuth.getInstance();
        initRegisterBtn();
        initLoginBtn();
    }

    void initLoginBtn(){
        Button _loginBtn = (Button) getView().findViewById(R.id.login_loginBtn);
        _loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText _username = (EditText) getView().findViewById(R.id.login_user);
                EditText _password = (EditText) getView().findViewById(R.id.login_password);
                final String _usernameStr = _username.getText().toString();
                final String _passwordStr = _password.getText().toString();
                if (_usernameStr.isEmpty() || _passwordStr.isEmpty()){
                    Toast.makeText(getActivity(),"Please enter your Email and password",Toast.LENGTH_SHORT).show();
                    Log.d("System", "[LoginFragment] Email or password is empty");
                }else {
                    _auth.signInWithEmailAndPassword(_usernameStr, _passwordStr).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            if (authResult.getUser().isEmailVerified()) {
                                getActivity()
                                        .getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.main_view, new MenuFragment())
                                        .commit();
                            } else {
                                Toast.makeText(getActivity(), "Plase confirm your email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    void initRegisterBtn(){
        TextView _registerBtn = (TextView) getView().findViewById(R.id.login_register);
        _registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new RegisterFragment())
                        .addToBackStack(null)
                        .commit();
                Log.d("System", "[LoginFragment] Go to register");
            }
        });
    }
}
