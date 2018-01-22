package online.himakeit.readhub.ui.fragment;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

import online.himakeit.readhub.module.news.NewsRsp;
import online.himakeit.readhub.module.news.NewsRspItem;
import online.himakeit.readhub.network.ApiClient;
import online.himakeit.readhub.network.IReadHubService;
import online.himakeit.readhub.ui.adapter.ReadHubNewsAdapter;
import online.himakeit.readhub.utils.ThreadManager;
import online.himakeit.readhub.utils.TimeUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author：LiXueLong
 * @date：2018/1/20
 * @mail1：skylarklxlong@outlook.com
 * @mail2：li_xuelong@126.com
 * @des:
 */
public class ReadHubTechFragment extends ReadHubFragment {
    private ReadHubNewsAdapter mAdapter;

    @Override
    protected void initView() {
        mAdapter = new ReadHubNewsAdapter(new ArrayList<NewsRspItem>(), getmContext());
        super.initView();
    }

    @Override
    protected void getData() {
        ApiClient.create(IReadHubService.class).requestTechNewsList(mCursor, pageSize)
                .enqueue(new Callback<NewsRsp>() {
                    @Override
                    public void onResponse(Call<NewsRsp> call, Response<NewsRsp> response) {
                        if (response.isSuccessful()) {
                            final NewsRsp newsRsp = response.body();
                            if (newsRsp == null || newsRsp.getData() == null) {
                                return;
                            }
                            if (newsRsp.getData() != null && newsRsp.getData().size() > 0) {
                                ThreadManager.getmInstance().runOnUIThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mAdapter.addAll(newsRsp.getData());
                                    }
                                });
                            }
                            mCursor = "" + TimeUtil.getTimeStampByReahubDateString(newsRsp.getData().get(newsRsp.getData().size() - 1).getPublishDate());
                            pageSize = newsRsp.getPageSize();
                            loadComplete();
                        } else {
                            // TODO: 2018/1/22 需要做下处理
                            showNetWorkError();
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsRsp> call, Throwable t) {
                        t.printStackTrace();
                        showNetWorkError();
                    }
                });
    }

    @Override
    protected void clearData() {
        mAdapter.clear();
    }

    @Override
    protected void setAdapter(XRecyclerView mXRecyclerView) {
        mXRecyclerView.setAdapter(mAdapter);
    }
}
