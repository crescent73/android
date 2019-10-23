package com.example.rcmatrix;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageButton;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

import petrov.kristiyan.colorpicker.ColorPicker;

class TextFragment extends Fragment implements BluetoothFragmentInterface {

    //private ImageButton mColorImageButton;
    private EditText mInputEditText;
    private SeekBar speed;
    private SeekBar light;
    private Button send;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_text, container, false);

        //mColorImageButton = rootView.findViewById(R.id.color_image_button);
        mInputEditText = rootView.findViewById(R.id.input);
        send = rootView.findViewById(R.id.send);
        light = rootView.findViewById(R.id.seekBar1);
        speed = rootView.findViewById(R.id.seekBar2);
        MainActivity mainActivity = (MainActivity) getActivity();
//        mainActivity.sendDataBluetooth('A');
        // send data via Bluetooth on send click
//        Button send = currentFragment.getView().findViewById(R.id.send);
        ;
        send.setOnClickListener(v -> mainActivity.sendDataBluetooth('A'));//A代表发送一段文字
        System.out.println("click message");

//        SeekBar light = currentFragment.getView().findViewById(R.id.seekBar1);
        light.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                System.out.println("progress:"+seekBar.getProgress()*15/100);
                mainActivity.sendDataBluetooth('B');
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {}
        });
//        SeekBar speed = currentFragment.getView().findViewById(R.id.seekBar2);
        speed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                System.out.println("progress1:"+seekBar.getProgress()*15/100);
                mainActivity.sendDataBluetooth('C');//C代表改变速度
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {}
        });
        // show color picker on color image button click
        //mColorImageButton.setOnClickListener(v -> showColorPicker());
        // set default color for color image button
        //mColorImageButton.setBackgroundColor(getResources().getColor(R.color.color_gray_50));
        System.out.println("OK");
        return rootView;
    }

//    private void showColorPicker() {
//        ColorPicker colorPicker = new ColorPicker(Objects.requireNonNull(getActivity()));
//        colorPicker.setOnFastChooseColorListener(new ColorPicker.OnFastChooseColorListener() {
//            @Override
//            public void setOnFastChooseColorListener(int position, int color) {
//                mColorImageButton.setBackgroundColor(color);
//            }
//
//            @Override
//            public void onCancel(){
//            }
//        })
//                .setTitle(getResources().getString(R.string.color_picker_title))
//                .setColors(getResources().getIntArray(R.array.color_picker_colors))
//                .setRoundColorButton(true)
//                .show();
//    }

    @Override
    public byte[] getMessage(char type) {
        ByteArrayOutputStream tempStream = new ByteArrayOutputStream();
        // message type
        String message = "";
        byte[] messageBytes = null;
        switch (type){
            case 'A':
                message = "A";
                message += mInputEditText.getText().toString();
//                System.out.println("message:"+message);
                messageBytes = message.getBytes();
                break;
            case 'B':
                message = "B";
                message += light.getProgress()*15/100;
                System.out.println(light.getProgress()*15/100);
                messageBytes = message.getBytes();

                break;
            case 'C':
                message = "C";
                message += speed.getProgress()*15/100;
//                System.out.println(speed.getProgress()*15/100);
                messageBytes = message.getBytes();
                break;
            case 'D':
                message = "D";
                messageBytes = message.getBytes();
                break;
            case 'E':
                message = "E";
                messageBytes = message.getBytes();
                break;
        }
//        byte[] messageTypeBytes = message.getBytes();
//
//        // text
//        String text = mInputEditText.getText().toString();
//        byte[] textBytes = text.getBytes();
//        System.out.println("--------------------------------");
        System.out.println("send message:"+message);

        try {
            tempStream.write(messageBytes);
//            tempStream.write(textColorBytes);
//            tempStream.write(textBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempStream.toByteArray();
    }
}
