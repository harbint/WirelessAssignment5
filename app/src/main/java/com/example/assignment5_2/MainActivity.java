package com.example.assignment5_2;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    SQLDatabase database;
    TextView currentBalance;
    EditText date;
    EditText price;
    EditText category;
    Button button;
    TableLayout history;
    DecimalFormat format = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new SQLDatabase(this);
        currentBalance = findViewById(R.id.currentBalance);
        date = findViewById(R.id.date);
        price = findViewById(R.id.price);
        category = findViewById(R.id.item);
        button = findViewById(R.id.button);
        history = findViewById(R.id.history);
        onClick();
        accessTable();
    }

    public void onClick(){
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double price = Double.parseDouble(MainActivity.this.price.getText().toString());
                        SQLDatabase.rowData model = new SQLDatabase.rowData();
                        model.mDate =  date.getText().toString();
                        model.mItem = category.getText().toString();
                        model.mPrice = price;
                        database.addRow(model);
                        accessTable();
                    }
                }
        );
    }

    public void accessTable(){
        int counter = history.getChildCount();
        for (int i = 1; i < counter; i++) {
            history.removeViewAt(1);
        }
        Cursor result = database.retrieveAll();
        Double balance = 0.0;
        while(result.moveToNext()){
            TableRow row = new TableRow(this);
            TableRow.LayoutParams columnLayout = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            columnLayout.weight = 1;
            TextView date = new TextView(this);
            date.setLayoutParams(columnLayout);
            date.setText(result.getString(2));
            row.addView(date);
            TextView price = new TextView(this);
            price.setLayoutParams(columnLayout);
            price.setText(result.getString(3));
            row.addView(price);
            TextView category = new TextView(this);
            category.setLayoutParams(columnLayout);
            category.setText(result.getString(1));
            row.addView(category);
            history.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            double price = Double.parseDouble(result.getString(3));
            balance += price;
        }
        MainActivity.this.currentBalance.setText("Balance = $" + format.format(balance));
    }
}
