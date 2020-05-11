package cn.beingyi.androidcore.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import cn.beingyi.androidcore.R;


public class LoadingDialog extends ProgressDialog
{
    private AnimationSet animationSet;
    String msg;

    ImageView img_loading;
    TextView tv_msg;

	public LoadingDialog(Context context, String msg)
	{
		super(context, R.style.CustomDialog);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		init(getContext());
		
		loadIng();
	}
	
	
	private void init(Context context)
	{
		setContentView(R.layout.dialog_progress);
		img_loading=findViewById(R.id.dialog_progress_ImageView_loading);
		tv_msg=findViewById(R.id.dialog_progress_TextView);

		setCancelable(false);
		setCanceledOnTouchOutside(false);
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.width = WindowManager.LayoutParams.WRAP_CONTENT;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		getWindow().setAttributes(params);

	}
	
	
	@Override
	public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
	
	private void loadIng() {
        animationSet = new AnimationSet(true);
        RotateAnimation animation_rotate = new RotateAnimation(0, +359, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animation_rotate.setRepeatCount(-1);
        animation_rotate.setStartOffset(0);
        animation_rotate.setDuration(1000);
        LinearInterpolator lir = new LinearInterpolator();
        animationSet.setInterpolator(lir);
        animationSet.addAnimation(animation_rotate);
        img_loading.startAnimation(animation_rotate);

    }
	
	
	@Override
	public void show()
	{
		//开启
		super.show();
		loadIng();
	}
	
	
	
	@Override
	public void dismiss()
	{//关闭
		super.dismiss();
	}
	
	
	
	
}
