package online.himakeit.readhub.ui.adapter;

import android.content.Context;
import android.content.Intent;

import java.util.List;

import online.himakeit.readhub.MyApp;
import online.himakeit.readhub.R;
import online.himakeit.readhub.module.news.NewsRspItem;
import online.himakeit.readhub.ui.activity.MainActivity;
import online.himakeit.readhub.ui.adapter.base.SolidRVBaseAdapter;
import online.himakeit.readhub.ui.widget.ReadhubOnClickListener;
import online.himakeit.readhub.utils.TimeUtil;

/**
 * @author：LiXueLong
 * @date：2018/1/22
 * @mail1：skylarklxlong@outlook.com
 * @mail2：li_xuelong@126.com
 * @des:
 */
public class ReadHubNewsAdapter extends SolidRVBaseAdapter<NewsRspItem> {
    public ReadHubNewsAdapter(List<NewsRspItem> mBeans, Context mContext) {
        super(mBeans, mContext);
    }

    @Override
    protected void onBindDataToView(SolidCommonViewHolder holder, NewsRspItem bean) {
        holder.setText(R.id.tv_news_item_title, bean.getTitle());
        holder.setText(R.id.tv_news_item_summary, bean.getSummary());
        String subText = String.format(MyApp.getContext().getString(R.string.news_desc),
                bean.getSiteName(), bean.getAuthorName() == null ? "" : bean.getAuthorName(),
                TimeUtil.getDateCompareResultByReadhubDateFormat(bean.getPublishDate()));
        holder.setText(R.id.tv_news_item_desc, subText);

        ReadhubOnClickListener listener = new ReadhubOnClickListener(bean.getTitle(), bean.getSummary(), bean.getUrl());
        holder.getView(R.id.tv_news_item_title).setOnClickListener(listener);
        holder.getView(R.id.tv_news_item_title).setOnLongClickListener(listener);
        holder.getView(R.id.tv_news_item_summary).setOnClickListener(listener);
        holder.getView(R.id.tv_news_item_summary).setOnLongClickListener(listener);
    }

    @Override
    protected void onItemClick(int position) {
        Intent intent = new Intent(mContext, MainActivity.class);
        NewsRspItem newsRspItem = mBeans.get(position - 1);
        if (null != newsRspItem) {
            intent.putExtra(MainActivity.INTENT_EXTRA_KEY_ITEM_URL, newsRspItem.getUrl());
            intent.putExtra(MainActivity.INTENT_EXTRA_KEY_ITEM_TITLE, newsRspItem.getTitle());
            mContext.startActivity(intent);
        }
    }

    @Override
    protected int getItemLayoutID(int viewType) {
        return R.layout.item_readhub_news;
    }
}
