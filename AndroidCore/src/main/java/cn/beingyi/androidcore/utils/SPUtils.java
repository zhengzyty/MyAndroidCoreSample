package cn.beingyi.androidcore.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import cn.beingyi.androidcore.AndroidCore;
import cn.beingyi.androidcore.utils.MD5;


public class SPUtils
{
    
    public static void putString(String name, String key, String value){
        Context context= AndroidCore.getContext();
        SharedPreferences sp =context.getSharedPreferences(name,context.MODE_PRIVATE);
        Editor editor=sp.edit();
        editor.putString(MD5.encode(key),BES.encode(value,SelfInfo.getPackageName(context)));
        editor.commit();
    }
    
    public static String getString(String name, String key){
        Context context= AndroidCore.getContext();
        SharedPreferences sp =context.getSharedPreferences(name,context.MODE_PRIVATE);
        return BES.decode(sp.getString(MD5.encode(key),BES.encode("",SelfInfo.getPackageName(context))),SelfInfo.getPackageName(context));
    }

    
    
    public static void putBoolean(String name, String key, boolean value){

        Context context= AndroidCore.getContext();
        SharedPreferences sp =context.getSharedPreferences(name,context.MODE_PRIVATE);
        Editor editor=sp.edit();
        editor.putBoolean(MD5.encode(key),value);
        editor.commit();
    }

    public static boolean getBoolean(String name, String key){

        Context context= AndroidCore.getContext();
        SharedPreferences sp =context.getSharedPreferences(name,context.MODE_PRIVATE);
        return sp.getBoolean(MD5.encode(key),false);
    }


    
    public static void putInt(String name, String key, int value){

        Context context= AndroidCore.getContext();
        SharedPreferences sp =context.getSharedPreferences(name,context.MODE_PRIVATE);
        Editor editor=sp.edit();
        editor.putInt(MD5.encode(key),value);
        editor.commit();
    }

    public static int getInt(String name, String key){

        Context context= AndroidCore.getContext();
        SharedPreferences sp =context.getSharedPreferences(name,context.MODE_PRIVATE);
        return sp.getInt(MD5.encode(key),0);
    }
    
    
    public static void putLong(String name, String key, long value){

        Context context= AndroidCore.getContext();
        SharedPreferences sp =context.getSharedPreferences(name,context.MODE_PRIVATE);
        Editor editor=sp.edit();
        editor.putLong(MD5.encode(key),value);
        editor.commit();
    }

    public static long getLong(String name, String key){

        Context context= AndroidCore.getContext();
        SharedPreferences sp =context.getSharedPreferences(name,context.MODE_PRIVATE);
        return sp.getLong(MD5.encode(key),0);
    }
    
    
    public static void clear(String name) {

        Context context= AndroidCore.getContext();
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
}
