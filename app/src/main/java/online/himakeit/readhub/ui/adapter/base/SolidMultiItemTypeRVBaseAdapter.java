package online.himakeit.readhub.ui.adapter.base;

import android.content.Context;

import java.util.List;

/**
 * @author：LiXueLong
 * @date：2018/1/22
 * @mail1：skylarklxlong@outlook.com
 * @mail2：li_xuelong@126.com
 * @des:支持多种ItemType的Adapter（适用于RecyclerView）
 */
public abstract class SolidMultiItemTypeRVBaseAdapter<T> extends SolidRVBaseAdapter<T> {
    public SolidMultiItemTypeRVBaseAdapter(List<T> mBeans, Context mContext) {
        super(mBeans, mContext);
    }

    @Override
    public abstract int getItemViewType(int position);
}
