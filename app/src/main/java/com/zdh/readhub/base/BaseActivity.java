package com.zdh.readhub.base;

import android.support.annotation.NonNull;
import android.view.View;

import com.zdh.readhub.base.mvp.IPresenter;
import com.zdh.readhub.base.mvp.IView;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by zdh on 2018/5/31.
 * Activity重复操作封装
 */

public class BaseActivity<P extends IPresenter> extends SupportActivity implements IView<P>{

    private P mPresenter;
    private boolean mIsPresenterNeeded = false;

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detachPresenter();
    }

    @Override
    public P getPreseneter() {
        if (!mIsPresenterNeeded) {
            throw new RuntimeException("This activity hasn't attached any presenters,if the presenter is needed,please invoke attachPresenter() first.");
        }
        return mPresenter;
    }

    @Override
    public void attchPresenter(@NonNull P presenter) {
        mIsPresenterNeeded = true;
        mPresenter = presenter;
        presenter.attachView(this);
    }

    @Override
    public void detachPresenter() {
        if (mPresenter == null) {
            return;
        }
        mPresenter.detachView();
        mPresenter = null;
    }
}
