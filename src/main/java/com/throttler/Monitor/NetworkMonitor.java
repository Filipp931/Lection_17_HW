package com.throttler.Monitor;


import java.net.URL;
import java.util.List;

public interface NetworkMonitor {
    void add(long downloadTime, long fileSize);
    long getTotalDownloadTime();
    long getTotalFilesSize();
    long getTotalDownloadSpeed();
}
