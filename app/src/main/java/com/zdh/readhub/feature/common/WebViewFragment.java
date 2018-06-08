package com.zdh.readhub.feature.common;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zdh.readhub.R;
import com.zdh.readhub.app.Constant;
import com.zdh.readhub.database.DatabaseUtil;
import com.zdh.readhub.model.News;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Created by zdh on 2018/6/3.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class WebViewFragment extends SwipeBackFragment implements Toolbar.OnMenuItemClickListener{


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.txt_toolbar_title)
    TextView mTxtTitle;
    @BindView(R.id.progress_bar_loading_web)
    ProgressBar mProgressBar;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.web_view_article_page)
    WebView mWebView;

    private String mUrl;
    private News mNews;
    private boolean mIsIndependentNews; // 如果是独立的新闻资讯，则在菜单中啊计入收藏选项
    private static final String APP_CACHE_DIRNAME = "/webcache";

    public static WebViewFragment newInstance(News news, boolean mIsIndependentNews) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle bundle = new Bundle();
        // 包装使用了@Parcels注解的对象
        bundle.putParcelable(Constant.BUNDLE_NEWS, Parcels.wrap(news));
        bundle.putBoolean(Constant.BUNDLE_IS_INDEPENDENT_NEWS, mIsIndependentNews);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static WebViewFragment newInstance(String url) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.BUNDLE_URL,url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_page, container, false);
        ButterKnife.bind(this, view);
        mNews = Parcels.unwrap(getArguments().getParcelable(Constant.BUNDLE_NEWS));
        mUrl = getArguments().getString(Constant.BUNDLE_URL);
        mIsIndependentNews = getArguments().getBoolean(Constant.BUNDLE_IS_INDEPENDENT_NEWS);
        if (mNews != null) {
            mUrl = mNews.getUrl();
        }
        // 返回一个布局，其中添加了要显示的view（fragment），使用fragmentManager
        return attachToSwipeBack(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initWebView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWebView == null) {
            return;
        }
        mWebView.stopLoading();
        mWebView.clearHistory();
        mWebView.destroy();
        mWebView = null;
    }


    private void initView() {
        mSwipeRefreshLayout.setColorSchemeColors(Color.parseColor("#607D8B"),
                Color.BLACK, Color.BLUE);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
                mWebView.reload();
            }
        });

        // 设置导航图标
        mToolbar.setNavigationIcon(R.drawable.ic_toolbar_close);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });

        // 设置菜单
        mToolbar.inflateMenu(mIsIndependentNews ? R.menu.menu_webview_with_favorite : R.menu.menu_webview_page);
        mToolbar.setOnMenuItemClickListener(this);
        MenuItem item = mToolbar.getMenu().findItem(R.id.menu_item_favorite);
        if (mIsIndependentNews && item != null) {
            item.setChecked(DatabaseUtil.isExist(News.class, mNews.id));
        }
    }

    private void initWebView() {
        initWebSettings();
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (URLUtil.isNetworkUrl(url)) {
                    return super.shouldOverrideUrlLoading(view, url);
                }
                return true;
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                // 更新进度
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                    mSwipeRefreshLayout.setRefreshing(false);
                    return;
                }
                mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar.setProgress(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                // 更新标题
                mTxtTitle.setText(title);
            }
        });

        mWebView.loadUrl(mUrl);
        mSwipeRefreshLayout.setRefreshing(true);
    }

    private void initWebSettings() {
        WebSettings mWebSettings = mWebView.getSettings();
        if (mWebSettings == null) {
            return;
        }
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setDatabaseEnabled(true);
        String cacheDirPath = getContext().getFilesDir().getAbsolutePath() + APP_CACHE_DIRNAME;
        mWebSettings.setDatabasePath(cacheDirPath);
        mWebSettings.setAppCachePath(cacheDirPath);
        mWebSettings.setAppCacheEnabled(true);
        mWebSettings.setSupportZoom(true);
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebSettings.supportMultipleWindows();
        mWebSettings.setNeedInitialFocus(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSettings.setLoadsImagesAutomatically(true);
        mWebSettings.setDefaultTextEncodingName("utf-8");
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        String shareUrl;
        if (mWebView != null) {
            shareUrl = mWebView.getUrl();
        } else {
            shareUrl = mUrl;
        }

        switch (item.getItemId()) {
            case R.id.menu_item_open_by_browser:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(shareUrl));
                startActivity(Intent.createChooser(intent, getString(R.string.open_by_browser)));
                return true;
            case R.id.menu_item_copy_link:
                ClipboardManager cmb = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setPrimaryClip(ClipData.newPlainText("simple text", shareUrl));
                Toast.makeText(getContext(), R.string.copy_success, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_item_share:
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, shareUrl);
                startActivity(Intent.createChooser(share, getString(R.string.share)));
                return true;
            case R.id.menu_item_favorite:
                if (!mIsIndependentNews || mNews == null) {
                    break;
                }
                if (!item.isChecked()) {
                    DatabaseUtil.insert(mNews);
                } else {
                    DatabaseUtil.delete(mNews);
                }
                item.setChecked(!item.isChecked());
                return true;
        }
        return false;
    }

    @Override
    public boolean onBackPressedSupport() {

        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onBackPressedSupport();
    }


}
