package com.kc345ws.knapsackproblemms.Simple;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

public class Simpleshow extends AppCompatActivity {
    private int[] itemWeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simpleshow);

        Intent intent= Simpleshow.this.getIntent();
        Bundle bundle = intent.getExtras();
        itemWeight = bundle.getIntArray("itemWeight");
        //itemWeight = intent.getIntArrayExtra("itemWeight");

        Button backBTN = findViewById(R.id.simpeshowback_BTN);
        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ListView listViewshow = findViewById(R.id.simplelistShow);
        List<MySimpleShowBean> lists = new ArrayList<>();
        int a = itemWeight.length;
        for(int i = 0 ; i < itemWeight.length ; i++){
            MySimpleShowBean mySimpleShowBean = new MySimpleShowBean(i);
            mySimpleShowBean.setItemweight(itemWeight[i]);
            lists.add(mySimpleShowBean);
        }
        MySimpleShowAdapter mySimpleShowAdapter = new MySimpleShowAdapter(Simpleshow.this,lists);
        listViewshow.setAdapter(mySimpleShowAdapter);
    }
}

class MySimpleShowAdapter extends BaseAdapter{
    private Context mContext;
    private List<MySimpleShowBean> lists;
    public MySimpleShowAdapter(Context context , List<MySimpleShowBean> lists){
        mContext = context;
        this.lists = lists;
    }
    @Override
    public int getCount() { return lists.size(); }
    @Override
    public Object getItem(int position) { return null; }
    @Override
    public long getItemId(int position) { return 0; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SimpleShowViewHolder Holder = null;
        if(convertView == null){
            convertView = View.inflate(mContext,R.layout.simplelistviewshow,null);
            Holder = new SimpleShowViewHolder(convertView);
            convertView.setTag(Holder);
        }else{
            Holder = (SimpleShowViewHolder)convertView.getTag();
        }
        Holder.getTextView_name().setText(lists.get(position).getStr());
        Holder.getTextView_weight().setText(String.valueOf(lists.get(position).getItemweight()));
        return convertView;
    }
}

class MySimpleShowBean{
    private int index;//ListView中的索引
    private String str;//TextView
    private int itemweight;//物品质量

    public MySimpleShowBean(int index){
        this.index = index;
        this.itemweight = 0;
        str = "第" + (index + 1) + "件物品质量:";
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
    private TextView textView_name;
    private TextView textView_weight;
    public SimpleShowViewHolder(View view){
        textView_name = view.findViewById(R.id.simpleshowName_TXV);
        textView_weight = view.findViewById(R.id.simpleshowWeight_TXV);
    }

    public TextView getTextView_name() {
        return textView_name;
    }

    public TextView getTextView_weight() {
        return textView_weight;
    }

    public void setTextView_name(TextView textView_name) {
        this.textView_name = textView_name;
    }

    public void setTextView_weight(TextView textView_weight) {
        this.textView_weight = textView_weight;
    }
}


