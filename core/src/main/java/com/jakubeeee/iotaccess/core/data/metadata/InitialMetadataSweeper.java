package com.jakubeeee.iotaccess.core.data.metadata;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static com.jakubeeee.iotaccess.core.data.metadata.InitialMetadataSweeper.INITIAL_METADATA_SWEEPER_ORDER;

@Slf4j
@RequiredArgsConstructor
@Order(INITIAL_METADATA_SWEEPER_ORDER)
@Component
public class InitialMetadataSweeper implements ApplicationRunner {

    public static final int INITIAL_METADATA_SWEEPER_ORDER = 1;

    private final MetadataServiceFacade metadataServiceFacade;

    @Override
    public void run(ApplicationArguments args) {
        LOG.info("Sweeping all metadata from previous application runs");
        metadataServiceFacade.deleteAll();
        LOG.info("All metadata swept successfully");
    }

}
