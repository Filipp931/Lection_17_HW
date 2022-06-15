package com.throttler.filePreparer;


import java.net.URL;
import java.util.List;

public interface FilePreparer {
    URL getUrl();
    List<String> getUrlsList();
}
