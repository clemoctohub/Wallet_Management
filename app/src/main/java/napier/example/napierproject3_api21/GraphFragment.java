package napier.example.napierproject3_api21;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphFragment extends Fragment implements OnListenerT {

    private RecyclerView attractionsListView;
    private EditText txtV;
    private Button button;
    private TextWatcher text = null;
    private RadioButton radioButton1,radioButton2,radioButton3;
    private ArrayList<Transaction> transactions = new ArrayList<>();
    private Map<String, Integer> map = new HashMap<>();
    private DataBaseTransac dm;
    private TransactionAdapter adapter;
    private List<String[]> names2 = null;
    private String issue;

    public GraphFragment(String issue) {
        this.issue = issue;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graph, container, false);
        attractionsListView = view.findViewById(R.id.search_list);
        txtV = view.findViewById(R.id.search_bar);
        this.dm = new DataBaseTransac(getActivity());
        map.put("Business",R.drawable.ic_action_business);
        map.put("Dividend",R.drawable.ic_action_dividend);
        map.put("Education",R.drawable.ic_action_education);
        map.put("Family",R.drawable.ic_action_family);
        map.put("Gift",R.drawable.ic_action_gift);
        map.put("Health",R.drawable.ic_action_health);
        map.put("Housing",R.drawable.ic_action_housing);
        map.put("Other",R.drawable.ic_action_other);
        map.put("Pension",R.drawable.ic_action_pension);
        map.put("Repayment",R.drawable.ic_action_repayment);
        map.put("Restaurant",R.drawable.ic_action_restaurant);
        map.put("Salary",R.drawable.ic_action_salary);
        map.put("Saving money transfer",R.drawable.ic_action_saving);
        map.put("Shopping",R.drawable.ic_action_shopping);
        map.put("Sport",R.drawable.ic_action_sport);
        map.put("Transport",R.drawable.ic_action_transport);
        radioButton1 = view.findViewById(R.id.income);
        radioButton2 = view.findViewById(R.id.spent);
        radioButton3 = view.findViewById(R.id.both);
        radioButton3.setChecked(true);
        names2 = dm.selectAll();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        transactions = new ArrayList<>();
        adapter = new TransactionAdapter(getActivity(), R.layout.transaction_entry, transactions, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        attractionsListView.setLayoutManager(layoutManager);
        attractionsListView.setAdapter(adapter);
        text = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int tr = transactions.size();
                transactions.clear();
                adapter.notifyItemRangeRemoved(0,tr);
                for (String[] name : names2) {
                    if((name[1].equals(String.valueOf(txtV.getText())) || name[2].contains(String.valueOf(txtV.getText())) || name[3].contains(String.valueOf(txtV.getText()))) && name[5].equals(issue)){
                        if(radioButton3.isChecked()){
                            transactions.add(new Transaction(name[1],name[2],name[3],name[6],map.get(name[2]),name[0],name[4],name[5]));
                            adapter.notifyItemInserted(transactions.size());
                        }
                        else if(radioButton2.isChecked() && name[4].equals("0")){
                            transactions.add(new Transaction(name[1],name[2],name[3],name[6],map.get(name[2]),name[0],name[4],name[5]));
                            adapter.notifyItemInserted(transactions.size());
                        }
                        else if(radioButton1.isChecked() && name[4].equals("1")){
                            transactions.add(new Transaction(name[1],name[2],name[3],name[6],map.get(name[2]),name[0],name[4],name[5]));
                            adapter.notifyItemInserted(transactions.size());
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        txtV.addTextChangedListener(text);
    }

    @Override
    public void onClickT(int position) {
        Intent intent = new Intent(getActivity(), TransactionDetails.class);
        intent.putExtra("transaction", (Parcelable) transactions.get(position));
        startActivityForResult(intent,1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Check which request we're responding to - only 1!
        if (requestCode == 1) {
            //Make sure request was successful
            if(resultCode==3){
                Transaction returnTransaction = (Transaction) data.getExtras().getSerializable("Transaction");
                for(int i=0;i<transactions.size();i++){
                    if(transactions.get(i).getId().equals(returnTransaction.getId())){
                        transactions.set(i,returnTransaction);
                        adapter.notifyItemChanged(i);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
            else if(resultCode==2){
                String returnString = data.getStringExtra("String");
                for(int i=0;i<transactions.size();i++){
                    if(transactions.get(i).getId().equals(returnString)){
                        transactions.remove(i);
                        attractionsListView.removeViewAt(i);
                        adapter.notifyItemRemoved(i);
                        adapter.notifyItemRangeChanged(i,transactions.size());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }
}