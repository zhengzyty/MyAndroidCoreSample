package cn.beingyi.androidcore.update;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.net.Proxy;

import cn.beingyi.androidcore.R;
import cn.beingyi.androidcore.ui.ToastUtils;
import cn.beingyi.androidcore.utils.APKUtils;
import cn.beingyi.androidcore.utils.ActivityUtils;
import cn.beingyi.androidcore.utils.ExceptionUtils;
import cn.beingyi.androidcore.utils.FileUtils;
import cn.beingyi.androidcore.utils.SelfInfo;
import cn.beingyi.androidcore.utils.alert;

/**
 * created by zhengyu
 * on 2020/5/19
 **/
public class UpdateDialog implements View.OnClickListener {
    Context context;
    UpdateConfig config;

    Dialog dialog;

    TextView tv_title;
    TextView tv_content;
    ProgressBar pb_progress;
    TextView tv_progress;
    Button btn_cancel;
    Button btn_update;

    LinearLayout ln_downloading;

    boolean isDownloaded;

    private void initView() {
        tv_title = dialog.findViewById(R.id.TextView_title);
        tv_content = dialog.findViewById(R.id.TextView_content);
        pb_progress = dialog.findViewById(R.id.ProgressBar_progress);
        tv_progress = dialog.findViewById(R.id.TextView_progress);
        btn_cancel = dialog.findViewById(R.id.Button_cancel);
        btn_update = dialog.findViewById(R.id.Button_update);
        ln_downloading =dialog.findViewById(R.id.LinearLayout_downloading);
    }

    private void initDialog() {

        DisplayMetrics outMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;
        int heightPixels = outMetrics.heightPixels;


        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.gravity = Gravity.TOP;
        p.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        p.width = widthPixels;
        //p.alpha = 0.9f;
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setAttributes(p);
        dialog.getWindow().setWindowAnimations(R.style.ActionSheetDialogAnimation);


    }

    public UpdateDialog(Context context, UpdateConfig config) {
        this.context = context;
        this.config = config;

        this.dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialog_update);
        initView();
        initDialog();

        tv_title.setText(config.getTitle());
        tv_content.setText(config.getContent());

        btn_cancel.setOnClickListener(this);
        btn_update.setOnClickListener(this);

        ln_downloading.setVisibility(View.GONE);

        String[] permissions = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.REQUEST_INSTALL_PACKAGES
        };
        ActivityCompat.requestPermissions((Activity) context, permissions, 1);

    }


    public void show() {

        if (config.getVersionCode() > SelfInfo.getVersionCode(context)) {
            dialog.show();
        } else {
            ToastUtils.show("当前已是最新版本");
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.Button_cancel) {
            dialog.dismiss();
            if (config.isForceUpdate()){
                ((Activity)context).finish();
                ActivityUtils.getInstance().finishAllActivity();
                ActivityUtils.getInstance().AppExit(context);
                System.exit(0);
            }
        } else if (id == R.id.Button_update) {
            ln_downloading.setVisibility(View.VISIBLE);
            tv_content.setVisibility(View.GONE);
            if(isDownloaded){
                btn_update.setClickable(false);
                APKUtils.installAPK(context, config.getSaveFilePath().getAbsolutePath());
            }else {
                downAPK(config.getDownloadUrl());
            }
        }
    }



    public void downAPK(String url) {

        FileUtils.mkdir(config.getSaveFilePath().getParent());
        RequestParams requestParams = new RequestParams(url);
        requestParams.setSaveFilePath(config.getSaveFilePath().getAbsolutePath());
        requestParams.setAutoRename(true);
        requestParams.setProxy(Proxy.NO_PROXY);
        x.http().get(requestParams, new Callback.ProgressCallback<File>() {

            @Override
            public void onSuccess(File result) {
                try {
                    Thread.sleep(100);
                    APKUtils.installAPK(context, result.getAbsolutePath());
                    isDownloaded=true;
                } catch (Exception e) {
                    e.printStackTrace();
                    new alert(context, ExceptionUtils.getDetail(e));
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                new alert(context,  "下载失败:"+ ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                new alert(context,  "下载失败:"+ cex.toString());
            }

            @Override
            public void onFinished() {
                btn_update.setClickable(true);
            }

            @Override
            public void onWaiting() {
            }

            @Override
            public void onStarted() {
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                pb_progress.setMax(100);
                pb_progress.setProgress((int) ((int) current/ (total/100)));
                tv_progress.setText((int) ((int) current/ (total/100))+"/"+100);
            }
        });

    }

    public void setTitleTextColor(int color){
        tv_title.setTextColor(color);
    }

    public void setContentTextColor(int color){
        tv_content.setTextColor(color);
    }


    public void setButtonTextColor(int color){
        btn_cancel.setTextColor(color);
        btn_update.setTextColor(color);
    }



}
