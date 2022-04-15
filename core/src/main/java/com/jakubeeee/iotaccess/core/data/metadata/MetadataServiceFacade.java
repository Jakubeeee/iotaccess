package com.jakubeeee.iotaccess.core.data.metadata;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class MetadataServiceFacade {

    private final Set<MetadataService<?>> metadataServices;

    public void deleteAll() {
        metadataServices.forEach(MetadataService::deleteAll);
    }

}
