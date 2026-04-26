package com.hotnew.spider.service;

import com.hotnew.spider.entity.HotNews;
import com.hotnew.spider.repository.HotNewsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HotNewsService {

    private static final Logger logger = LoggerFactory.getLogger(HotNewsService.class);
    
    @Autowired
    private HotNewsRepository hotNewsRepository;
    
    @Transactional
    public void saveHotNewsList(List<HotNews> hotNewsList) {
        if (hotNewsList == null || hotNewsList.isEmpty()) {
            logger.warn("没有数据需要保存");
            return;
        }
        
        LocalDateTime now = LocalDateTime.now();
        for (HotNews hotNews : hotNewsList) {
            hotNews.setCrawlTime(now);
        }
        
        hotNewsRepository.saveAll(hotNewsList);
        logger.info("成功保存 {} 条热搜数据", hotNewsList.size());
    }
    
    public List<HotNews> getLatestHotNews() {
        return hotNewsRepository.findLatestHotNews();
    }
    
    public LocalDateTime getLatestCrawlTime() {
        return hotNewsRepository.findLatestCrawlTime();
    }
    
    public List<HotNews> getHotNewsByTime(LocalDateTime time) {
        return hotNewsRepository.findByCrawlTimeOrderByRankAsc(time);
    }
}
