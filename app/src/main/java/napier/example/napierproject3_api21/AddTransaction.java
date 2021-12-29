package napier.example.napierproject3_api21;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class AddTransaction extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private DataBaseTransac dm;
    private EditText editText1, editText3, editText4;
    private Spinner editText2;
    private RadioButton rb0,rb1;
    private Spinner spinner;
    private String id_account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        View saveButton = findViewById(R.id.add_btnSave);
        saveButton.setOnClickListener(this);
        View back = findViewById(R.id.add_btnBack);
        back.setOnClickListener(this);

        Button date_pick = (Button) findViewById(R.id.date_picker);
        date_pick.setOnClickListener(this);

        spinner = findViewById(R.id.add_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(AddTransaction.this, R.array.type_pick, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        editText1 = (EditText) findViewById(R.id.add_amount);
        editText2 = (Spinner) findViewById(R.id.add_type);
        editText3 = (EditText) findViewById(R.id.add_date);
        editText4 = (EditText) findViewById(R.id.add_commentary);

        rb0 = (RadioButton) findViewById(R.id.radioSpent);
        rb0.setChecked(true);

        rb1 = (RadioButton) findViewById(R.id.radioIncome);

        rb0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(AddTransaction.this, R.array.type_pick, android.R.layout.simple_spinner_item);
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter1);
                }
            }
        });

        rb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(AddTransaction.this, R.array.type_pick2, android.R.layout.simple_spinner_item);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter2);
                }
            }
        });

        id_account = getIntent().getStringExtra("id_account");
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_btnBack:
                this.finish();
                break;
            case R.id.add_btnSave:
                String amount = ((EditText) findViewById(R.id.add_amount)).getText().toString();
                String type = ((Spinner) findViewById(R.id.add_type)).getSelectedItem().toString();
                String date = ((EditText) findViewById(R.id.add_date)).getText().toString();
                String commentary = ((EditText) findViewById(R.id.add_commentary)).getText().toString();
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
                    if(commentary==null)
                        commentary = "";
                    this.dm = new DataBaseTransac(this);
                    long identification;
                    String stringToReturn;
                    if (rb0.isChecked()){
                        identification = this.dm.insert(amount, type, date, "0", id_account, commentary);
                        stringToReturn = amount+" - "+type+" - "+date+" - "+identification+" - 0 - "+id_account+" - "+commentary;
                    }
                    else{
                        identification = this.dm.insert(amount, type, date, "1", id_account, commentary);
                        stringToReturn = amount+" - "+type+" - "+date+" - "+identification+" - 1 - "+id_account+" - "+commentary;
                    }
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("String", stringToReturn);
                    setResult(1, returnIntent);

                    editText1.getText().clear();
                    editText3.getText().clear();
                    editText4.getText().clear();
                    editText2.setSelection(0);
                    AddTransaction.this.finish();
                }
                break;
            case R.id.date_picker:
                DialogFragment datePicker = new DataPickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
                break;
        }
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());
        TextView textView = (TextView)findViewById(R.id.add_date);
        textView.setText(currentDateString);
    }
}