package com.hotnew.spider.service;

import com.hotnew.spider.entity.HotNews;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BaiduSpiderService {

    private static final Logger logger = LoggerFactory.getLogger(BaiduSpiderService.class);
    private static final String BAIDU_URL = "https://www.baidu.com/";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36";

    public List<HotNews> crawlHotNews() {
        List<HotNews> hotNewsList = new ArrayList<>();
        
        try {
            logger.info("开始爬取百度热搜榜...");
            
            Document doc = Jsoup.connect(BAIDU_URL)
                    .userAgent(USER_AGENT)
                    .timeout(10000)
                    .get();
            
            Elements hotItems = doc.select("#hotsearch-content-wrapper li.hotsearch-item");
            
            if (hotItems.isEmpty()) {
                logger.warn("未找到热搜榜数据，尝试其他选择器...");
                hotItems = doc.select(".hotsearch-item");
            }
            
            if (hotItems.isEmpty()) {
                logger.warn("仍未找到热搜榜数据，尝试查找包含热搜的元素...");
                hotItems = doc.select("li[class*='hot']");
            }
            
            int rank = 1;
            for (Element item : hotItems) {
                if (rank > 30) break;
                
                String title = extractTitle(item);
                String url = extractUrl(item);
                
                if (title != null && !title.trim().isEmpty() && url != null) {
                    HotNews hotNews = new HotNews(rank, title.trim(), url);
                    hotNewsList.add(hotNews);
                    logger.debug("爬取到热搜 {}: {} - {}", rank, title, url);
                    rank++;
                }
            }
            
            logger.info("爬取完成，共获取 {} 条热搜数据", hotNewsList.size());
            
        } catch (IOException e) {
            logger.error("爬取百度热搜榜失败: {}", e.getMessage(), e);
        }
        
        return hotNewsList;
    }
    
    private String extractTitle(Element item) {
        Element titleElement = item.selectFirst(".title-content-title");
        if (titleElement != null) {
            return titleElement.text();
        }
        
        titleElement = item.selectFirst(".hotsearch-item-title");
        if (titleElement != null) {
            return titleElement.text();
        }
        
        titleElement = item.selectFirst("span[class*='title']");
        if (titleElement != null) {
            return titleElement.text();
        }
        
        titleElement = item.selectFirst("a");
        if (titleElement != null) {
            return titleElement.text();
        }
        
        return item.text();
    }
    
    private String extractUrl(Element item) {
        Element linkElement = item.selectFirst("a");
        if (linkElement != null) {
            String href = linkElement.attr("href");
            if (href != null && !href.isEmpty()) {
                if (href.startsWith("//")) {
                    return "https:" + href;
                } else if (!href.startsWith("http")) {
                    return BAIDU_URL + href;
                }
                return href;
            }
        }
        return null;
    }
}
