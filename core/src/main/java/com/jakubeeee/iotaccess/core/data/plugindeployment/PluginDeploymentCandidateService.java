package com.jakubeeee.iotaccess.core.data.plugindeployment;

import com.jakubeeee.iotaccess.core.data.DataService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class PluginDeploymentCandidateService implements DataService<PluginDeploymentCandidate> {

    private final PluginDeploymentCandidateRepository pluginDeploymentCandidateRepository;

    public Set<PluginDeploymentCandidate> findNotDeployed() {
        return pluginDeploymentCandidateRepository.findAllByDeployedFalse();
    }

    public void save(PluginDeploymentCandidate pluginDeploymentCandidate) {
        pluginDeploymentCandidateRepository.save(pluginDeploymentCandidate);
    }

    public void setDeployedTrue(@NonNull String jarName) {
        pluginDeploymentCandidateRepository.updateDeployed(true, jarName);
    }

}
