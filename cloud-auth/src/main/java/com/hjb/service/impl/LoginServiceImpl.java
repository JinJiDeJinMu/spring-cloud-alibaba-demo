package com.hjb.service.impl;

import com.hjb.service.LoginService;
import com.hjb.util.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Override
    public AuthToken login(String username, String password, String clientId, String clientSecret, String grandType) {
        String url =getUrl();

        MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization","Basic "+ Base64.getEncoder().encodeToString(new String(clientId+":"+clientSecret).getBytes()));
        MultiValueMap<String,String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type",grandType);
        formData.add("username",username);
        formData.add("password",password);

        HttpEntity<MultiValueMap> result = new HttpEntity<MultiValueMap>(formData,headers);

        ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.POST, result, Map.class);
        Map body = responseEntity.getBody();

        return getToken(body);
    }

    @Override
    public AuthToken refresh(String refresh_token, String clientId, String clientSecret, String grandType) {
        String url =getUrl();

        MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization","Basic "+ Base64.getEncoder().encodeToString(new String(clientId+":"+clientSecret).getBytes()));

        MultiValueMap<String,String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type",grandType);
        formData.add("refresh_token",refresh_token);

        HttpEntity<MultiValueMap> result = new HttpEntity<MultiValueMap>(formData,headers);

        ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.POST, result, Map.class);

        Map body = responseEntity.getBody();

        return getToken(body);
    }

    private AuthToken getToken(Map body){
        AuthToken authToken = new AuthToken();

        String accessToken = (String) body.get("access_token");
        String refreshToken = (String) body.get("refresh_token");
        String jwtToken= (String) body.get("jti");
        authToken.setJti(jwtToken);
        authToken.setAccessToken(accessToken);
        authToken.setRefreshToken(refreshToken);

        return authToken;
    }

    private String getUrl(){
        String url = "";
        ServiceInstance choose = loadBalancerClient.choose("service-auth");
        if(choose != null){
            url = choose.getUri().toString() + "/oauth/token";
        }
        return url;
    }
}
