package com.throttler;

import com.throttler.downloadStarter.MultithreadingDownloadStarter;
import com.throttler.filePreparer.FilePreparer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class controller {
    private final FilePreparer filePreparer;
    private final MultithreadingDownloadStarter multithreadingDownloadStarter;

    @Autowired
    public controller(FilePreparer filePreparer, MultithreadingDownloadStarter multithreadingDownloadStarter) {
        this.filePreparer = filePreparer;
        this.multithreadingDownloadStarter = multithreadingDownloadStarter;
    }

    @GetMapping("/")
    public String init(Model model) {
        multithreadingDownloadStarter.startDownload();
        model.addAttribute("listURLs", filePreparer.getUrlsList());
        return "mainView";
    }
}
