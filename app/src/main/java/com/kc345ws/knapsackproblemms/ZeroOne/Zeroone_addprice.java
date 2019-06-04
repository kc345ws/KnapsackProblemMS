package com.kc345ws.knapsackproblemms.ZeroOne;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.kc345ws.knapsackproblemms.R;

import java.util.ArrayList;
import java.util.List;

public class Zeroone_addprice extends AppCompatActivity {
    private Button zeroone_addprice_ok;
    private ListView zeroone_addprice_LV;
    private int ItemNumer;
    private float[] ItemPrices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zeroone_addprice);

        init();
    }

    private void init(){
        Bundle bundle = getIntent().getExtras();
        ItemNumer = bundle.getInt("itemNumber");
        zeroone_addprice_ok = findViewById(R.id.zeroone_addprice_ok);
        zeroone_addprice_LV = findViewById(R.id.zeroone_addprice_LV);

        zeroone_addprice_ok.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                boolean isFull = false;
                for(int i = 0 ; i < ItemNumer ; i++){
                    if(ItemPrices[i] == 0){
                        isFull = false;
                        break;
                    }else{
                        isFull =true;
                    }
                }
                if(isFull ==true){
                    Bundle bundle = new Bundle();
                    bundle.putFloatArray("ItemPrices",ItemPrices);
                    Intent intent= getIntent();
                    intent.putExtras(bundle);
                    setResult(0x12,intent);
                    finish();
                }else{
                    Toast.makeText(Zeroone_addprice.this,"请先完成数据输入(效益要求大于0)",Toast.LENGTH_SHORT).show();
                }
            }
        });

        ItemPrices = new float[ItemNumer];
        List<ZeroOne_addPrice_Bean> lists = new ArrayList<>();
        for(int i = 0 ; i < ItemNumer ; i ++){
            ZeroOne_addPrice_Bean bean = new ZeroOne_addPrice_Bean(i);
            lists.add(bean);
        }
        Zeroone_addprice_Adapter myAdapter = new Zeroone_addprice_Adapter(
                Zeroone_addprice.this,lists,ItemPrices);
        zeroone_addprice_LV.setAdapter(myAdapter);
    }
}

class Zeroone_addprice_Adapter extends BaseAdapter {//填写效益的适配器
    private Context mContext;
    private List<ZeroOne_addPrice_Bean> MyBeans;
    private float[] ItemPrices;
    public Zeroone_addprice_Adapter(Context context,List<ZeroOne_addPrice_Bean> lists,float[] itemPrices){
        mContext = context;
        MyBeans = lists;
        ItemPrices = itemPrices;
    }
    @Override
    public int getCount() { return MyBeans.size(); }
    @Override
    public Object getItem(int position) { return null; }
    @Override
    public long getItemId(int position) { return 0; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ZeroOne_addWeight_ViewHolder holder;
        if(convertView == null){
            convertView = View.inflate(mContext,R.layout.zeroone_addweight_lvitem,null);
            holder = new ZeroOne_addWeight_ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ZeroOne_addWeight_ViewHolder)convertView.getTag();
            //获取item中的组件
        }
        holder.getEditText().setTag(position);//将EditText与position动态绑定防止数据错乱
        holder.getEditText().clearFocus();
        holder.getTextView().setText(MyBeans.get(position).getTitle());

        holder.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int pos = (int)holder.getEditText().getTag();
                String str = s.toString();
                if(s.length() > 0 && !str.equals(".")){
                    MyBeans.get(pos).setPrice(Float.parseFloat(s.toString()));
                    ItemPrices[pos] = Float.parseFloat(s.toString());
                }else{
                    MyBeans.get(pos).setPrice(0);
                    ItemPrices[pos] = 0;
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        String TempWeight;
        if(MyBeans.get(position).getPrice() == 0){
            TempWeight = "";
        }else{
            TempWeight = String.valueOf(MyBeans.get(position).getPrice());
        }
        if(TempWeight.equals("")){
            holder.getEditText().setText("");
        }else{
            holder.getEditText().setText(TempWeight);
        }
        return convertView;
    }
}

class ZeroOne_addPrice_Bean{//质量item类
    private int Index;//在ListView中的索引
    private float Price;//质量
    private String Title;
    public ZeroOne_addPrice_Bean(int index){
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

class ZeroOne_addPrice_ViewHolder{//ListView item持久化类
    private TextView textView;
    private EditText editText;
    public ZeroOne_addPrice_ViewHolder(View view){
        textView = view.findViewById(R.id.zeroone_addweight_LVTV);
        editText = view.findViewById(R.id.zeroone_addweight_LVEDT);
    }
    public TextView getTextView() { return textView; }
    public EditText getEditText() { return editText; }
    public void setTextView(TextView textView) { this.textView = textView; }
    public void setEditText(EditText editText) { this.editText = editText; }
}
