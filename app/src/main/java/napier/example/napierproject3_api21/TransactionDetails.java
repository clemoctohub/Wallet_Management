package napier.example.napierproject3_api21;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class TransactionDetails extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    private DataBaseTransac dataBaseManipulator;
    private EditText editText1, editText3, editText4;
    private Spinner editText2;
    Transaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);

        transaction = (Transaction) getIntent().getParcelableExtra("transaction");

        if (transaction != null) {
            View saveButton = findViewById(R.id.save_btn);
            saveButton.setOnClickListener(this);
            View back = findViewById(R.id.back_btn);
            back.setOnClickListener(this);
            View delete = findViewById(R.id.delete_btn);
            delete.setOnClickListener(this);

            ImageView imageView = (ImageView)findViewById(R.id.type_image);
            imageView.setImageDrawable(getResources().getDrawable(transaction.getIconId()));

            Button date_pick = (Button) findViewById(R.id.modify_picker);
            date_pick.setOnClickListener(this);

            Spinner spinner = findViewById(R.id.modify_type);
            ArrayAdapter<CharSequence> adapter;
            if(transaction.getIdt().equals("0"))
                adapter = ArrayAdapter.createFromResource(this, R.array.type_pick, android.R.layout.simple_spinner_item);
            else
                adapter = ArrayAdapter.createFromResource(this, R.array.type_pick2, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(this);

            editText1 = (EditText) findViewById(R.id.modify_amount);
            editText2 = (Spinner) findViewById(R.id.modify_type);
            editText3 = (EditText) findViewById(R.id.modify_date);
            editText4 = (EditText) findViewById(R.id.modify_commentary);

            editText1.setText(transaction.getAmount());
            editText3.setText(transaction.getDate());
            editText4.setText(transaction.getCommentary());
            if(transaction.getIdt().equals("0"))
                switch(transaction.getType()){
                    case "Transport":
                        editText2.setSelection(1);
                        break;
                    case "Family":
                        editText2.setSelection(2);
                        break;
                    case "Sport":
                        editText2.setSelection(3);
                        break;
                    case "Gift":
                        editText2.setSelection(4);
                        break;
                    case "Restaurant":
                        editText2.setSelection(5);
                        break;
                    case "Shopping":
                        editText2.setSelection(6);
                        break;
                    case "Education":
                        editText2.setSelection(7);
                        break;
                    case "Housing":
                        editText2.setSelection(8);
                        break;
                    case "Health":
                        editText2.setSelection(9);
                        break;
                    case "Other":
                        editText2.setSelection(10);
                        break;
                    default:
                        editText2.setSelection(10);
                        break;
                }
            else
                switch(transaction.getType()){
                    case "Business":
                        editText2.setSelection(1);
                        break;
                    case "Pension":
                        editText2.setSelection(2);
                        break;
                    case "Dividend":
                        editText2.setSelection(3);
                        break;
                    case "Repayment":
                        editText2.setSelection(4);
                        break;
                    case "Salary":
                        editText2.setSelection(5);
                        break;
                    case "Saving money transfer":
                        editText2.setSelection(6);
                        break;
                    case "Other":
                        editText2.setSelection(7);
                        break;
                    default:
                        editText2.setSelection(7);
                        break;
                }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());
        TextView textView = (TextView)findViewById(R.id.modify_date);
        textView.setText(currentDateString);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                TransactionDetails.this.finish();
                break;
            case R.id.modify_picker:
                DialogFragment datePicker = new DataPickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
                break;
            case R.id.save_btn:
                String amount = ((EditText) findViewById(R.id.modify_amount)).getText().toString();
                String type = ((Spinner) findViewById(R.id.modify_type)).getSelectedItem().toString();
                String date = ((EditText) findViewById(R.id.modify_date)).getText().toString();
                String commentary = ((EditText) findViewById(R.id.modify_commentary)).getText().toString();
                if(amount.equals("") || amount.charAt(0)=='-'){
                    Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show();
                }
                else if(type.equals("") || type.equals("--Choose Type--")){
                    Toast.makeText(this, "Please select the type of the transaction", Toast.LENGTH_SHORT).show();
                }
                else if(date.equals("")){
                    Toast.makeText(this, "Please pick a date", Toast.LENGTH_SHORT).show();
                }
                else{
                    this.dataBaseManipulator = new DataBaseTransac(this);
                    this.dataBaseManipulator.updateData(transaction.getId(),amount,type,date,transaction.getIdt(),transaction.getId_account(),commentary);
                    Transaction transac;
                    Intent returnIntent = new Intent();
                    switch(type){
                        case "Transport":
                            transac = new Transaction(amount,type,date,commentary,R.drawable.ic_action_transport,transaction.getId(),transaction.getIdt(), transaction.getId_account());
                            break;
                        case "Family":
                            transac = new Transaction(amount,type,date,commentary,R.drawable.ic_action_family,transaction.getId(),transaction.getIdt(), transaction.getId_account());
                            break;
                        case "Sport":
                            transac = new Transaction(amount,type,date,commentary,R.drawable.ic_action_sport,transaction.getId(),transaction.getIdt(), transaction.getId_account());
                            break;
                        case "Gift":
                            transac = new Transaction(amount,type,date,commentary,R.drawable.ic_action_gift,transaction.getId(),transaction.getIdt(), transaction.getId_account());
                            break;
                        case "Restaurant":
                            transac = new Transaction(amount,type,date,commentary,R.drawable.ic_action_restaurant,transaction.getId(),transaction.getIdt(), transaction.getId_account());
                            break;
                        case "Shopping":
                            transac = new Transaction(amount,type,date,commentary,R.drawable.ic_action_shopping,transaction.getId(),transaction.getIdt(), transaction.getId_account());
                            break;
                        case "Education":
                            transac = new Transaction(amount,type,date,commentary,R.drawable.ic_action_education,transaction.getId(),transaction.getIdt(), transaction.getId_account());
                            break;
                        case "Housing":
                            transac = new Transaction(amount,type,date,commentary,R.drawable.ic_action_housing,transaction.getId(),transaction.getIdt(), transaction.getId_account());
                            break;
                        case "Health":
                            transac = new Transaction(amount,type,date,commentary,R.drawable.ic_action_health,transaction.getId(),transaction.getIdt(), transaction.getId_account());
                            break;
                        case "Other":
                            transac = new Transaction(amount,type,date,commentary,R.drawable.ic_action_other,transaction.getId(),transaction.getIdt(), transaction.getId_account());
                            break;
                        case "Business":
                            transac = new Transaction(amount,type,date,commentary,R.drawable.ic_action_business,transaction.getId(),transaction.getIdt(), transaction.getId_account());
                            break;
                        case "Pension":
                            transac = new Transaction(amount,type,date,commentary,R.drawable.ic_action_pension,transaction.getId(),transaction.getIdt(), transaction.getId_account());
                            break;
                        case "Dividend":
                            transac = new Transaction(amount,type,date,commentary,R.drawable.ic_action_dividend,transaction.getId(),transaction.getIdt(), transaction.getId_account());
                            break;
                        case "Repayment":
                            transac = new Transaction(amount,type,date,commentary,R.drawable.ic_action_repayment,transaction.getId(),transaction.getIdt(), transaction.getId_account());
                            break;
                        case "Salary":
                            transac = new Transaction(amount,type,date,commentary,R.drawable.ic_action_salary,transaction.getId(),transaction.getIdt(), transaction.getId_account());
                            break;
                        case "Saving money transfer":
                            transac = new Transaction(amount,type,date,commentary,R.drawable.ic_action_saving,transaction.getId(),transaction.getIdt(), transaction.getId_account());
                            break;
                        default:
                            transac = new Transaction(amount,type,date,commentary,R.drawable.ic_action_other,transaction.getId(),transaction.getIdt(), transaction.getId_account());
                            break;
                    }
                    Toast.makeText(this, transac.getId(), Toast.LENGTH_LONG).show();
                    returnIntent.putExtra("Transaction", (Parcelable) transac);
                    setResult(3, returnIntent);
                    TransactionDetails.this.finish();
                }
                break;
            case R.id.delete_btn:
                showInformationSavedDialog();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    protected void showInformationSavedDialog() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(TransactionDetails.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(TransactionDetails.this);
        }
        builder.setMessage("Are you sure you want to delete it ?");
        builder.setCancelable(false);
        builder.setNegativeButton("Yes",
                new DialogInterface.OnClickListener() {
                    private DataBaseTransac dm;
                    public void onClick(DialogInterface dialog, int which){
                        this.dm = new DataBaseTransac(getApplicationContext());
                        this.dm.deleteData(transaction.getId());
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("String", transaction.getId());
                        setResult(2, returnIntent);
                        TransactionDetails.this.finish();
                    }
                });
        builder.setPositiveButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which){
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}