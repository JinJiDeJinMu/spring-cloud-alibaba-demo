package com.hjb.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

    @Value("${elasticsearch.ips}")
    private String ips;

    private HttpHost makeHttpHost(String hosts) {

        String[] address = hosts.split(":");
        String ip = address[0];
        int port = Integer.parseInt(address[1]);

        return  new HttpHost(ip, port, "http");
    }

    @Bean
    public RestClientBuilder restClientBuilder() {
        //集群模式下(new HttpHost(HOST, PORT, SCHEMA),new HttpHost(),...)
        return RestClient.builder(makeHttpHost(ips));
    }


    @Bean(name = "highLevelClient")
    public RestHighLevelClient highLevelClient(@Autowired RestClientBuilder restClientBuilder) {
        //restClientBuilder.setMaxRetryTimeoutMillis(60000);
        return new RestHighLevelClient(restClientBuilder);
    }
}
