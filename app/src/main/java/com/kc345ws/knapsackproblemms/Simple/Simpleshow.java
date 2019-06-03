package com.kc345ws.knapsackproblemms.Simple;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.kc345ws.knapsackproblemms.R;

public class Simpleshow extends AppCompatActivity {
    private int[] itemWeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simpleshow);

        Intent intent= Simpleshow.this.getIntent();
        itemWeight = intent.getIntArrayExtra("itemWeight");

        Button backBTN = findViewById(R.id.simpeshowback_BTN);
        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ListView listViewshow = findViewById(R.id.simplelistShow);
    }
}

class MySimpleShowAdapter extends BaseAdapter{
    public MySimpleShowAdapter(){

    }
    @Override
    public int getCount() { return 0; }
    @Override
    public Object getItem(int position) { return null; }
    @Override
    public long getItemId(int position) { return 0; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}

class MySimpleShowBean{
    private int index;//ListView中的索引
    private String str;//TextView
    private int itemweight;//物品质量

    public MySimpleShowBean(int index){
        this.index = index;
        this.itemweight = 0;
        str = "第" + (index + 1) + "件物品质量";
    }

    public int getIndex() {
        return index;
    }

    public String getStr() {
        return str;
    }

    public int getItemweight() {
        return itemweight;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public void setItemweight(int itemweight) {
        this.itemweight = itemweight;
    }
}

class SimpleShowViewHolder{
    private TextView textView;
    private EditText editText;
    public SimpleShowViewHolder(View view){
        textView = view.findViewById(R.id.MySimpleTextView);
        editText = view.findViewById(R.id.MySimpleEDT);
    }

    public TextView getTextView() {
        return textView;
    }

    public EditText getEditText() {
        return editText;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }
}


