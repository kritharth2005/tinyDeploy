package com.tinydeploy.orchestrator.service;


import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Ports;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DockerService {

    private final DockerClient dockerClient;

    public String createContainer(String imageName) {
        try {
            dockerClient.pullImageCmd(imageName).start().awaitCompletion();
        }catch (InterruptedException e) {
            throw new RuntimeException("Error pulling image" + e);
        }

        ExposedPort tcp80 =  ExposedPort.tcp(80);
        Ports portBindings = new Ports();
        portBindings.bind(tcp80,Ports.Binding.bindPort(8085));

        CreateContainerResponse container = dockerClient.createContainerCmd(imageName)
                .withExposedPorts(tcp80)
                .withHostConfig(HostConfig.newHostConfig().withPortBindings(portBindings))
                .exec();

        dockerClient.startContainerCmd(container.getId()).exec();

        return container.getId();
    }

}
