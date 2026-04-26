package com.hotnew.spider.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class HotNewsMessage implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private LocalDateTime timestamp;
    private String source;
    private List<NewsItem> news;
    
    public HotNewsMessage() {
    }
    
    public HotNewsMessage(LocalDateTime timestamp, String source, List<NewsItem> news) {
        this.timestamp = timestamp;
        this.source = source;
        this.news = news;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getSource() {
        return source;
    }
    
    public void setSource(String source) {
        this.source = source;
    }
    
    public List<NewsItem> getNews() {
        return news;
    }
    
    public void setNews(List<NewsItem> news) {
        this.news = news;
    }
    
    public static class NewsItem implements Serializable {
        
        private static final long serialVersionUID = 1L;
        
        private Integer rank;
        private String title;
        private String url;
        
        public NewsItem() {
        }
        
        public NewsItem(Integer rank, String title, String url) {
            this.rank = rank;
            this.title = title;
            this.url = url;
        }
        
        public Integer getRank() {
            return rank;
        }
        
        public void setRank(Integer rank) {
            this.rank = rank;
        }
        
        public String getTitle() {
            return title;
        }
        
        public void setTitle(String title) {
            this.title = title;
        }
        
        public String getUrl() {
            return url;
        }
        
        public void setUrl(String url) {
            this.url = url;
        }
    }
}
