package com.example.taobaounion.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.taobaounion.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * The type Base fragment.
 */
//abstract表示一定要子类来实现里面的方法
//取消注册，不然会泄露
public abstract class BaseFragment extends Fragment{

    private View mLoadingView;
    private View mSuccessView;
    private View mErrorView;
    private View mEmptyView;
    //下面是根据状态来加载布局的参数，默认什么都隐藏
    private State currentState=State.NONE;
    public enum State{
        NONE,LOADING,SUCCESS,ERROR,EMPTY
    }

    private Unbinder mBind;
    private FrameLayout mBaseContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        加载布局
        View rootView = inflater.inflate(R.layout.base_fragment_layout, container, false);//加载一个FrameLayout坑界面
        mBaseContainer = rootView.findViewById(R.id.base_container);//加载base_fragment_layout坑里面的id
        loadStatesView(inflater,container);//把各种状态打气到容器，设置为默认隐藏
        mBind = ButterKnife.bind(this, rootView);//ButterKnife初始化
        /**
         * 下面的方法要重写
         */
        initView(rootView);//加载id
        initPresenter();//主持人处理了：得到数据，注册
        loadData();//加载数据：
        return rootView;
    }

    /**
     * 加入全部状态的view，设置为State.NONE
     *
     * @param inflater
     * @param container
     */
    private void loadStatesView(LayoutInflater inflater, ViewGroup container) {
        //成功的view
        mSuccessView = loadSuccessView(inflater, container);
        mBaseContainer.addView(mSuccessView);
        //loading 的view
        mLoadingView = loadLoadingView(inflater,container);
        mBaseContainer.addView(mLoadingView);
        //错误页面
        mErrorView = loadErrorView(inflater, container);
        mBaseContainer.addView(mErrorView);
        //内容为空的页面
        mEmptyView = loadEmptyView(inflater, container);
        mBaseContainer.addView(mEmptyView);
        //根据状态设置加入的布局
        setUpState(State.NONE);
    }

    /**
     * 子类通过这个方法来切换状态页面即可
     *
     * @param state 状态
     *
     */
    public void setUpState(State state){
        this.currentState=state;
        mSuccessView.setVisibility(currentState==State.SUCCESS ? View.VISIBLE : View.GONE);
        mLoadingView.setVisibility(currentState==State.LOADING ? View.VISIBLE : View.GONE);
        mErrorView.setVisibility(currentState==State.ERROR ? View.VISIBLE : View.GONE);
        mEmptyView.setVisibility(currentState==State.EMPTY ? View.VISIBLE : View.GONE);
    }

    /**
     * Load success view view.
     *
     * @param inflater  the inflater
     * @param container the container
     * @return the view
     */
    protected View loadSuccessView(LayoutInflater inflater, ViewGroup container) {
        int resId=getRootViewResId();//这里的到fragment的id
        return inflater.inflate(resId,container,false);
    }
    /**
     * 加载loading界面
     *
     * protected才能复写
     * @param inflater
     * @param container
     * @return
     */
    protected View loadLoadingView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_loading,container,false);
    }
    protected View loadErrorView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_error,container,false);
    }
    protected View loadEmptyView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_empty,container,false);
    }

    /**
     * Init view.
     *
     * @param rootView the root view
     */
    protected void initView(View rootView) {
    }


    /**
     * Init presenter.
     */
//不是每个都要用到这个方法，所以不用abstract
    protected void initPresenter() {
          //创建Presenter
    }

    /**
     * Load data.
     */
//protected:可以让继承者来维护，即重写
    protected void loadData() {
//        加载数据
    }
    /**
     * Gets root view res id.
     *
     * @return the root view res id
     */
    protected abstract int getRootViewResId();
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mBind!=null){
            mBind.unbind();
        }
        release();
    }
    /**
     * Release.
     */
    protected void release() {
        //释放资源
    }


}
