package online.himakeit.readhub.ui.adapter;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.List;

import online.himakeit.readhub.R;
import online.himakeit.readhub.module.topic.TopicRspItem;
import online.himakeit.readhub.module.topic.TopicRspItemNews;
import online.himakeit.readhub.ui.adapter.base.SolidRVBaseAdapter;
import online.himakeit.readhub.ui.widget.ReadHubTopicDetailItemView;
import online.himakeit.readhub.ui.widget.ReadhubOnClickListener;

/**
 * @author：LiXueLong
 * @date：2018/1/22
 * @mail1：skylarklxlong@outlook.com
 * @mail2：li_xuelong@126.com
 * @des:
 */
public class ReadHubTopicAdapter extends SolidRVBaseAdapter<TopicRspItem> {
    public ReadHubTopicAdapter(List<TopicRspItem> mBeans, Context mContext) {
        super(mBeans, mContext);
    }

    @Override
    protected void onBindDataToView(SolidRVBaseAdapter<TopicRspItem>.SolidCommonViewHolder holder, TopicRspItem bean) {
        holder.setText(R.id.tv_topic_item_title, bean.getTitle());
        LinearLayout llMore = holder.getView(R.id.ll_topic_item_more);
        llMore.removeAllViews();
        if (null != bean.getNewsArray() && bean.getNewsArray().size() > 0) {
            for (TopicRspItemNews news : bean.getNewsArray()) {
                ReadHubTopicDetailItemView view = new ReadHubTopicDetailItemView(this.mContext);
                view.init(news);
                llMore.addView(view);
            }
        }
        ReadhubOnClickListener listener = new ReadhubOnClickListener(llMore, bean.getTitle(), bean.getSummary());
        holder.setText(R.id.tv_topic_item_summary, bean.getSummary());
        holder.getView(R.id.tv_topic_item_title).setOnClickListener(listener);
        holder.getView(R.id.tv_topic_item_title).setOnLongClickListener(listener);
        holder.getView(R.id.tv_topic_item_summary).setOnClickListener(listener);
        holder.getView(R.id.tv_topic_item_summary).setOnLongClickListener(listener);
    }

    @Override
    protected int getItemLayoutID(int viewType) {
        return R.layout.item_readhub_topic;
    }
}
