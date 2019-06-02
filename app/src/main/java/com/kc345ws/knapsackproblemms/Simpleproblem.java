package com.kc345ws.knapsackproblemms;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Simpleproblem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simpleproblem);

        ImageButton simpleWight_BTN = findViewById(R.id.simpleWight_BTN);
        ListView listView = findViewById(R.id.simple_ListView);

        simpleWight_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText numberEditText = findViewById(R.id.simple_number);
                int number = Integer.parseInt(numberEditText.getText().toString());
                Intent intent = new Intent(Simpleproblem.this,SimpleInput.class);
                //Intent intent = getIntent();
                //Bundle bundle = new Bundle();
                //bundle.putInt("number",number);
                intent.putExtra("number",number);
                //intent.putExtras(bundle);
                startActivityForResult(intent,0x10);
            }
        });
    }
}

class MyAdapter extends ArrayAdapter{
    public MyAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}

class MysimpleListViewData{
    private int index;//EditText索引
    public MysimpleListViewData(int index){
        this.index = index;
    }
    public int getIndex(){return index;}
}
