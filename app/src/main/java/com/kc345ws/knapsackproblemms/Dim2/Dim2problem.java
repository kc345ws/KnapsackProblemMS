package com.kc345ws.knapsackproblemms.Dim2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Dim2problem extends AppCompatActivity {
    private ImageButton dim2_ok_BTN;//确定按钮
    private ImageButton dim2_back_BTN;//返回按钮
    private ImageButton dim2_addWeight_BTN;//填写质量按钮
    private ImageButton dim2_showWeight_BTN;//查看质量按钮
    private ImageButton dim2_addPrice_BTN;//填写效益按钮
    private ImageButton dim2_showPrice_BTN;//查看效益按钮
    private ImageButton dim2_addP_BTN;//添加体积按钮
    private ImageButton dim2_showP_BTN;//查看体积按钮
    private EditText dim2_MaxWeight_EDT;//最大质量EDT
    private EditText dim2_ItemNumber_EDT;//物品数量EDT
    private EditText Dim2_MaxP_EDT;//最大体积EDT
    private float[] ItemWeights;//物品质量
    private float[] ItemPrices;//物品效益
    private float[] ItemPs;//物品体积
    private boolean isNumberChanged = false;//物品数量是否改变了
    private int beforeNumber = 0;//先前的物品数量
    private int ItemNumber = 0;//物品数量

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
        if(requestCode == 0x13 && resultCode == 0x11){
            Bundle bundle = data.getExtras();
            ItemPs = bundle.getFloatArray("ItemWeights");
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dim2problem);

        initEDT();
        initButton();
    }

    private void initEDT(){//初始化EDT
        dim2_MaxWeight_EDT = findViewById(R.id.dim2_maxWeight_EDT);
        dim2_ItemNumber_EDT = findViewById(R.id.dim2_itemNumber_EDT);
        Dim2_MaxP_EDT = findViewById(R.id.dim2_maxP_EDT);

        dim2_ItemNumber_EDT.addTextChangedListener(new TextWatcher() {
            int TempNumber = 0;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if(dim2_ItemNumber_EDT.getText().length() > 0) {
                    TempNumber = Integer.parseInt(dim2_ItemNumber_EDT.getText().toString());
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
        dim2_ok_BTN = findViewById(R.id.dim2_ok_BTN);
        dim2_back_BTN = findViewById(R.id.dim2_back_BTN);
        dim2_addWeight_BTN = findViewById(R.id.dim2_addWeight_BTN);
        dim2_showWeight_BTN = findViewById(R.id.dim2_showWeight_BTN);
        dim2_addPrice_BTN = findViewById(R.id.dim2_addPrice_BTN);
        dim2_showPrice_BTN = findViewById(R.id.dim2_showPrice_BTN);
        dim2_addP_BTN = findViewById(R.id.dim2_addP_BTN);
        dim2_showP_BTN = findViewById(R.id.dim2_showP_BTN);

        //添加单击事件监听器
        dim2_ok_BTN.setOnClickListener(okBTN_ClickListener);
        dim2_back_BTN.setOnClickListener(backBTN_ClickListener);
        dim2_addWeight_BTN.setOnClickListener(addWeightBTN_ClickListener);
        dim2_showWeight_BTN.setOnClickListener(showWeightBTN_ClickListener);
        dim2_addPrice_BTN.setOnClickListener(addPriceBTN_ClickListener);
        dim2_showPrice_BTN.setOnClickListener(showPriceBTN_ClickListener);
        dim2_addP_BTN.setOnClickListener(addPBTN_Clickstener);
        dim2_showP_BTN.setOnClickListener(showPBTN_Clickstener);
    }
    public int getMaxWeight(){
        if(dim2_MaxWeight_EDT.getText().length() > 0) {
            int MaxWeight = Integer.parseInt(dim2_MaxWeight_EDT.getText().toString());
            return MaxWeight;
        }else{
            return 0;
        }
    }
    public int getMaxP(){
        if(Dim2_MaxP_EDT.getText().length() > 0){
            int MaxWeight = Integer.parseInt(Dim2_MaxP_EDT.getText().toString());
            return MaxWeight;
        }
        else{
            return 0;
        }
    }

    public boolean checkIsFull() {
        if (dim2_MaxWeight_EDT.getText().length() == 0) {//如果最大质量没有输入
            return false;
        }
        if (dim2_ItemNumber_EDT.getText().length() == 0) {//如果物品数量没有输入
            return false;
        }
        if (ItemWeights == null || ItemWeights.length == 0) {//如果物品质量没有输入
            return false;
        }
        if(ItemPs == null || ItemPs.length == 0){//如果体积没有输入
            return false;
        }
        if(ItemPrices == null || ItemPrices.length == 0){//如果效益没有输入
            return false;
        }
        return true;
    }

    private View.OnClickListener okBTN_ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (checkIsFull() == false) {
                Toast.makeText(Dim2problem.this, "请先完成数据输入", Toast.LENGTH_SHORT).show();
            } else {

                ResultForDim2Problem myResult = new ResultForDim2Problem(getMaxWeight(),ItemNumber,ItemWeights,ItemPrices,ItemPs,getMaxP());
                if(myResult.ResultState() == 0){//无解
                    AlertDialog alertDialog = new AlertDialog.Builder(Dim2problem.this).setTitle("解的情况")
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
                    AlertDialog alertDialog = new AlertDialog.Builder((Dim2problem.this)).setTitle("只有一组解")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) { }}).setItems(strings, new DialogInterface.OnClickListener() {
                                @Override public void onClick(DialogInterface dialog, int which) { }
                            }).create();
                    alertDialog.show();
                }else{//其他情况
                    if(myResult.solveProblem() == false){//无解
                        AlertDialog alertDialog = new AlertDialog.Builder(Dim2problem.this)
                                .setTitle("解的情况").setMessage("无解").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) { }}).create();
                        alertDialog.show();
                    }else {//有解
                        List<Integer[]> indexs = myResult.getIndexs();
                        List<Float>prices = myResult.getPrices();
                        String[] strings = new String[indexs.size()];
                        for(int i = 0 ; i < indexs.size() ; i++){
                            strings[i] = "第"+(i+1)+"组解\n质量:";
                            Integer[] integers = indexs.get(i);
                            float TempPrice = prices.get(i);
                            for(int j = 0 ; j < integers.length ; j++){
                                strings[i] += ItemWeights[integers[j]] +",";
                            }
                            strings[i]+="\n体积:";
                            for(int k = 0 ; k < integers.length ; k++){
                                strings[i] += ItemPs[integers[k]] +",";
                            }
                            strings[i]+="\n总效益"+TempPrice+"\n";
                        }

                        AlertDialog alertDialog = new AlertDialog.Builder(Dim2problem.this).setTitle("共有"+indexs.size()+"组解")
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
            Intent intent = new Intent(Dim2problem.this, Zeroone_addweight.class);
            Bundle bundle = new Bundle();
            if(dim2_ItemNumber_EDT.getText().length() > 0){
                ItemNumber = Integer.parseInt(dim2_ItemNumber_EDT.getText().toString());
                bundle.putInt("itemNumber" , ItemNumber);
                intent.putExtras(bundle);
                startActivityForResult(intent,0x11);
            }
            else{
                Toast.makeText(Dim2problem.this,"请先输入物品数量",Toast.LENGTH_SHORT).show();
            }
        }
    };

    private View.OnClickListener showWeightBTN_ClickListener = new View.OnClickListener(){
        public void onClick(View v){
            if(ItemWeights!=null && ItemWeights.length >0) {
                Intent intent = new Intent(Dim2problem.this, zeroone_showweight.class);
                Bundle bundle = new Bundle();
                bundle.putFloatArray("ItemWeights", ItemWeights);
                intent.putExtras(bundle);
                startActivity(intent);
            }else{
                Toast.makeText(Dim2problem.this,"请先输入质量",Toast.LENGTH_SHORT);
            }
        }
    };

    private View.OnClickListener addPBTN_Clickstener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Dim2problem.this, Zeroone_addweight.class);
            Bundle bundle = new Bundle();
            if(dim2_ItemNumber_EDT.getText().length() > 0){
                ItemNumber = Integer.parseInt(dim2_ItemNumber_EDT.getText().toString());
                bundle.putInt("itemNumber" , ItemNumber);
                intent.putExtras(bundle);
                startActivityForResult(intent,0x13);
            }
            else{
                Toast.makeText(Dim2problem.this,"请先输入物品数量",Toast.LENGTH_SHORT).show();
            }
        }
    };

    private View.OnClickListener showPBTN_Clickstener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ItemPs!=null && ItemPs.length > 0) {
                Intent intent = new Intent(Dim2problem.this, zeroone_showweight.class);
                Bundle bundle = new Bundle();
                bundle.putFloatArray("ItemWeights", ItemPs);
                intent.putExtras(bundle);
                startActivity(intent);
            }else{
                Toast.makeText(Dim2problem.this,"请先输入体积",Toast.LENGTH_SHORT);
            }
        }
    };

    private View.OnClickListener addPriceBTN_ClickListener = new View.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent(Dim2problem.this, Zeroone_addprice.class);
            Bundle bundle = new Bundle();
            if(dim2_ItemNumber_EDT.getText().length() > 0){
                ItemNumber = Integer.parseInt(dim2_ItemNumber_EDT.getText().toString());
                bundle.putInt("itemNumber" , ItemNumber);
                intent.putExtras(bundle);
                startActivityForResult(intent,0x12);
            }
            else{
                Toast.makeText(Dim2problem.this,"请先输入物品数量",Toast.LENGTH_SHORT).show();
            }
        }
    };

    private View.OnClickListener showPriceBTN_ClickListener = new View.OnClickListener(){
        public void onClick(View v){
            if (ItemPrices !=null && ItemPrices.length>0) {
                Intent intent = new Intent(Dim2problem.this, Zeroone_showprice.class);
                Bundle bundle = new Bundle();
                bundle.putFloatArray("ItemPrices", ItemPrices);
                intent.putExtras(bundle);
                startActivity(intent);
            }else{
                Toast.makeText(Dim2problem.this,"请先输入效益",Toast.LENGTH_SHORT);
            }
        }
    };
}

//二维背包问题的解决结果
class ResultForDim2Problem{
    private float MaxWeight;//最大背包质量
    private int Number;//物品数量
    private float MaxP;//最大背包体积
    private float[] ItemWeight;//每件物品的质量
    private float[] ItemPrice;//每件物品的效益
    private float[] ItemP;//每件物品的体积
    private float []CostPer; //物品的性价比排序
    private int []CostPerIndex;//物品性价比排序索引
    List<Integer[]> Indexs = new ArrayList<>();//解的索引集合
    List<Integer[]> Results = new ArrayList<>();//质量的集合
    List<Float> Prices = new ArrayList<>();
    public ResultForDim2Problem(int maxWeight,int number,float[] itemWeight,float[] itemPrice,float[] itemP,int maxP){
        MaxWeight = maxWeight;
        Number = number;
        ItemWeight = itemWeight;
        ItemPrice = itemPrice;
        ItemP = itemP;
        MaxP = maxP;
        CostPer = new float[Number];
        CostPerIndex = new int[Number];
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

    public List<Integer[]> getIndexs() {
        return Indexs;
    }

    public List<Float> getPrices() {
        return Prices;
    }

    public void sortPrice(){//按效益排序
        float price = 0;
        for(int i = 0 ; i < Indexs.size() ; i++){
            for(int j = 0 ; j < Indexs.get(i).length;j++){
                float tempprice = ItemPrice[Indexs.get(i)[j]];
                price += tempprice;
            }
            Prices.add(price);
            price = 0;
        }
        for(int i = 0 ; i < Indexs.size() - 1; i++){
            for(int j = i+1 ; j < Indexs.size() ; j++){
                if(Prices.get(i) < Prices.get(j)){
                    float Temp = Prices.get(i);
                    Prices.set(i,Prices.get(j));
                    Prices.set(j,Temp);

                    Integer[] tempIndex = Indexs.get(i);
                    Indexs.set(i,Indexs.get(j));
                    Indexs.set(j,tempIndex);

                }
            }
        }
    }

    public boolean solveProblem(){//继续解决问题
        Indexs = new ArrayList<>();//解的索引集合
        Results = new ArrayList<>();//质量的集合
        Stack indexStack = new Stack();
        Stack stack = new Stack();//
        int index = 0;
        float restMax = MaxWeight;
        float restVolume = MaxP;
        boolean hasResult = false;//是否有解
        sortCostPer();//先按性价比进行排序

        do {
            while(restMax > 0 && index < Number && restVolume > 0){//当剩余质量大于0且索引小于物品数量时进行
                if(ItemWeight[CostPerIndex[index]] <= restMax && ItemP[CostPerIndex[index]]<=restVolume){//如果索引的物品质量小于等于剩余质量
                    restMax -= ItemWeight[CostPerIndex[index]];
                    restVolume -= ItemP[CostPerIndex[index]];
                    stack.push(CostPerIndex[index]);//按性价比排序后的索引
                    indexStack.push(index);//正常索引
                }
                index++;
            }

            if(restMax!=MaxWeight && !stack.empty()){//如果剩余质量有减少说明有一组解
                hasResult = true;
                Integer[] integers =new Integer[stack.size()];
                for(int i = 0 ; i < stack.size();i++){
                    integers[i] = (int)stack.get(i);
                }
                Indexs.add(integers);
            }

            if(!stack.empty()) {
                index = (int) indexStack.pop();//弹出栈顶元素
                int p = (int)stack.pop();
                int temp = CostPerIndex[index];
                restVolume += ItemP[temp];
                restMax += ItemWeight[temp];//剩余质量重新加上物品质量
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
            float weightper = ItemWeight[i] / ItemP[i];//质量性价比,单位体积质量越小越好
            float costper = ItemPrice[i] / weightper;//效益性价比
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
}
