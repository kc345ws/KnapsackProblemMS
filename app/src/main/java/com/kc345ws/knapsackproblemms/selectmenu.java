package com.kc345ws.knapsackproblemms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kc345ws.knapsackproblemms.Simple.Simpleproblem;

public class selectmenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectmenu);

        final Button simpleBTN = findViewById(R.id.Simple_BTN);
        simpleBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(selectmenu.this, Simpleproblem.class);
                startActivity(intent);
            }
        });
    }
}
