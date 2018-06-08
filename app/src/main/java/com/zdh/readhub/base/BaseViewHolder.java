package com.zdh.readhub.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by zdh on 2018/5/31.
 * 封装
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    public BaseViewHolder(Context context, ViewGroup parent, int layoutRes) {
        super(LayoutInflater.from(context).inflate(layoutRes, parent, false));
        ButterKnife.bind(this, itemView);
    }

    public abstract void bindTo(T value);
}
