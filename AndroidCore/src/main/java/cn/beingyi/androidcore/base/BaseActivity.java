package cn.beingyi.androidcore.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import cn.beingyi.androidcore.ui.DialogLoading;

public class BaseActivity extends AppCompatActivity {
    public Context context;
    public Activity activity;
    public DialogLoading progress;
    public boolean isInstallFromGP=false;
    public String Title;
    private void init(){
        context = this;
        this.activity=(Activity)context;
        progress=new DialogLoading(context,"加载中");



    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        init();
    }

    @Override
    protected void onStart() {
        super.onStart();


    }



    protected <T extends View> T find(int viewId) {
        return (T) findViewById(viewId);
    }

    public Context getContext() {
        return context;
    }


    public void StartActivity(Class<?> cls) {
        startActivity(new Intent(getContext(), cls));
    }


    public void finish(View view) {
        finish();
    }
}
