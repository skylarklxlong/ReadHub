package online.himakeit.readhub.module.topic;

import java.util.ArrayList;

/**
 * @author：LiXueLong
 * @date：2018/1/22
 * @mail1：skylarklxlong@outlook.com
 * @mail2：li_xuelong@126.com
 * @des:
 */
public class TopicRspItem {

    private String id;
    private String title;
    private String summary;
    private ArrayList<TopicRspItemNews> newsArray;
    private ArrayList<TopicRspItemNews> weiboArray;
    private ArrayList<TopicRspItemNews> wechatArray;
    private ArrayList<TopicRspItemNews> relatedTopicArray;
    private String publishUserId;
    private String order;
    private String publishDate;
    private String createdAt;
    private String updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public ArrayList<TopicRspItemNews> getNewsArray() {
        return newsArray;
    }

    public void setNewsArray(ArrayList<TopicRspItemNews> newsArray) {
        this.newsArray = newsArray;
    }

    public ArrayList<TopicRspItemNews> getWeiboArray() {
        return weiboArray;
    }

    public void setWeiboArray(ArrayList<TopicRspItemNews> weiboArray) {
        this.weiboArray = weiboArray;
    }

    public ArrayList<TopicRspItemNews> getWechatArray() {
        return wechatArray;
    }

    public void setWechatArray(ArrayList<TopicRspItemNews> wechatArray) {
        this.wechatArray = wechatArray;
    }

    public ArrayList<TopicRspItemNews> getRelatedTopicArray() {
        return relatedTopicArray;
    }

    public void setRelatedTopicArray(ArrayList<TopicRspItemNews> relatedTopicArray) {
        this.relatedTopicArray = relatedTopicArray;
    }

    public String getPublishUserId() {
        return publishUserId;
    }

    public void setPublishUserId(String publishUserId) {
        this.publishUserId = publishUserId;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
