package com.kc345ws.knapsackproblemms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kc345ws.knapsackproblemms.Simple.Simpleproblem;
import com.kc345ws.knapsackproblemms.ZeroOne.Zerooneproblem;

public class selectmenu extends AppCompatActivity {
    private Button simpleBTN;//简单背包问题按钮
    private Button zerooneBTN;//0-1背包问题按钮


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectmenu);

        initButton();
    }

    private void initButton(){
        simpleBTN = findViewById(R.id.Simple_BTN);
        zerooneBTN = findViewById(R.id.zeroone_BTN);

        simpleBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(selectmenu.this, Simpleproblem.class);
                startActivity(intent);
            }
        });

        zerooneBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(selectmenu.this, Zerooneproblem.class);
                startActivity(intent);
            }
        });
    }
}
