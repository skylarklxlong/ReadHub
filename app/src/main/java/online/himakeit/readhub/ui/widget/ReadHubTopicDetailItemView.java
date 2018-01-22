package online.himakeit.readhub.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import online.himakeit.readhub.R;
import online.himakeit.readhub.module.topic.TopicRspItemNews;
import online.himakeit.readhub.ui.activity.MainActivity;

/**
 * @author：LiXueLong
 * @date：2018/1/22
 * @mail1：skylarklxlong@outlook.com
 * @mail2：li_xuelong@126.com
 * @des:
 */
public class ReadHubTopicDetailItemView extends RelativeLayout {

    private Context mContext;

    public ReadHubTopicDetailItemView(Context context) {
        super(context);
        mContext = context;
    }

    public void init(final TopicRspItemNews news) {
        if (news != null) {
            ((LayoutInflater) (mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE))).inflate(R.layout.item_readhub_topic_detail, this);
            TextView mTvTitle = findViewById(R.id.tv_common_title);
            TextView mTvDes = findViewById(R.id.tv_common_des);
            /**
             * 增加曝光统计, 首先构建item的统计信息，然后曝光
             */
            mTvTitle.setText(Html.fromHtml(news.getTitle()));
            mTvDes.setText(Html.fromHtml(news.getSiteName()));
            this.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    intent.putExtra(MainActivity.INTENT_EXTRA_KEY_ITEM_URL, news.getUrl());
                    intent.putExtra(MainActivity.INTENT_EXTRA_KEY_ITEM_TITLE, news.getTitle());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
