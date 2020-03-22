package com.ddhuy4298.note.utils;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import java.text.SimpleDateFormat;

public class AppBinding {
    @BindingAdapter("time")
    public static void setTime(TextView tv, long time){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        tv.setText(format.format(time));
    }
}
