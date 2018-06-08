package com.zdh.readhub.base.mvp;

import android.support.annotation.NonNull;

/**
 * Created by zdh on 2018/5/31.
 */

public interface IView<P extends IPresenter> {

    P getPreseneter();

    void attchPresenter(@NonNull P presenter);

    void detachPresenter();
}
