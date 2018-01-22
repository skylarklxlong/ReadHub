package online.himakeit.readhub.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import online.himakeit.readhub.R;
import online.himakeit.readhub.utils.ThreadManager;

/**
 * @author：LiXueLong
 * @date：2018/1/20
 * @mail1：skylarklxlong@outlook.com
 * @mail2：li_xuelong@126.com
 * @des:
 */
public abstract class ReadHubFragment extends BaseFragment {

    private static final int ACTION_INIT = 0;
    private static final int ACTION_REFRESH = 1;
    private static final int ACTION_LOAD_MORE = 2;

    protected XRecyclerView mXRecyclerView;

    protected String mCursor = "";
    protected int pageSize = 10;

    private int mCurrentAction = ACTION_INIT;

    private Button mBtnRefresh = null;
    private LinearLayout mLLErroePage = null;

    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_readhub, container, false);
    }

    @Override
    protected void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getmContext());
        mXRecyclerView = customFindViewById(R.id.xrecyclerview);
        setAdapter(mXRecyclerView);
        mXRecyclerView.setLayoutManager(linearLayoutManager);
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                switchAction(ACTION_REFRESH);
            }

            @Override
            public void onLoadMore() {
                switchAction(ACTION_LOAD_MORE);
            }
        });
        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallClipRotatePulse);
        mXRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        TextView textView = new TextView(getmContext());
        textView.setText("Empty");
        mXRecyclerView.setEmptyView(textView);
        mXRecyclerView.setRefreshing(true);
        mXRecyclerView.setVisibility(View.VISIBLE);

        mLLErroePage = customFindViewById(R.id.ll_network_error);
        mLLErroePage.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        switchAction(ACTION_INIT);
    }

    protected void showNetWorkError() {
        mXRecyclerView.setVisibility(View.GONE);
        mLLErroePage.setVisibility(View.VISIBLE);
        mBtnRefresh = customFindViewById(R.id.btn_network_refresh);
        mBtnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchAction(ACTION_REFRESH);
            }
        });
    }

    protected void loadComplete() {
        ThreadManager.getmInstance().runOnUIThread(new Runnable() {
            @Override
            public void run() {
                mXRecyclerView.setVisibility(View.VISIBLE);
                mLLErroePage.setVisibility(View.GONE);
                if (mCurrentAction == ACTION_REFRESH) {
                    mXRecyclerView.refreshComplete();
                }
                if (mCurrentAction == ACTION_LOAD_MORE) {
                    mXRecyclerView.loadMoreComplete();
                }
            }
        });
    }

    private void switchAction(int mAction) {
        mCurrentAction = mAction;
        switch (mCurrentAction) {
            case ACTION_INIT:
                clearAdapter();
                mXRecyclerView.refreshComplete();
                mXRecyclerView.loadMoreComplete();
                break;
            case ACTION_REFRESH:
                clearAdapter();
                mCursor = "";
                getData();
                break;
            case ACTION_LOAD_MORE:
                getData();
                break;
            default:
        }
    }

    protected abstract void getData();

    protected abstract void clearAdapter();

    protected abstract void setAdapter(XRecyclerView mXRecyclerView);
}
