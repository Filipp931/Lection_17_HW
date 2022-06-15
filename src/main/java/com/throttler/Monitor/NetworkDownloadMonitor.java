package com.throttler.Monitor;


import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class NetworkDownloadMonitor implements NetworkMonitor{

    Map<Long, Long> statistic = new ConcurrentHashMap();

    @Override
    public void add(long downloadTime, long fileSize) {
        statistic.put(downloadTime / 1000, fileSize);
    }

    @Override
    public long getTotalDownloadTime() {
        return statistic.keySet().stream().mapToLong(Long::longValue).sum();
    }

    @Override
    public long getTotalFilesSize() {
        return statistic.values().stream().mapToLong(Long::longValue).sum();
    }

    @Override
    public long getTotalDownloadSpeed() {
        return statistic.values().stream().mapToLong(Long::longValue).sum() / statistic.keySet().stream().mapToLong(Long::longValue).sum() ;
    }
}
