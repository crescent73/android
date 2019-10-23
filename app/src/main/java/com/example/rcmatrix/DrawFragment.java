package com.example.rcmatrix;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

class DrawFragment extends Fragment implements BluetoothFragmentInterface {

//    private PaintView mPaintView
    private ButtonM[][] buttonMS;
    private int id;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_draw, container, false);
        MainActivity mainActivity = (MainActivity) getActivity();
        LinearLayout linearLayout=new LinearLayout(getActivity());//定义布局=new LinearLayout(getActivity());//定义布局
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout[] layout=new LinearLayout[9];

        buttonMS=new ButtonM[8][8];
        int i,j;
        //获取屏幕的宽度
        int width = getResources().getDisplayMetrics().widthPixels;
        int btnWidth=(width-470)/8;
        System.out.println("width:"+btnWidth);
        LinearLayout.LayoutParams lp;
        lp = new LinearLayout.LayoutParams(btnWidth,btnWidth);
        for(i=0;i<8;i++){
            layout[i] = new LinearLayout(getActivity());
            for (j=0;j<8;j++){
                buttonMS[i][j] = new ButtonM(getContext());
                buttonMS[i][j].setId(i*8+j);
                buttonMS[i][j].setShape(GradientDrawable.OVAL);
                buttonMS[i][j].setFillet(true);
                buttonMS[i][j].setBackColor(Color.WHITE);
                lp.setMargins(15,15,15,15);
                buttonMS[i][j].setLayoutParams(lp);
                buttonMS[i][j].setOnClickListener(v->{
                    ((ButtonM)v).setBackColor(Color.RED);
                    id = v.getId();
                    System.out.println("x:"+id/8+"  y:"+id%8);
                    System.out.println("您选择了第" + v.getId() + "个");
                    mainActivity.sendDataBluetooth('D');
                });
                layout[i].addView(buttonMS[i][j]);
            }
            linearLayout.addView(layout[i]);
        }
        //设置最后添加的按钮
        i=64;
        layout[8] = new LinearLayout(getActivity());
        layout[8].setPadding((width-600)/2,50,0,0);
        linearLayout.setPadding(100,100,100,200);
        ButtonM clearAll = new ButtonM(getContext());
        clearAll.setText("CLEAR");
        clearAll.setId(i);
        clearAll.setPadding(10,10,10,10);
        clearAll.setBackColor(Color.BLUE);
        clearAll.setTextColor(Color.WHITE);
        clearAll.setTextColorSelected(Color.WHITE);
        clearAll.setTextColori(Color.WHITE);
        clearAll.setWidth(400);
        clearAll.setHeight(150);
        layout[8].addView(clearAll);
        clearAll.setOnClickListener(v->{
            System.out.println("Clear all");
            for(int m=0;m<8;m++)
                for(int n=0;n<8;n++)
                    buttonMS[m][n].setBackColor(Color.WHITE);
            id = v.getId();
            mainActivity.sendDataBluetooth('D');
        });
        linearLayout.addView(layout[8]);
        ViewGroup viewGroup=(ViewGroup)view;//把v转成ViewGroup
        viewGroup.addView(linearLayout);

        return viewGroup;
    }

    @Override
    public byte[] getMessage(char type) {
        ByteArrayOutputStream tempStream = new ByteArrayOutputStream();
        String message=null;
        if(type == 'D'){
            message = "D";
            message += (id/8+1);
            message += (id%8+1);//id=64,应发送D90


        }else if(type == 'E'){
            message = "E";

        }
        System.out.println("message"+message);
        byte[] messageBytes = message.getBytes();

        try {
            tempStream.write(messageBytes);
//            tempStream.write(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // message type


        return tempStream.toByteArray();
    }
}