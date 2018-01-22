package online.himakeit.readhub.network;

import online.himakeit.readhub.module.news.NewsRsp;
import online.himakeit.readhub.module.topic.TopicRsp;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author：LiXueLong
 * @date：2018/1/22
 * @mail1：skylarklxlong@outlook.com
 * @mail2：li_xuelong@126.com
 * @des:
 */
public interface IReadHubService {

    /**
     * 获取热门话题
     *
     * @param lastCursor
     * @param pageSize
     * @return
     */
    @GET("topic")
    Call<TopicRsp> requestTopicNewsList(@Query("lastCursor") String lastCursor, @Query("pageSize") int pageSize);

    /**
     * 获取科技动态
     *
     * @param lastCursor
     * @param pageSize
     * @return
     */
    @GET("technews")
    Call<NewsRsp> requestTechNewsList(@Query("lastCursor") String lastCursor, @Query("pageSize") int pageSize);

    /**
     * 获取开发者资讯
     *
     * @param lastCursor
     * @param pageSize
     * @return
     */
    @GET("news")
    Call<NewsRsp> requestDevNewsList(@Query("lastCursor") String lastCursor, @Query("pageSize") int pageSize);
}
