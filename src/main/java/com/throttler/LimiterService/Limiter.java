package com.throttler.LimiterService;

import org.springframework.stereotype.Component;

@Component
public interface Limiter {
    public void acquire(int counts);
}
