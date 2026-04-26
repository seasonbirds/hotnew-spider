package com.hotnew.spider.task;

import com.hotnew.spider.entity.HotNews;
import com.hotnew.spider.service.BaiduSpiderService;
import com.hotnew.spider.service.HotNewsService;
import com.hotnew.spider.service.RabbitMQSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpiderScheduledTask {

    private static final Logger logger = LoggerFactory.getLogger(SpiderScheduledTask.class);
    
    @Autowired
    private BaiduSpiderService baiduSpiderService;
    
    @Autowired
    private HotNewsService hotNewsService;
    
    @Autowired(required = false)
    private RabbitMQSenderService rabbitMQSenderService;
    
    @Scheduled(fixedRate = 300000)
    public void crawlHotNews() {
        logger.info("========== 开始执行定时爬虫任务 ==========");
        
        try {
            List<HotNews> hotNewsList = baiduSpiderService.crawlHotNews();
            
            if (hotNewsList.isEmpty()) {
                logger.warn("爬取到的数据为空，跳过保存");
                return;
            }
            
            hotNewsService.saveHotNewsList(hotNewsList);
            
            if (rabbitMQSenderService != null) {
                rabbitMQSenderService.sendHotNews(hotNewsList);
            } else {
                logger.info("RabbitMQ 服务未启用，跳过消息发送");
            }
            
            logger.info("========== 定时爬虫任务执行完成 ==========");
            
        } catch (Exception e) {
            logger.error("定时爬虫任务执行失败: {}", e.getMessage(), e);
        }
    }
}
