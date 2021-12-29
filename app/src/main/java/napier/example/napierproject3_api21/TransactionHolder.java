package napier.example.napierproject3_api21;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class TransactionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView transactionName;
    private final TextView transactionDate;
    private final ImageView transactionIcon;
    private OnListenerT onListenerT;
    private Transaction transaction;
    private Context context;

    public TransactionHolder(Context context, View itemView, OnListenerT onListenerT) {
        super(itemView);

        this.context = context;
        this.onListenerT = onListenerT;
        this.transactionIcon = (ImageView) itemView.findViewById(R.id.entry_transaction_icon);
        this.transactionName = (TextView) itemView.findViewById(R.id.entry_transaction_name);
        this.transactionDate = (TextView) itemView.findViewById(R.id.entry_transaction_date);
        itemView.setOnClickListener(this);
    }

    public void bindPlace(Transaction transaction) {
        // Bind the data to the ViewHolder
        this.transaction = transaction;
        this.transactionName.setText(transaction.getAmount()+" £");
        this.transactionDate.setText(transaction.getDate());
        this.transactionIcon.setImageDrawable(this.context.getResources().getDrawable(transaction.getIconId()));
        /*if(color.equals("FFA500")){
            transactionName.setTextColor(Color.parseColor(#FF99CC00));
        }
        else{
            transactionName.setTextColor(#ffffbb33);
        }*/

    }

    @Override
    public void onClick(View v) {
        if (this.transaction != null) {
            onListenerT.onClickT(getAdapterPosition());
        }
    }
}
