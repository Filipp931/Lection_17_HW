package com.throttler.InputStream;

import com.throttler.LimiterService.Limiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
@Scope("prototype")
public class ThrottledInputStream extends InputStream {
    private InputStream inputStream;
    private Limiter limiter;
    @Autowired
    public ThrottledInputStream(Limiter limiter) {
        this.limiter = limiter;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public int read() throws IOException {
        limiter.acquire(1);
        return inputStream.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        limiter.acquire(b.length);
        return inputStream.read();
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        limiter.acquire(len);
        return inputStream.read(b, off, len);
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
    }


}
