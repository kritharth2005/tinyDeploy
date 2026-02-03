package com.tinydeploy.orchestrator.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class JenkinsService {

    @Value("${jenkins.url}")
    private String jenkinsUrl;

    @Value("${jenkins.username}")
    private String username;

    @Value("${jenkins.token}")
    private String token;

    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        this.restTemplate = new RestTemplate();

        this.restTemplate.getInterceptors().add(
                new BasicAuthenticationInterceptor(username, token)
        );
    }

    public String triggerBuild(String jobName) {
        String buildUrl = jenkinsUrl + "/job/" + jobName + "/build";

        System.out.println("Triggering Jenkins job: " + buildUrl);

        try {
            restTemplate.postForLocation(buildUrl, null);
            return "Successfully Triggered Job:" + jobName;
        }catch (Exception e) {
            throw new RuntimeException("Failed to trigger Jenkins build: " + e.getMessage());
        }
    }

}
