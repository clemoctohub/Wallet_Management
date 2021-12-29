package napier.example.napierproject3_api21;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener, OnListenerT{
    private String issue;
    private RecyclerView attractionsListView;
    private ArrayList<Transaction> transactions;
    private TransactionAdapter adapter;
    private TextView txtV;

    private DataBaseTransac dm;
    private List<String[]> names2 = null;
    private ArrayList<String> stg1 = new ArrayList<>();
    private Double total;
    private Button button;

    public HomeFragment(String issue) {
        this.issue = issue;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        attractionsListView = view.findViewById(R.id.attractions_list);
        txtV = view.findViewById(R.id.total);
        button = (Button)view.findViewById(R.id.button);
        button.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        total = 0.0;
        dm = new DataBaseTransac(getActivity());
        names2 = dm.selectAll();
        String stg;

        transactions = new ArrayList<>();
        attractionsListView.setHasFixedSize(false);

        for (String[] name : names2) {
            if(name[5].equals(issue)){
                stg = name[0] + " - "
                        + name[1] + " - "
                        + name[2] + " - "
                        + name[3] + " - "
                        + name[4] + " - "
                        + name[5] + " - "
                        + name[6];
                stg1.add(stg);
                Transaction transac;
                switch(name[2]){
                    case "Transport":
                        transac = new Transaction(name[1],name[2],name[3],name[6],R.drawable.ic_action_transport,name[0], name[4], name[5]);
                        break;
                    case "Family":
                        transac = new Transaction(name[1],name[2],name[3],name[6],R.drawable.ic_action_family,name[0], name[4], name[5]);
                        break;
                    case "Sport":
                        transac = new Transaction(name[1],name[2],name[3],name[6],R.drawable.ic_action_sport,name[0], name[4], name[5]);
                        break;
                    case "Gift":
                        transac = new Transaction(name[1],name[2],name[3],name[6],R.drawable.ic_action_gift,name[0], name[4], name[5]);
                        break;
                    case "Restaurant":
                        transac = new Transaction(name[1],name[2],name[3],name[6],R.drawable.ic_action_restaurant,name[0], name[4], name[5]);
                        break;
                    case "Shopping":
                        transac = new Transaction(name[1],name[2],name[3],name[6],R.drawable.ic_action_shopping,name[0], name[4], name[5]);
                        break;
                    case "Education":
                        transac = new Transaction(name[1],name[2],name[3],name[6],R.drawable.ic_action_education,name[0], name[4], name[5]);
                        break;
                    case "Housing":
                        transac = new Transaction(name[1],name[2],name[3],name[6],R.drawable.ic_action_housing,name[0], name[4], name[5]);
                        break;
                    case "Health":
                        transac = new Transaction(name[1],name[2],name[3],name[6],R.drawable.ic_action_health,name[0], name[4], name[5]);
                        break;
                    case "Other":
                        transac = new Transaction(name[1],name[2],name[3],name[6],R.drawable.ic_action_other,name[0], name[4], name[5]);

                    case "Business":
                        transac = new Transaction(name[1],name[2],name[3],name[6],R.drawable.ic_action_business,name[0], name[4], name[5]);
                        break;
                    case "Pension":
                        transac = new Transaction(name[1],name[2],name[3],name[6],R.drawable.ic_action_pension,name[0], name[4], name[5]);
                        break;
                    case "Dividend":
                        transac = new Transaction(name[1],name[2],name[3],name[6],R.drawable.ic_action_dividend,name[0], name[4], name[5]);
                        break;
                    case "Repayment":
                        transac = new Transaction(name[1],name[2],name[3],name[6],R.drawable.ic_action_repayment,name[0], name[4], name[5]);
                        break;
                    case "Salary":
                        transac = new Transaction(name[1],name[2],name[3],name[6],R.drawable.ic_action_salary,name[0], name[4], name[5]);
                        break;
                    case "Saving money transfer":
                        transac = new Transaction(name[1],name[2],name[3],name[6],R.drawable.ic_action_saving,name[0], name[4], name[5]);
                        break;
                    default:
                        transac = new Transaction(name[1],name[2],name[3],name[6],R.drawable.ic_action_other,name[0], name[4], name[5]);
                        break;
                }
                transactions.add(transac);
                if(name[4].equals("0"))
                    total-=Double.parseDouble(name[1]);
                else
                    total+=Double.parseDouble(name[1]);
            }
        }
        if(total<0)
            txtV.setTextColor(Color.RED);
        else
            txtV.setTextColor(Color.GREEN);
        adapter = new TransactionAdapter(getActivity(), R.layout.transaction_entry, transactions, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        attractionsListView.setLayoutManager(layoutManager);
        attractionsListView.setAdapter(adapter);
        txtV.setText("Amount : "+total+" £");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                Intent i = new Intent(getActivity(), AddTransaction.class);
                i.putExtra("id_account", issue);
                startActivityForResult(i, 1);
                break;
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Check which request we're responding to - only 1!
        if (requestCode == 1) {
            //Make sure request was successful
            if (resultCode == 1) {
                String returnString = data.getStringExtra("String");
                Toast.makeText(getActivity(), returnString, Toast.LENGTH_LONG).show();
                String[] temp = new String[7];
                int x=0,y=0;
                for(int i=0;i<returnString.length();i++){
                    if(returnString.charAt(i)==' '){
                        temp[x] = returnString.substring(y,i);
                        i = i+2;
                        y = i+1;
                        x++;
                    }
                }
                Transaction transac;
                switch(temp[1]){
                    case "Transport":
                        transac = new Transaction(temp[0],temp[1],temp[2],temp[6],R.drawable.ic_action_transport,temp[3], temp[4], temp[5]);
                        break;
                    case "Family":
                        transac = new Transaction(temp[0],temp[1],temp[2],temp[6],R.drawable.ic_action_family,temp[3], temp[4], temp[5]);
                        break;
                    case "Sport":
                        transac = new Transaction(temp[0],temp[1],temp[2],temp[6],R.drawable.ic_action_sport,temp[3], temp[4], temp[5]);
                        break;
                    case "Gift":
                        transac = new Transaction(temp[0],temp[1],temp[2],temp[6],R.drawable.ic_action_gift,temp[3], temp[4], temp[5]);
                        break;
                    case "Restaurant":
                        transac = new Transaction(temp[0],temp[1],temp[2],temp[6],R.drawable.ic_action_restaurant,temp[3], temp[4], temp[5]);
                        break;
                    case "Shopping":
                        transac = new Transaction(temp[0],temp[1],temp[2],temp[6],R.drawable.ic_action_shopping,temp[3], temp[4], temp[5]);
                        break;
                    case "Education":
                        transac = new Transaction(temp[0],temp[1],temp[2],temp[6],R.drawable.ic_action_education,temp[3], temp[4], temp[5]);
                        break;
                    case "Housing":
                        transac = new Transaction(temp[0],temp[1],temp[2],temp[6],R.drawable.ic_action_housing,temp[3], temp[4], temp[5]);
                        break;
                    case "Health":
                        transac = new Transaction(temp[0],temp[1],temp[2],temp[6],R.drawable.ic_action_health,temp[3], temp[4], temp[5]);
                        break;
                    case "Other":
                        transac = new Transaction(temp[0],temp[1],temp[2],temp[6],R.drawable.ic_action_other,temp[3], temp[4], temp[5]);
                        break;
                    case "Business":
                        transac = new Transaction(temp[0],temp[1],temp[2],temp[6],R.drawable.ic_action_business,temp[3], temp[4], temp[5]);
                        break;
                    case "Pension":
                        transac = new Transaction(temp[0],temp[1],temp[2],temp[6],R.drawable.ic_action_pension,temp[3], temp[4], temp[5]);
                        break;
                    case "Dividend":
                        transac = new Transaction(temp[0],temp[1],temp[2],temp[6],R.drawable.ic_action_dividend,temp[3], temp[4], temp[5]);
                        break;
                    case "Repayment":
                        transac = new Transaction(temp[0],temp[1],temp[2],temp[6],R.drawable.ic_action_repayment,temp[3], temp[4], temp[5]);
                        break;
                    case "Salary":
                        transac = new Transaction(temp[0],temp[1],temp[2],temp[6],R.drawable.ic_action_salary,temp[3], temp[4], temp[5]);
                        break;
                    case "Saving money transfer":
                        transac = new Transaction(temp[0],temp[1],temp[2],temp[6],R.drawable.ic_action_saving,temp[3], temp[4], temp[5]);
                        break;
                    default:
                        transac = new Transaction(temp[0],temp[1],temp[2],temp[6],R.drawable.ic_action_other,temp[3], temp[4], temp[5]);
                        break;
                }
                if(temp[4].equals("0"))
                    total-=Double.parseDouble(temp[0]);
                else
                    total+=Double.parseDouble(temp[0]);
                txtV.setText("Amount : "+total+" £");
                if(total<0)
                    txtV.setTextColor(Color.RED);
                else
                    txtV.setTextColor(Color.GREEN);
                transactions.add(transac);
                adapter.notifyItemInserted(transactions.size()-1);
                adapter.notifyDataSetChanged();
            }
            else if(resultCode==3){
                Transaction returnTransaction = (Transaction) data.getExtras().getSerializable("Transaction");
                for(int i=0;i<transactions.size();i++){
                    if(transactions.get(i).getId().equals(returnTransaction.getId())){
                        if(returnTransaction.getIdt().equals("0")){
                            total+=Double.parseDouble(transactions.get(i).getAmount());
                            total-=Double.parseDouble(returnTransaction.getAmount());
                        }
                        else{
                            total-=Double.parseDouble(transactions.get(i).getAmount());
                            total+=Double.parseDouble(returnTransaction.getAmount());
                        }
                        transactions.set(i,returnTransaction);
                        adapter.notifyItemChanged(i);
                        adapter.notifyDataSetChanged();
                        txtV.setText("Amount : "+total+" £");
                        if(total<0)
                            txtV.setTextColor(Color.RED);
                        else
                            txtV.setTextColor(Color.GREEN);
                    }
                }
            }
            else if(resultCode==2){
                String returnString = data.getStringExtra("String");
                Toast.makeText(getActivity(), returnString, Toast.LENGTH_LONG).show();
                for(int i=0;i<transactions.size();i++){
                    if(transactions.get(i).getId().equals(returnString)){
                        if(transactions.get(i).getIdt().equals("0")){
                            total+=Double.parseDouble(transactions.get(i).getAmount());
                        }
                        else
                            total-=Double.parseDouble(transactions.get(i).getAmount());
                        transactions.remove(i);
                        attractionsListView.removeViewAt(i);
                        adapter.notifyItemRemoved(i);
                        adapter.notifyItemRangeChanged(i,transactions.size());
                        adapter.notifyDataSetChanged();
                        txtV.setText("Amount : "+total+" £");
                        if(total<0)
                            txtV.setTextColor(Color.RED);
                        else
                            txtV.setTextColor(Color.GREEN);
                    }
                }
            }
        }
    }

    @Override
    public void onClickT(int position) {
        Intent intent = new Intent(getActivity(), TransactionDetails.class);
        intent.putExtra("transaction", (Parcelable) transactions.get(position));
        startActivityForResult(intent,1);
    }
}