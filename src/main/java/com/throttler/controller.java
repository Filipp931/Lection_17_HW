package com.throttler;

import com.throttler.Monitor.NetworkMonitor;
import com.throttler.downloadStarter.MultithreadingDownloadStarter;
import com.throttler.filePreparer.FilePreparer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class controller {
    private final FilePreparer filePreparer;
    private final MultithreadingDownloadStarter multithreadingDownloadStarter;
    private final NetworkMonitor networkMonitor;

    public controller(FilePreparer filePreparer, MultithreadingDownloadStarter multithreadingDownloadStarter, NetworkMonitor networkMonitor) {
        this.filePreparer = filePreparer;
        this.multithreadingDownloadStarter = multithreadingDownloadStarter;
        this.networkMonitor = networkMonitor;
    }

    @GetMapping("/")
    public String init(Model model) {
        multithreadingDownloadStarter.startDownload();
        model.addAttribute("listURLs", filePreparer.getUrlsList());
        return "mainView";
    }
    @GetMapping("/monitor")
    public String monitor(Model model){
        model.addAttribute("totalSize", networkMonitor.getTotalFilesSize());
        model.addAttribute("totalTime", networkMonitor.getTotalDownloadTime());
        model.addAttribute("totalSpeed", networkMonitor.getTotalDownloadSpeed());
        return "result";
    }


}
