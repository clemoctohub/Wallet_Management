package napier.example.napierproject3_api21;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionHolder> {

    private final List<Transaction> transactionList;
    private OnListenerT onListenerT;
    private Context context;
    private int itemResource;

    public TransactionAdapter(Context context, int itemResource, List<Transaction> transactions, OnListenerT onListenerT) {
        this.transactionList = transactions;
        this.context = context;
        this.itemResource = itemResource;
        this.onListenerT = onListenerT;
    }

    @Override
    public TransactionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(this.itemResource, parent, false);
        return new TransactionHolder(this.context, view, onListenerT);
    }

    @Override
    public void onBindViewHolder(TransactionHolder holder, int position) {
        Transaction p = this.transactionList.get(position);
        // Bind the place object to the holder
        if(p.getIdt().equals("0"))
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffbb33"));
        else
            holder.itemView.setBackgroundColor(Color.parseColor("#ff99cc00"));
        holder.bindPlace(p);
    }

    @Override
    public int getItemCount() {
        return this.transactionList.size();
    }
}
