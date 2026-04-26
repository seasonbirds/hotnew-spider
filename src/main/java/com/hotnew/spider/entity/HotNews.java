package com.hotnew.spider.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "hot_news")
public class HotNews {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "rank_num", nullable = false)
    private Integer rank;
    
    @Column(name = "title", nullable = false, length = 500)
    private String title;
    
    @Column(name = "url", nullable = false, length = 1000)
    private String url;
    
    @Column(name = "crawl_time", nullable = false)
    private LocalDateTime crawlTime;
    
    public HotNews() {
    }
    
    public HotNews(Integer rank, String title, String url) {
        this.rank = rank;
        this.title = title;
        this.url = url;
        this.crawlTime = LocalDateTime.now();
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
    
    public LocalDateTime getCrawlTime() {
        return crawlTime;
    }
    
    public void setCrawlTime(LocalDateTime crawlTime) {
        this.crawlTime = crawlTime;
    }
}
