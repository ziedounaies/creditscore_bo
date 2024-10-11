package com.reactit.credit.score.config;

import java.net.URI;
import java.util.concurrent.TimeUnit;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import org.redisson.Redisson;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.jcache.configuration.RedissonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;

    @Bean
    public javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration(JHipsterProperties jHipsterProperties) {
        MutableConfiguration<Object, Object> jcacheConfig = new MutableConfiguration<>();

        URI redisUri = URI.create(jHipsterProperties.getCache().getRedis().getServer()[0]);

        Config config = new Config();
        if (jHipsterProperties.getCache().getRedis().isCluster()) {
            ClusterServersConfig clusterServersConfig = config
                .useClusterServers()
                .setMasterConnectionPoolSize(jHipsterProperties.getCache().getRedis().getConnectionPoolSize())
                .setMasterConnectionMinimumIdleSize(jHipsterProperties.getCache().getRedis().getConnectionMinimumIdleSize())
                .setSubscriptionConnectionPoolSize(jHipsterProperties.getCache().getRedis().getSubscriptionConnectionPoolSize())
                .addNodeAddress(jHipsterProperties.getCache().getRedis().getServer());

            if (redisUri.getUserInfo() != null) {
                clusterServersConfig.setPassword(redisUri.getUserInfo().substring(redisUri.getUserInfo().indexOf(':') + 1));
            }
        } else {
            SingleServerConfig singleServerConfig = config
                .useSingleServer()
                .setConnectionPoolSize(jHipsterProperties.getCache().getRedis().getConnectionPoolSize())
                .setConnectionMinimumIdleSize(jHipsterProperties.getCache().getRedis().getConnectionMinimumIdleSize())
                .setSubscriptionConnectionPoolSize(jHipsterProperties.getCache().getRedis().getSubscriptionConnectionPoolSize())
                .setAddress(jHipsterProperties.getCache().getRedis().getServer()[0]);

            if (redisUri.getUserInfo() != null) {
                singleServerConfig.setPassword(redisUri.getUserInfo().substring(redisUri.getUserInfo().indexOf(':') + 1));
            }
        }
        jcacheConfig.setStatisticsEnabled(true);
        jcacheConfig.setExpiryPolicyFactory(
            CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, jHipsterProperties.getCache().getRedis().getExpiration()))
        );
        return RedissonConfiguration.fromInstance(Redisson.create(config), jcacheConfig);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer(javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration) {
        return cm -> {
            createCache(cm, com.reactit.credit.score.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            createCache(cm, com.reactit.credit.score.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            createCache(cm, com.reactit.credit.score.domain.MemberUser.class.getName(), jcacheConfiguration);
            createCache(cm, com.reactit.credit.score.domain.MemberUser.class.getName() + ".addresses", jcacheConfiguration);
            createCache(cm, com.reactit.credit.score.domain.MemberUser.class.getName() + ".payments", jcacheConfiguration);
            createCache(cm, com.reactit.credit.score.domain.MemberUser.class.getName() + ".claims", jcacheConfiguration);
            createCache(cm, com.reactit.credit.score.domain.MemberUser.class.getName() + ".notifications", jcacheConfiguration);
            createCache(cm, com.reactit.credit.score.domain.MemberUser.class.getName() + ".contacts", jcacheConfiguration);
            createCache(cm, com.reactit.credit.score.domain.Address.class.getName(), jcacheConfiguration);
            createCache(cm, com.reactit.credit.score.domain.Contact.class.getName(), jcacheConfiguration);
            createCache(cm, com.reactit.credit.score.domain.Claim.class.getName(), jcacheConfiguration);
            createCache(cm, com.reactit.credit.score.domain.Product.class.getName(), jcacheConfiguration);
            createCache(cm, com.reactit.credit.score.domain.Product.class.getName() + ".payments", jcacheConfiguration);
            createCache(cm, com.reactit.credit.score.domain.Payment.class.getName(), jcacheConfiguration);
            createCache(cm, com.reactit.credit.score.domain.Payment.class.getName() + ".products", jcacheConfiguration);
            createCache(cm, com.reactit.credit.score.domain.Invoice.class.getName(), jcacheConfiguration);
            createCache(cm, com.reactit.credit.score.domain.Invoice.class.getName() + ".products", jcacheConfiguration);
            createCache(cm, com.reactit.credit.score.domain.Invoice.class.getName() + ".payments", jcacheConfiguration);
            createCache(cm, com.reactit.credit.score.domain.Agencies.class.getName(), jcacheConfiguration);
            createCache(cm, com.reactit.credit.score.domain.Agencies.class.getName() + ".contacts", jcacheConfiguration);
            createCache(cm, com.reactit.credit.score.domain.Agencies.class.getName() + ".addresses", jcacheConfiguration);
            createCache(cm, com.reactit.credit.score.domain.Banks.class.getName(), jcacheConfiguration);
            createCache(cm, com.reactit.credit.score.domain.Banks.class.getName() + ".contacts", jcacheConfiguration);
            createCache(cm, com.reactit.credit.score.domain.Banks.class.getName() + ".addresses", jcacheConfiguration);
            createCache(cm, com.reactit.credit.score.domain.Banks.class.getName() + ".agencies", jcacheConfiguration);
            createCache(cm, com.reactit.credit.score.domain.CreditRapport.class.getName(), jcacheConfiguration);
            createCache(cm, com.reactit.credit.score.domain.CreditRapport.class.getName() + ".invoices", jcacheConfiguration);
            createCache(cm, com.reactit.credit.score.domain.Notification.class.getName(), jcacheConfiguration);
            // jhipster-needle-redis-add-entry
        };
    }

    private void createCache(
        javax.cache.CacheManager cm,
        String cacheName,
        javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration
    ) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
