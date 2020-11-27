package com.hjb.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

//@Configuration
public class RedissonConfig {

    @Value("${spring.redis.cluster.nodes}")
    private String redisUrl;

    @Bean
    public RedissonClient redissonClient(){

        String[] urls = redisUrl.split(",");
        List<String> clusterNodes = new ArrayList<>();
        for (int i = 0; i < urls.length; i++) {
            clusterNodes.add("redis://" + urls[i]);
        }
        Config config = new Config();
        ClusterServersConfig clusterServersConfig = config.useClusterServers()
                .addNodeAddress(clusterNodes.toArray(new String[clusterNodes.size()]));
        clusterServersConfig.setScanInterval(5000);
        return Redisson.create(config);
    }
}
