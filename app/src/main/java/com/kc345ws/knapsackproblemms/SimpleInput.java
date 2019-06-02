package com.kc345ws.knapsackproblemms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleInput extends AppCompatActivity {
    private String[] str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_input);


        //EditText numberEditText = findViewById(R.id.simple_number);
        int number = getIntent().getIntExtra("number",0);
        str = new String[number];
        List<Map<String,Object>>  listitem = new ArrayList<>();
        for(int i = 0 ; i < number ; i++){
            str[i] = "第"+(i+1)+"件物品质量";
            Map<String,Object> item = new HashMap<>();
            item.put("index",str[i]);
            listitem.add(item);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(this,listitem,R.layout.simplelistview,
                new String[]{"index"},new int[]{R.id.MySimpleTextView});
        ListView listView = findViewById(R.id.simple_ListView);
        listView.setAdapter(simpleAdapter);

        Button okButton = findViewById(R.id.simpleok_BTN);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
