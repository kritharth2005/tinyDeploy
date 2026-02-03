package com.tinydeploy.orchestrator.controller;


import com.tinydeploy.orchestrator.service.DockerService;
import com.tinydeploy.orchestrator.service.JenkinsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/deploy")
public class DeployController {

    private final DockerService dockerService;

    private final JenkinsService jenkinsService;

    public DeployController(final DockerService dockerService, final JenkinsService jenkinsService) {
        this.dockerService = dockerService;
        this.jenkinsService = jenkinsService;
    }

    @PostMapping("/test")
    public String testDeployApp() {
        String containerId = dockerService.createContainer("nginx:alpine");
        return "Success! Container created with id: " + containerId;
    }

    @PostMapping("/build")
    public String startBuild() {
        return jenkinsService.triggerBuild("build-my-app");
    }

}
