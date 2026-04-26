<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>今日热点 - 百度热搜榜</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Microsoft YaHei', 'PingFang SC', Arial, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
        }
        
        .container {
            max-width: 800px;
            margin: 0 auto;
        }
        
        .header {
            text-align: center;
            margin-bottom: 30px;
        }
        
        .title {
            font-size: 28px;
            color: #fff;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
            margin-bottom: 10px;
        }
        
        .time-display {
            font-size: 18px;
            color: rgba(255, 255, 255, 0.9);
            background: rgba(255, 255, 255, 0.1);
            padding: 10px 20px;
            border-radius: 25px;
            display: inline-block;
        }
        
        .crawl-info {
            text-align: center;
            color: rgba(255, 255, 255, 0.8);
            font-size: 14px;
            margin-bottom: 20px;
        }
        
        .news-list {
            background: #fff;
            border-radius: 15px;
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.2);
            overflow: hidden;
        }
        
        .news-item {
            display: flex;
            align-items: center;
            padding: 15px 20px;
            border-bottom: 1px solid #f0f0f0;
            transition: background 0.3s ease;
        }
        
        .news-item:hover {
            background: #f8f9fa;
        }
        
        .news-item:last-child {
            border-bottom: none;
        }
        
        .rank {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: bold;
            font-size: 16px;
            margin-right: 15px;
            flex-shrink: 0;
        }
        
        .rank-1 {
            background: linear-gradient(135deg, #ff6b6b, #ee5a24);
            color: #fff;
        }
        
        .rank-2 {
            background: linear-gradient(135deg, #ffa502, #ff6348);
            color: #fff;
        }
        
        .rank-3 {
            background: linear-gradient(135deg, #ffd93d, #ffa502);
            color: #fff;
        }
        
        .rank-default {
            background: #e8e8e8;
            color: #666;
        }
        
        .title-link {
            text-decoration: none;
            color: #333;
            font-size: 16px;
            line-height: 1.5;
            flex: 1;
            transition: color 0.3s ease;
        }
        
        .title-link:hover {
            color: #667eea;
        }
        
        .empty-state {
            text-align: center;
            padding: 60px 20px;
            color: #999;
        }
        
        .empty-state .icon {
            font-size: 48px;
            margin-bottom: 20px;
        }
        
        .footer {
            text-align: center;
            margin-top: 30px;
            color: rgba(255, 255, 255, 0.7);
            font-size: 14px;
        }
        
        @keyframes pulse {
            0%, 100% { opacity: 1; }
            50% { opacity: 0.5; }
        }
        
        .live-indicator {
            display: inline-block;
            width: 8px;
            height: 8px;
            background: #00ff88;
            border-radius: 50%;
            margin-right: 8px;
            animation: pulse 2s infinite;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1 class="title">
                <span class="live-indicator"></span>
                今日热点
            </h1>
            <div class="time-display" id="currentTime">${currentTime}</div>
        </div>
        
        <div class="crawl-info">
            数据更新时间：${crawlTime}
        </div>
        
        <div class="news-list">
            <#if hotNewsList?? && hotNewsList?size gt 0>
                <#list hotNewsList as news>
                    <div class="news-item">
                        <div class="rank <#if news.rank == 1>rank-1<#elseif news.rank == 2>rank-2<#elseif news.rank == 3>rank-3<#else>rank-default</#if>">
                            ${news.rank}
                        </div>
                        <a href="${news.url}" target="_blank" rel="noopener noreferrer" class="title-link">
                            ${news.title}
                        </a>
                    </div>
                </#list>
            <#else>
                <div class="empty-state">
                    <div class="icon">📰</div>
                    <p>暂无热点数据</p>
                    <p style="font-size: 14px; margin-top: 10px;">请等待定时任务执行或刷新页面</p>
                </div>
            </#if>
        </div>
        
        <div class="footer">
            <p>数据来源：百度热搜榜 | 每5分钟自动更新</p>
        </div>
    </div>
    
    <script>
        function formatDate(date) {
            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, '0');
            const day = String(date.getDate()).padStart(2, '0');
            const hours = String(date.getHours()).padStart(2, '0');
            const minutes = String(date.getMinutes()).padStart(2, '0');
            const seconds = String(date.getSeconds()).padStart(2, '0');
            return year + '年' + month + '月' + day + '日 ' + hours + ':' + minutes + ':' + seconds;
        }
        
        function updateTime() {
            const now = new Date();
            const timeElement = document.getElementById('currentTime');
            if (timeElement) {
                timeElement.textContent = formatDate(now);
            }
        }
        
        updateTime();
        setInterval(updateTime, 1000);
    </script>
</body>
</html>
