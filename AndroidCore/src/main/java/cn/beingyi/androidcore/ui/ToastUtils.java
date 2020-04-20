package cn.beingyi.androidcore.ui;

import android.content.Context;
import android.widget.Toast;

import cn.beingyi.androidcore.AndroidCore;


public class ToastUtils {

    public static void show(String msg) {
        show(msg, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int msg) {
        show(context.getString(msg), Toast.LENGTH_SHORT);
    }

    public static void showLong(String msg) {
        show(msg, Toast.LENGTH_LONG);
    }


    private static void show(String message, int show_length) {
        Context context = AndroidCore.getContext();
        Toast.makeText(context,message,show_length).show();
    }


}

