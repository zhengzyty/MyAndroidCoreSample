package cn.beingyi.androidcore;

import android.content.Context;

public class AndroidCore {
    static Context context;

    public static void init(Context mContext){
        context=mContext;
    }

    public static Context getContext(){
        return context;
    }
}
