package cn.beingyi.androidcore.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ProgressBar;
import android.widget.TextView;

import cn.beingyi.androidcore.R;


public class DialogLoading extends ProgressDialog
{
    private AnimationSet animationSet;
    String msg;

    ProgressBar progressBar;
    TextView tv_msg;

	public DialogLoading(Context context,String msg)
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
		progressBar=findViewById(R.id.dialog_progress_ProgressBar);
		tv_msg=findViewById(R.id.dialog_progress_TextView);


		setCancelable(false);
		setCanceledOnTouchOutside(false);
		setContentView(R.layout.dialog_progress);
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
        //第一个参数fromDegrees为动画起始时的旋转角度 //第二个参数toDegrees为动画旋转到的角度
        //第三个参数pivotXType为动画在X轴相对于物件位置类型 //第四个参数pivotXValue为动画相对于物件的X坐标的开始位置
        //第五个参数pivotXType为动画在Y轴相对于物件位置类型 //第六个参数pivotYValue为动画相对于物件的Y坐标的开始位置
        animation_rotate.setRepeatCount(-1);
        animation_rotate.setStartOffset(0);
        animation_rotate.setDuration(1000);
        LinearInterpolator lir = new LinearInterpolator();
        animationSet.setInterpolator(lir);
        animationSet.addAnimation(animation_rotate);
    }
	
	
	@Override
	public void show()
	{//开启
		super.show();
	}
	
	
	
	@Override
	public void dismiss()
	{//关闭
		super.dismiss();
	}
	
	
	
	
}
