package com.throttler.downloadStarter;

import com.throttler.Downloader.Downloader;
import com.throttler.filePreparer.FilePreparer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class MultithreadingUrlDownloadStarter implements MultithreadingDownloadStarter{
    private final Downloader downloader;
    private final FilePreparer filePreparer;

    @Autowired
    public MultithreadingUrlDownloadStarter(Downloader downloader, FilePreparer filePreparer) {
        this.downloader = downloader;
        this.filePreparer = filePreparer;
    }

    @Override
    public void startDownload() {
        URL url = filePreparer.getUrl();
        while (url != null) {
            downloader.download(url);
            url = filePreparer.getUrl();
        }
    }
}
