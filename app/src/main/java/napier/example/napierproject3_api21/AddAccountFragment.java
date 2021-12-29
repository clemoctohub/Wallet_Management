package napier.example.napierproject3_api21;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddAccountFragment extends Fragment implements View.OnClickListener{

    private Button button;
    private ImageButton imageButton;
    private EditText editText;
    private DataBaseAccount dataBaseAccount;

    public AddAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_account, container, false);
        button = view.findViewById(R.id.button_next);
        imageButton = view.findViewById(R.id.button_back);
        editText = view.findViewById(R.id.editTextTextPersonName);
        dataBaseAccount = new DataBaseAccount(getActivity());
        button.setOnClickListener(this);
        imageButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_back:
                Bundle bundle = new Bundle();
                bundle.putString("client_id",getArguments().getString("client_id"));
                Navigation.findNavController(getView()).navigate(R.id.action_addAccountFragment_to_accountFragment,bundle);
                break;
            case R.id.button_next:
                String text = editText.getText().toString();
                if(!text.equals("")){
                    List<String[]> temp = dataBaseAccount.selectAll();
                    int y=0;
                    boolean condi=false;
                    for(int i=0;i<temp.size() && condi!=true;i++){
                        if(Integer.parseInt(temp.get(i)[0])==y){
                            y++;
                        }
                        else
                            condi = true;
                    }
                    long test = dataBaseAccount.insert(Integer.toString(y),text,getArguments().getString("client_id"));
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("account_id",Integer.toString(y));
                    Navigation.findNavController(getView()).navigate(R.id.action_addAccountFragment_to_menuFragment,bundle2);
                }
                else{
                    Toast.makeText(getActivity(),"Enter a name please",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}