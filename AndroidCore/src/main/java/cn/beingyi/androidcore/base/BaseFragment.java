package cn.beingyi.androidcore.base;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public abstract class BaseFragment extends Fragment {
    public Context context;
    public Activity activity;
    /**
     * Fragment当前状态是否可见
     */
    public boolean isVisible;

    public boolean isPrepared;
    public boolean mHasLoadedOnce;
    public boolean isRequested;//是否请求成功

    /**
     * inflate布局文件 返回的view
     */
    public View mView;

    /**
     * 简化 findViewById
     *
     * @param viewId
     * @param <T>
     * @return
     */
    protected <T extends View> T find(int viewId) {
        return (T) mView.findViewById(viewId);
    }


    public BaseFragment() {
        super();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }



        /**
             * setUserVisibleHint是在onCreateView之前调用的
             * 设置Fragment可见状态
             */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        /**
         * 判断是否可见
         */
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 可见
     */
    private void onVisible() {
        lazyLoad();
    }

    /**
     * 不可见
     */
    private void onInvisible() {
    }

    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    public abstract void lazyLoad();
    
    public void StartActivity(Class<?> cls) {
        startActivity(new Intent(getContext(), cls));
    }
    
}

