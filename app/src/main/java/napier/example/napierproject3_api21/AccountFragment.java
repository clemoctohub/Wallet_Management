package napier.example.napierproject3_api21;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AccountFragment extends Fragment implements View.OnClickListener{

    private DataBaseAccount dataBaseAccount;
    private List<String[]> names = null;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        LayoutInflater vi = (LayoutInflater)getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout insertPoint = view.findViewById(R.id.insert_point);
        dataBaseAccount = new DataBaseAccount(getActivity());
        names = dataBaseAccount.selectAll();
        for(String[] name: names){
            Log.i("message","Here : "+name[0]+" "+name[1]+" "+name[2]);
        }
        int i=0;
        for(String[] name: names){
            if(name[2].equals(getArguments().getString("client_id"))){
                View v = vi.inflate(R.layout.blocks_account,null);
                //Log.i("message","hello from "+name[0]);
                Button button = v.findViewById(R.id.testText);
                button.setText(name[1]);
                final String stp = name[0];
                insertPoint.addView(v,i,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                i++;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("account_id",stp);
                        Navigation.findNavController(getView()).navigate(R.id.action_accountFragment_to_menuFragment,bundle);
                    }
                });
            }
        }
        if(names.size()<9){
            View v = vi.inflate(R.layout.blocks_account,null);
            Button button = v.findViewById(R.id.testText);
            button.setText("Add another account");
            button.setId(R.id.view1);
            button.setOnClickListener(this);
            insertPoint.addView(v,i,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.view1:
                Bundle bundle = new Bundle();
                bundle.putString("client_id",getArguments().getString("client_id"));
                Navigation.findNavController(getView()).navigate(R.id.action_accountFragment_to_addAccountFragment,bundle);
                break;
        }
    }
}