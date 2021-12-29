package napier.example.napierproject3_api21;

import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private Button button1,button2;
    private EditText editText1,editText2;
    private DataBaseManipulator dm;
    private List<String[]> names2 = null;
    private ArrayList<ClientId> clientIds;
    private FrameLayout coordinatorLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_login, container, false);
        button1 = view.findViewById(R.id.button1);
        button2 = view.findViewById(R.id.button2);
        editText1 = view.findViewById(R.id.editText1);
        editText2 = view.findViewById(R.id.editText2);
        coordinatorLayout = view.findViewById(R.id.coordinator);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        dm = new DataBaseManipulator(getActivity());
        names2 = dm.selectAll();
        clientIds = new ArrayList<>();
        for(String[] name : names2){
            clientIds.add(new ClientId(name[0],name[1],name[2],name[3]));
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                String login = editText1.getText().toString();
                String password = editText2.getText().toString();
                boolean condi = false;
                for(int i=0;i<clientIds.size();i++){
                    if(login.equals(clientIds.get(i).getUsername()) && password.equals(clientIds.get(i).getPassword())){
                        condi = true;
                    }
                }
                if(condi==true){
                    Bundle bundle = new Bundle();
                    bundle.putString("client_id",login);
                    Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_accountFragment, bundle);
                }
                else{
                    if(login.equals(""))
                        showSnackBar("Enter a username please");
                    else if(password.equals(""))
                        showSnackBar("Enter a password please");
                    else{
                        showSnackBar("Wrong username or password");
                        editText1.setText("");
                        editText2.setText("");
                    }
                }
                break;
            case R.id.button2:
                Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_registerFragment);
                break;
        }
    }

    public void showSnackBar(String message){
        Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT)
                .setAction("UNDO", new View.OnClickListener(){
                    @Override
                    public void onClick(View v){

                    }
                });
        snackbar.show();
    }
}