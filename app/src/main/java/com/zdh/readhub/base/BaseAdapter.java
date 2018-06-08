package com.zdh.readhub.base;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by zdh on 2018/6/2.
 * 万能adapter
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder<T>>{

    private List<T> mItemList = new ArrayList<>();

    @Override
    public void onBindViewHolder(BaseViewHolder<T> holder, int position) {
        if (position < mItemList.size()) {
            holder.bindTo(mItemList.get(position)); //防止loadingviewHolder进行bindto
        }
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public void addItem(T value) {
        mItemList.add(value);
        notifyItemInserted(mItemList.size() - 1);
    }

    public void addItems(Collection<T> valueCollection) {
        int oldSize = mItemList.size();
        mItemList.addAll(oldSize, valueCollection);
        notifyItemRangeInserted(oldSize, valueCollection.size());
    }

    public void clear() {
        mItemList.clear();
        notifyDataSetChanged();
    }

    public void remove(T value) {
        if (mItemList.contains(value)) {
            int oldPosition = mItemList.indexOf(value);
            mItemList.remove(value);
            notifyItemRemoved(oldPosition);
        }
    }

}
