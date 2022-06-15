package com.throttler.LimiterService;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class URLLimiter implements Limiter{
    @Value("${limitPerSecond}")
    int limitPerSecond;
    private RateLimiter rateLimiter;
    @Override
    public synchronized void acquire(int counts) {
        rateLimiter.acquire(counts);
    }
    @PostConstruct
    private void init(){
        rateLimiter = RateLimiter.create(limitPerSecond);
    }
}
