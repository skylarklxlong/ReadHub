package online.himakeit.readhub.module.news;

import java.util.List;

/**
 * @author：LiXueLong
 * @date：2018/1/22
 * @mail1：skylarklxlong@outlook.com
 * @mail2：li_xuelong@126.com
 * @des:
 */
public class NewsRsp {

    private List<NewsRspItem> data;
    private int pageSize;
    private int totalItems;
    private int totalPages;

    public List<NewsRspItem> getData() {
        return data;
    }

    public void setData(List<NewsRspItem> data) {
        this.data = data;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
