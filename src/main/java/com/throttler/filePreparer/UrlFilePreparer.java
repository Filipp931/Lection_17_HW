package com.throttler.filePreparer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Collectors;

@Service
public class UrlFilePreparer implements FilePreparer{
    @Value("${listFiles}")
    private String listFilesPath;
    private Queue<URL> urls;

    @Override
    public URL getUrl() {
        return urls.poll();
    }

    @PostConstruct
    private void init() throws FileNotFoundException {
        Path path = Paths.get(listFilesPath);
        if(!Files.exists(path)&&Files.isRegularFile(path)) {
            throw new FileNotFoundException();
        }
        List<String> urlsList = null;
        try {
            urlsList = Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("error reading uris from file");
        }
        urls = urlsList.stream().map(uri -> {
            URL url = null;
            try {
                url = new URL(uri);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return url;
        }).collect(Collectors.toCollection(LinkedBlockingDeque::new));
        //control
        System.out.println("Read urls from file: \n" + urls.toString());
    }
}
