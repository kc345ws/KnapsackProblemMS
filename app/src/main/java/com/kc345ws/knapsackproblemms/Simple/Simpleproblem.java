package com.kc345ws.knapsackproblemms.Simple;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.xml.transform.Result;

//简单背包问题主页面
public class Simpleproblem extends AppCompatActivity {
    private int[] itemWeight;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0X10 && resultCode == 0X10) {
            Bundle bundle = data.getExtras();//获取每件物品的质量
            itemWeight = bundle.getIntArray("itemWeight");
            //Toast.makeText(Simpleproblem.this,itemWeight.length,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simpleproblem);

        ImageButton simpleWight_BTN = findViewById(R.id.simpleWight_BTN);//添加物品质量
        ImageButton simpleshow_BTN = findViewById(R.id.simpleshow_BTN);//查看物品质量按钮
        ImageButton simplaeback_BTN = findViewById(R.id.simpleback_BTN);//返回
        ImageButton simpleok_BTN = findViewById(R.id.simpleok_BTN);

        simpleok_BTN.setOnClickListener(okBTN_ClickListener);

        simplaeback_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //
        simpleWight_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText numberEditText = findViewById(R.id.simple_number);
                int number = 0;
                if (numberEditText.length() > 0) {
                    number = Integer.parseInt(numberEditText.getText().toString());
                }
                if (number > 0) {
                    Intent intent = new Intent(Simpleproblem.this, SimpleInput.class);
                    intent.putExtra("number", number);
                    startActivityForResult(intent, 0x10);
                } else {
                    Toast.makeText(Simpleproblem.this, "请输入物品数量", Toast.LENGTH_LONG).show();
                }
            }
        });

        simpleshow_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemWeight == null || itemWeight.length == 0) {
                    Toast.makeText(Simpleproblem.this, "请先输入每件物品的质量", Toast.LENGTH_SHORT).show();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putIntArray("itemWeight", itemWeight);
                    Intent intent = new Intent(Simpleproblem.this, Simpleshow.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });


    }

    public boolean checkIsFull() {
        EditText simple_MaxWeight = findViewById(R.id.simple_MaxWeight);
        EditText simple_number = findViewById(R.id.simple_number);
        if (simple_MaxWeight.getText().length() == 0) {//如果最大质量没有输入
            return false;
        }
        if (simple_number.getText().length() == 0) {//如果物品数量没有输入
            return false;
        }
        if (itemWeight == null || itemWeight.length == 0) {//如果没见物品质量没有输入
            return false;
        }
        return true;
    }

    private View.OnClickListener okBTN_ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (checkIsFull() == false) {
                Toast.makeText(Simpleproblem.this, "请先完成数据输入", Toast.LENGTH_SHORT).show();
            } else {
                ResultForSimpleProblem myResult = new ResultForSimpleProblem(getMaxWeight(),getNumber(),itemWeight);
                if(myResult.ResultState() == 0){//无解
                    AlertDialog alertDialog = new AlertDialog.Builder(Simpleproblem.this).setTitle("解的情况")
                            .setMessage("无解").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) { }}).create();
                    alertDialog.show();
                }else if(myResult.ResultState() == 1){//有一个解
                    int[] items = myResult.getItemWeight();
                    String[] strings = new String[1];
                    strings[0] = "";
                    for (int i = 0 ; i < items.length ; i++){
                        strings[0]+=items[i]+",";
                    }
                    AlertDialog alertDialog = new AlertDialog.Builder((Simpleproblem.this)).setTitle("只有一组解")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) { }}).setItems(strings, new DialogInterface.OnClickListener() {
                                @Override public void onClick(DialogInterface dialog, int which) { }
                            }).create();
                    alertDialog.show();
                }else{//其他情况
                    if(myResult.solveProblem() == null){//无解
                        AlertDialog alertDialog = new AlertDialog.Builder(Simpleproblem.this)
                                .setTitle("解的情况").setMessage("无解").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) { }}).create();
                        alertDialog.show();
                    }else{//有解
                        List<Integer[]> Results= myResult.solveProblem();//解空间
                        String[] strings = new String[Results.size()];
                        for(int i = 0 ; i < Results.size() ; i++){
                            strings[i] = "";
                            Integer[] integers = Results.get(i);
                            for(int j = 0 ; j < integers.length ; j++){
                                strings[i] += integers[j] +",";
                            }
                        }

                        AlertDialog alertDialog = new AlertDialog.Builder(Simpleproblem.this).setTitle("共有"+Results.size()+"组解")
                                .setItems(strings, new DialogInterface.OnClickListener() {@Override
                                public void onClick(DialogInterface dialog, int which) { }
                                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) { }
                                }).create();
                        alertDialog.show();
                    }
                }
            }
        }
    };

    public int getMaxWeight(){
        EditText simple_MaxWeight = findViewById(R.id.simple_MaxWeight);
        if(simple_MaxWeight.getText().length() > 0) {
            int MaxWeight = Integer.parseInt(simple_MaxWeight.getText().toString());
            return MaxWeight;
        }else{
            return 0;
        }
    }
    public int getNumber(){
        EditText simple_number = findViewById(R.id.simple_number);
        if(simple_number.getText().length()>0){
            int Number = Integer.parseInt(simple_number.getText().toString());
            return  Number;
        }else{
            return 0;
        }
    }
}

//简单背包问题的解决结果
class ResultForSimpleProblem{
    private int MaxWeight;//最大背包质量
    private int Number;//物品数量
    private int[] ItemWeight;//每件物品的数量
    public ResultForSimpleProblem(int maxWeight,int number,int[] itemWeight){
        MaxWeight = maxWeight;
        Number = number;
        ItemWeight = itemWeight;
    }

    public int ResultState(){//问题解的状态 0为无解 1为只有一个解 2为需要继续判断是否有解
        int sumWeight = 0;
        for(int i = 0 ; i < Number ; i ++){
            sumWeight+=ItemWeight[i];
        }
        if(sumWeight < MaxWeight){//如果物品总质量小于最大质量则无解
            return 0;
        }
        else if(sumWeight == MaxWeight){//如果物品总质量刚好等于最大质量则只有一个解
            return 1;
        }else{
            return 2;//其他情况需要继续判断
        }
    }

    public int[] getItemWeight() {
        return ItemWeight;
    }

    public List<Integer[]> solveProblem(){//继续解决问题
        List<Integer[]> Indexs = new ArrayList<>();//解的索引集合
        List<Integer[]> Results = new ArrayList<>();//解的集合
        Stack stack = new Stack();//
        int index = 0;
        int restMax = MaxWeight;
        boolean hasResult = false;//是否有解
        sortWeight();//先对质量进行排序

        do {
            while(restMax > 0 && index < Number){//当剩余质量大于0且索引小于物品数量时进行
                if(ItemWeight[index] <= restMax){//如果索引的物品质量小于等于剩余质量
                    restMax -= ItemWeight[index];//剩余质量减去物品质量
                    stack.push(index);//向栈中压入物品质量的索引
                }
                index++;
            }

            if(restMax == 0){//如果剩余质量为0说明有一组解
                hasResult = true;
                Integer[] result = new Integer[stack.size()];
                Integer[] weights = new Integer[stack.size()];
                for(int i = 0 ; i < stack.size() ; i ++){
                    result[i] = (Integer) stack.get(i);//构造一组解
                }
                Indexs.add(result);//向解空间压入解
                for(int i = 0 ; i < stack.size();i++){
                    weights[i] = ItemWeight[(int)stack.get(i)];
                }
                Results.add(weights);
            }
            if(!stack.empty()) {
                index = (int) stack.pop();//弹出栈顶元素
                restMax+=ItemWeight[index];//剩余质量重新加上物品质量
                index++;//继续尝试栈顶元素后面的元素是否能构成解
            }
        } while(!(index == Number && stack.empty()));//当索引不为Number且栈不为空时进行

        if(hasResult == true){
            return Results;//如果有解则返回解空间
        }else {
            return null;
        }
    }

    private void sortWeight(){//从小到大排序质量
        int TempWeight = 0;
        for (int i = 0 ; i < Number - 1 ; i ++){
            for (int j = i + 1 ; j < Number ; j++){
                if(ItemWeight[i]>ItemWeight[j]){
                    TempWeight = ItemWeight[i];
                    ItemWeight[i] = ItemWeight[j];
                    ItemWeight[j] = TempWeight;
                }
            }
        }
    }
}


