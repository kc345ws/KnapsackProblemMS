package com.kc345ws.knapsackproblemms.Simple;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.kc345ws.knapsackproblemms.R;

import java.util.List;

public class Simpleproblem extends AppCompatActivity {
    private int[] itemWeight;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0X10 && resultCode == 0X10){
            Bundle bundle = data.getExtras();//获取每件物品的质量
            itemWeight = bundle.getIntArray("itemWeight");
            //Toast.makeText(Simpleproblem.this,itemWeight.length,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simpleproblem);

        ImageButton simpleWight_BTN = findViewById(R.id.simpleWight_BTN);
        ImageButton simpleshow_BTN = findViewById(R.id.simpleshow_BTN);

        simpleWight_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText numberEditText = findViewById(R.id.simple_number);
                int number = 0;
                if(numberEditText.length() > 0){
                number = Integer.parseInt(numberEditText.getText().toString());
                //itemWeight = new int[number];
                }
                if(number > 0){
                Intent intent = new Intent(Simpleproblem.this,SimpleInput.class);
                //Intent intent = getIntent();
                //Bundle bundle = new Bundle();
                //bundle.putInt("number",number);
                intent.putExtra("number",number);
                //intent.putExtras(bundle);
                startActivityForResult(intent,0x10);
                }else{
                    Toast.makeText(Simpleproblem.this,"请输入物品数量",Toast.LENGTH_LONG).show();
                }
            }
        });

        simpleshow_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemWeight.length == 0){
                    Toast.makeText(Simpleproblem.this,"请先输入每件物品的质量",Toast.LENGTH_SHORT).show();
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putIntArray("itemWeight",itemWeight);
                    Intent intent = new Intent(Simpleproblem.this,Simpleshow.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }
}
