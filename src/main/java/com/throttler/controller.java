package com.throttler;

import com.throttler.Downloader.Downloader;
import com.throttler.filePreparer.FilePreparer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URL;

@Controller
public class controller {
    private Downloader downloader;
    private FilePreparer filePreparer;

    @Autowired
    public controller(Downloader downloader, FilePreparer filePreparer) {
        this.downloader = downloader;
        this.filePreparer = filePreparer;
    }

    @GetMapping("/")
    public void init(Model model){
        URL url = filePreparer.getUrl();
        while (url != null) {
            downloader.download(url);
            url = filePreparer.getUrl();
        }

    }
}
