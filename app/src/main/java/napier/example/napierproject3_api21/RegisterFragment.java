package napier.example.napierproject3_api21;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class RegisterFragment extends Fragment implements View.OnClickListener{

    private EditText editText1, editText2, editText3, editText4, editText5;
    private Button button1, button2;
    private FrameLayout frameLayout;
    private DataBaseManipulator dm;
    private List<String[]> names = null;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        editText1 = view.findViewById(R.id.editTextU);
        editText2 = view.findViewById(R.id.editTextS);
        editText3 = view.findViewById(R.id.editTextN);
        editText4 = view.findViewById(R.id.editTextP);
        editText5 = view.findViewById(R.id.editTextC);
        button1 = view.findViewById(R.id.buttonRegister);
        button2 = view.findViewById(R.id.button3);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        frameLayout = view.findViewById(R.id.coordinator2);
        dm = new DataBaseManipulator(getActivity());
        names = dm.selectAll();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button3:
                Bundle bundle = new Bundle();
                bundle.putString("client_id",editText1.getText().toString());
                Navigation.findNavController(getView()).navigate(R.id.action_registerFragment_to_loginFragment,bundle);
                break;
            case R.id.buttonRegister:
                String edit1 = editText1.getText().toString();
                String edit2 = editText2.getText().toString();
                String edit3 = editText3.getText().toString();
                String edit4 = editText4.getText().toString();
                String edit5 = editText5.getText().toString();
                if(edit1.equals("") || edit2.equals("") || edit3.equals("") || edit4.equals("") || edit5.equals("")){
                    showSnackBar("Please fill all the inputs");
                }
                else if(!edit4.equals(edit5)){
                    showSnackBar("Please enter same password");
                }
                else if(edit4.length()<5){
                    showSnackBar("Please enter a password of at least 5 characters");
                }
                else{
                    boolean condi = false;
                    for(String name[] : names){
                        if(name[0].equals(edit1)){
                            condi = true;
                        }
                    }
                    if(condi==true){
                        showSnackBar("Enter another username");
                        editText1.setText("");
                    }
                    else{
                        dm.insert(edit1,edit2,edit3,edit4);
                        Navigation.findNavController(getView()).navigate(R.id.action_registerFragment_to_accountFragment);
                    }
                }
                break;
        }
    }

    public void showSnackBar(String message){
        Snackbar snackbar = Snackbar.make(frameLayout, message, Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener(){
                    @Override
                    public void onClick(View v){}
                });
        snackbar.show();
    }
}