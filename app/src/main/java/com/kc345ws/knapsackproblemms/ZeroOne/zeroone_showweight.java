package com.kc345ws.knapsackproblemms.ZeroOne;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class zeroone_showweight extends AppCompatActivity {
    private Button zeroone_showweight_backBTN;
    private ListView zeroone_showweight_LV;
    private float[]ItemWeights;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zeroone_showweight);

        init();
    }

    private void init(){
        Bundle bundle = getIntent().getExtras();
        ItemWeights = bundle.getFloatArray("ItemWeights");
        zeroone_showweight_backBTN = findViewById(R.id.zeroone_showweight_backBTN);
        zeroone_showweight_LV = findViewById(R.id.zeroone_showweight_LV);

        zeroone_showweight_backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        List<ZeroOne_showweight_Bean> lists = new ArrayList<>();
        for(int i = 0 ; i < ItemWeights.length ; i ++){
            ZeroOne_showweight_Bean bean = new ZeroOne_showweight_Bean(i);
            bean.setWeight(ItemWeights[i]);
            lists.add(bean);
        }
        Zeroone_showweight_Adapter myAdapter = new Zeroone_showweight_Adapter(
                zeroone_showweight.this,lists);
        zeroone_showweight_LV.setAdapter(myAdapter);
    }
}

class Zeroone_showweight_Adapter extends BaseAdapter {//填写质量的适配器
    private Context mContext;
    private List<ZeroOne_showweight_Bean> MyBeans;
    public Zeroone_showweight_Adapter(Context context,List<ZeroOne_showweight_Bean> lists){
        mContext = context;
        MyBeans = lists;
    }
    @Override
    public int getCount() { return MyBeans.size(); }
    @Override
    public Object getItem(int position) { return null; }
    @Override
    public long getItemId(int position) { return 0; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ZeroOne_showWeight_ViewHolder holder;
        if(convertView == null){
            convertView = View.inflate(mContext,R.layout.zeroone_showweight_lvitem,null);
            holder = new ZeroOne_showWeight_ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ZeroOne_showWeight_ViewHolder)convertView.getTag();
            //获取item中的组件
        }
        holder.getTextView_name().setText(MyBeans.get(position).getTitle());
        holder.getTextView_Weight().setText(String.valueOf(MyBeans.get(position).getWeight()));
        return convertView;
    }
}

class ZeroOne_showweight_Bean{//质量item类
    private int Index;//在ListView中的索引
    private float Weight;//质量
    private String Title;
    public ZeroOne_showweight_Bean(int index){
        Index = index;
        Weight = 0;
        Title ="第"+(Index+1)+"件物品质量:";
    }
    public int getIndex() { return Index; }
    public float getWeight() { return Weight; }
    public String getTitle(){return Title;}
    public void setIndex(int index) { Index = index; }
    public void setWeight(float weight) { Weight = weight; }
    public void setTitle(String title){Title =title;}
}

class ZeroOne_showWeight_ViewHolder{//ListView item持久化类
    private TextView textView_name;
    private TextView textView_Weight;
    public ZeroOne_showWeight_ViewHolder(View view){
        textView_name = view.findViewById(R.id.zeroone_showweight_NAMETV);
        textView_Weight = view.findViewById(R.id.zeroone_showweight_WEIGHTTV);
    }
    public TextView getTextView_name() { return textView_name; }
    public TextView getTextView_Weight() { return textView_Weight; }
    public void setTextView_name(TextView textView_name) { this.textView_name = textView_name; }
    public void setTextView_Weight(TextView textView_Weight) { this.textView_Weight = textView_Weight; }
}