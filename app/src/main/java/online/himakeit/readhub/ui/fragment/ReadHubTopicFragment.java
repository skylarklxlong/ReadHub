package online.himakeit.readhub.ui.fragment;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

import online.himakeit.readhub.module.topic.TopicRsp;
import online.himakeit.readhub.module.topic.TopicRspItem;
import online.himakeit.readhub.network.ApiClient;
import online.himakeit.readhub.network.IReadHubService;
import online.himakeit.readhub.ui.adapter.ReadHubTopicAdapter;
import online.himakeit.readhub.utils.ThreadManager;
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
public class ReadHubTopicFragment extends ReadHubFragment {

    protected ReadHubTopicAdapter mAdapter;

    @Override
    protected void initView() {
        mAdapter = new ReadHubTopicAdapter(new ArrayList<TopicRspItem>(), getmContext());
        super.initView();
    }

    @Override
    protected void getData() {
        ApiClient.create(IReadHubService.class).requestTopicNewsList(mCursor, pageSize)
                .enqueue(new Callback<TopicRsp>() {
                    @Override
                    public void onResponse(Call<TopicRsp> call, Response<TopicRsp> response) {
                        if (response.isSuccessful()) {
                            final TopicRsp topicRsp = response.body();
                            if (topicRsp == null || topicRsp.getData() == null) {
                                return;
                            }
                            if (topicRsp.getData() != null && topicRsp.getData().size() > 0) {
                                ThreadManager.getmInstance().runOnUIThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mAdapter.addAll(topicRsp.getData());
                                    }
                                });
                            }
                            mCursor = topicRsp.getData().get(topicRsp.getData().size() - 1).getOrder();
                            pageSize = topicRsp.getPageSize();
                            loadComplete();
                        } else {
                            // TODO: 2018/1/22 需要做下处理
                            showNetWorkError();
                        }
                    }

                    @Override
                    public void onFailure(Call<TopicRsp> call, Throwable t) {
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
