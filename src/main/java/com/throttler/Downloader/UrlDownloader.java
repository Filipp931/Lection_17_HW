package com.throttler.Downloader;

import com.throttler.InputStream.ThrottledInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UrlDownloader implements Downloader{
    @Value("${downloadPath}")
    Path downloadDirectoryPath;

    private final ThrottledInputStream throttledInputStream;

    @Autowired
    public UrlDownloader(ThrottledInputStream inputStream) {
        this.throttledInputStream = inputStream;
    }

    @Override
    @Async
    public void download(URL url) {
        if (url != null) {
            Path filePath = getFileName(url);
            System.out.printf("Thread %s starting download %s \n", Thread.currentThread().getName(), url.toString());
            Long startTime = System.currentTimeMillis();
            try{
                URLConnection connection = url.openConnection();
                InputStream inputStream = connection.getInputStream();
                throttledInputStream.setInputStream(inputStream);
                Files.copy(throttledInputStream, filePath);
                System.out.printf("Thread %s successfully downloaded %s \n", Thread.currentThread().getName(), url.toString());
                Long downloadingTime = System.currentTimeMillis() - startTime;

                throttledInputStream.close();
            } catch (FileAlreadyExistsException e) {
                System.out.printf("File %s has already been downloaded \n", url.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Path getFileName(URL url) {
        String fileName = url.toString().substring(url.toString().lastIndexOf('/')+1);
        return Paths.get(downloadDirectoryPath.toString()+"/"+fileName);
    }

    @PostConstruct
    private void checkDownloadDirectory(){
        if( !Files.exists(downloadDirectoryPath) ) {
            try {
                Files.createDirectory(downloadDirectoryPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
