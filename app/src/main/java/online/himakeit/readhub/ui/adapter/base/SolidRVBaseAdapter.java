package online.himakeit.readhub.ui.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import online.himakeit.readhub.utils.DeviceUtil;
import online.himakeit.readhub.utils.DownloadManager;

/**
 * @author：LiXueLong
 * @date：2018/1/22
 * @mail1：skylarklxlong@outlook.com
 * @mail2：li_xuelong@126.com
 * @des:
 */
public abstract class SolidRVBaseAdapter<T> extends RecyclerView.Adapter<SolidRVBaseAdapter.SolidCommonViewHolder> {

    protected List<T> mBeans;
    protected Context mContext;
    protected boolean mAnimationItems = true;
    protected int mLastAnimatedPosition = -1;

    public SolidRVBaseAdapter(List<T> mBeans, Context mContext) {
        this.mBeans = mBeans;
        this.mContext = mContext;
    }

    @Override
    public SolidRVBaseAdapter.SolidCommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(getItemLayoutID(viewType), parent, false);
        SolidCommonViewHolder solidCommonViewHolder = new SolidCommonViewHolder(view);
        return solidCommonViewHolder;
    }

    @Override
    public void onBindViewHolder(SolidRVBaseAdapter.SolidCommonViewHolder holder, int position) {
        setAnimation(holder.itemView, position);
        onBindDataToView(holder, mBeans.get(position));
    }

    @Override
    public int getItemCount() {
        return mBeans.size();
    }

    /**
     * 设置动画
     *
     * @param view
     * @param position
     */
    private void setAnimation(View view, int position) {
        if (!mAnimationItems) {
            return;
        }
        if (position > mLastAnimatedPosition) {
            mLastAnimatedPosition = position;
            view.setTranslationY(DeviceUtil.getScreenHeight(mContext));
            view.animate()
                    .translationY(50)
                    .setStartDelay(100)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(300)
                    .start();
        }
    }

    /**
     * 绑定数据到Item的控件中去
     *
     * @param holder
     * @param bean
     */
    protected abstract void onBindDataToView(SolidCommonViewHolder holder, T bean);

    /**
     * 取得ItemView的布局文件
     *
     * @param viewType
     * @return
     */
    protected abstract int getItemLayoutID(int viewType);

    public void add(T bean) {
        mBeans.add(bean);
        notifyDataSetChanged();
    }

    public void addAll(List<T> beans) {
        mBeans.addAll(beans);
        notifyDataSetChanged();
    }

    public void clear() {
        mBeans.clear();
        // TODO: 2018/1/22 这里不够友好，如果每次情况数据时直接刷新，会导致布局显示空白
//        notifyDataSetChanged();
    }

    /**
     * ItemView的单击事件(如果需要，重写此方法就行)
     *
     * @param position
     */
    protected void onItemClick(int position) {

    }

    public class SolidCommonViewHolder extends RecyclerView.ViewHolder {
        private final SparseArray<View> mViews;
        private View itemView;

        public SolidCommonViewHolder(View itemView) {
            super(itemView);
            this.mViews = new SparseArray<>();
            this.itemView = itemView;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(getAdapterPosition());
                }
            });
        }

        public <T extends View> T getView(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        public void setText(int viewId, String text) {
            TextView textView = getView(viewId);
            textView.setText(text);
        }

        public void setImage(int viewId, int resId) {
            ImageView imageView = getView(viewId);
            imageView.setImageResource(resId);
        }

        public void setImageFromInternet(int viewId, String url) {
            ImageView imageView = getView(viewId);
            DownloadManager.setBitmapToImgView(imageView, url);
        }
    }
}
