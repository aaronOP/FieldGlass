package com.example.fieldglass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Calculator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        //Calculator


        //Floating action button to close Calculator
        FloatingActionButton fab = findViewById(R.id.fabCalc);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext()
                        , About.class));
                Toast.makeText(Calculator.this, "Return to About", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //calculate method
    public void getSelectedCheckboxes(View view) {

        //Checkboxes
        CheckBox CBMow = findViewById(R.id.CBMow);
        CheckBox CBTed = findViewById(R.id.CBTed);
        CheckBox CBRake = findViewById(R.id.CBRake);
        CheckBox CBBale = findViewById(R.id.CBBale);
        CheckBox CBCombi = findViewById(R.id.CBCombi);

        //text views
        TextView TVSerText = findViewById(R.id.TVSerText);
        final EditText ETAcre = findViewById(R.id.ETAcre);
        TextView TVTotal = findViewById(R.id.TVTotal);

        //Buttons
         Button btnMinus;
         Button btnAddition;

        btnAddition = findViewById(R.id.btnAddition2);
        btnMinus = findViewById(R.id.btnMinus2);

        btnAddition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int new_acre = Integer.parseInt(ETAcre.getText().toString().trim());
                new_acre +=1;
                ETAcre.setText(String.valueOf(new_acre));
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int new_acre = Integer.parseInt(ETAcre.getText().toString().trim());
                new_acre -=1;
                ETAcre.setText(String.valueOf(new_acre));
            }
        });

        //output sting
        String displayTotal = "";
        String totalMes = "Total Services=£";

        //Int for price
        int totalSales = 0;

        //check if checked
        if (CBMow.isChecked()) {
            displayTotal = displayTotal + CBMow.getText() + "\n";
            totalSales = totalSales + 13;
        }

        if (CBTed.isChecked()) {
            displayTotal = displayTotal + CBTed.getText() + "\n";
            totalSales = totalSales + 8;
        }

        if (CBRake.isChecked()) {
            displayTotal = displayTotal + CBRake.getText() + "\n";
            totalSales = totalSales + 9;
        }

        if (CBBale.isChecked()) {
            displayTotal = displayTotal + CBBale.getText() + "\n";
            totalSales = totalSales + 40;
        }

        if (CBCombi.isChecked()) {
            displayTotal = displayTotal + CBCombi.getText() + "\n";
            totalSales = totalSales + 64;
        }
        //total X acres
        String AcrefromET = ETAcre.getText().toString();
        int Acre = new Integer(AcrefromET).intValue();

        int GrandTotal = totalSales * Acre;

        //Display message
        TVSerText.setText(displayTotal);
        //Local prevents bugs and format is for joining different values
        TVTotal.setText(String.valueOf(("£" + GrandTotal + ".00")));

    }
}
