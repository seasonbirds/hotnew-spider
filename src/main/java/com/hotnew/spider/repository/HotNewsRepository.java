package com.hotnew.spider.repository;

import com.hotnew.spider.entity.HotNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HotNewsRepository extends JpaRepository<HotNews, Long> {
    
    @Query("SELECT h FROM HotNews h WHERE h.crawlTime >= :time ORDER BY h.rank ASC")
    List<HotNews> findByCrawlTimeAfterOrderByRankAsc(LocalDateTime time);
    
    @Query("SELECT h FROM HotNews h WHERE h.crawlTime = (SELECT MAX(h2.crawlTime) FROM HotNews h2) ORDER BY h.rank ASC")
    List<HotNews> findLatestHotNews();
    
    @Query("SELECT MAX(h.crawlTime) FROM HotNews h")
    LocalDateTime findLatestCrawlTime();
    
    List<HotNews> findByCrawlTimeOrderByRankAsc(LocalDateTime crawlTime);
}
