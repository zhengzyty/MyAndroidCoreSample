package cn.beingyi.androidcore;

import android.content.Context;

import cn.beingyi.androidcore.utils.RecoveryPMS;

public class AndroidCore {
    static Context context;
    static String FileProvider;
    static int ThemeColor;

    public static void init(Context mContext){
        context=mContext;
        RecoveryPMS.recoverPMS(context,RecoveryPMS.getIPackageManager());
    }

    public static void setFileProvider(String mFileProvider){
        FileProvider=mFileProvider;
    }

    public static void setThemeColor(int color){
        ThemeColor=color;
    }


    public static Context getContext(){
        return context;
    }
    public static String getFileProvider(){
        return FileProvider;
    }
    public static int getThemeColor(){
        return ThemeColor;
    }

}
