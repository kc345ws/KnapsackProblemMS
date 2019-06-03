package com.kc345ws.knapsackproblemms.Simple;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.kc345ws.knapsackproblemms.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleInput extends AppCompatActivity {
    //private String[] str;
    private int[] itemWeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_input);

        //EditText numberEditText = findViewById(R.id.simple_number);

        /*int number = getIntent().getIntExtra("number",0);
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
        });*/

        int number = getIntent().getIntExtra("number",0);
        itemWeight = new int[number];
        //str = new String[number];
        //List<Map<String,Object>>  listitem = new ArrayList<>();
        List<MySimpleBean> listitem = new ArrayList<>();
        for(int i = 0 ; i < number ; i ++){
            MySimpleBean mySimpleBean = new MySimpleBean(i);
            listitem.add(mySimpleBean);
        }
        MySimpleAdapter mySimpleAdapter = new MySimpleAdapter(this,listitem,itemWeight);
        ListView listView = findViewById(R.id.simple_ListView);
        listView.setAdapter(mySimpleAdapter);

        Button okBTN = findViewById(R.id.simpleok_BTN);
        okBTN.setOnClickListener(new View.OnClickListener() {
            boolean isFull = true;//是否全部填写完毕
            @Override
            public void onClick(View v) {
                for(int i = 0 ; i < itemWeight.length ; i ++){
                    if(itemWeight[i] == 0){
                        isFull = false;
                        break;
                    }else{
                        isFull = true;
                    }
                }
                if(isFull == true){
                    Bundle bundle = new Bundle();
                    bundle.putIntArray("itemWeight",itemWeight);
                    Intent intent= getIntent();
                    intent.putExtras(bundle);
                    SimpleInput.this.setResult(0x10,intent);//传回每件物品的质量
                    finish();
                }else{
                    Toast.makeText(SimpleInput.this,"数据没有填写完",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

class MySimpleAdapter extends BaseAdapter{
    private Context mContext;
    private List<MySimpleBean> lists;
    private int Index = -1;
    private int[] itemWeight;
    public MySimpleAdapter(Context context , List<MySimpleBean> lists,int[] itemWeight){
        this.mContext = context;
        this.lists = lists;
        this.itemWeight = itemWeight;
    }
    @Override
    public int getCount() {
        return lists.size();
        //return 0;
    }

    @Override
    public Object getItem(int position) {
        // return null;
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        //return 0;
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        /*第一个参数position----------该视图在适配器数据中的位置
          第二个参数convertView-----旧视图
          第三个参数parent------------此视图最终会被附加到的父级视图*/
        String TempWeight;
        final ViewHolder viewHolder;
        if(convertView == null){//如果是第一次被创建
            //convertView = View.inflate(mContext,R.layout.simplelistview,null);
            convertView = LayoutInflater.from(mContext).inflate(R.layout.simplelistview,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{//如果不是第一次创建
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final MySimpleBean mySimpleBean = lists.get(position);
        viewHolder.getEditText().setTag(position);
        viewHolder.getEditText().clearFocus();
        viewHolder.getTextView().setText(mySimpleBean.getStr());
        //viewHolder.getEditText().setTag(position);

        viewHolder.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int pos = (int)viewHolder.getEditText().getTag();
                MySimpleBean bean = lists.get(pos);
                if(s.length() > 0) {
                    bean.setItemweight(Integer.parseInt(s.toString()));
                    itemWeight[pos] = Integer.parseInt(s.toString());
                }else{
                    bean.setItemweight(0);
                    itemWeight[pos] = 0;
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        if(mySimpleBean.getItemweight() == 0){
            TempWeight = "";
        }else{
            TempWeight = String.valueOf(mySimpleBean.getItemweight());
        }

        if(!TextUtils.isEmpty(TempWeight)){
            viewHolder.getEditText().setText(TempWeight);
        }else{
            viewHolder.getEditText().setText("");
        }

        /*viewHolder.getEditText().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    //Index = position;
                    EditText editText = (EditText) v;
                    Index = (int)editText.getTag();
                }
                return false;
            }
        });

        viewHolder.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText editText = (EditText)v;
                if(hasFocus){
                    editText.addTextChangedListener(myTextWatcher);
                }else{
                    editText.removeTextChangedListener(myTextWatcher);
                }
            }
        });*/

        /*viewHolder.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               if(Index >= 0 && s.length() > 0 && Index == position){
                   lists.get(Index).setItemweight(Integer.parseInt(s.toString()));
               }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });*/

        //viewHolder.getEditText().setText(TempWeight);

        //将输入框与数据进行绑定
        //viewHolder.getEditText().setTag(mySimpleBean);

        //viewHolder.getEditText().addTextChangedListener(null);//清除上个item的监听
        /*viewHolder.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //MySimpleBean bean = viewHolder.getEditText().getTag();
                if (s.length() == 0){
                    mySimpleBean.setItemweight(0);
                }else {
                    mySimpleBean.setItemweight(Integer.parseInt(s.toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

        //viewHolder.getEditText().clearFocus();
        /*if(Index != -1 && Index == position){//保证统一时刻只有一个EditText获得焦点
            viewHolder.getEditText().requestFocus();
        }else{
            viewHolder.getEditText().clearFocus();
        }*/

        //convertView.setTag(position);
        return convertView;
    }

    private TextWatcher myTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(Index !=-1 && s.length() > 0){
                MySimpleBean bean = (MySimpleBean) getItem(Index);
                bean.setItemweight(Integer.parseInt(s.toString()));
            }else{
                MySimpleBean bean = (MySimpleBean) getItem(Index);
                bean.setItemweight(0);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}

class ViewHolder{
    private TextView textView;
    private EditText editText;
    public ViewHolder(View view){
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

class MySimpleBean{
    private int index;//ListView中的索引
    private String str;//TextView
    private int itemweight;//物品质量

    public MySimpleBean(int index){
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

class MyTextWatcher implements TextWatcher {
    @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
    @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
    @Override public void afterTextChanged(Editable editable) { }
}
