package com.academy.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

@Slf4j
public class CacheLoggerEventConfiguration implements CacheEventListener<Object, Object> {

    @Override
    public void onEvent(CacheEvent<?, ?> cacheEvent)
    {
        log.info("Key: {} | EventType: {} | Old value: {} | New value: {}",
            cacheEvent.getKey(), cacheEvent.getType(),
            cacheEvent.getOldValue(), cacheEvent.getNewValue()
        );
    }
}