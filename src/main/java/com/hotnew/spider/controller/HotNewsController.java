package com.hotnew.spider.controller;

import com.hotnew.spider.entity.HotNews;
import com.hotnew.spider.service.HotNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class HotNewsController {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
    
    @Autowired
    private HotNewsService hotNewsService;

    @GetMapping("/")
    public String index(Model model) {
        List<HotNews> hotNewsList = hotNewsService.getLatestHotNews();
        LocalDateTime latestCrawlTime = hotNewsService.getLatestCrawlTime();
        
        String currentTime = LocalDateTime.now().format(FORMATTER);
        String crawlTime = latestCrawlTime != null ? latestCrawlTime.format(FORMATTER) : "暂无数据";
        
        model.addAttribute("currentTime", currentTime);
        model.addAttribute("crawlTime", crawlTime);
        model.addAttribute("hotNewsList", hotNewsList);
        
        return "index";
    }
}
