package com.pomodoro.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.pomodoro.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.pomodoro.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.pomodoro.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.pomodoro.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.pomodoro.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.pomodoro.domain.SocialUserConnection.class.getName(), jcacheConfiguration);
            cm.createCache(com.pomodoro.domain.AssociatedUser.class.getName(), jcacheConfiguration);
            cm.createCache(com.pomodoro.domain.Action.class.getName(), jcacheConfiguration);
            cm.createCache(com.pomodoro.domain.Task.class.getName(), jcacheConfiguration);
            cm.createCache(com.pomodoro.domain.Task.class.getName() + ".actions", jcacheConfiguration);
            cm.createCache(com.pomodoro.domain.DailyTaskList.class.getName(), jcacheConfiguration);
            cm.createCache(com.pomodoro.domain.DailyTaskList.class.getName() + ".tasks", jcacheConfiguration);
            cm.createCache(com.pomodoro.domain.DailyTaskList.class.getName() + ".taskLists", jcacheConfiguration);
            cm.createCache(com.pomodoro.domain.Task.class.getName() + ".dailyTaskLists", jcacheConfiguration);
            cm.createCache(com.pomodoro.domain.UserPoke.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
