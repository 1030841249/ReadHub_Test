package com.zdh.readhub.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.zdh.readhub.R;
import com.zdh.readhub.base.BaseFragment;
import com.zdh.readhub.base.mvp.INetworkView;
import com.zdh.readhub.base.mvp.IPresenter;

import java.util.List;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportActivity;
import mehdi.sakout.dynamicbox.DynamicBox;

/**
 * Created by zdh on 2018/6/2.
 * 网络请求view
 */

@SuppressLint("ValidFragment")
public abstract class BaseListFragment<D> extends BaseFragment<BaseListPresenter>
        implements INetworkView<BaseListPresenter, List<D>> {

    private static final int VIEW_TYPE_LAST_ITEM = 1;

    private SupportActivity mActivity;
    public DynamicBox mBox;
    boolean hasMore = true;

    @BindView(R.id.fab)
    FloatingActionButton mFAB;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.frame_list_container)
    FrameLayout mContainer;

    private BaseAdapter<D> mAdpater = new BaseAdapter<D>() {
        @Override
        public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == VIEW_TYPE_LAST_ITEM) { // 加载脚布局
                return  new LoadingViewHolder(parent, hasMore());
            }
            // 返回普通的item
            return provideViewHodler(parent, viewType);
        }

        @Override
        public int getItemCount() {
            return super.getItemCount() == 0 ? super.getItemCount()
                    : super.getItemCount() + 1; // 有数据时，显示loadingViewHolder所以+1
        }

        @Override
        public int getItemViewType(int position) {
            if (position == getItemCount() - 1) { // 最后一个item
                return VIEW_TYPE_LAST_ITEM;
            }
            return super.getItemViewType(position);
        }

    };

    private LinearLayoutManager mManager;

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_base_list;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attchPresenter(createPresenter());
        initContent();
        if (mAdpater.getItemCount() == 0) {
            requestData();
        }
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (SupportActivity) context;
    }

    private void initContent() {
        mBox = new DynamicBox(mActivity, mContainer);
        mBox.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 加载失败时的按钮点击事件
                mAdpater.clear();
                // 重新请求数据
                requestData();
            }
        });
        mRecyclerView.setAdapter(mAdpater);
        mManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(mManager);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mManager.smoothScrollToPosition(mRecyclerView, null, 0);
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdpater.clear();
                requestData();
            }
        });

        mSwipeRefreshLayout.setColorSchemeColors(Color.parseColor("#607D8B"),
                Color.BLACK, Color.BLUE);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 上滑显示悬浮按钮
                mFAB.setVisibility((mManager.findFirstVisibleItemPosition() > 0) ? View.VISIBLE : View.GONE);

                // 没有在刷新，且有更多内容，当前滑动到最后一个item
                if (!mSwipeRefreshLayout.isRefreshing() && hasMore()
                        && mManager.findLastVisibleItemPosition() == mAdpater.getItemCount() - 1) {
                    requestMore();
                }
            }
        });
    }

    private void requestData() {
        mBox.showLoadingLayout();
        hasMore = true;
        getPreseneter().start();
    }

    private void requestMore() {
        mSwipeRefreshLayout.setRefreshing(true); // 防止多次requestmore
        getPreseneter().startRequestMore();
    }


    /**
     * 网络请求成功
     * @param data
     */
    @Override
    public void onSuccess(List<D> data) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mBox.hideAll();
        if (data != null && data.size() != 0) {
            mAdpater.addItems(data);
        }
        if (mAdpater.getItemCount() != 0) {
            RecyclerView.ViewHolder viewHolder = mRecyclerView.findViewHolderForAdapterPosition(mAdpater.getItemCount() - 1);
            if (viewHolder instanceof LoadingViewHolder) {
                ((LoadingViewHolder) viewHolder).bindTo(hasMore());
            } else {
                mAdpater.notifyItemChanged(mAdpater.getItemCount() - 1);
            }
        }

    }

    @Override
    public void onError(Throwable e) {
        mBox.setOtherExceptionMessage(e.getMessage());
        mBox.showExceptionLayout();
    }

    public boolean hasMore() {
        return hasMore;
    }

    public void onTabClick() {
        if (mManager.findFirstCompletelyVisibleItemPosition() != 0) {
            mRecyclerView.smoothScrollToPosition(0);
            return;
        }
        if (!mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(true);
            mAdpater.clear();
            requestData();
        }
    }

    public abstract BaseViewHolder<D> provideViewHodler(ViewGroup parent, int viewType);

    public abstract BaseListPresenter<D> createPresenter();

}
