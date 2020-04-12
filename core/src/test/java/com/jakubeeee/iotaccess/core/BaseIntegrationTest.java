package com.jakubeeee.iotaccess.core;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
@SpringBootTest(
        classes = CoreApplicationEntryPoint.class
)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class
})
@AutoConfigureMockMvc
@ActiveProfiles("integration-test")
@TestPropertySource(locations = "classpath:core-integration-test.properties")
@PropertySource(value = "classpath:generated/testdatabase-generated.properties", ignoreResourceNotFound = true)
public abstract class BaseIntegrationTest {

    private static final boolean AUTOCONFIGURE_TEST_DATABASE = true;

    static {
        if (AUTOCONFIGURE_TEST_DATABASE)
            CustomPostgreSQL12Container.getInstance().start();
    }

    @BeforeAll
    static void setUp() {
        Awaitility.setDefaultPollInterval(1, SECONDS);
        Awaitility.setDefaultTimeout(30, SECONDS);
    }

}
