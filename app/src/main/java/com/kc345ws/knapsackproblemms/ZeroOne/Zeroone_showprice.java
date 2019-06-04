package com.kc345ws.knapsackproblemms.ZeroOne;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.kc345ws.knapsackproblemms.R;

import java.util.ArrayList;
import java.util.List;

public class Zeroone_showprice extends AppCompatActivity {
    private Button zeroone_showprice_backBTN;
    private ListView zeroone_showprice_LV;
    private float[]ItemPrices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zeroone_showprice);

        init();
    }

    private void init(){
        Bundle bundle = getIntent().getExtras();
        ItemPrices = bundle.getFloatArray("ItemPrices");
        zeroone_showprice_backBTN = findViewById(R.id.zeroone_showprice_back);
        zeroone_showprice_LV = findViewById(R.id.zeroone_showprice_LV);

        zeroone_showprice_backBTN.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { finish(); }});

        List<ZeroOne_showPrice_Bean> lists = new ArrayList<>();
        for(int i = 0 ; i < ItemPrices.length ; i ++){
            ZeroOne_showPrice_Bean bean = new ZeroOne_showPrice_Bean(i);
            bean.setPrice(ItemPrices[i]);
            lists.add(bean);
        }
        Zeroone_showPrice_Adapter myAdapter = new Zeroone_showPrice_Adapter(
                Zeroone_showprice.this,lists);
        zeroone_showprice_LV.setAdapter(myAdapter);
    }
}

class Zeroone_showPrice_Adapter extends BaseAdapter {//填写质量的适配器
    private Context mContext;
    private List<ZeroOne_showPrice_Bean> MyBeans;
    public Zeroone_showPrice_Adapter(Context context,List<ZeroOne_showPrice_Bean> lists){
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
        holder.getTextView_Weight().setText(String.valueOf(MyBeans.get(position).getPrice()));
        return convertView;
    }
}

class ZeroOne_showPrice_Bean{//质量item类
    private int Index;//在ListView中的索引
    private float Price;//质量
    private String Title;
    public ZeroOne_showPrice_Bean(int index){
        Index = index;
        Price = 0;
        Title ="第"+(Index+1)+"件物品效益:";
    }
    public int getIndex() { return Index; }
    public float getPrice() { return Price; }
    public String getTitle(){return Title;}
    public void setIndex(int index) { Index = index; }
    public void setPrice(float weight) { Price = weight; }
    public void setTitle(String title){Title =title;}
}

class ZeroOne_showPrice_ViewHolder{//ListView item持久化类
    private TextView textView_name;
    private TextView textView_Price;
    public ZeroOne_showPrice_ViewHolder(View view){
        textView_name = view.findViewById(R.id.zeroone_showweight_NAMETV);
        textView_Price = view.findViewById(R.id.zeroone_showweight_WEIGHTTV);
    }
    public TextView getTextView_name() { return textView_name; }
    public TextView gettextView_Price() { return textView_Price; }
    public void setTextView_name(TextView textView_name) { this.textView_name = textView_name; }
    public void settextView_Price(TextView textView_Weight) { this.textView_Price = textView_Weight; }
}
