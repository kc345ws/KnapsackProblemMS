package com.kc345ws.knapsackproblemms.ZeroOne;

import android.content.ClipData;
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

public class Zeroone_addweight extends AppCompatActivity {
    private ListView zeroone_LV;
    private float[]ItemWeights;
    private int ItemNumber = 0;//接受到的物品数量
    private Button zeroone_addweightok_BTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zeroone_addweight);

        initButton();
        initListView();
    }

    private void initButton(){
        zeroone_addweightok_BTN = findViewById(R.id.zeroone_addweightok_BTN);

        zeroone_addweightok_BTN.setOnClickListener(new View.OnClickListener() {
            boolean isFull = false;
            @Override
            public void onClick(View v) {
                for(int i = 0 ; i < ItemNumber ; i++){
                    if(ItemWeights[i] == 0){
                        isFull = false;
                        break;
                    }else{
                        isFull =true;
                    }
                }
                if(isFull ==true){
                Bundle bundle = new Bundle();
                bundle.putFloatArray("ItemWeights",ItemWeights);
                Intent intent= getIntent();
                intent.putExtras(bundle);
                setResult(0x11,intent);
                finish();
                }else{
                    Toast.makeText(Zeroone_addweight.this,"请先完成数据输入(质量要求大于0)",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initListView(){
        zeroone_LV = findViewById(R.id.zeroone_addweight_LV);
        Intent intent = Zeroone_addweight.this.getIntent();
        Bundle bundle = intent.getExtras();
        ItemNumber = bundle.getInt("itemNumber");
        ItemWeights = new float[ItemNumber];
        List<ZeroOne_addweight_Bean> lists = new ArrayList<>();
        for(int i = 0 ; i < ItemNumber ; i ++){
            ZeroOne_addweight_Bean bean = new ZeroOne_addweight_Bean(i);
            lists.add(bean);
        }
        Zeroone_addweight_Adapter myAdapter = new Zeroone_addweight_Adapter(
                Zeroone_addweight.this,lists,ItemWeights);
        zeroone_LV.setAdapter(myAdapter);
    }
}

class Zeroone_addweight_Adapter extends BaseAdapter {//填写质量的适配器
    private Context mContext;
    private List<ZeroOne_addweight_Bean> MyBeans;
    private float[] ItemWeights;
    public Zeroone_addweight_Adapter(Context context,List<ZeroOne_addweight_Bean> lists,float[] itemWeights){
        mContext = context;
        MyBeans = lists;
        ItemWeights = itemWeights;
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
                MyBeans.get(pos).setWeight(Float.parseFloat(s.toString()));
                ItemWeights[pos] = Float.parseFloat(s.toString());
                }else{
                    MyBeans.get(pos).setWeight(0);
                    ItemWeights[pos] = 0;
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        String TempWeight;
        if(MyBeans.get(position).getWeight() == 0){
            TempWeight = "";
        }else{
            TempWeight = String.valueOf(MyBeans.get(position).getWeight());
        }
        if(TempWeight.equals("")){
            holder.getEditText().setText("");
        }else{
            holder.getEditText().setText(TempWeight);
        }
        return convertView;
    }
}

class ZeroOne_addweight_Bean{//质量item类
    private int Index;//在ListView中的索引
    private float Weight;//质量
    private String Title;
    public ZeroOne_addweight_Bean(int index){
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

class ZeroOne_addWeight_ViewHolder{//ListView item持久化类
    private TextView textView;
    private EditText editText;
    public ZeroOne_addWeight_ViewHolder(View view){
        textView = view.findViewById(R.id.zeroone_addweight_LVTV);
        editText = view.findViewById(R.id.zeroone_addweight_LVEDT);
    }
    public TextView getTextView() { return textView; }
    public EditText getEditText() { return editText; }
    public void setTextView(TextView textView) { this.textView = textView; }
    public void setEditText(EditText editText) { this.editText = editText; }
}
