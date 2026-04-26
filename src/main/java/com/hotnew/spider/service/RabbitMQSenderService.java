package com.hotnew.spider.service;

import com.hotnew.spider.config.RabbitMQConfig;
import com.hotnew.spider.dto.HotNewsMessage;
import com.hotnew.spider.entity.HotNews;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@ConditionalOnProperty(name = "rabbitmq.enabled", havingValue = "true", matchIfMissing = false)
public class RabbitMQSenderService {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQSenderService.class);
    
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendHotNews(List<HotNews> hotNewsList) {
        if (hotNewsList == null || hotNewsList.isEmpty()) {
            logger.warn("没有数据需要发送到 RabbitMQ");
            return;
        }
        
        HotNewsMessage message = convertToMessage(hotNewsList);
        
        try {
            rabbitTemplate.convertAndSend(
                RabbitMQConfig.TOPIC_EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                message
            );
            logger.info("成功发送 {} 条热搜数据到 RabbitMQ，Exchange: {}, RoutingKey: {}", 
                hotNewsList.size(), 
                RabbitMQConfig.TOPIC_EXCHANGE_NAME, 
                RabbitMQConfig.ROUTING_KEY
            );
        } catch (Exception e) {
            logger.error("发送消息到 RabbitMQ 失败: {}", e.getMessage(), e);
        }
    }
    
    private HotNewsMessage convertToMessage(List<HotNews> hotNewsList) {
        List<HotNewsMessage.NewsItem> items = hotNewsList.stream()
            .map(hotNews -> new HotNewsMessage.NewsItem(
                hotNews.getRank(),
                hotNews.getTitle(),
                hotNews.getUrl()
            ))
            .collect(Collectors.toList());
        
        return new HotNewsMessage(
            LocalDateTime.now(),
            "baidu",
            items
        );
    }
}
