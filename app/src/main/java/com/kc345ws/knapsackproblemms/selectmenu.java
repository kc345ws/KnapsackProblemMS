package com.kc345ws.knapsackproblemms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kc345ws.knapsackproblemms.Common.Commonproblem;
import com.kc345ws.knapsackproblemms.Dim2.Dim2problem;
import com.kc345ws.knapsackproblemms.Simple.Simpleproblem;
import com.kc345ws.knapsackproblemms.ZeroOne.Zerooneproblem;

public class selectmenu extends AppCompatActivity {
    private Button simpleBTN;//简单背包问题按钮
    private Button zerooneBTN;//0-1背包问题按钮
    private Button commonBTN;//一般背包问题按钮
    private Button dim2BTN;//二维背包问题

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectmenu);

        initButton();
    }

    private void initButton(){
        simpleBTN = findViewById(R.id.Simple_BTN);
        zerooneBTN = findViewById(R.id.zeroone_BTN);
        commonBTN = findViewById(R.id.common_BTN);
        dim2BTN = findViewById(R.id.dim2_BTN);

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

        commonBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(selectmenu.this, Commonproblem.class);
                startActivity(intent);
            }
        });

        dim2BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(selectmenu.this, Dim2problem.class);
                startActivity(intent);
            }
        });
    }
}
