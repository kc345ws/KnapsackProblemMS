package com.kc345ws.knapsackproblemms.Common;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.kc345ws.knapsackproblemms.R;
import com.kc345ws.knapsackproblemms.ZeroOne.Zeroone_addprice;
import com.kc345ws.knapsackproblemms.ZeroOne.Zeroone_addweight;
import com.kc345ws.knapsackproblemms.ZeroOne.Zeroone_showprice;
import com.kc345ws.knapsackproblemms.ZeroOne.Zerooneproblem;
import com.kc345ws.knapsackproblemms.ZeroOne.zeroone_showweight;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Commonproblem extends AppCompatActivity {
    private ImageButton zeroone_ok_BTN;//确定按钮
    private ImageButton zeroone_back_BTN;//返回按钮
    private ImageButton zeroone_addWeight_BTN;//填写质量按钮
    private ImageButton zeroone_showWeight_BTN;//查看质量按钮
    private ImageButton zeroone_addPrice_BTN;//填写效益按钮
    private ImageButton zeroone_showPrice_BTN;//查看效益按钮
    private EditText zerooneMaxWeight_EDT;//最大质量EDT
    private EditText zerooneItemNumber_EDT;//物品数量EDT
    private float[] ItemWeights;//物品质量
    private float[] ItemPrices;//物品效益
    private boolean isNumberChanged = false;//物品数量是否改变了
    private int beforeNumber = 0;//先前的物品数量
    private int ItemNumber = 0;//物品数量
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0x11 && resultCode == 0x11){//接受物品质量
            Bundle bundle = data.getExtras();
            ItemWeights = bundle.getFloatArray("ItemWeights");
        }
        if(requestCode == 0x12 && resultCode == 0x12){//接受物品效益
            Bundle bundle = data.getExtras();
            ItemPrices = bundle.getFloatArray("ItemPrices");
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zerooneproblem);

        initEDT();
        initButton();
    }

    private void initEDT(){//初始化EDT
        zerooneMaxWeight_EDT = findViewById(R.id.zerooneMaxWeight_EDT);
        zerooneItemNumber_EDT = findViewById(R.id.zerooneItemNumber_EDT);

        zerooneItemNumber_EDT.addTextChangedListener(new TextWatcher() {
            int TempNumber = 0;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if(zerooneItemNumber_EDT.getText().length() > 0) {
                    TempNumber = Integer.parseInt(zerooneItemNumber_EDT.getText().toString());
                }
                if(TempNumber != beforeNumber){
                    isNumberChanged = true;
                    beforeNumber = TempNumber;
                }else{
                    isNumberChanged = false;
                }
            }
        });
    }

    private void initButton(){//初始化按钮
        zeroone_ok_BTN = findViewById(R.id.zeroone_ok_BTN);
        zeroone_back_BTN = findViewById(R.id.zeroone_back_BTN);
        zeroone_addWeight_BTN = findViewById(R.id.zerooneItemWeight_BTN);
        zeroone_showWeight_BTN = findViewById(R.id.zerooneshowItemWeight_BTN);
        zeroone_addPrice_BTN = findViewById(R.id.zerooneaddItemPrice_BTN);
        zeroone_showPrice_BTN = findViewById(R.id.zerooneshowItemprice_BTN);

        //添加单击事件监听器
        zeroone_ok_BTN.setOnClickListener(okBTN_ClickListener);
        zeroone_back_BTN.setOnClickListener(backBTN_ClickListener);
        zeroone_addWeight_BTN.setOnClickListener(addWeightBTN_ClickListener);
        zeroone_showWeight_BTN.setOnClickListener(showWeightBTN_ClickListener);
        zeroone_addPrice_BTN.setOnClickListener(addPriceBTN_ClickListener);
        zeroone_showPrice_BTN.setOnClickListener(showPriceBTN_ClickListener);
    }
    public int getMaxWeight(){
        if(zerooneMaxWeight_EDT.getText().length() > 0) {
            int MaxWeight = Integer.parseInt(zerooneMaxWeight_EDT.getText().toString());
            return MaxWeight;
        }else{
            return 0;
        }
    }

    private View.OnClickListener okBTN_ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (checkIsFull() == false) {
                Toast.makeText(Commonproblem.this, "请先完成数据输入", Toast.LENGTH_SHORT).show();
            } else {

                ResultForCommonProblem myResult = new  ResultForCommonProblem(getMaxWeight(),ItemNumber,ItemWeights,ItemPrices);
                if(myResult.ResultState() == 0){//无解
                    AlertDialog alertDialog = new AlertDialog.Builder(Commonproblem.this).setTitle("解的情况")
                            .setMessage("无解").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) { }}).create();
                    alertDialog.show();
                }else if(myResult.ResultState() == 1){//有一个解
                    float[] items = myResult.getItemWeight();
                    String[] strings = new String[1];
                    strings[0] = "";
                    for (int i = 0 ; i < items.length ; i++){
                        strings[0]+=items[i]+",";
                    }
                    AlertDialog alertDialog = new AlertDialog.Builder((Commonproblem.this)).setTitle("只有一组解")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) { }}).setItems(strings, new DialogInterface.OnClickListener() {
                                @Override public void onClick(DialogInterface dialog, int which) { }
                            }).create();
                    alertDialog.show();
                }else{//其他情况
                    if(myResult.solveProblem() == false){//无解
                        AlertDialog alertDialog = new AlertDialog.Builder(Commonproblem.this)
                                .setTitle("解的情况").setMessage("无解").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) { }}).create();
                        alertDialog.show();
                    }else {//有解
                        List<Integer[]> indexs = myResult.getIndexs();
                        List<Float[]>volumes = myResult.getVolumes();
                        List<Float>prices = myResult.getPrices();
                        String[] strings = new String[indexs.size()];
                        for(int i = 0 ; i < indexs.size() ; i++){
                            strings[i] = "第"+(i+1)+"组解\n质量:";
                            Integer[] integers = indexs.get(i);
                            Float[] volum = volumes.get(i);
                            float TempPrice = prices.get(i);
                            for(int j = 0 ; j < integers.length ; j++){
                                strings[i] += ItemWeights[integers[j]] +",";
                            }
                            strings[i] +="\n份数:";
                            for(int k = 0 ;k < volum.length ; k ++){
                                strings[i] += volum[k] +",";
                            }
                            strings[i]+="\n总效益"+TempPrice+"\n";
                        }

                        AlertDialog alertDialog = new AlertDialog.Builder(Commonproblem.this).setTitle("共有"+indexs.size()+"组解")
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

    private View.OnClickListener backBTN_ClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) { finish(); }
    };

    private View.OnClickListener addWeightBTN_ClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Commonproblem.this, Zeroone_addweight.class);
            Bundle bundle = new Bundle();
            if(zerooneItemNumber_EDT.getText().length() > 0){
                ItemNumber = Integer.parseInt(zerooneItemNumber_EDT.getText().toString());
                bundle.putInt("itemNumber" , ItemNumber);
                intent.putExtras(bundle);
                startActivityForResult(intent,0x11);
            }
            else{
                Toast.makeText(Commonproblem.this,"请先输入物品数量",Toast.LENGTH_SHORT).show();
            }
        }
    };

    private View.OnClickListener showWeightBTN_ClickListener = new View.OnClickListener(){
        public void onClick(View v){
            if(ItemWeights != null && ItemWeights.length>0){
            Intent intent = new Intent(Commonproblem.this, zeroone_showweight.class);
            Bundle bundle =new Bundle();
            bundle.putFloatArray("ItemWeights",ItemWeights);
            intent.putExtras(bundle);
            startActivity(intent);}
            else{
                Toast.makeText(Commonproblem.this,"请先输入效益",Toast.LENGTH_SHORT);
            }
        }
    };

    private View.OnClickListener addPriceBTN_ClickListener = new View.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent(Commonproblem.this, Zeroone_addprice.class);
            Bundle bundle = new Bundle();
            if(zerooneItemNumber_EDT.getText().length() > 0){
                ItemNumber = Integer.parseInt(zerooneItemNumber_EDT.getText().toString());
                bundle.putInt("itemNumber" , ItemNumber);
                intent.putExtras(bundle);
                startActivityForResult(intent,0x12);
            }
            else{
                Toast.makeText(Commonproblem.this,"请先输入物品数量",Toast.LENGTH_SHORT).show();
            }
        }
    };

    private View.OnClickListener showPriceBTN_ClickListener = new View.OnClickListener(){
        public void onClick(View v){
            if(ItemPrices!=null&&ItemPrices.length>0) {
                Intent intent = new Intent(Commonproblem.this, Zeroone_showprice.class);
                Bundle bundle = new Bundle();
                bundle.putFloatArray("ItemPrices", ItemPrices);
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                Toast.makeText(Commonproblem.this,"请先输入效益",Toast.LENGTH_SHORT);
            }
        }
    };

    public boolean checkIsFull() {
        if (zerooneMaxWeight_EDT.getText().length() == 0) {//如果最大质量没有输入
            return false;
        }
        if (zerooneItemNumber_EDT.getText().length() == 0) {//如果物品数量没有输入
            return false;
        }
        if (ItemWeights == null || ItemWeights.length == 0) {//如果没见物品质量没有输入
            return false;
        }
        if(ItemPrices == null || ItemPrices.length == 0){//如果效益没有输入
            return false;
        }
        return true;
    }
}

//一般背包问题的解决结果
class ResultForCommonProblem{
    private int MaxWeight;//最大背包质量
    private int Number;//物品数量
    private float[] ItemWeight;//每件物品的质量
    private float[] ItemPrice;//每件物品的效益
    private float []CostPer; //物品的性价比排序
    private int []CostPerIndex;//物品性价比排序索引
    List<Integer[]> Indexs = new ArrayList<>();//解的索引集合
    List<Float[]> Volumes = new ArrayList<>();//份数
    List<Float> Prices = new ArrayList<>();
    public ResultForCommonProblem(int maxWeight,int number,float[] itemWeight,float[] itemPrice){
        MaxWeight = maxWeight;
        Number = number;
        ItemWeight = itemWeight;
        ItemPrice = itemPrice;
        CostPer = new float[Number];
        CostPerIndex = new int[Number];
    }

    public List<Integer[]> getIndexs() {
        return Indexs;
    }

    public List<Float[]> getVolumes() {
        return Volumes;
    }

    public List<Float> getPrices() {
        return Prices;
    }

    public int ResultState(){//问题解的状态 0为无解 1为只有一个解 2为需要继续判断是否有解
        float sumWeight = 0;
        for(int i = 0 ; i < Number ; i ++){
            sumWeight+=ItemWeight[i];
        }
        /*if(sumWeight < MaxWeight){//如果物品总质量小于最大质量则无解
            return 0;
        }
        else if(sumWeight == MaxWeight){//如果物品总质量刚好等于最大质量则只有一个解
            return 1;
        }else{
            return 2;//其他情况需要继续判断
        }*/
        return 2;
    }

    public float[] getItemWeight() {
        return ItemWeight;
    }


    public void sortPrice(){//按效益排序
        float price = 0;
        for(int i = 0 ; i < Indexs.size() ; i++){
            for(int j = 0 ; j < Indexs.get(i).length;j++){
                float tempprice = ItemPrice[Indexs.get(i)[j]];
                float volume = Volumes.get(i)[j];
                price += tempprice * volume;
            }
            Prices.add(price);
            price = 0;
        }

        for(int i = 0 ; i < Indexs.size() - 1; i++){
            float pricei = Prices.get(i);
            for(int j = i+1 ; j < Indexs.size() ; j++){
                float pricej = Prices.get(j);
                if(Prices.get(i) < Prices.get(j)){
                    float Temp = Prices.get(i);
                    Prices.set(i,Prices.get(j));
                    Prices.set(j,Temp);

                    Integer[] tempIndex = Indexs.get(i);
                    Indexs.set(i,Indexs.get(j));
                    Indexs.set(j,tempIndex);

                    Float[] tempVolue = Volumes.get(i);
                    Volumes.set(i,Volumes.get(j));
                    Volumes.set(j,tempVolue);
                }
            }
        }
    }

    public boolean solveProblem(){//继续解决问题
        Stack stack = new Stack();//
        Stack volumeStack = new Stack();
        Stack indexStack = new Stack();
        int index = 0;
        float restMax = MaxWeight;
        boolean hasResult = false;//是否有解
        sortCostPer();//先对性价比

        do {
            while(restMax > 0 && index < Number){//当剩余质量大于0且索引小于物品数量时进行
                if(ItemWeight[CostPerIndex[index]] <= restMax){//如果能整个物品全部放入
                    restMax -= ItemWeight[CostPerIndex[index]];
                    stack.push(CostPerIndex[index]);
                    float temp = 1.0f;
                    volumeStack.push(temp);//整个全部放入
                    indexStack.push(index);
                }else{
                    float Per = restMax / ItemWeight[CostPerIndex[index]];//放入物品的部分
                    stack.push(CostPerIndex[index]);
                    volumeStack.push(Per);
                    restMax = 0;
                    indexStack.push(index);
                }
                index++;
                /*if(ItemWeight[index] <= restMax){//如果索引的物品质量小于等于剩余质量
                    restMax -= ItemWeight[index];//剩余质量减去物品质量
                    stack.push(index);//向栈中压入物品质量的索引
                }
                index++;*/
            }

            /*if(restMax == 0){//如果剩余质量为0说明有一组解
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
            }*/
            if(restMax!=MaxWeight && !stack.empty()){//如果剩余质量有减少说明有一组解
                hasResult = true;
                Integer[] integers =new Integer[stack.size()];
                Float[] volumes = new Float[volumeStack.size()];
                for(int i = 0 ; i < stack.size();i++){
                    integers[i] = (int)stack.get(i);
                    volumes[i] = (float)volumeStack.get(i);
                }
                Indexs.add(integers);
                Volumes.add(volumes);
            }
            if(!stack.empty()) {
                index = (int)indexStack.pop();
                //index = (int) stack.pop();//弹出栈顶元素
                stack.pop();
                float volume = (float)volumeStack.pop();//弹出体积
                restMax+=ItemWeight[CostPerIndex[index]] * volume;//剩余质量重新加上物品质量
                index++;//继续尝试栈顶元素后面的元素是否能构成解
            }
        } while(!(index == Number && stack.empty()));//当索引不为Number且栈不为空时进行

        if(hasResult == true){
            sortPrice();
            return true;//如果有解则返回解空间
        }else {
            return false;
        }
    }

    private void sortCostPer(){//按性价比从高到底排序
        for(int i = 0 ; i < Number ; i++){
            float costper = ItemPrice[i] / ItemWeight[i];
            CostPer[i] = costper;
            CostPerIndex[i] = i;
        }
        for(int i = 0 ; i < Number - 1; i++){
            for(int j = i+1;j<Number;j++){
                if(CostPer[i] < CostPer[j]){
                    int Temp = CostPerIndex[i];
                    CostPerIndex[i] = CostPerIndex[j];
                    CostPerIndex[j] = Temp;
                }
            }
        }
    }
    /*private void sortWeight(){//从小到大排序质量
        float TempWeight = 0;
        for (int i = 0 ; i < Number - 1 ; i ++){
            for (int j = i + 1 ; j < Number ; j++){
                if(ItemWeight[i]>ItemWeight[j]){
                    TempWeight = ItemWeight[i];
                    ItemWeight[i] = ItemWeight[j];
                    ItemWeight[j] = TempWeight;
                }
            }
        }
    }*/
}
